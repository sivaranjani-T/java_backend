package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/divum_Employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> getAllEmployee(){
        return  employeeRepository.findAll();
    }

   @PostMapping
    public Employee createEmployee( @RequestBody Employee employee){
       // System.out.println(emlpoyee);
       return employeeRepository.save(employee);
    }

    @GetMapping("verifyuser/{email}")
    public boolean verifyuser(@PathVariable String email){
        Optional<Employee> userOptional = employeeRepository.findByEmailId(email);
        return userOptional.isPresent();

    }



    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        Employee getEmployee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee does not exist"));
       return ResponseEntity.ok(getEmployee);
    }
   @PutMapping({"{id}"})
    public ResponseEntity<Employee> employee( @PathVariable long id,@RequestBody Employee employeeDetails){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee does not exist"));
       System.out.println(employeeDetails);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setMobileNumber(employeeDetails.getMobileNumber()+"");
        employee.setDate(employeeDetails.getDate());
        employee.setAddress(employeeDetails.getAddress());
        employeeRepository.save(employee);
       System.out.println(employee.toString());
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteemployee(@PathVariable long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee does not found"));
        employeeRepository.delete(employee);
       return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
