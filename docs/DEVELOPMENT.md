# Development Setup Guide

## Prerequisites

1. **Java Development Kit (JDK) 17+**
   ```bash
   # On macOS with Homebrew
   brew install openjdk@17
   
   # Verify installation
   java -version
   ```

2. **Node.js 18+**
   ```bash
   # On macOS with Homebrew
   brew install node
   
   # Verify installation
   node --version
   npm --version
   ```

3. **PostgreSQL 14+**
   ```bash
   # On macOS with Homebrew
   brew install postgresql@14
   brew services start postgresql@14
   
   # Create database
   createdb personal_finance_db
   ```

4. **Maven (Optional - Spring Boot includes Maven Wrapper)**
   ```bash
   # On macOS with Homebrew
   brew install maven
   ```

## Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Update database configuration:**
   - Open `src/main/resources/application.properties`
   - Update database URL, username, and password as needed

3. **Run the application:**
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw spring-boot:run
   
   # Or using installed Maven
   mvn spring-boot:run
   ```

4. **Verify backend is running:**
   - Open http://localhost:8080/swagger-ui.html
   - You should see the API documentation

## Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm start
   ```

4. **Verify frontend is running:**
   - Open http://localhost:3000
   - You should see the welcome page

## Database Setup

1. **Connect to PostgreSQL:**
   ```bash
   psql -d personal_finance_db
   ```

2. **The application will automatically create tables on first run thanks to Hibernate's `ddl-auto=update` setting.**

3. **Optional: Load sample data:**
   - Use the sample data from `database/sample-data.md`
   - Insert via SQL or through the application UI

## Development Workflow

1. **Backend Changes:**
   - Make changes to Java files
   - Spring Boot DevTools will automatically reload the application
   - If you don't have DevTools, restart with `./mvnw spring-boot:run`

2. **Frontend Changes:**
   - Make changes to React/TypeScript files
   - The development server will automatically reload the browser

3. **Database Changes:**
   - Modify entity classes in the backend
   - Hibernate will update the database schema automatically
   - For production, consider using Flyway migrations

## Testing

### Backend Testing
```bash
cd backend
./mvnw test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## Building for Production

### Backend
```bash
cd backend
./mvnw clean package
```

### Frontend
```bash
cd frontend
npm run build
```

## Common Issues

1. **Port Conflicts:**
   - Backend runs on port 8080
   - Frontend runs on port 3000
   - Make sure these ports are available

2. **Database Connection:**
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`

3. **CORS Issues:**
   - CORS is configured in `WebSecurityConfig.java`
   - Add your frontend URL if deploying to different domains

4. **JWT Secret:**
   - Change the JWT secret in production
   - Use environment variables for sensitive configuration

## Environment Variables

Create environment-specific configuration:

### Backend (.env or environment variables)
```
DATABASE_URL=jdbc:postgresql://localhost:5432/personal_finance_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_password
JWT_SECRET=your_long_secret_key
AWS_REGION=us-west-2
AWS_S3_BUCKET=your-bucket-name
```

### Frontend (.env.local)
```
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENVIRONMENT=development
```
