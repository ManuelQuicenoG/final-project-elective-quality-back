package com.shelter.employeeback.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String name) {
        super(String.format("the %s employee does not exists", name));
    }
}
