package com.example.controller;

import com.example.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class EmployeeDetailsController {

    @FXML private Label employeeIdLabel;
    @FXML private Label fullNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label departmentIdLabel;
    @FXML private Label positionLabel;
    @FXML private Label hireDateLabel;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public void setEmployee(Employee employee) {
        if (employee == null) return;

        employeeIdLabel.setText(String.valueOf(employee.getEmployeeId()));
        fullNameLabel.setText(employee.getFullName());
        firstNameLabel.setText(employee.getFirstName());
        lastNameLabel.setText(employee.getLastName());
        emailLabel.setText(employee.getEmail());
        phoneLabel.setText(employee.getPhone() != null ? employee.getPhone() : "N/A");
        departmentIdLabel.setText(String.valueOf(employee.getDepartmentId()));
        positionLabel.setText(employee.getPosition());
        hireDateLabel.setText(employee.getHireDate().format(DATE_FORMATTER));
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) employeeIdLabel.getScene().getWindow();
        stage.close();
    }
}
