package com.bajaj.internship.service;

import com.bajaj.internship.dto.AccessTokenResponse;
import com.bajaj.internship.dto.SubmissionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Alternative implementation using RestTemplate
 * You can switch between WebClient and RestTemplate by changing the @Service annotation
 */
//@Service
public class BajajApiServiceRestTemplate {
    
    private static final Logger logger = LoggerFactory.getLogger(BajajApiServiceRestTemplate.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${bajaj.api.base-url}")
    private String baseUrl;
    
    @Value("${bajaj.api.webhook-endpoint}")
    private String webhookEndpoint;
    
    @Value("${bajaj.registration.number}")
    private String registrationNumber;
    
    /**
     * Get access token using RestTemplate
     */
    public String getAccessToken() {
        try {
            logger.info("Requesting access token using RestTemplate...");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);
            
            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(
                    baseUrl + "/auth/token", // Placeholder endpoint
                    requestEntity,
                    AccessTokenResponse.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && 
                response.getBody() != null && 
                response.getBody().getAccessToken() != null) {
                
                logger.info("Successfully obtained access token using RestTemplate");
                return response.getBody().getAccessToken();
            } else {
                logger.error("Failed to obtain access token - invalid response");
                throw new RuntimeException("Failed to obtain access token");
            }
            
        } catch (Exception e) {
            logger.error("Error getting access token using RestTemplate: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get access token", e);
        }
    }
    
    /**
     * Submit SQL query using RestTemplate
     */
    public void submitSqlQuery(String accessToken, String sqlQuery) {
        try {
            logger.info("Submitting SQL query using RestTemplate...");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, accessToken);
            
            SubmissionRequest request = new SubmissionRequest(sqlQuery);
            HttpEntity<SubmissionRequest> requestEntity = new HttpEntity<>(request, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + webhookEndpoint,
                    requestEntity,
                    String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("SQL query submitted successfully using RestTemplate. Response: {}", 
                           response.getBody());
            } else {
                logger.error("Failed to submit SQL query. Status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to submit SQL query");
            }
            
        } catch (Exception e) {
            logger.error("Error submitting SQL query using RestTemplate: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit SQL query", e);
        }
    }
    
    /**
     * Same logic as WebClient version
     */
    public String getSqlQueryBasedOnRegNumber() {
        try {
            String regNumStr = registrationNumber.replaceAll("[^0-9]", "");
            if (regNumStr.isEmpty()) {
                throw new IllegalArgumentException("Registration number must contain digits");
            }
            
            int lastTwoDigits = Integer.parseInt(regNumStr.substring(Math.max(0, regNumStr.length() - 2)));
            boolean isOdd = (lastTwoDigits % 2) != 0;
            
            logger.info("Registration number: {}, Last two digits: {}, Is odd: {}", 
                       registrationNumber, lastTwoDigits, isOdd);
            
            if (isOdd) {
                return "SELECT * FROM employees WHERE department = 'IT' ORDER BY salary DESC LIMIT 10;";
            } else {
                return "SELECT department, COUNT(*) as employee_count FROM employees GROUP BY department HAVING COUNT(*) > 5;";
            }
            
        } catch (Exception e) {
            logger.error("Error determining SQL query: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to determine SQL query based on registration number", e);
        }
    }
}