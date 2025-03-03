package com.codeshare.springboot.microservice.example.conversion.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CCModel {
	
	public CCModel() {
		// TODO Auto-generated constructor stub
	}
	
	public CCModel(CCErrorModel error,BigDecimal rateValue) {
		super();
		this.error = error;
		this.rateValue = rateValue;
	}
	
	private CCErrorModel error;

	private BigDecimal rateValue;
	/*
	@Id
	private Long id;
	
	@Column(name="source_currency")
	private String srcCurrency;
	
	@Column(name="targer_currency")
	private String trgtCurrency;
	
	@Column(name="exchange_rate")
	private BigDecimal exchangeRate;
	
	@Column(name="source_amount")
	private BigDecimal sourceAmount;
	
	@Column(name="total_calculated_amount")
	private BigDecimal totalCalculatedAmount;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSrcCurrency() {
		return srcCurrency;
	}
	public void setSrcCurrency(String srcCurrency) {
		this.srcCurrency = srcCurrency;
	}
	public String getTrgtCurrency() {
		return trgtCurrency;
	}
	public void setTrgtCurrency(String trgtCurrency) {
		this.trgtCurrency = trgtCurrency;
	}
	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(BigDecimal sourceAmount) {
		this.sourceAmount = sourceAmount;
	}
	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}
	public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	*/

	public CCErrorModel getError() {
		return error;
	}

	public void setError(CCErrorModel error) {
		this.error = error;
	}

	public BigDecimal getrateValue() {
		return rateValue;
	}

	public void setrateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}
	
}
