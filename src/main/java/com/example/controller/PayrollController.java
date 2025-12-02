package com.example.controller;

import java.sql.SQLException;

import com.example.model.Payroll;
import com.example.dao.PayrollDAO;
import com.example.dao.impl.PayrollDAOImpl;
import com.example.model.Employee;
import com.example.dao.EmployeeDAO;
import com.example.dao.impl.EmployeeDAOImpl;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.Node;
import javafx.util.Duration;

public class PayrollController extends BaseController {
    public boolean isExistingPayroll = false;
    public int targetPayrollId;
    public int targetEmployeeId;

    // Input Value
    @FXML
    private TextField searchField;
    @FXML
    private TextField totalRegularHours;
    @FXML
    private TextField totalOvertimeHours;
    @FXML
    private TextField totalHolidayBonus;
    @FXML
    private TextField totalCommission;

    // Employee Info Label
    @FXML
    private Label employeeNmaeLabel;
    @FXML
    private Label employeePositionLabel;
    @FXML
    private Label payRateLabel;
    @FXML
    private Label overtimeRateLabel;

    // Calculation Result Label
    @FXML
    private Label grossPayLabel;
    @FXML
    private Label employmentInsuranceLabel;
    @FXML
    private Label incomeTaxLabel;
    @FXML
    private Label netPayLabel;

    // Pane
    @FXML
    private ScrollPane payrollPane;

    // 假設的時薪
    private static final double REGULAR_HOURLY_RATE = 20.0;
    private static final double OVERTIME_HOURLY_RATE = 30.0;

    private static final double INSURANCE_RATE = 0.0164;
    private PayrollDAO payrollDAO = new PayrollDAOImpl();
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @FXML
    public void initialize() {
        setupNumericField(totalRegularHours, 0.0, 2288.0);
        setupNumericField(totalOvertimeHours, 0.0, 208.0);
        setupNumericField(totalHolidayBonus, 0.00, 100000.0);
        setupNumericField(totalCommission, 0.0, 100000.0);

        payrollPane.setVisible(false);
        payrollPane.setManaged(false);

    }

    private void showTooltip(String message) {
        Node node = totalRegularHours;
        Tooltip tooltip = new Tooltip(message);
        tooltip.setStyle("-fx-background-color: #1c2a77ff; -fx-text-fill: white; -fx-font-size: 14;");

        tooltip.setAutoHide(true);

        tooltip.show(node,
                node.getScene().getWindow().getX() + node.getLayoutX() + 50,
                node.getScene().getWindow().getY() + node.getLayoutY() + 75);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> tooltip.hide());
        pause.play();
    }

    /* Calculate */
    private double calculateIncomeTax(double grossValue) {
        double tax = 0;

        if (grossValue <= 57375) {
            tax = grossValue * 0.145;
        } else if (grossValue <= 114750) {
            tax = 57375 * 0.145 + (grossValue - 57375) * 0.205;
        } else if (grossValue <= 177882) {
            tax = 57375 * 0.145 + (114750 - 57375) * 0.205
                    + (grossValue - 114750) * 0.260;
        } else if (grossValue <= 253414) {
            tax = 57375 * 0.145 + (114750 - 57375) * 0.205
                    + (177882 - 114750) * 0.260 + (grossValue - 177882) * 0.290;
        } else {
            tax = 57375 * 0.145 + (114750 - 57375) * 0.205
                    + (177882 - 114750) * 0.260 + (253414 - 177882) * 0.290
                    + (grossValue - 253414) * 0.330;
        }
        return tax;
    }

    private Payroll calculatePayrollFromInputs() throws NumberFormatException {
        Payroll result = new Payroll();

        // Input
        result.regularHours = Double.parseDouble(totalRegularHours.getText());
        result.overtimeHours = Double.parseDouble(totalOvertimeHours.getText());
        result.holidayBonus = Double.parseDouble(totalHolidayBonus.getText());
        result.commission = Double.parseDouble(totalCommission.getText());

        // Calculation
        result.grossPay = (result.regularHours * REGULAR_HOURLY_RATE)
                + (result.overtimeHours * OVERTIME_HOURLY_RATE)
                + result.holidayBonus
                + result.commission;

        result.employmentInsurance = result.grossPay * INSURANCE_RATE;
        result.incomeTax = calculateIncomeTax(result.grossPay);
        result.netPay = result.grossPay
                - result.employmentInsurance
                - result.incomeTax;

        return result;
    }

    /* Labels */
    private void updatePayrollLabels(Payroll payroll) {
        grossPayLabel.setText(String.format("$%.2f", payroll.grossPay));
        employmentInsuranceLabel.setText(String.format("$%.2f", payroll.employmentInsurance));
        incomeTaxLabel.setText(String.format("$%.2f", payroll.incomeTax));
        netPayLabel.setText(String.format("$%.2f", payroll.netPay));
    }

    private void setPayrollLabels(Payroll payroll) {
        totalRegularHours.setText(String.valueOf(payroll.getTotalRegularHours()));
        totalOvertimeHours.setText(String.valueOf(payroll.getTotalOvertimeHours()));
        totalHolidayBonus.setText(String.valueOf(payroll.getTotalHolidayBonus()));
        totalCommission.setText(String.valueOf(payroll.getTotalCommission()));
        grossPayLabel.setText(String.format("%.2f", payroll.getTotalGrossPay()));
        employmentInsuranceLabel.setText(String.format("%.2f", payroll.getTotalEmploymentInsurance()));
        incomeTaxLabel.setText(String.format("%.2f", payroll.getTotalIncomeTax()));
        netPayLabel.setText(String.format("%.2f", payroll.getTotalNetPay()));
    }

    private void setEmployeeInfo(Employee employee) {
        employeeNmaeLabel.setText(String.valueOf(employee.getFullName()));
        employeePositionLabel.setText(String.valueOf(employee.getPosition()));
        payRateLabel.setText(String.valueOf(employee.getFullName()));
        overtimeRateLabel.setText(String.valueOf(employee.getFullName()));
    }

    /* Handl Event */
    @FXML
    private void handleSearch() {
        payrollPane.setVisible(false);
        payrollPane.setManaged(false);

        String searchText = searchField.getText().trim();
        if (searchText.isEmpty())
            return;

        if (verifyInputType("int", searchText)) {
            int searchID = Integer.parseInt(searchText);
            try {
                Employee employeeInfo = employeeDAO.getEmployeeById(searchID);
                if (employeeInfo == null) {
                    showAlertWarning("Employee ID " + searchID + " does not exist.");
                    return;
                }

                payrollPane.setVisible(true);
                payrollPane.setManaged(true);

                setEmployeeInfo(employeeInfo);
                Payroll payroll = payrollDAO.getPayrollByEmployeeId(searchID);
                if (payroll != null) {
                    isExistingPayroll = true;
                    targetPayrollId = payroll.getPayrollId();
                    setPayrollLabels(payroll);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlertWarning("Input error, please enter an integer ID");
        }
    }

    @FXML
    private void handleCalculate() {
        try {
            Payroll result = calculatePayrollFromInputs();
            updatePayrollLabels(result);
        } catch (NumberFormatException e) {
            grossPayLabel.setText("Invalid input!");
            employmentInsuranceLabel.setText("Invalid input!");
            incomeTaxLabel.setText("Invalid input!");
            netPayLabel.setText("Invalid input!");
        }
    }

    @FXML
    private void handleSavePayroll() {
        Payroll calculateResult = calculatePayrollFromInputs();
        updatePayrollLabels(calculateResult);
        if (targetEmployeeId < 0)
            return;

        Payroll payroll = new Payroll(
                targetPayrollId,
                targetEmployeeId,
                calculateResult.regularHours,
                calculateResult.overtimeHours,
                calculateResult.holidayBonus,
                calculateResult.commission,
                calculateResult.grossPay,
                calculateResult.employmentInsurance,
                calculateResult.incomeTax,
                calculateResult.netPay);

        try {
            if (isExistingPayroll) {
                payrollDAO.updatePayroll(payroll);
            } else {
                payrollDAO.addPayroll(payroll);
            }
        } catch (Exception e) {
            showAlertWarning(e.getMessage());
        }
    }

    @FXML
    private void handleDeletePayroll() {
        Alert alert = showAlertWarning("Are you sure you want to delete this payroll?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = payrollDAO.deletePayroll(targetPayrollId);
                if (success) {
                    showTooltip("Employee deleted successfully");
                } else {
                    showAlertWarning("Failed to delete employee from database");
                }
            } catch (SQLException e) {
                showAlertWarning(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
