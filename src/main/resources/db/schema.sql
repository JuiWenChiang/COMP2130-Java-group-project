DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS payrolls CASCADE;

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
    pay_rate NUMERIC(10, 2) DEFAULT 0.00,
    overtime_rate NUMERIC(10, 2) DEFAULT 0.00,
);

CREATE TABLE payrolls (
    payroll_id SERIAL PRIMARY KEY,
    employee_id INTEGER UNIQUE NOT NULL REFERENCES employees(employee_id) ON DELETE CASCADE,
    total_regular_hours NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    total_overtime_hours NUMERIC(10, 2) DEFAULT 0.00,
    total_holiday_bonus NUMERIC(10, 2) DEFAULT 0.00,
    total_commission NUMERIC(6, 2) DEFAULT 0.00,
    total_gross_pay NUMERIC(6, 2) DEFAULT 0.00,
    total_employment_insurance NUMERIC(10, 2) DEFAULT 0.00,
    total_income_tax NUMERIC(10, 2) DEFAULT 0.00,
    total_net_pay NUMERIC(10, 2) DEFAULT 0.00,    
);

-- Initial Data
INSERT INTO departments (department_name, description) VALUES
('Information Technology', 'IT Services and Software Development'),
('Business Administration', 'Business Operations and Management'),
('Hospitality Services', 'Food Services and Event Management'),
('Student Services', 'Student Support and Academic Advising'),
('Facilities Management', 'Campus Maintenance and Operations');

INSERT INTO employees (first_name, last_name, email, phone, department_id, position, hire_date)
VALUES
('Aiden', 'Tremblay', 'atremblay@georgebrown.ca', '416-555-2001', 1, 'Junior Developer', '2025-11-01', 28.00, 42.00),
('Emma', 'Wong', 'ewong@georgebrown.ca', '647-555-2002', 5, 'Maintenance Technician', '2025-11-15', 25.00, 37.50),
('Liam', 'Kowalski', 'lkowalski@georgebrown.ca', '416-555-2003', 1, 'Senior Developer', '2025-01-10', 45.00, 67.50),
('Olivia', 'Nguyen', 'onguyen@georgebrown.ca', '647-555-2004', 4, 'Student Advisor', '2025-09-01', 30.00, 45.00),
('Noah', 'Singh', 'nsingh@georgebrown.ca', '416-555-2005', 3, 'Event Coordinator', '2025-11-01', 32.00, 48.00);

-- total_gross_pay = (pay_rate * total_regular_hours) 
--                 + (overtime_rate * total_overtime_hours) 
--                 + total_holiday_bonus 
--                 + total_commission

-- total_net_pay = total_gross_pay 
--               - total_employment_insurance 
--               - total_income_tax

INSERT INTO payrolls (
    employee_id, 
    total_regular_hours, 
    total_overtime_hours, 
    total_holiday_bonus, 
    total_commission, 
    total_gross_pay, 
    total_employment_insurance, 
    total_income_tax, 
    total_net_pay
)
VALUES (
    3,
    30.00,   -- total_regular_hours
    10.00,   -- total_overtime_hours
    500.00,  -- total_holiday_bonus
    0.00,    -- total_commission
    2525.00, -- total_gross_pay (45*30 + 67.5*10 + 500)
    41.26,   -- total_employment_insurance (2525 * 1.64%)
    187.50,  -- total_income_tax (2525 * 14.5% = 366.13)
    2296.24  -- total_net_pay (2525 - 41.26 - 187.50)
);

INSERT INTO payrolls (
    employee_id, 
    total_regular_hours, 
    total_overtime_hours, 
    total_holiday_bonus, 
    total_commission, 
    total_gross_pay, 
    total_employment_insurance, 
    total_income_tax, 
    total_net_pay
)
VALUES (
    5,
    30.00,   -- total_regular_hours
    1.00,    -- total_overtime_hours
    400.00,  -- total_holiday_bonus
    800.00,  -- total_commission
    2208.00, -- total_gross_pay (32*30 + 48*1 + 400 + 800)
    36.21,   -- total_employment_insurance (2208 * 1.64%)
    170.00,  -- total_income_tax (2208 * 14.5% = 320.16)
    2001.79  -- total_net_pay (2208 - 36.21 - 170)
);