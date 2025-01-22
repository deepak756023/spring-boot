package com.springboot.assignment.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.assignment.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findByEmployeeId(int id);
    
    
}


