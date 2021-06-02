package com.shelter.employeeback.service.interfaces;
import com.shelter.employeeback.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    Employee get(String name);
    Employee save(Employee employee);
    Employee replace(String name, Employee employee);
    void delete(String name);
}
