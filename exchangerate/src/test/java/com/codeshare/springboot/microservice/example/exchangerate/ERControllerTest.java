package com.codeshare.springboot.microservice.example.exchangerate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.codeshare.springboot.microservice.example.exchangerate.controller.ERController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ERControllerTest 
{
    @Autowired
    ERController ercontroller;
    
    @Autowired
    private MockMvc mockMvc;
    

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testERService_WrongURLPathAnd404NotFound() throws Exception 
    {
    	  mockMvc.perform(get("/exchangerate/"))
          .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testERService_WithBadCredentials() throws Exception 
    {
    	  mockMvc.perform(get("/exchangerate/USDXXX"))
          .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testERService_WithMissingCredentials() throws Exception 
    {
    	  mockMvc.perform(get("/exchangerate/USD"))
          .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testERService_WithProperExchangeToValue() throws Exception 
    {
    	  mockMvc.perform(get("/exchangerate/USDALL"))
          .andExpect(status().isOk());
    }
    
    @Test
    public void testERService_WithProperToValueAndNumberResult() throws Exception 
    {
    	  mockMvc.perform(get("/exchangerate/USDALL"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.rateValue").isNumber());
    }
     
    
}