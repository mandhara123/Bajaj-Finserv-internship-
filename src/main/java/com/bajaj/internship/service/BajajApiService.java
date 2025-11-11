package com.bajaj.internship.service;

import com.bajaj.internship.dto.AccessTokenResponse;
import com.bajaj.internship.dto.SubmissionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BajajApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(BajajApiService.class);
    
    @Autowired
    private WebClient webClient;
    
    @Value("${bajaj.api.base-url}")
    private String baseUrl;
    
    @Value("${bajaj.api.webhook-endpoint}")
    private String webhookEndpoint;
    
    @Value("${bajaj.registration.number}")
    private String registrationNumber;
    
    /**
     * Get access token from the Bajaj API
     * This method should be called first to get the JWT token
     */
    public String getAccessToken() {
        try {
            logger.info("Requesting access token from Bajaj API...");
            
            // Note: You may need to adjust the endpoint and request body based on actual API documentation
            // This is a placeholder implementation
            AccessTokenResponse response = webClient.post()
                    .uri(baseUrl + "/auth/token") // Placeholder endpoint
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue("{}") // Add required request body here
                    .retrieve()
                    .bodyToMono(AccessTokenResponse.class)
                    .block();
            
            if (response != null && response.getAccessToken() != null) {
                logger.info("Successfully obtained access token");
                return response.getAccessToken();
            } else {
                logger.error("Failed to obtain access token - empty response");
                throw new RuntimeException("Failed to obtain access token");
            }
            
        } catch (Exception e) {
            logger.error("Error getting access token: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get access token", e);
        }
    }
    
    /**
     * Submit the final SQL query to the Bajaj API
     */
    public void submitSqlQuery(String accessToken, String sqlQuery) {
        try {
            logger.info("Submitting SQL query to Bajaj API...");
            
            SubmissionRequest request = new SubmissionRequest(sqlQuery);
            
            String response = webClient.post()
                    .uri(baseUrl + webhookEndpoint)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            logger.info("SQL query submitted successfully. Response: {}", response);
            
        } catch (Exception e) {
            logger.error("Error submitting SQL query: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit SQL query", e);
        }
    }
    
    /**
     * Determine the SQL query based on registration number parity
     */
    public String getSqlQueryBasedOnRegNumber() {
        try {
            // Extract last two digits
            String regNumStr = registrationNumber.replaceAll("[^0-9]", "");
            if (regNumStr.isEmpty()) {
                throw new IllegalArgumentException("Registration number must contain digits");
            }
            
            int lastTwoDigits = Integer.parseInt(regNumStr.substring(Math.max(0, regNumStr.length() - 2)));
            boolean isOdd = (lastTwoDigits % 2) != 0;
            
            logger.info("Registration number: {}, Last two digits: {}, Is odd: {}", 
                       registrationNumber, lastTwoDigits, isOdd);
            
            if (isOdd) {
                // Question 1 SQL Query - Update this based on the actual question
                return "SELECT * FROM employees WHERE department = 'IT' ORDER BY salary DESC LIMIT 10;";
            } else {
                // Question 2 SQL Query - Update this based on the actual question  
                return "SELECT department, COUNT(*) as employee_count FROM employees GROUP BY department HAVING COUNT(*) > 5;";
            }
            
        } catch (Exception e) {
            logger.error("Error determining SQL query: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to determine SQL query based on registration number", e);
        }
    }
}