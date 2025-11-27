package com.example.model;

import java.io.Serializable;

public class Payroll implements Serializable {
    private int payrollId;
    private int employeeId;
    private double totalRegularHours;
    private double totalOvertimeHours;
    private double totalHolidayBonus;
    private double totalCommission;
    private double totalGrossPay;
    private double totalEmploymentInsurance;
    private double totalIncomeTax;
    private double totalNetPay;

    // Default constructor
    public Payroll() {
        this.totalRegularHours = 0.00;
        this.totalOvertimeHours = 0.00;
        this.totalHolidayBonus = 0.00;
        this.totalCommission = 0.00;
        this.totalGrossPay = 0.00;
        this.totalEmploymentInsurance = 0.00;
        this.totalIncomeTax = 0.00;
        this.totalNetPay = 0.00;
    }

    // Constructor with all fields
    public Payroll(int payrollId, int employeeId, double totalRegularHours, 
                   double totalOvertimeHours, double totalHolidayBonus, 
                   double totalCommission, double totalGrossPay, 
                   double totalEmploymentInsurance, double totalIncomeTax, 
                   double totalNetPay) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.totalRegularHours = totalRegularHours;
        this.totalOvertimeHours = totalOvertimeHours;
        this.totalHolidayBonus = totalHolidayBonus;
        this.totalCommission = totalCommission;
        this.totalGrossPay = totalGrossPay;
        this.totalEmploymentInsurance = totalEmploymentInsurance;
        this.totalIncomeTax = totalIncomeTax;
        this.totalNetPay = totalNetPay;
    }

    // Constructor without payrollId (for insertions)
    public Payroll(int employeeId, double totalRegularHours, 
                   double totalOvertimeHours, double totalHolidayBonus, 
                   double totalCommission, double totalGrossPay, 
                   double totalEmploymentInsurance, double totalIncomeTax, 
                   double totalNetPay) {
        this.employeeId = employeeId;
        this.totalRegularHours = totalRegularHours;
        this.totalOvertimeHours = totalOvertimeHours;
        this.totalHolidayBonus = totalHolidayBonus;
        this.totalCommission = totalCommission;
        this.totalGrossPay = totalGrossPay;
        this.totalEmploymentInsurance = totalEmploymentInsurance;
        this.totalIncomeTax = totalIncomeTax;
        this.totalNetPay = totalNetPay;
    }

    // Getters
    public int getPayrollId() {
        return payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getTotalRegularHours() {
        return totalRegularHours;
    }

    public double getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public double getTotalHolidayBonus() {
        return totalHolidayBonus;
    }

    public double getTotalCommission() {
        return totalCommission;
    }

    public double getTotalGrossPay() {
        return totalGrossPay;
    }

    public double getTotalEmploymentInsurance() {
        return totalEmploymentInsurance;
    }

    public double getTotalIncomeTax() {
        return totalIncomeTax;
    }

    public double getTotalNetPay() {
        return totalNetPay;
    }

    // Setters
    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setTotalRegularHours(double totalRegularHours) {
        this.totalRegularHours = totalRegularHours;
    }

    public void setTotalOvertimeHours(double totalOvertimeHours) {
        this.totalOvertimeHours = totalOvertimeHours;
    }

    public void setTotalHolidayBonus(double totalHolidayBonus) {
        this.totalHolidayBonus = totalHolidayBonus;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public void setTotalGrossPay(double totalGrossPay) {
        this.totalGrossPay = totalGrossPay;
    }

    public void setTotalEmploymentInsurance(double totalEmploymentInsurance) {
        this.totalEmploymentInsurance = totalEmploymentInsurance;
    }

    public void setTotalIncomeTax(double totalIncomeTax) {
        this.totalIncomeTax = totalIncomeTax;
    }

    public void setTotalNetPay(double totalNetPay) {
        this.totalNetPay = totalNetPay;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employeeId=" + employeeId +
                ", totalRegularHours=" + totalRegularHours +
                ", totalOvertimeHours=" + totalOvertimeHours +
                ", totalHolidayBonus=" + totalHolidayBonus +
                ", totalCommission=" + totalCommission +
                ", totalGrossPay=" + totalGrossPay +
                ", totalEmploymentInsurance=" + totalEmploymentInsurance +
                ", totalIncomeTax=" + totalIncomeTax +
                ", totalNetPay=" + totalNetPay +
                '}';
    }
}
