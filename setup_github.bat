@echo off
echo ========================================
echo  Bajaj Internship Task - GitHub Setup
echo ========================================
echo.

echo Step 1: Initialize Git Repository
git init
echo.

echo Step 2: Add all files
git add .
echo.

echo Step 3: Create initial commit
git commit -m "Initial commit: Bajaj internship Spring Boot task"
echo.

echo Step 4: Set up GitHub repository
echo Please create a repository on GitHub first, then enter the details:
echo.
set /p username="Enter your GitHub username: "
set /p reponame="Enter your repository name (e.g., bajaj-internship-task): "
echo.

echo Step 5: Add remote and push
git remote add origin https://github.com/%username%/%reponame%.git
git branch -M main
git push -u origin main
echo.

echo ========================================
echo  Setup Complete!
echo ========================================
echo.
echo Next steps:
echo 1. Go to: https://github.com/%username%/%reponame%
echo 2. Click 'Code' button ^> 'Codespaces' tab
echo 3. Click 'Create codespace on main'
echo 4. Wait for automatic setup to complete
echo 5. Update your registration number in application.properties
echo 6. Update SQL queries in BajajApiService.java
echo 7. Run: mvn clean package
echo 8. JAR will be at: target/internship-task-1.0.0.jar
echo.
pause