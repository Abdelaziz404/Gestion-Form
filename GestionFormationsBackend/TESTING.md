# Database Initialization Test

This project includes a database initialization script that automatically creates test data when the application starts for the first time.

## Test Data Created

The `DatabaseInitializer` service creates the following test data:

### Users
- **Admin**: admin@test.com / admin123
- **Formateurs**: 
  - jean.dupont@test.com / formateur123 (Java Development)
  - marie.martin@test.com / formateur123 (Web Development)
- **Participants**:
  - pierre.durand@test.com / participant123
  - sophie.bernard@test.com / participant123
  - luc.petit@test.com / participant123

### Course Data
- 3 Formations with different topics and prices
- 3 Inscriptions linking participants to formations
- 2 Seances (class sessions) scheduled for formations

## Testing Instructions

### Method 1: Using the Test Scripts

**Windows:**
```cmd
test-database.bat
```

**Linux/Mac:**
```bash
chmod +x test-database.sh
./test-database.sh
```

### Method 2: Manual Testing

1. **Start the application:**
   ```cmd
   mvnw spring-boot:run
   ```

2. **Check data summary:**
   ```
   GET http://localhost:8080/api/test/data-summary
   ```

3. **View detailed test data:**
   ```
   GET http://localhost:8080/api/test/test-data
   ```

4. **Test authentication:**
   ```
   POST http://localhost:8080/api/auth/login
   Content-Type: application/json
   
   {
     "email": "admin@test.com",
     "password": "admin123"
   }
   ```

## Test Credentials

| User Type | Email | Password |
|-----------|-------|----------|
| Admin | admin@test.com | admin123 |
| Formateur | jean.dupont@test.com | formateur123 |
| Participant | pierre.durand@test.com | participant123 |

## What Gets Tested

1. **Database Connection**: Verifies the application can connect to the database
2. **Entity Creation**: Tests creation of all entity types
3. **Relationships**: Verifies foreign key relationships work correctly
4. **Authentication**: Tests login functionality for all user types
5. **Data Retrieval**: Tests API endpoints for data access

The initialization only runs once - if data already exists in the database, it will skip the initialization process.