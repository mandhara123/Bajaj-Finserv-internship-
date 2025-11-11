# GitHub Codespaces Setup Guide

## Quick Start with GitHub Codespaces

### Step 1: Push to GitHub Repository

1. Create a new repository on GitHub (e.g., `bajaj-internship-task`)
2. Push this code to your repository:

```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit files
git commit -m "Initial commit: Bajaj internship Spring Boot task"

# Add your GitHub repository as remote
git remote add origin https://github.com/YOUR_USERNAME/bajaj-internship-task.git

# Push to GitHub
git push -u origin main
```

### Step 2: Open in GitHub Codespaces

1. Go to your GitHub repository
2. Click the **Code** button (green button)
3. Select **Codespaces** tab
4. Click **Create codespace on main**

### Step 3: Automatic Setup

The codespace will automatically:
- Set up Java 17
- Install Maven
- Configure VS Code extensions for Java development
- Be ready to build and run your project

### Step 4: Configure Your Project

1. **Update Registration Number**:
   ```bash
   # Edit the application.properties file
   code src/main/resources/application.properties
   ```
   Replace `YOUR_REG_NUMBER_HERE` with your actual registration number.

2. **Update SQL Queries**:
   ```bash
   # Edit the service file
   code src/main/java/com/bajaj/internship/service/BajajApiService.java
   ```
   Replace the placeholder SQL queries with actual ones from Google Drive.

### Step 5: Build and Test

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/internship-task-1.0.0.jar
```

### Step 6: Download JAR File

After successful build, your JAR file will be available at:
- **Location**: `target/internship-task-1.0.0.jar`
- **GitHub Actions**: Automatic builds will create releases with downloadable JAR files
- **Raw Download Link**: `https://github.com/YOUR_USERNAME/bajaj-internship-task/releases/download/v1.0.0-X/internship-task-1.0.0.jar`

## Benefits of Using GitHub Codespaces

✅ **Pre-configured Environment**: Java 17 and Maven ready  
✅ **No Local Setup Required**: Everything runs in the cloud  
✅ **Automatic Builds**: GitHub Actions builds JAR on every push  
✅ **Easy Sharing**: Public repository with downloadable releases  
✅ **VS Code Integration**: Full IDE experience in browser  

## Commands in Codespaces

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Build project
mvn clean package

# Run application
java -jar target/internship-task-1.0.0.jar

# Clean and rebuild
mvn clean compile package

# Run with Maven
mvn spring-boot:run
```

## Troubleshooting

### If Build Fails
```bash
# Clean Maven cache
rm -rf ~/.m2/repository

# Rebuild
mvn clean package -U
```

### If Java Issues
```bash
# Check JAVA_HOME
echo $JAVA_HOME

# List Java versions
ls /usr/local/sdkman/candidates/java/
```

## Submission Links

After pushing to GitHub and successful build:

1. **GitHub Repository**: `https://github.com/YOUR_USERNAME/bajaj-internship-task`
2. **JAR Download**: Go to Releases section for direct JAR download
3. **Raw JAR Link**: Use the direct link from GitHub releases

## Example Repository Structure

```
bajaj-internship-task/
├── .devcontainer/
│   └── devcontainer.json          # Codespaces configuration
├── .github/
│   ├── workflows/
│   │   └── build.yml              # Automatic JAR building
│   └── copilot-instructions.md
├── src/main/java/...              # Your Java source code
├── target/
│   └── internship-task-1.0.0.jar  # Built JAR file
├── pom.xml                        # Maven configuration
├── README.md                      # Project documentation
└── SETUP_GUIDE.md                 # Setup instructions
```

This setup gives you a professional development environment with automatic building and easy JAR distribution!