package com.shelter.employeeback.service;

import com.shelter.employeeback.domain.Employee;
import com.shelter.employeeback.exceptions.EmployeeNotFoundException;
import com.shelter.employeeback.exceptions.DataConflictException;
import com.shelter.employeeback.model.EmployeeDao;
import com.shelter.employeeback.repository.EmployeeRepository;
import com.shelter.employeeback.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAll() {
        var employeesDao = repository.findAll();
        System.out.println(employeesDao.iterator().next().getRolPosition());
        return StreamSupport
                .stream(employeesDao.spliterator(), false)
                .map(dao -> this.map(dao)).collect(Collectors.toList());
    }

    @Override
    public Employee get(String name) {
        var employeeDao = repository.findByName(name);

        if (employeeDao == null) {
            throw new EmployeeNotFoundException(name);
        }

        return map(employeeDao);
    }

    @Override
    public Employee save(Employee employee) {
        var dao = map(employee);

        var saved = repository.save(dao);
        for(int i = 0; i < employee.getCourses().length; i++){
            try {
                repository.insert(saved.getId(),repository.search(employee.getCourses()[i]));
            }catch (Exception e){
            }
        }
        return map(saved);
    }

    @Override
    public Employee replace(String name, Employee employee) {

        var oldEmployee = repository.findByName(name);

        var otherEmployee = repository.findByName(employee.getName());

        if (oldEmployee == null) {
            throw new EmployeeNotFoundException(name);
        }

        if (employee.getName() != null && otherEmployee!=null && otherEmployee!=oldEmployee) {
            throw new DataConflictException();
        }

        if (employee.getName() == null) {
            employee.setName(name);
        }

        EmployeeDao newDao = map(employee);
        newDao.setId(oldEmployee.getId());
        try {
            repository.delete(oldEmployee.getId());
        }catch (Exception e){
        }
        repository.save(newDao);
        newDao.setCourses(new ArrayList<>());
        for(int i = 0; i < employee.getCourses().length; i++){
            System.out.println(employee.getCourses()[i]);

            try {
                System.out.println(repository.search(employee.getCourses()[i]));
                repository.insert(oldEmployee.getId(),repository.search(employee.getCourses()[i]));
            }catch (Exception e){
            }
        }



        return employee;
    }

    @Override
    public void delete(String name) {
        var dao = repository.findByName(name);

        if (dao == null) {
            throw new EmployeeNotFoundException(name);
        }

        repository.delete(dao);
    }

    private Employee map(EmployeeDao dao) {
        var coursesDao = dao.getCourses();

        var courses = coursesDao == null ? new String[0] :
                coursesDao.stream().map(courseDao -> courseDao.getName()).toArray(size -> new String[size]);

        return new Employee(
                dao.getId(),
                dao.getName(),
                dao.getRolPosition(),
                dao.getGender(),
                dao.isHasCourses(),
                courses
        );
    }

    private EmployeeDao map(Employee employee) {
        return new EmployeeDao(
                employee.getName(),
                employee.getRolPosition(),
                employee.getGender(),
                employee.isHasCourses());
    }
}
