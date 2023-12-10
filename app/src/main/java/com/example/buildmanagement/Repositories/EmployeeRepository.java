package com.example.buildmanagement.Repositories;



import com.example.buildmanagement.Models.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();
    Employee findOneById(int id);
    int save(Employee employee);
    int update(Employee employee);
    int delete(int id);
    List<Employee> getForemanList();
    List<Employee> getFreeEmployees();
    List<Employee> getObjectEmployees(int objectId);
}
