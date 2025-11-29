package com.example.dao;

import com.example.model.Payroll;

import java.sql.SQLException;
import java.util.List;

public interface PayrollDAO {

    /**
     * Get a payroll by Employee ID
     * @param employeeId The employee ID
     * @return The payroll Id, or null if not found
     * @throws SQLException if a database error occurs
     */
    Payroll getPayrollByEmployeeId(int employeeId) throws SQLException;

    /**
     * Check if payroll exists for employee by ID and name
     * @param employeeId The employee ID
     * @param employeeName The employee name
     * @return true if payroll exists for the given employee, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean existsPayroll(int employeeId, String employeeName) throws SQLException;

    /**
     * Add a new payroll
     * @param employee The payroll to add
     * @return The generated payroll Id
     * @throws SQLException if a database error occurs
     */
    int addPayroll(Payroll payroll) throws SQLException;

    /**
     * Update an existing payroll
     * @param employee The payroll to update
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean updatePayroll(Payroll payroll) throws SQLException;

    /**
     * Delete a payroll
     * @param payrollId The payroll ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean deletePayroll(int payrollId) throws SQLException;

    /**
     * Get all payrolls (with optional filters)
     * @return List of all payrolls
     * @throws SQLException if a database error occurs
     */
    List<Payroll> getAllPayrolls() throws SQLException;
}
