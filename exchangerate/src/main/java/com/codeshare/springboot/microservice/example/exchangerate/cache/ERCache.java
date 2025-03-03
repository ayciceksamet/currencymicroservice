package com.codeshare.springboot.microservice.example.exchangerate.cache;

import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component
public class ERCache {

	@Value("${er.cache.expiry}")
	private int CACHE_DURATION;

	private Cache<String, BigDecimal> cache;

	@PostConstruct
	public void init() {
		if (cache == null) {					
			cache = CacheBuilder.newBuilder().expireAfterWrite(CACHE_DURATION, TimeUnit.MINUTES).build();
		}
	}

	public BigDecimal getCachedRate(String code) {
		return cache.getIfPresent(code);
	}

	public void cacheRate(String code, BigDecimal rate) {
		cache.put(code, rate);
	}
}
