package com.codeshare.springboot.microservice.example.exchangerate.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeshare.springboot.microservice.example.exchangerate.implemantation.IERRestService;
import com.codeshare.springboot.microservice.example.exchangerate.model.ERConvertionRateResponseModel;

@RestController
public class ERController {
	
	@Autowired 
	IERRestService execution;
	
	@RequestMapping(value = "/exchange/ping", method = RequestMethod.GET)
	public String getPing(){
		
		return "Exchange Service id IDLE!";
	}
	
	@RequestMapping(value = "/exchangerate/{currencypair}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<ERConvertionRateResponseModel> getRate(@PathVariable String currencypair) {

		HttpStatus status = HttpStatus.OK;

		ERConvertionRateResponseModel rm = execution.getExchangeRate(currencypair);
		
		 HttpHeaders responseHeaders = new HttpHeaders();
		 responseHeaders.set("Transaction-ID", execution.ERgetTransactionID());
		
		if(rm.getError()!=null && "400".equalsIgnoreCase(rm.getError().getCode())) {
			return new ResponseEntity<ERConvertionRateResponseModel>(rm, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ERConvertionRateResponseModel>(rm,responseHeaders,status);
	}

}
