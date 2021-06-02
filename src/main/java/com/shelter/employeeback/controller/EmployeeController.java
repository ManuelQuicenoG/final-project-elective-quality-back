package com.shelter.employeeback.controller;

import com.shelter.employeeback.controller.dto.CreateEmployeeBodyDto;
import com.shelter.employeeback.controller.dto.EmployeeDto;
import com.shelter.employeeback.controller.dto.UpdateEmployeeBodyDto;
import com.shelter.employeeback.domain.Employee;
import com.shelter.employeeback.exceptions.EmployeeNotFoundException;
import com.shelter.employeeback.exceptions.DataConflictException;
import com.shelter.employeeback.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {})
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<Collection<EmployeeDto>> listEmployees() {
        var employees = employeeService.getAll();
        var dtos = employees.stream().map(employee -> map(employee)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/employees/{name}")
    public ResponseEntity<?> getEmployee(@PathVariable("name") String name) {
        try {
            var employee = employeeService.get(name);

            return ResponseEntity.status(HttpStatus.OK).body(map(employee));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The employee called %s does not exists", name));
        }
    }

    @DeleteMapping("/employees/{name}")
    public ResponseEntity deleteEmployee(@PathVariable("name") String name) {
        try {
            employeeService.delete(name);
            return ResponseEntity.noContent().build();
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The employee called %s does not exists", name));
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<?> saveEmployee(@RequestBody CreateEmployeeBodyDto employeeDto) {
        try {
            var employee = employeeService.save(map(employeeDto));
            for(int i = 0; i<employee.getCourses().length; i++){
            }
            return new ResponseEntity<EmployeeDto>(map(employee), HttpStatus.CREATED);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("The employee called %s has already been created", employeeDto.getName()));
        }
    }

    @PutMapping("/employees/{name}")
    public ResponseEntity updateEmployee(@PathVariable("name") String name, @RequestBody UpdateEmployeeBodyDto employeeDto) {
        try {
            var updatedEmployee = employeeService.replace(name, map(employeeDto));
            return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The employee does not exist");
        } catch (DataConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The name can not be modified");
        }
    }

    private EmployeeDto map(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getRolPosition(),
                employee.getGender(),
                employee.isHasCourses(),
                employee.getCourses());
    }

    private Employee map(CreateEmployeeBodyDto dto) {
        return new Employee(
                dto.getName(),
                dto.getRolPosition(),
                dto.getGender(),
                dto.isHasCourses(),
                dto.getCourses());
    }

    private Employee map(UpdateEmployeeBodyDto dto) {
        return new Employee(
                dto.getId(),
                dto.getName(),
                dto.getRolPosition(),
                dto.getGender(),
                dto.isHasCourses(),
                dto.getCourses());
    }
}
