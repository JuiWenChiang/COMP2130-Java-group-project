package com.example.controller;

import com.example.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class BaseController {

    // general page switching methods
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
        App.setRoot("view/employee_management");
    }

    @FXML
    private void switchToPayroll(ActionEvent event) throws IOException {
        App.setRoot("view/payroll"); // Keep the scene size unchanged
    }

    // Reservation (name can be changed)
    // @FXML
    // protected void switchToReport(ActionEvent event) {
    // switchToView("view/report");
    // }
}