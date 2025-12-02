package com.example.dao;

import com.example.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

    /**
     * Get all employees from the database
     * @return List of all employees
     * @throws SQLException if a database error occurs
     */
    List<Employee> getAllEmployees() throws SQLException;

    /**
     * Get an employee by ID
     * @param employeeIdField The employee ID
     * @return The employee, or null if not found
     * @throws SQLException if a database error occurs
     */
    Employee getEmployeeById(int employeeIdField) throws SQLException;

    /**
     * Get an department by ID
     * @param employeeId The department ID
     * @return The employee, or null if not found
     * @throws SQLException if a database error occurs
     */
    List<Employee> getEmployeeByDepartmentId(int departmentId) throws SQLException;

    /**
     * Add a new employee
     * @param employee The employee to add
     * @return The generated employee ID
     * @throws SQLException if a database error occurs
     */
    int addEmployee(Employee employee) throws SQLException;

    /**
     * Update an existing employee
     * @param employee The employee to update
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean updateEmployee(Employee employee) throws SQLException;

    /**
     * Delete an employee
     * @param employeeId The employee ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean deleteEmployee(int employeeId) throws SQLException;

    /**
     * Search employees by name or email
     * @param searchTerm The search term
     * @return List of matching employees
     * @throws SQLException if a database error occurs
     */
    List<Employee> searchEmployees(String searchTerm) throws SQLException;
}
