package com.practice.assignment.repo.hr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practice.assignment.entities.hr.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
     public Optional<Employee> findByEmployeeId(int id);

     @Query(value = "SELECT * FROM employees " +
             "WHERE LOWER(last_name) LIKE LOWER(CONCAT('%', :searchText, '%'))" +
             " LIMIT 5", nativeQuery = true)
     List<Employee> findEmployeesByLastNameSearchText(@Param("searchText") String searchText);

     @Query(value = "SELECT * FROM employees " +
             "WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
             "OR LOWER(last_name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
             "OR LOWER(job_title) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
             "OR LOWER(salary) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
             "OR LOWER(office_id) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
             "LIMIT 5", nativeQuery = true)
     List<Employee> findEmployeesByGlobalSearchText(@Param("searchText") String searchText);



}
