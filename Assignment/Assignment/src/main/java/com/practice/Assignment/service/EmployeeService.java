package com.practice.Assignment.service;

import java.util.List;

import com.practice.Assignment.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practice.Assignment.entities.hr.Employee;
import com.practice.Assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.Assignment.repo.hr.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseEntity<ApiResponse<Employee>> getAllEmployees() {
        List<Employee> list = this.employeeRepository.findAll();
        String message = list.isEmpty() ? "No employees found" : "Employees fetched successfully";
        ApiResponse<Employee> response = new ApiResponse<>(
                "SUCCESS",
                message,
                list);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<Employee>> getAllEmployeesByPagination(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();// Ascending or Descending
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Employee> employee = this.employeeRepository.findAll(p);
        List<Employee> list = employee.getContent();

        String message = list.isEmpty() ? "No employees found" : "Employees fetched successfully";
        ApiResponse<Employee> response = new ApiResponse<>(
                "SUCCESS",
                message,
                list);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public void addEmployee(Employee e) {
        this.employeeRepository.save(e);
    }


    // Custom Exception - GlobalExceptionHandler - ErrorResponse
    public Employee findEmployeeById(int id) {
        return employeeRepository.findByEmployeeId(id)
                .orElseThrow(() -> new NoSuchEmployeeExistsException("NO EMPLOYEE PRESENT WITH ID = " + id));
    }

    public Employee updateEmployee(int id, Employee employee){

        Employee original = employeeRepository.findById(id).orElseThrow(() -> new NoSuchEmployeeExistsException("NO EMPLOYEE PRESENT WITH ID = " + id));
        original.setFirstName(employee.getFirstName());
        original.setLastName(employee.getLastName());
        original.setJobTitle(employee.getJobTitle());
        original.setSalary(employee.getSalary());
        original.setOffice(employee.getOffice());
        return employeeRepository.save(original);
    }


    public List<Employee> searchEmployees(String searchText) {
        return employeeRepository.findEmployeesByLastNameSearchText(searchText);
    }

    public List<Employee> globalSearchEmployees(String searchText) {
        return employeeRepository.findEmployeesByGlobalSearchText(searchText);
    }
}
