@echo off
REM Database Initialization Test Script for Windows

echo === Gestion Formations Backend - Database Test ===
echo Starting application to initialize database...

REM Start the Spring Boot application
echo Starting Spring Boot application...
start "SpringBootApp" cmd /k "mvnw spring-boot:run"

REM Wait for the application to start
echo Waiting for application to start...
timeout /t 30 /nobreak >nul

echo Testing database initialization...

REM Test data summary
echo 1. Testing data summary endpoint:
curl -s http://localhost:8080/api/test/data-summary
echo.

echo 2. Testing detailed data endpoint:
curl -s http://localhost:8080/api/test/test-data
echo.

echo 3. Testing authentication endpoints:

REM Test admin login
echo Admin login test:
curl -s -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"admin@test.com\",\"password\":\"admin123\"}"
echo.

REM Test formateur login
echo Formateur login test:
curl -s -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"jean.dupont@test.com\",\"password\":\"formateur123\"}"
echo.

REM Test participant login
echo Participant login test:
curl -s -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"pierre.durand@test.com\",\"password\":\"participant123\"}"
echo.

echo === Test Complete ===
echo Press any key to close...
pause >nul