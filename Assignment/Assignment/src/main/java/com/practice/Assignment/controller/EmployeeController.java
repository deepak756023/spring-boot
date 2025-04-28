package com.practice.Assignment.controller;

import java.util.List;
import java.util.Map;

import com.practice.Assignment.helper.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practice.Assignment.entities.hr.Employee;
import com.practice.Assignment.response.ApiResponse;
import com.practice.Assignment.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ResponseEntity
    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<Employee>> getEmployees() {
        return this.employeeService.getAllEmployees();
    }

    // PAGINATION AND SORTING
    @GetMapping("/employees_pagination_sorting")
    public ResponseEntity<ApiResponse<Employee>> getEmployeesByPagination(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "employeeId", required = false) String sortBy,
            @RequestParam(value ="sortDir", defaultValue = "true", required = false) boolean ascending
            ) {
        return  this.employeeService.getAllEmployeesByPagination(pageNumber, pageSize, sortBy, ascending);
    }

    @PostMapping("/save-employee")
    public void addEmployee(@RequestBody Employee e) {
        employeeService.addEmployee(e);
    }


    @GetMapping("/employee")
    public Employee getEmployeeById(@RequestParam Integer id) {
        return employeeService.findEmployeeById(id);
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee employee){
        Employee employee1 =  employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

    // COLUMN SEARCH
    @GetMapping("/lastname-search")
    public ResponseEntity<List<Employee>> searchEmployeeByLastName(@RequestParam String searchText)
    {
        List<Employee> foundEmployees = employeeService.searchEmployees(searchText);
        if (!foundEmployees.isEmpty()) {
            return ResponseEntity.ok(foundEmployees);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/global-search")
    public ResponseEntity<List<Employee>> globalSearchEmployee(@RequestParam String searchText)
    {
        List<Employee> foundEmployees = employeeService.globalSearchEmployees(searchText);
        if (!foundEmployees.isEmpty()) {
            return ResponseEntity.ok(foundEmployees);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/employees/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.checkExcelFormat(file)) {

            this.employeeService.save(file);

            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));


        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }
}
