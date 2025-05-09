package com.practice.assignment.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practice.assignment.entities.hr.Employee;
import com.practice.assignment.response.ApiResponse;
import com.practice.assignment.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin("*")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

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

    @PostMapping("employees/dump_excel")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        return employeeService.save(file);
    }

    @GetMapping("/employees/export_excel")
    public ResponseEntity<Resource> download() throws IOException {
        String fileName = "employees_export.xlsx";

        ByteArrayInputStream actualData = employeeService.getDataToExcel();
        InputStreamResource file = new InputStreamResource(actualData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " +fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
