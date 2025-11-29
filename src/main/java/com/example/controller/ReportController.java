package com.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

// import java.sql.SQLException;
// import java.util.function.UnaryOperator;

// import com.example.model.Employee;

// import javafx.fxml.FXML;
// import javafx.scene.control.Alert;
// import javafx.scene.control.ButtonType;
// import javafx.scene.control.TextField;
// import javafx.scene.control.TextFormatter;

// import com.example.controller.EmployeeDetailsController;
// import com.example.controller.EmployeeFormController;

// import com.example.dao.PayrollDAO;
// import com.example.dao.impl.EmployeeDAOImpl;

// import com.example.model.Employee;
// // import com.example.model.Payroll;

// import javafx.fxml.FXML;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.stage.Stage;
// import java.sql.SQLException;
// import java.time.format.DateTimeFormatter;

public class ReportController extends BaseController {
    // Radio buttons
    @FXML
    private RadioButton employeeRadio;
    @FXML
    private RadioButton departmentRadio;
    @FXML
    private ToggleGroup searchTypeGroup;

    // Search fields
    @FXML
    private HBox employeeSearchBox;
    @FXML
    private HBox departmentSearchBox;
    @FXML
    private TextField employeeIdField;
    @FXML
    private TextField employeeNameField;
    @FXML
    private ComboBox<String> departmentComboBox;
    @FXML
    private ComboBox<String> positionComboBox;

    // Pane
    @FXML
    private ScrollPane employeeScrollPane;
    @FXML
    private ScrollPane departmentScrollPane;

    @FXML
    public void initialize() {
        // Initial state
        employeeSearchBox.setVisible(true);
        employeeSearchBox.setManaged(true);
        departmentSearchBox.setVisible(false);
        departmentSearchBox.setManaged(false);
        employeeScrollPane.setVisible(true);
        employeeScrollPane.setManaged(true);
        departmentScrollPane.setVisible(false);
        departmentScrollPane.setManaged(false);

        // Listen for RadioButton toggle events
        searchTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean isEmployee = (newValue == employeeRadio);

                // Switch the displayed search bar
                employeeSearchBox.setVisible(isEmployee);
                employeeSearchBox.setManaged(isEmployee);
                departmentSearchBox.setVisible(!isEmployee);
                departmentSearchBox.setManaged(!isEmployee);

                // Switch the displayed pane and if there is data, it will be displayed.
                employeeScrollPane.setVisible(isEmployee);
                employeeScrollPane.setManaged(isEmployee);
                departmentScrollPane.setVisible(!isEmployee);
                departmentScrollPane.setManaged(!isEmployee);

                // Clear the field
                if (isEmployee) {
                    employeeIdField.clear();
                    employeeNameField.clear();
                } else {
                    departmentComboBox.setValue(null);
                }
            }
        });
    }
}
