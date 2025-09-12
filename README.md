# Personal Finance Planning App

A multi-user personal finance and budgeting platform that helps users track expenses, manage budgets, and maximize credit card rewards.

## Features

- Multi-user support for collaborative financial planning
- Expense tracking with category-based spending analysis
- Credit card optimization recommendations
- Real-time notifications and reminders
- Comprehensive financial reports and analytics

## Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.x
- Spring Security
- PostgreSQL
- AWS Lambda (for event-driven processing)
- AWS S3 (for file storage)

**Frontend:**
- React.js 18
- TypeScript
- Material-UI
- Redux Toolkit
- Chart.js for data visualization

## Project Structure

```
credit_app/
├── backend/                 # Spring Boot backend
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
├── frontend/               # React frontend
│   ├── src/
│   ├── public/
│   └── package.json
├── database/              # Database scripts
└── docs/                  # Documentation
```

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- AWS CLI (for cloud features)

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

## API Documentation
The API documentation will be available at `http://localhost:8080/swagger-ui.html` when the backend is running.
