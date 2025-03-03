package com.codeshare.springboot.microservice.example.conversion.repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.codeshare.springboot.microservice.example.conversion.model.CCConversionList;
import com.codeshare.springboot.microservice.example.conversion.model.CCConvertionAmountResponseModel;
import com.codeshare.springboot.microservice.example.conversion.model.CCDataObjectTransaction;
@Repository
public class CCRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    class ConversionMapper implements RowMapper < CCDataObjectTransaction > {
        @Override
        public CCDataObjectTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        	CCDataObjectTransaction model = new CCDataObjectTransaction();
        	model.setAmountValue(rs.getBigDecimal("amount"));
        	model.setTransactionID(rs.getString("transactionid"));
            return model;
        }
    }
    
    public List < CCDataObjectTransaction > findAll() {
        return jdbcTemplate.query("select * from conversion", new ConversionMapper());
    }
    
    public CCDataObjectTransaction findByTransactionID(String id) {
        return jdbcTemplate.queryForObject("select distinct transactionid   from conversion where transactionid=?", new Object[] {
        		id
            },
            new BeanPropertyRowMapper < CCDataObjectTransaction > (CCDataObjectTransaction.class));
    }
    
    public List<CCDataObjectTransaction> findByAllTransactionID(String id) {

    	return  jdbcTemplate.query("select * from conversion a where transactionid=?",
    			 new BeanPropertyRowMapper<CCDataObjectTransaction>(CCDataObjectTransaction.class),id);
    	
    }
    
    public List<CCDataObjectTransaction> findByTransactionDate(String date) {
    	return  jdbcTemplate.query("select * from conversion a where date=?",
   			 new BeanPropertyRowMapper<CCDataObjectTransaction>(CCDataObjectTransaction.class),date);
    }
    
    public int deleteById(String id) {
        return jdbcTemplate.update("delete from conversion where transactionid=?", new Object[] {
            id
        });
    }
    public int insert(CCDataObjectTransaction model) {
        return jdbcTemplate.update("insert into conversion (id, transactionid, amount, date) " + "values(?, ?,  ?, ?)",
            new Object[] {
            		model.getId(), model.getTransactionID(), model.getAmountValue(), model.getDate()
            });
    }
}
