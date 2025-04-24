package com.springboot.assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.assignment.entity.Employee;
import com.springboot.assignment.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	// When I applied basic authentication with a user ID and password,
	// it worked for GET requests but not for DELETE or POST requests.
	// This happens because CSRF (Cross-Site Request Forgery) applies less security to GET requests since they are read-only.
    // However, for requests that modify data, such as POST, PUT, and DELETE, CSRF protection is stricter.
	// To handle this, we need to use a CSRF token. // Development purpose only
    //This token changes frequently, making it difficult for attackers to exploit it, even if they gain access through malicious websites.
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}


	@GetMapping("/welcome-page")
	public String greet() {
		return "Welcome to the home page"; 
	}

	// Handler for getting All Employees
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		logger.info("inside getEmployees method..............................");
		List<Employee> employees = employeeService.getAllEmployees();
		// return ResponseEntity.of(Optional.of(employees));
		return employees;
	}

	// Handler for getting Particular Employee by id
	@GetMapping("/employee")
	public Employee getEmployeeById(@RequestParam Integer id) {
		Employee employee = employeeService.findEmployeeById(id);
		return employee;
	}

	//Handler for save or update an employee 
	@PostMapping("/admin/save-employee")
	public void addEmployee(@RequestBody Employee e) {
		employeeService.addEmployee(e);
	}

	//Handler for deleting an employee
	@DeleteMapping("/admin/employee")
	public void deleteEmployee(@RequestParam Integer id) {
		employeeService.deleteEmployee(id);
	}
	
	//Handler for save or update multiple rows
	@PostMapping("/admin/save-multi-employee")
	public void addMultiEmployee(@RequestBody List<Employee> list) {
		employeeService.saveMultiEmployee(list);
	}

}
