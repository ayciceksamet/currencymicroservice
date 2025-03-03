package com.codeshare.springboot.microservice.example.exchangerate.implemantation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.codeshare.springboot.microservice.example.exchangerate.cache.ERCache;
import com.codeshare.springboot.microservice.example.exchangerate.model.ERModel;
import com.codeshare.springboot.microservice.example.exchangerate.model.ERConvertionRateResponseModel;
import com.codeshare.springboot.microservice.example.exchangerate.model.ERErrorModel;

@Service
public class ERRestServiceImpl implements IERRestService{


	@Autowired
    private Environment env;

	@Autowired
	private ERCache cache;
	
	private static String transactionID;

	@Override
	public ERConvertionRateResponseModel getExchangeRate(String ratePair) {
		
		ERConvertionRateResponseModel response = new ERConvertionRateResponseModel();
		
		BigDecimal cachedRate = cache.getCachedRate(ratePair);
		if(null != cachedRate) {
			response.setRateValue(cachedRate);
			response.setError(null);
			System.out.println("Returing data for " + ratePair + " from Cache");
			return response;
		}
		
		RestTemplate restTemplate = new RestTemplate();		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("currency.api.base.uri"))
		        .queryParam("access_key", env.getProperty("api.key"))
		        .queryParam("source", ratePair.substring(0, 3))
		        .queryParam("format", "1");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<ERModel> responseEnt = restTemplate.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        ERModel.class);
		
		ERModel rates = responseEnt.getBody();
		
		HttpHeaders headersResponse = responseEnt.getHeaders();
		
		String transactionID = headersResponse.getFirst("X-Apilayer-Transaction-Id");
		
		this.transactionID = transactionID;
		
		String toPair = ratePair.replaceAll(ratePair.substring(0, 3),"");
		
	    String result = rates.getQuotes().entrySet().stream()
	    		.filter(map -> toPair.equals(map.getKey().replaceAll(ratePair.substring(0, 3),"")))
	    		.map(map -> map.getValue())
	    		.collect(Collectors.joining());
	    
	    if("".equalsIgnoreCase(result) || "1".equalsIgnoreCase(result)) {
	    	ERErrorModel errormodel = new ERErrorModel();
	    	errormodel.setCode("400");
	    	errormodel.setType("Bad Request");
	    	errormodel.setInfo("Please enter valid currency pair value !");
	    	response.setError(errormodel);
	    	response.setRateValue(BigDecimal.ZERO);
	    	return response;
	    }
	    
	    if (rates.getSuccess()) {
	    	String exRate = result;
	    	BigDecimal bgExRate = new BigDecimal(exRate);
	    	response.setRateValue(bgExRate);
	    	cache.cacheRate(ratePair, bgExRate);
	    } else {
	    	response.setError(rates.getError());
	    	response.setRateValue(BigDecimal.ZERO);
	    }
	    System.out.println("Returing data for " + ratePair + " from service");
        return response;
		
	}

	public static String getTransactionID() {
		return transactionID;
	}

	public static void setTransactionID(String transactionID) {
		ERRestServiceImpl.transactionID = transactionID;
	}

	@Override
	public String ERgetTransactionID() {
		return getTransactionID();
	}

	

}
