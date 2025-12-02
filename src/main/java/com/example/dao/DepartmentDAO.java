package com.example.dao;

import com.example.model.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentDAO {

    /**
     * Get all departments from the database
     * @return List of all departments
     * @throws SQLException if database error occurs
     */
    List<Department> getAllDepartments() throws SQLException;

    /**
     * Get department by ID
     * @param departmentId Department ID
     * @return Department object or null if not found
     * @throws SQLException if database error occurs
     */
    Department getDepartmentById(int departmentId) throws SQLException;

    /**
     * Get department by Name
     * 
     * @param departmentName Department Name
     * @return Department object or null if not found
     * @throws SQLException if database error occurs
     */
    Department getDepartmentByName(String departmentName) throws SQLException;

    // /**
    // * Add new department to database
    // * @param department Department to add
    // * @return Generated department ID
    // * @throws SQLException if database error occurs
    // */
    // int addDepartment(Department department) throws SQLException;

    // /**
    // * Update existing department
    // * @param department Department with updated information
    // * @return true if update successful
    // * @throws SQLException if database error occurs
    // */
    // boolean updateDepartment(Department department) throws SQLException;

    // /**
    // * Delete department by ID
    // * @param departmentId Department ID to delete
    // * @return true if deletion successful
    // * @throws SQLException if database error occurs
    // */
    // boolean deleteDepartment(int departmentId) throws SQLException;

    /**
     * Get total number of departments
     * 
     * @return Count of departments
     * @throws SQLException if database error occurs
     */
    int getDepartmentCount() throws SQLException;

    /**
     * Search department by name
     * 
     * @param departmentName The department name
     * @return List of matching department
     * @throws SQLException if a database error occurs
     */
    List<Department> searchDepartmentByName(String departmentName) throws SQLException;
}
