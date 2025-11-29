package com.example.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.PayrollDAO;
import com.example.model.Payroll;
import com.example.util.DatabaseConnection;

public class PayrollDAOImpl implements PayrollDAO {

    @Override
    public List<Payroll> getAllPayrolls() throws SQLException {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM payroll ORDER BY payroll_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                payrolls.add(createPayrollFromResultSet(rs));
            }
        }

        return payrolls;
    }

    @Override
    public Payroll getPayrollByEmployeeId(int employeeId) throws SQLException {
        String sql = "SELECT * FROM payrolls WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return createPayrollFromResultSet(rs);
            }
        }

        return null;
    }

    @Override
    public boolean existsPayroll(int employeeId, String employeeName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Payroll p " +
                "INNER JOIN Employee e ON p.employee_id = e.employee_id " +
                "WHERE p.employee_id = ? AND e.employee_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, employeeName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public int addPayroll(Payroll payroll) throws SQLException {
        String sql = "INSERT INTO payrolls (employee_id, total_regular_hours, total_overtime_hours, total_holiday_bonus, total_commission, total_gross_pay, total_employment_insurance,total_income_tax, total_net_pay)"
                +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, payroll.getEmployeeId());
            pstmt.setDouble(2, payroll.getTotalRegularHours());
            pstmt.setDouble(3, payroll.getTotalOvertimeHours());
            pstmt.setDouble(4, payroll.getTotalHolidayBonus());
            pstmt.setDouble(5, payroll.getTotalCommission());
            pstmt.setDouble(6, payroll.getTotalGrossPay());
            pstmt.setDouble(7, payroll.getTotalEmploymentInsurance());
            pstmt.setDouble(8, payroll.getTotalIncomeTax());
            pstmt.setDouble(9, payroll.getTotalNetPay());

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
    public boolean updatePayroll(Payroll payroll) throws SQLException {
        String sql = "UPDATE payrolls SET total_regular_hours = ?, total_overtime_hours = ?, "
                +
                "total_holiday_bonus = ?, total_commission = ?, total_gross_pay = ?,"
                +
                "total_employment_insurance = ?, total_income_tax = ?, total_net_pay = ? "
                +
                "WHERE payroll_id = ? AND employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, payroll.getTotalRegularHours());
            pstmt.setDouble(2, payroll.getTotalOvertimeHours());
            pstmt.setDouble(3, payroll.getTotalHolidayBonus());
            pstmt.setDouble(4, payroll.getTotalCommission());
            pstmt.setDouble(5, payroll.getTotalGrossPay());
            pstmt.setDouble(6, payroll.getTotalEmploymentInsurance());
            pstmt.setDouble(7, payroll.getTotalIncomeTax());
            pstmt.setDouble(8, payroll.getTotalNetPay());
            pstmt.setInt(9, payroll.getPayrollId());
            pstmt.setInt(10, payroll.getEmployeeId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deletePayroll(int payrollId) throws SQLException {
        String sql = "DELETE FROM payrolls WHERE payroll_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payrollId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Helper method to create Payroll object from ResultSet
     */
    private Payroll createPayrollFromResultSet(ResultSet rs) throws SQLException {
        return new Payroll(
                rs.getInt("payroll_id"),
                rs.getInt("employee_id"),
                rs.getDouble("total_regular_hours"),
                rs.getDouble("total_overtime_hours"),
                rs.getDouble("total_holiday_bonus"),
                rs.getDouble("total_commission"),
                rs.getDouble("total_gross_pay"),
                rs.getDouble("total_employment_insurance"),
                rs.getDouble("total_income_tax"),
                rs.getDouble("total_net_pay"));
    }
}
