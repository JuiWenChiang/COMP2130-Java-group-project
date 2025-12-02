package com.example.dao.impl;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY employee_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(createEmployeeFromResultSet(rs));
            }
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return createEmployeeFromResultSet(rs);
            }
        }

        return null;
    }

    @Override
    public List<Employee> getEmployeeByDepartmentId(int departmentId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                employees.add(createEmployeeFromResultSet(rs));
            }
        }

        return employees;
    }

    @Override
    public int addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (first_name, last_name, email, phone, " +
                    "department_id, position, hire_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone());
            pstmt.setInt(5, employee.getDepartmentId());
            pstmt.setString(6, employee.getPosition());
            pstmt.setDate(7, Date.valueOf(employee.getHireDate()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, " +
                    "phone = ?, department_id = ?, position = ?, hire_date = ? " +
                    "WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone());
            pstmt.setInt(5, employee.getDepartmentId());
            pstmt.setString(6, employee.getPosition());
            pstmt.setDate(7, Date.valueOf(employee.getHireDate()));
            pstmt.setInt(8, employee.getEmployeeId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteEmployee(int employeeId) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public List<Employee> searchEmployees(String searchTerm) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE " +
                    "LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ? OR LOWER(email) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                employees.add(createEmployeeFromResultSet(rs));
            }
        }

        return employees;
    }

    /**
     * Helper method to create Employee object from ResultSet
     */
    private Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("employee_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getInt("department_id"),
            rs.getString("position"),
            rs.getDate("hire_date").toLocalDate()
        );
    }
}
