package com.example.dao.impl;

import com.example.dao.DepartmentDAO;
import com.example.model.Department;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Override
    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY department_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departments.add(createDepartmentFromResultSet(rs));
            }
        }

        return departments;
    }

    @Override
    public Department getDepartmentById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM departments WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createDepartmentFromResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws SQLException {
        String sql = "SELECT * FROM departments WHERE department_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, departmentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createDepartmentFromResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public int getDepartmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public List<Department> searchDepartmentByName(String searchTerm) throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments WHERE LOWER(department_name) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                departments.add(createDepartmentFromResultSet(rs));
            }
        }

        return departments;
    }

    /**
     * Helper method to create Employee object from ResultSet
     */
    private Department createDepartmentFromResultSet(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("department_id"),
                rs.getString("department_name"),
                rs.getString("description"));
    }
}
