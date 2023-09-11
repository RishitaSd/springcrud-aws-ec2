package com.example.springcruddemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.springcruddemo.entity.Employee;
import com.example.springcruddemo.controller.EmployeeController;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.springcruddemo.App.class)

public class AppTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void testGetEmployeeById() {
        Employee newEmployee = new Employee("John", "Doe");
        Employee createdEmployee = employeeController.createEmployee(newEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(createdEmployee.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee retrievedEmployee = response.getBody();
        assertNotNull(retrievedEmployee);
        assertEquals("John", retrievedEmployee.getFirstName());
        assertEquals("Doe", retrievedEmployee.getLastName());
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = employeeController.getAllEmployees();
        assertNotNull(employees);
        assertTrue(employees.isEmpty()); // Assuming the list should be empty initially
    }

    @Test
    public void testUpdateEmployee() {
        Employee newEmployee = new Employee("Alice", "Smith");
        Employee createdEmployee = employeeController.createEmployee(newEmployee);

        Employee updatedEmployee = new Employee("Updated", "Name");
        ResponseEntity<Employee> response = employeeController.updateEmployee(createdEmployee.getId(), updatedEmployee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Employee result = response.getBody();

        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("Name", result.getLastName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee newEmployee = new Employee("Jane", "Doe");
        Employee createdEmployee = employeeController.createEmployee(newEmployee);

        employeeController.deleteEmployee(createdEmployee.getId());
        ResponseEntity<Employee> deletedEmployeeResponse = employeeController.getEmployeeById(createdEmployee.getId());

        assertEquals(HttpStatus.NOT_FOUND, deletedEmployeeResponse.getStatusCode());
    }

    //
}
