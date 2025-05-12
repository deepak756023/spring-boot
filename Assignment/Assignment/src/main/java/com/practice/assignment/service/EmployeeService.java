package com.practice.assignment.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.practice.assignment.exception.custom_exception.ExcelColumnMismatch;
import com.practice.assignment.helper.ExcelHelper;
import com.practice.assignment.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.practice.assignment.entities.hr.Employee;
import com.practice.assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.assignment.repo.hr.EmployeeRepository;
import org.springframework.web.multipart.MultipartFile;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ExcelHelper excelHelper;

    public EmployeeService(EmployeeRepository employeeRepository, ExcelHelper excelHelper) {
        this.employeeRepository = employeeRepository;
        this.excelHelper = excelHelper;
    }

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



    //Export to Excel
    public ByteArrayInputStream getDataToExcel() throws IOException {
        List<Employee> all = employeeRepository.findAll();
        return ExcelHelper.dataToExcel(all);


    }

    public ResponseEntity<Map<String, Object>> save(MultipartFile file) {
        String status = "status";
        String error = "error";
        String message = "message";
        String details = "details";
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.unmodifiableMap(Map.of(
                        status, error,
                        message, "Please select a file to upload"
                )));
            }

            try (InputStream inputStream = file.getInputStream()) {
                List<Employee> employees = excelHelper.convertExcelToListOfEmployee(inputStream);
                employeeRepository.saveAll(employees);

                return ResponseEntity.ok(Collections.unmodifiableMap(Map.of(
                        status, "success",
                        message, "File uploaded successfully",
                        "recordsProcessed", employees.size()
                )));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.unmodifiableMap(Map.of(
                    status, error,
                    message, "Excel file structure issue",
                    details, e.getMessage() != null ? e.getMessage() : "No details available"
            )));
        } catch (ExcelColumnMismatch e) { // Changed to follow naming conventions
            return ResponseEntity.badRequest().body(Collections.unmodifiableMap(Map.of(
                    status, error,
                    message, "Excel column validation failed",
                    details, e.getMessage() != null ? e.getMessage() : "No detail available"
            )));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.unmodifiableMap(Map.of(
                    status, error,
                    message, "Failed to process Excel file",
                    details, e.getMessage() != null ? e.getMessage() : "No details available"
            )));
        }
    }
}
