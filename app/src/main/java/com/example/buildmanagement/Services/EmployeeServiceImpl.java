package com.example.buildmanagement.Services;


import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Repositories.EmployeeRepositoryImpl;


import java.util.List;

/**
 * Реализация интерфейса EmployeeService для работы с сотрудниками.
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();

    /**
     * Возвращает список всех сотрудников.
     *
     * @return список сотрудников
     */
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * Возвращает сотрудника по указанному идентификатору.
     *
     * @param id идентификатор сотрудника
     * @return сотрудник
     */
    @Override
    public Employee findOneById(int id) {
        return employeeRepository.findOneById(id);
    }

    /**
     * Создает нового сотрудника.
     *
     * @param employee сотрудник для создания
     */
    @Override
    public int create(Employee employee) {
        int newId = employeeRepository.save(employee);
        return newId;
    }

    /**
     * Обновляет данные сотрудника.
     *
     * @param employee сотрудник для обновления
     */
    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }

    /**
     * Удаляет сотрудника по указанному идентификатору.
     *
     * @param id идентификатор сотрудника для удаления
     */
    @Override
    public void delete(int id) {
        employeeRepository.delete(id);
    }

    /**
     * Возвращает список сотрудников, работающих на указанном объекте строительства.
     *
     * @param objectId идентификатор объекта строительства
     * @return список сотрудников
     */
    @Override
    public List<Employee> getObjectEmployees(int objectId) {
        return employeeRepository.getObjectEmployees(objectId);
    }

    /**
     * Возвращает список свободных сотрудников, не работающих на объектах строительства.
     *
     * @return список свободных сотрудников
     */
    @Override
    public List<Employee> getFreeEmployees() {
        return employeeRepository.getFreeEmployees();
    }

    @Override
    public List<Employee> getForemanList() {
        return employeeRepository.getForemanList();
    }
}
