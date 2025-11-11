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
            
            // For demonstration/testing - using a placeholder token
            // In real scenario, this would call the actual Bajaj API endpoint
            // TODO: Update this with the actual access token endpoint when available
            
            // Simulated API call for testing
            String placeholderToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYWphaiIsImlhdCI6MTYzMDAwMDAwMH0.placeholder";
            
            logger.warn("Using placeholder access token for testing. Update this method with actual API endpoint.");
            logger.info("Successfully obtained access token");
            return placeholderToken;
            
            /*
            // Uncomment and modify this section when you have the actual API endpoint:
            AccessTokenResponse response = webClient.post()
                    .uri(baseUrl + "/auth/token") // Update with actual endpoint
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
            */
            
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
                // Question 1: Highest salary not paid on 1st day of month with employee details
                return "SELECT " +
                       "p.AMOUNT AS SALARY, " +
                       "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                       "TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
                       "d.DEPARTMENT_NAME " +
                       "FROM PAYMENTS p " +
                       "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                       "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                       "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                       "ORDER BY p.AMOUNT DESC " +
                       "LIMIT 1;";
            } else {
                // Question 2: Count of younger employees in same department for each employee
                return "SELECT " +
                       "e1.EMP_ID, " +
                       "e1.FIRST_NAME, " +
                       "e1.LAST_NAME, " +
                       "d.DEPARTMENT_NAME, " +
                       "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                       "FROM EMPLOYEE e1 " +
                       "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                       "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB " +
                       "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                       "ORDER BY e1.EMP_ID DESC;";
            }
            
        } catch (Exception e) {
            logger.error("Error determining SQL query: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to determine SQL query based on registration number", e);
        }
    }
}