-- Create database
CREATE DATABASE personal_finance_db;

-- Create user and grant privileges
CREATE USER 'financeapp'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON personal_finance_db.* TO 'financeapp'@'localhost';
FLUSH PRIVILEGES;

-- Use the database
USE personal_finance_db;

-- Users table will be created by Hibernate, but we can pre-populate some sample data
-- This script is for reference and initial setup
