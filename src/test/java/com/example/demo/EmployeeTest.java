package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testgetallemployee() {
        List<Employee> employee = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("aaa");
        employee1.setEmailId("abc@gmail.com");
        employee.add(employee1);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("bbb");
        employee2.setEmailId("bbb@gmail.com");
        employee.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employee);
        List<Employee> result = employeeController.getAllEmployee();
        assertEquals(2, result.size());
        assertEquals("aaa", result.get(0).getFirstName());
        assertEquals("abc@gmail.com", result.get(0).getEmailId());
        assertEquals("bbb", result.get(1).getFirstName());
        assertEquals("bbb@gmail.com", result.get(1).getEmailId());

    }

    @Test
    public void testDeleteEmployeeSuccess() {
        long id = 1L;
        Employee employeeToDelete = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employeeToDelete));
        ResponseEntity<Employee> response = employeeController.deleteemployee(id);
        verify(employeeRepository, times(1)).delete(employeeToDelete);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void testDeleteEmployee_NotFound() {
        long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeController.deleteemployee(id));
    }

    @Test
    public void testfindbyid() {
        long id = 2L;
        Employee employee = new Employee();
        employee.setId(2L);
        employee.setFirstName("bbb");
        employee.setEmailId("bbb@gmail.com");
        // Employee.add(employee);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        ResponseEntity<Employee> result = employeeController.getEmployee(id);

        assertEquals("bbb", result.getBody().getFirstName());
        assertEquals("bbb@gmail.com", result.getBody().getEmailId());
    }

    @Test
    public void testfind_NotFound() {
        long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeController.deleteemployee(id));

    }
    @Test
    public  void saveemp(){
       Employee employee2=new Employee();
        employee2.setId(2L);
        employee2.setFirstName("bbb");
        employee2.setEmailId("bbb@gmail.com");
        employeeController.createEmployee(employee2);
        verify(employeeRepository,times(1)).save(employee2);

    }

    @Test
    public void testUpdateEmployee() {
        long id = 2L;

        // Create a sample employee object with updated information
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setFirstName("UpdatedFirstName");
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmailId("updated.email@example.com");
        updatedEmployee.setMobileNumber("1234567890");
        updatedEmployee.setDate("2023-09-06");
        updatedEmployee.setAddress("Updated Address");

        // Mock the behavior of employeeRepository to return the existing employee
        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));

        // Invoke the controller method
        ResponseEntity<Employee> responseEntity = employeeController.employee(id, updatedEmployee);

        // Assert the HTTP status code
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify that the employee in the response matches the updatedEmployee
        Employee updatedEmployeeResponse = responseEntity.getBody();
        assertEquals(updatedEmployee.getId(), updatedEmployeeResponse.getId());
        assertEquals(updatedEmployee.getFirstName(), updatedEmployeeResponse.getFirstName());
        assertEquals(updatedEmployee.getLastName(), updatedEmployeeResponse.getLastName());
        assertEquals(updatedEmployee.getEmailId(), updatedEmployeeResponse.getEmailId());
        assertEquals(updatedEmployee.getMobileNumber(), updatedEmployeeResponse.getMobileNumber());
        assertEquals(updatedEmployee.getDate(), updatedEmployeeResponse.getDate());
        assertEquals(updatedEmployee.getAddress(), updatedEmployeeResponse.getAddress());
    }
    @Test
    public void testVerifyUserWithEmailExists() {
        // Replace with a valid email that exists in your test data
        String validEmail = "test@example.com";

        // Mock the behavior of employeeRepository
        when(employeeRepository.findByEmailId(validEmail)).thenReturn(Optional.of(new Employee()));

        // Call the method being tested
        boolean result = employeeController.verifyuser(validEmail);

        // Assert that the result is true since the email exists
        assertTrue(result);
    }

    @Test
    public void testVerifyUserWithEmailDoesNotExist() {
        // Replace with an email that doesn't exist in your test data
        String invalidEmail = "nonexistent@example.com";

        // Mock the behavior of employeeRepository
        when(employeeRepository.findByEmailId(invalidEmail)).thenReturn(Optional.empty());

        // Call the method being tested
        boolean result = employeeController.verifyuser(invalidEmail);

        // Assert that the result is false since the email doesn't exist
        assertFalse(result);
    }

        }

