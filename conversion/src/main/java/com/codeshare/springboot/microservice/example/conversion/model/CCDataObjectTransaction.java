package com.codeshare.springboot.microservice.example.conversion.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CCDataObjectTransaction {
	
	@Id
	@GeneratedValue
	private Long id;
	private String transactionid;
	private BigDecimal amount;
	private Long date;
	
	public String getTransactionID() {
		return transactionid;
	}
	public void setTransactionID(String transactionid) {
		this.transactionid = transactionid;
	}
	public BigDecimal getAmountValue() {
		return amount;
	}
	public void setAmountValue(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
