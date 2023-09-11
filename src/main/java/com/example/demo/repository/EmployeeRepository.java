package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface EmployeeRepository  extends JpaRepository<Employee,Long> {
    //all db operations
//  Optional<Employee> findByEmailId(String email);

    Optional<Employee> findByEmailId(String email);

}
