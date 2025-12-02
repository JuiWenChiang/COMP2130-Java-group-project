package com.example.controller;

import com.example.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class BaseController {

    /* Switch pages */
    protected void switchToView(String viewPath) {
        try {
            System.out.println("Preparing to switch pages: " + viewPath);
            App.setRoot(viewPath);
            System.out.println("Page switching successful!");
        } catch (IOException e) {
            System.err.println("Page switching failed:" + viewPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEmployeeManagement(ActionEvent event) throws IOException {
        App.setRoot("view/employee_management");  //  App.setRoot: Keep the scene size unchanged
    }

    @FXML
    private void switchToPayroll(ActionEvent event) throws IOException {
        App.setRoot("view/payroll");
    }

    @FXML
    private void switchToReport(ActionEvent event) throws IOException {
        App.setRoot("view/report");
    }

    /* Input Value */
    protected boolean verifyInputType(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            switch (type.toLowerCase()) {
                case "integer":
                case "int":
                    Integer.parseInt(value);
                    return true;

                case "double":
                case "decimal":
                    Double.parseDouble(value);
                    return true;

                case "boolean":
                    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                        return false;
                    }
                    return true;

                default:
                    return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected void setupNumericField(TextField textField, Double minValue, Double maxValue) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                change.setText("0");
                change.setRange(0, change.getControlText().length());
                return change;
            }

            if (newText.matches("\\d*\\.?\\d*")) {
                try {
                    if (!newText.endsWith(".")) {
                        double value = Double.parseDouble(newText);

                        if (minValue != null && maxValue != null) {
                            if (value >= minValue && value <= maxValue) {
                                return change;
                            } else {
                                String newValue = value > maxValue ? String.valueOf(maxValue)
                                        : String.valueOf(minValue);
                                change.setText(newValue);
                                change.setRange(0, change.getControlText().length());
                                return change;
                            }
                        }
                    } else {
                        return change;
                    }
                } catch (NumberFormatException e) {
                    change.setText("0");
                    change.setRange(0, change.getControlText().length());
                    return change;
                }
            }

            change.setText("0");
            change.setRange(0, change.getControlText().length());
            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    /* UI Tool */
    protected Alert showAlertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }

}