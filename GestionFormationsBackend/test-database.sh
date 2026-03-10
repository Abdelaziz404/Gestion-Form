#!/bin/bash

# Database Initialization Test Script
echo "=== Gestion Formations Backend - Database Test ==="
echo "Starting application to initialize database..."

# Start the Spring Boot application
echo "Starting Spring Boot application..."
./mvnw spring-boot:run &

# Store the process ID
APP_PID=$!

# Wait for the application to start
echo "Waiting for application to start..."
sleep 30

# Test endpoints
echo "Testing database initialization..."

# Test data summary
echo "1. Testing data summary endpoint:"
curl -s http://localhost:8080/api/test/data-summary | jq '.'

echo -e "\n2. Testing detailed data endpoint:"
curl -s http://localhost:8080/api/test/test-data

echo -e "\n3. Testing authentication endpoints:"

# Test admin login
echo -e "\nAdmin login test:"
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@test.com","password":"admin123"}' | jq '.'

# Test formateur login
echo -e "\nFormateur login test:"
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"jean.dupont@test.com","password":"formateur123"}' | jq '.'

# Test participant login
echo -e "\nParticipant login test:"
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"pierre.durand@test.com","password":"participant123"}' | jq '.'

echo -e "\n=== Test Complete ==="

# Stop the application
echo "Stopping application..."
kill $APP_PID