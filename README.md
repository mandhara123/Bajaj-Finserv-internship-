# Bajaj Internship Task - Spring Boot API Integration

## Overview
This Spring Boot application integrates with Bajaj API to:
1. Obtain an access token
2. Determine SQL query based on registration number parity (odd/even)
3. Submit the SQL query using JWT authentication

## Features
- **No Controller Required**: Uses CommandLineRunner to execute the flow
- **Dual HTTP Client Support**: Both WebClient and RestTemplate implementations
- **JWT Authentication**: Uses access token in Authorization header
- **Registration-based Logic**: SQL query selection based on last two digits of registration number

## Project Structure
```
src/main/java/com/bajaj/internship/
├── InternshipTaskApplication.java          # Main Spring Boot application
├── config/
│   └── HttpClientConfig.java               # HTTP client configuration
├── dto/
│   ├── AccessTokenResponse.java            # Access token response DTO
│   └── SubmissionRequest.java              # Submission request DTO
├── service/
│   ├── BajajApiService.java               # WebClient implementation
│   └── BajajApiServiceRestTemplate.java   # RestTemplate implementation
└── runner/
    └── ApiExecutionRunner.java            # CommandLineRunner implementation
```

## Configuration
Update `src/main/resources/application.properties`:

```properties
# Replace with your actual registration number
bajaj.registration.number=YOUR_REG_NUMBER_HERE

# API Configuration (default values)
bajaj.api.base-url=https://bfhldevapigw.healthrx.co.in
bajaj.api.webhook-endpoint=/hiring/testWebhook/JAVA
```

## SQL Query Logic
- **Odd last two digits**: Question 1 SQL query
- **Even last two digits**: Question 2 SQL query

## Quick Start Options

### Option 1: GitHub Codespaces (Recommended - No Local Setup Required!)

1. **Push to GitHub**: Run `setup_github.bat` or manually push this code to your GitHub repository
2. **Open Codespaces**: Go to your repository → Code → Codespaces → Create codespace
3. **Automatic Setup**: Java 17 and Maven will be pre-configured
4. **Build**: `mvn clean package`
5. **Download JAR**: Available at `target/internship-task-1.0.0.jar`

See `GITHUB_CODESPACES_GUIDE.md` for detailed instructions.

### Option 2: Local Development

#### Prerequisites
- Java 17 or higher
- Internet connection (for Maven dependencies)

#### Build JAR (Windows)
```cmd
.\mvnw.cmd clean package
```

#### Build JAR (Linux/Mac)
```bash
./mvnw clean package
```

#### Run Application
```cmd
java -jar target/internship-task-1.0.0.jar
```

**Note**: See `SETUP_GUIDE.md` for detailed local setup instructions.

## API Implementation Notes
1. **Access Token Endpoint**: Currently uses placeholder `/auth/token` - update based on actual API documentation
2. **SQL Queries**: Default placeholder queries are provided - update with actual SQL from Google Drive documents
3. **Error Handling**: Comprehensive exception handling with detailed logging

## Switching HTTP Clients
To use RestTemplate instead of WebClient:
1. Comment the `@Service` annotation on `BajajApiService`
2. Uncomment the `@Service` annotation on `BajajApiServiceRestTemplate`

## Output
The application will execute the following flow:
1. Log startup and configuration details
2. Request access token from Bajaj API
3. Determine SQL query based on registration number
4. Submit SQL query with JWT authorization
5. Log completion status

## Important Notes
- **Update Registration Number**: Set your actual registration number in `application.properties`
- **Update SQL Queries**: Replace placeholder queries with actual ones from Google Drive documents
- **Update API Endpoints**: Verify and update API endpoints based on official documentation
- **JAR Location**: Built JAR will be available at `target/internship-task-1.0.0.jar`

## Troubleshooting
- Check logs for detailed error information
- Verify registration number format and API endpoints
- Ensure proper internet connectivity for API calls
- Validate JWT token format and expiration