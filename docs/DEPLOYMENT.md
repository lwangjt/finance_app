# Deployment Guide

This guide covers deploying the Personal Finance Planning App to production.

## Architecture Overview

- **Frontend:** React app (served as static files)
- **Backend:** Spring Boot application (JAR file)
- **Database:** PostgreSQL
- **Cloud Services:** AWS (S3, Lambda)

## Deployment Options

### Option 1: Traditional Server Deployment

#### Backend Deployment

1. **Build the application:**
   ```bash
   cd backend
   ./mvnw clean package -Dmaven.test.skip=true
   ```

2. **Set environment variables:**
   ```bash
   export DATABASE_URL=jdbc:postgresql://your-db-host:5432/personal_finance_db
   export DATABASE_USERNAME=your_username
   export DATABASE_PASSWORD=your_password
   export JWT_SECRET=your_production_jwt_secret
   ```

3. **Run the application:**
   ```bash
   java -jar target/personal-finance-backend-1.0.0.jar
   ```

#### Frontend Deployment

1. **Build the application:**
   ```bash
   cd frontend
   npm run build
   ```

2. **Serve static files:**
   - Use Nginx, Apache, or any static file server
   - Point to the `build/` directory

3. **Sample Nginx configuration:**
   ```nginx
   server {
       listen 80;
       server_name your-domain.com;
       
       location / {
           root /path/to/frontend/build;
           try_files $uri $uri/ /index.html;
       }
       
       location /api/ {
           proxy_pass http://localhost:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
       }
   }
   ```

### Option 2: Docker Deployment

#### Backend Dockerfile
```dockerfile
FROM openjdk:17-jre-slim

WORKDIR /app
COPY target/personal-finance-backend-1.0.0.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

#### Frontend Dockerfile
```dockerfile
FROM node:18-alpine AS build

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

#### Docker Compose
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: personal_finance_db
      POSTGRES_USER: financeapp
      POSTGRES_PASSWORD: your_password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  backend:
    build: ./backend
    environment:
      DATABASE_URL: jdbc:postgresql://postgres:5432/personal_finance_db
      DATABASE_USERNAME: financeapp
      DATABASE_PASSWORD: your_password
      JWT_SECRET: your_jwt_secret
    ports:
      - "8080:8080"
    depends_on:
      - postgres

  frontend:
    build: ./frontend
    ports:
      - "80:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```

### Option 3: Cloud Deployment (AWS)

#### Using AWS Elastic Beanstalk

1. **Install AWS CLI and EB CLI:**
   ```bash
   pip install awscli awsebcli
   ```

2. **Initialize Elastic Beanstalk:**
   ```bash
   cd backend
   eb init
   ```

3. **Deploy:**
   ```bash
   eb create production
   eb deploy
   ```

#### Using AWS ECS (Container Service)

1. **Push images to ECR:**
   ```bash
   aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin your-account-id.dkr.ecr.us-west-2.amazonaws.com
   
   docker build -t personal-finance-backend ./backend
   docker tag personal-finance-backend:latest your-account-id.dkr.ecr.us-west-2.amazonaws.com/personal-finance-backend:latest
   docker push your-account-id.dkr.ecr.us-west-2.amazonaws.com/personal-finance-backend:latest
   ```

2. **Create ECS service and task definitions**

#### Database (AWS RDS)

1. **Create RDS PostgreSQL instance:**
   ```bash
   aws rds create-db-instance \
     --db-instance-identifier personal-finance-db \
     --db-instance-class db.t3.micro \
     --engine postgres \
     --master-username admin \
     --master-user-password your_secure_password \
     --allocated-storage 20
   ```

## Environment Variables for Production

### Backend Environment Variables
```bash
# Database
DATABASE_URL=jdbc:postgresql://your-rds-endpoint:5432/personal_finance_db
DATABASE_USERNAME=admin
DATABASE_PASSWORD=your_secure_password

# Security
JWT_SECRET=your_very_long_and_secure_jwt_secret_key_at_least_32_characters
JWT_EXPIRATION=86400000

# AWS
AWS_REGION=us-west-2
AWS_ACCESS_KEY_ID=your_access_key
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_S3_BUCKET=your-finance-app-bucket

# Application
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080
```

### Frontend Environment Variables
```bash
REACT_APP_API_URL=https://your-api-domain.com/api
REACT_APP_ENVIRONMENT=production
```

## SSL/HTTPS Setup

### Using Let's Encrypt with Certbot
```bash
# Install certbot
sudo apt-get update
sudo apt-get install certbot python3-certbot-nginx

# Get certificate
sudo certbot --nginx -d your-domain.com

# Auto-renewal
sudo certbot renew --dry-run
```

## Monitoring and Logging

### Application Monitoring
- Use Spring Boot Actuator endpoints
- Set up health checks at `/actuator/health`
- Monitor metrics at `/actuator/metrics`

### Log Management
```yaml
# application-production.yml
logging:
  level:
    com.financeapp: INFO
  file:
    name: /var/log/personal-finance-app.log
  logback:
    rollingpolicy:
      max-file-size: 100MB
      total-size-cap: 1GB
```

## Security Considerations

1. **Use strong JWT secrets**
2. **Enable HTTPS only**
3. **Configure proper CORS settings**
4. **Use environment variables for sensitive data**
5. **Regular security updates**
6. **Database connection security**
7. **Rate limiting**
8. **Input validation**

## Performance Optimization

### Backend
- Enable caching with Redis
- Use connection pooling
- Optimize database queries
- Enable gzip compression

### Frontend
- Use CDN for static assets
- Enable caching headers
- Minimize bundle sizes
- Use lazy loading

### Database
- Create proper indexes
- Regular maintenance
- Connection pooling
- Read replicas for scaling

## Backup Strategy

### Database Backups
```bash
# Automated daily backup
pg_dump -h your-db-host -U username personal_finance_db > backup_$(date +%Y%m%d).sql

# Restore from backup
psql -h your-db-host -U username personal_finance_db < backup_20231115.sql
```

### Application Backups
- Regular code commits to Git
- Automated builds and deployments
- Configuration backups
- AWS S3 for file storage backups

## Scaling Considerations

1. **Horizontal scaling:** Multiple backend instances
2. **Load balancing:** Nginx or AWS ALB
3. **Database scaling:** Read replicas, sharding
4. **Caching:** Redis for session storage
5. **CDN:** CloudFront for static assets
6. **Microservices:** Split into smaller services as needed
