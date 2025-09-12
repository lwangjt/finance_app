# API Documentation

## Authentication Endpoints

### POST /api/auth/signup
Register a new user account.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "User registered successfully!",
  "userId": 1
}
```

### POST /api/auth/signin
Authenticate a user and get JWT token.

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

## User Endpoints

### GET /api/users/categories
Get all available spending categories.

**Response:**
```json
[
  "GROCERIES",
  "DINING", 
  "GAS",
  "TRAVEL",
  "ENTERTAINMENT",
  "SHOPPING",
  "UTILITIES",
  "HEALTHCARE",
  "EDUCATION",
  "TRANSPORTATION",
  "OTHER"
]
```

### POST /api/users/preferred-categories
Update user's preferred spending categories.

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
["GROCERIES", "DINING", "GAS"]
```

### GET /api/users/profile
Get current user's profile information.

**Headers:**
```
Authorization: Bearer <token>
```

## Transaction Endpoints

### POST /api/transactions
Add a new transaction.

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "amount": 85.40,
  "description": "Weekly grocery shopping",
  "merchant": "Whole Foods",
  "category": "GROCERIES",
  "transactionDate": "2023-11-15T14:30:00",
  "creditCardId": 1,
  "groupId": null
}
```

### GET /api/transactions
Get all transactions for the current user.

**Headers:**
```
Authorization: Bearer <token>
```

### GET /api/transactions/category/{category}
Get transactions by category for the current user.

**Headers:**
```
Authorization: Bearer <token>
```

## Error Responses

All endpoints may return error responses in the following format:

```json
{
  "message": "Error description",
  "timestamp": "2023-11-15T10:30:00Z"
}
```

Common HTTP status codes:
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Missing or invalid authentication
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error
