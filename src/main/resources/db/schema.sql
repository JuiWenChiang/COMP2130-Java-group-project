-- HR Management System
-- PostgreSQL Database Schema
-- Date: 2025-11-22

DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS departments CASCADE;

CREATE TABLE departments (
    department_id SERIAL PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    department_id INTEGER REFERENCES departments(department_id),
    position VARCHAR(100),
    hire_date DATE NOT NULL
);

INSERT INTO departments (department_name, description) VALUES
('Information Technology', 'IT Services and Software Development'),
('Business Administration', 'Business Operations and Management'),
('Hospitality Services', 'Food Services and Event Management'),
('Student Services', 'Student Support and Academic Advising'),
('Facilities Management', 'Campus Maintenance and Operations');

INSERT INTO employees (first_name, last_name, email, phone, department_id, position, hire_date)
VALUES
('Aiden', 'Tremblay', 'atremblay@georgebrown.ca', '416-555-2001', 1, 'Junior Developer', '2025-11-01'),
('Emma', 'Wong', 'ewong@georgebrown.ca', '647-555-2002', 5, 'Maintenance Technician', '2025-11-15'),
('Liam', 'Kowalski', 'lkowalski@georgebrown.ca', '416-555-2003', 1, 'Senior Developer', '2025-01-10'),
('Olivia', 'Nguyen', 'onguyen@georgebrown.ca', '647-555-2004', 4, 'Student Advisor', '2025-09-01'),
('Noah', 'Singh', 'nsingh@georgebrown.ca', '416-555-2005', 3, 'Event Coordinator', '2025-11-01');
