-- Setup script for PostgreSQL database
-- Run this script to create the database and user if not using Docker Compose

-- Create database (connect as postgres superuser)
-- CREATE DATABASE assecor_assessment;

-- Create user (connect as postgres superuser)
-- CREATE USER assecor_user WITH PASSWORD 'assecor_password';

-- Grant privileges
-- GRANT ALL PRIVILEGES ON DATABASE assecor_assessment TO assecor_user;

-- Connect to assecor_assessment database and grant schema privileges
-- \c assecor_assessment;
-- GRANT ALL ON SCHEMA public TO assecor_user;

-- The persons table will be created automatically by Hibernate
-- based on the PersonEntity JPA annotations when the application starts