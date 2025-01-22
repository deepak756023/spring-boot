package com.springboot.assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.assignment.entity.Employee;
import com.springboot.assignment.service.EmployeeService;

@RestController
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

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
	@PostMapping("/save-employee")
	public void addEmployee(@RequestBody Employee e) {
		employeeService.addEmployee(e);
	}

	//Handler for deleting an employee
	@DeleteMapping("/employee")
	public void deleteEmployee(@RequestParam Integer id) {
		employeeService.deleteEmployee(id);
	}
	
	//Handler for save or update multiple rows
	@PostMapping("/save-multi-employee")
	public void addMultiEmployee(@RequestBody List<Employee> list) {
		employeeService.saveMultiEmployee(list);
	}

}
