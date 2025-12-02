package com.example.controller;

import java.sql.SQLException;
import java.util.List;

import com.example.dao.DepartmentDAO;
import com.example.dao.EmployeeDAO;
import com.example.dao.PayrollDAO;
import com.example.dao.impl.DepartmentDAOImpl;
import com.example.dao.impl.EmployeeDAOImpl;
import com.example.dao.impl.PayrollDAOImpl;
import com.example.model.Department;
import com.example.model.Employee;
import com.example.model.Payroll;
import com.example.model.EmployeeReportView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private ScrollPane departmentScrollPane;
    @FXML
    private ScrollPane employeeScrollPane;
    @FXML
    private VBox departmentDetailsVBox;
    @FXML
    private Label departmentNameLable;
    @FXML
    private Label departmentIntroductionLable;
    @FXML
    private Label departmentEmployeesNumberLable;
    @FXML
    private TableView<EmployeeReportView> reportEmployeeTable;
    @FXML
    private Label employeeIDLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label totalGrossPayLabel;
    @FXML
    private Label totalNetPayLabel;

    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private DepartmentDAO departmentDAO = new DepartmentDAOImpl();
    private PayrollDAO payrollDAO = new PayrollDAOImpl();
    public Department secletedDept;

    @FXML
    public void initialize() {
        boolean isInitial = true;
        // Initial Search Section state
        departmentSearchBox.setVisible(isInitial);
        departmentSearchBox.setManaged(isInitial);
        departmentScrollPane.setVisible(isInitial);
        departmentScrollPane.setManaged(isInitial);
        employeeSearchBox.setVisible(!isInitial);
        employeeSearchBox.setManaged(!isInitial);
        employeeScrollPane.setVisible(!isInitial);
        employeeScrollPane.setManaged(!isInitial);
        // Initial Department Details Section state
        departmentDetailsVBox.setVisible(!isInitial);
        departmentDetailsVBox.setManaged(!isInitial);

        // setup
        setupDepartmentComboBox();
        loadAllEmployees();

        // Listen for RadioButton toggle events
        searchTypeGroup.selectedToggleProperty().addListener((observable, oldValue,
                newValue) -> {
            if (newValue != null) {
                boolean isEmployee = (newValue == employeeRadio);

                // Switch the displayed search bar
                employeeSearchBox.setVisible(isEmployee);
                employeeSearchBox.setManaged(isEmployee);
                departmentSearchBox.setVisible(!isEmployee);
                departmentSearchBox.setManaged(!isEmployee);
                // Switch the displayed pane and if there is data, it will be displayed.
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

    /* Setup */
    private void setupDepartmentComboBox() {
        try {
            List<Department> deptList = departmentDAO.getAllDepartments();

            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            departmentNames.add("All Departments");

            for (Department dept : deptList) {
                departmentNames.add(dept.getDepartmentName());
            }

            departmentComboBox.setItems(departmentNames);
            departmentComboBox.setValue("All Departments");

        } catch (SQLException e) {
            showAlertWarning(e.getMessage());
        }
    }

    /* Load */
    private EmployeeReportView loadEmployeeReport(Employee employee) {
        try {
            Payroll payroll = payrollDAO.getPayrollByEmployeeId(employee.getEmployeeId());

            double grossPay = 0.0;
            double netPay = 0.0;
            if (payroll != null) {
                grossPay = payroll.getTotalGrossPay();
                netPay = payroll.getTotalNetPay();
            }

            return new EmployeeReportView(
                    employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhone(),
                    employee.getPosition(),
                    grossPay,
                    netPay);
        } catch (SQLException e) {
            showAlertWarning(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private ObservableList<EmployeeReportView> loadReportDepartmentTable(List<Employee> employees) {
        ObservableList<EmployeeReportView> reportData = FXCollections.observableArrayList();

        for (Employee emp : employees) {
            EmployeeReportView data = loadEmployeeReport(emp);
            if (data != null) {
                reportData.add(data);
            }
        }

        return reportData;
    }

    private void loadDepartmentDetails(String departmentName) {
        try {
            Department dept = departmentDAO.getDepartmentByName(departmentName);
            if (dept != null) {
                departmentNameLable.setText(dept.getDepartmentName());
                departmentIntroductionLable.setText(dept.getDescription());
            }
        } catch (SQLException e) {
            showAlertWarning(e.getMessage());
        }
    }

    private void loadDepartmentEmployees() {
        int deptID = secletedDept.getDepartmentId();
        if (deptID > 0) {
            try {
                List<Employee> employees = employeeDAO.getEmployeeByDepartmentId(deptID);
                if (employees.size() > 0) {
                    departmentEmployeesNumberLable.setText(String.valueOf(employees.size()));
                }
                ObservableList<EmployeeReportView> reportData = loadReportDepartmentTable(employees);
                reportEmployeeTable.setItems(reportData);
            } catch (SQLException e) {
                showAlertWarning(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadAllEmployees() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            ObservableList<EmployeeReportView> reportData = loadReportDepartmentTable(employees);
            reportEmployeeTable.setItems(reportData);

        } catch (SQLException e) {
            showAlertWarning(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setEmployeeScrollLabels(EmployeeReportView employeeReport) {
        employeeIDLabel.setText(String.valueOf(employeeReport.getEmployeeId()));
        positionLabel.setText(String.valueOf(employeeReport.getPosition()));
        firstNameLabel.setText(String.valueOf(employeeReport.getFirstName()));
        lastNameLabel.setText(String.valueOf(employeeReport.getLastName()));
        emailLabel.setText(String.valueOf(employeeReport.getEmail()));
        phoneLabel.setText(String.valueOf(employeeReport.getPhone()));
        totalGrossPayLabel.setText(String.format("%.2f", employeeReport.getGrossPay()));
        totalNetPayLabel.setText(String.format("%.2f", employeeReport.getNetPay()));
    }

    /* Handl Event */
    @FXML
    private void handleRefresh() throws SQLException {
        if (departmentRadio.isSelected()) {
            // Department Search Mode
            String selectedDept = departmentComboBox.getValue();

            if (selectedDept == null || selectedDept.equals("All Departments")) {
                departmentDetailsVBox.setVisible(false);
                departmentDetailsVBox.setManaged(false);
                loadAllEmployees();
            } else {
                secletedDept = departmentDAO.getDepartmentByName(selectedDept);
                departmentDetailsVBox.setVisible(true);
                departmentDetailsVBox.setManaged(true);
                loadDepartmentDetails(selectedDept);
                loadDepartmentEmployees();
            }
        }

        if (employeeRadio.isSelected()) {
            departmentDetailsVBox.setVisible(false);
            departmentDetailsVBox.setManaged(false);
            // Filter by ID
            String searchID = employeeIdField.getText().trim();
            if (searchID.isEmpty())
                return;

            if (verifyInputType("int", searchID)) {
                int searchIntID = Integer.parseInt(searchID);
                try {
                    Employee result = employeeDAO.getEmployeeById(searchIntID);
                    if (result != null) {
                        employeeScrollPane.setVisible(true);
                        employeeScrollPane.setManaged(true);
                        EmployeeReportView reportData = loadEmployeeReport(result);
                        setEmployeeScrollLabels(reportData);
                    }
                } catch (SQLException e) {
                    showAlertWarning(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
