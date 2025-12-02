package com.example.controller;

import com.example.model.Department;
import com.example.model.Employee;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EmployeeFormController {

    @FXML private Label formTitle;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<Department> departmentComboBox;
    @FXML private TextField positionField;
    @FXML private DatePicker hireDatePicker;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;

    private Employee employeeToEdit;
    private boolean saved = false;

    @FXML
    private void initialize() {
        setupDepartments();
        hireDatePicker.setValue(LocalDate.now());
    }

    private void setupDepartments() {
        // TODO: Load departments from database
        Department dept1 = new Department(1, "Information Technology", "IT Services and Software Development");
        Department dept2 = new Department(2, "Business Administration", "Business Operations and Management");
        Department dept3 = new Department(3, "Hospitality Services", "Food Services and Event Management");
        Department dept4 = new Department(4, "Student Services", "Student Support and Academic Advising");
        Department dept5 = new Department(5, "Facilities Management", "Campus Maintenance and Operations");

        departmentComboBox.setItems(FXCollections.observableArrayList(dept1, dept2, dept3, dept4, dept5));
    }

    public void setEmployee(Employee employee) {
        this.employeeToEdit = employee;
        formTitle.setText("Edit Employee");
        populateForm(employee);
    }

    private void populateForm(Employee employee) {
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        phoneField.setText(employee.getPhone());
        positionField.setText(employee.getPosition());
        hireDatePicker.setValue(employee.getHireDate());

        for (Department dept : departmentComboBox.getItems()) {
            if (dept.getDepartmentId() == employee.getDepartmentId()) {
                departmentComboBox.setValue(dept);
                break;
            }
        }
    }

    @FXML
    private void handleSave() {
        if (validateForm()) {
            saved = true;
            closeDialog();
        }
    }

    @FXML
    private void handleCancel() {
        saved = false;
        closeDialog();
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (firstNameField.getText().trim().isEmpty()) {
            errors.append("First name is required\n");
        }
        if (lastNameField.getText().trim().isEmpty()) {
            errors.append("Last name is required\n");
        }
        if (emailField.getText().trim().isEmpty()) {
            errors.append("Email is required\n");
        }
        if (departmentComboBox.getValue() == null) {
            errors.append("Department is required\n");
        }
        if (positionField.getText().trim().isEmpty()) {
            errors.append("Position is required\n");
        }
        if (hireDatePicker.getValue() == null) {
            errors.append("Hire date is required\n");
        }

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    public Employee getEmployee() {
        if (!saved) return null;
        return createEmployeeFromForm();
    }

    private Employee createEmployeeFromForm() {
        int employeeId = (employeeToEdit != null) ? employeeToEdit.getEmployeeId() : 0;

        return new Employee(
            employeeId,
            firstNameField.getText().trim(),
            lastNameField.getText().trim(),
            emailField.getText().trim(),
            phoneField.getText().trim(),
            departmentComboBox.getValue().getDepartmentId(),
            positionField.getText().trim(),
            hireDatePicker.getValue()
        );
    }

    public boolean isSaved() {
        return saved;
    }

    private void closeDialog() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
