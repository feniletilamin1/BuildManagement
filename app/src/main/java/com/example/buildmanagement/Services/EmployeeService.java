package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findOneById(int id);
    int create(Employee employee);
    void update(Employee employee);
    void delete(int id);
    List<Employee> getObjectEmployees(int objectId);
    List<Employee> getFreeEmployees();
    List<Employee> getForemanList();
}
