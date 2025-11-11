package com.bajaj.internship.runner;

import com.bajaj.internship.service.BajajApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiExecutionRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiExecutionRunner.class);
    
    @Autowired
    private BajajApiService bajajApiService;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Bajaj Internship API execution...");
        
        try {
            // Step 1: Get access token
            logger.info("Step 1: Obtaining access token...");
            String accessToken = bajajApiService.getAccessToken();
            logger.info("Access token obtained successfully");
            
            // Step 2: Determine SQL query based on registration number
            logger.info("Step 2: Determining SQL query based on registration number...");
            String sqlQuery = bajajApiService.getSqlQueryBasedOnRegNumber();
            logger.info("SQL Query determined: {}", sqlQuery);
            
            // Step 3: Submit the SQL query
            logger.info("Step 3: Submitting SQL query...");
            bajajApiService.submitSqlQuery(accessToken, sqlQuery);
            logger.info("SQL query submitted successfully");
            
            logger.info("Bajaj Internship API execution completed successfully!");
            
        } catch (Exception e) {
            logger.error("Error during API execution: {}", e.getMessage(), e);
            throw new RuntimeException("API execution failed", e);
        }
    }
}