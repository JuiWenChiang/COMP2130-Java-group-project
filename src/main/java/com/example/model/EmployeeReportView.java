package com.example.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeReportView {
    // Employee data
    private final IntegerProperty employeeId = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty position = new SimpleStringProperty();
    // Payroll data
    private final DoubleProperty grossPay = new SimpleDoubleProperty();
    private final DoubleProperty netPay = new SimpleDoubleProperty();

    public EmployeeReportView(int employeeId, String firstName, String lastName, String email, 
        String phone, String position, double grossPay, double netPay) {
        this.employeeId.set(employeeId);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.email.set(email);
        this.phone.set(phone);
        this.position.set(position);
        this.grossPay.set(grossPay);
        this.netPay.set(netPay);
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getPosition() {
        return position.get();
    }

    public double getGrossPay() {
        return grossPay.get();
    }

    public double getNetPay() {
        return netPay.get();
    }
}
