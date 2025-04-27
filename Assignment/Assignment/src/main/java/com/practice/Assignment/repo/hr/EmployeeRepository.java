package com.practice.Assignment.repo.hr;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.Assignment.entities.hr.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
     public Optional<Employee> findByEmployeeId(int id);
}
