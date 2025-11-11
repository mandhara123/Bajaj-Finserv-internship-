# Setup Guide for Bajaj Internship Task

## Prerequisites Setup

### 1. Install Java 17 or Higher
Download and install Java 17 from:
- **Oracle JDK**: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- **OpenJDK**: https://adoptium.net/temurin/releases/?version=17

### 2. Verify Java Installation
```cmd
java -version
```
Should show Java 17 or higher.

### 3. Set JAVA_HOME Environment Variable
- **Windows**: 
  1. Open System Properties → Advanced → Environment Variables
  2. Add new system variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-17.x.x`
  3. Add to PATH: `%JAVA_HOME%\bin`

## Building the Application

### Option 1: Using Maven Wrapper (Recommended)
```cmd
cd e:\bajaj_internship
.\mvnw.cmd clean package
```

### Option 2: Using Local Maven Installation
If you have Maven installed:
```cmd
cd e:\bajaj_internship
mvn clean package
```

### Option 3: Using IDE
1. Open the project in IntelliJ IDEA or Eclipse
2. Import as Maven project
3. Run: `Maven → Lifecycle → package`

## Configuration Steps

### 1. Update Registration Number
Edit `src\main\resources\application.properties`:
```properties
bajaj.registration.number=YOUR_ACTUAL_REG_NUMBER
```

### 2. Update SQL Queries (Important!)
Edit `BajajApiService.java` and replace the placeholder SQL queries:
```java
if (isOdd) {
    // Replace with Question 1 SQL from Google Drive
    return "YOUR_QUESTION_1_SQL_HERE";
} else {
    // Replace with Question 2 SQL from Google Drive  
    return "YOUR_QUESTION_2_SQL_HERE";
}
```

### 3. Update API Endpoints (If needed)
Check and update the endpoint for getting access token in `BajajApiService.java`:
```java
.uri(baseUrl + "/auth/token") // Update this endpoint
```

## Running the Application

### After Successful Build
```cmd
java -jar target\internship-task-1.0.0.jar
```

## Expected Output
```
Starting Bajaj Internship API execution...
Step 1: Obtaining access token...
Access token obtained successfully
Step 2: Determining SQL query based on registration number...
SQL Query determined: [YOUR_SQL_QUERY]
Step 3: Submitting SQL query...
SQL query submitted successfully
Bajaj Internship API execution completed successfully!
```

## Troubleshooting

### Java Issues
- Ensure Java 17+ is installed and JAVA_HOME is set
- Check PATH includes `%JAVA_HOME%\bin`

### Build Issues
- Ensure internet connection for Maven dependencies
- Clear Maven cache: Delete `.m2\repository` folder in user home

### API Issues
- Check registration number format
- Verify API endpoints are correct
- Ensure SQL queries are properly formatted

## Submission Checklist
- [ ] Java 17+ installed
- [ ] Project builds successfully (`.\mvnw.cmd clean package`)
- [ ] Registration number updated in application.properties
- [ ] SQL queries updated based on Google Drive documents
- [ ] JAR file created in `target\internship-task-1.0.0.jar`
- [ ] Application runs without errors
- [ ] Upload to GitHub repository
- [ ] Submit GitHub link and JAR download link