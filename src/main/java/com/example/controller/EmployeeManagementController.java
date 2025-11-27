package com.example.controller;

import com.example.dao.EmployeeDAO;
import com.example.dao.impl.EmployeeDAOImpl;
import com.example.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;

public class EmployeeManagementController extends BaseController{
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> departmentFilter;
    @FXML
    private ComboBox<String> employeeTypeFilter;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private Label statusLabel;

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @FXML
    private void initialize() {
        setupFilters();
        loadEmployees();
    }

    private void setupFilters() {
        ObservableList<String> departments = FXCollections.observableArrayList(
                "All Departments", "Engineering", "Sales", "Marketing", "HR", "Finance");
        departmentFilter.setItems(departments);
        departmentFilter.setValue("All Departments");

        ObservableList<String> types = FXCollections.observableArrayList(
                "All Types", "Hourly", "Salaried", "Contract", "Commission");
        employeeTypeFilter.setItems(types);
        employeeTypeFilter.setValue("All Types");
    }

    private void loadEmployees() {
        employeeList.clear();
        try {
            employeeList.addAll(employeeDAO.getAllEmployees());
            employeeTable.setItems(employeeList);
            updateStatusLabel();
        } catch (SQLException e) {
            showError("Error loading employees: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadEmployees();
            return;
        }

        try {
            employeeList.clear();
            employeeList.addAll(employeeDAO.searchEmployees(searchText));
            employeeTable.setItems(employeeList);
            updateStatusLabel();
        } catch (SQLException e) {
            showError("Error searching employees: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFilter() {
        // TODO: Implement filtering based on department and type
        loadEmployees();
    }

    @FXML
    private void handleRefresh() {
        loadEmployees();
        showInfo("Employee list refreshed");
    }

    @FXML
    private void handleAddEmployee() {
        try {
            Employee newEmployee = showEmployeeForm(null);
            if (newEmployee != null) {
                int employeeId = employeeDAO.addEmployee(newEmployee);
                newEmployee.setEmployeeId(employeeId);
                employeeList.add(newEmployee);
                updateStatusLabel();
                showInfo("Employee added successfully!");
            }
        } catch (Exception e) {
            showError("Error adding employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewDetails() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select an employee to view details");
            return;
        }
        showEmployeeDetails(selected);
    }

    @FXML
    private void handleEditEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select an employee to edit");
            return;
        }

        try {
            Employee editedEmployee = showEmployeeForm(selected);
            if (editedEmployee != null) {
                boolean success = employeeDAO.updateEmployee(editedEmployee);
                if (success) {
                    int index = employeeList.indexOf(selected);
                    if (index >= 0) {
                        employeeList.set(index, editedEmployee);
                    }
                    employeeTable.refresh();
                    showInfo("Employee updated successfully!");
                } else {
                    showError("Failed to update employee in database");
                }
            }
        } catch (Exception e) {
            showError("Error editing employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select an employee to delete");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText(selected.getFullName() + " will be permanently removed.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = employeeDAO.deleteEmployee(selected.getEmployeeId());
                if (success) {
                    employeeList.remove(selected);
                    updateStatusLabel();
                    showInfo("Employee deleted successfully");
                } else {
                    showError("Failed to delete employee from database");
                }
            } catch (SQLException e) {
                showError("Error deleting employee: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateStatusLabel() {
        statusLabel.setText("Total Employees: " + employeeList.size());
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Employee showEmployeeForm(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/employee_form.fxml"));
            Parent root = loader.load();

            EmployeeFormController controller = loader.getController();
            if (employee != null) {
                controller.setEmployee(employee);
            }

            Stage stage = new Stage();
            stage.setTitle(employee == null ? "Add New Employee" : "Edit Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isSaved()) {
                return controller.getEmployee();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading employee form: " + e.getMessage());
        }
        return null;
    }

    private void showEmployeeDetails(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/employee_details.fxml"));
            Parent root = loader.load();

            EmployeeDetailsController controller = loader.getController();
            controller.setEmployee(employee);

            Stage stage = new Stage();
            stage.setTitle("Employee Details - " + employee.getFullName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading employee details: " + e.getMessage());
        }
    }
}
