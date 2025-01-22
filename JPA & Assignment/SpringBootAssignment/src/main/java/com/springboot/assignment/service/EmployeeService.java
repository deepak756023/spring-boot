package com.springboot.assignment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.assignment.controller.EmployeeController;
import com.springboot.assignment.entity.Employee;
import com.springboot.assignment.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		logger.info("danger...............................");
		return this.employeeRepository.findAll();
	}

	public Employee findEmployeeById(int id) {
		return this.employeeRepository.findByEmployeeId(id);
	}

	public void addEmployee(Employee e) {
		this.employeeRepository.save(e);
	}

	public void deleteEmployee(int id) {
		this.employeeRepository.deleteById(id);
	}

	public void saveMultiEmployee(List<Employee> employees) {
		this.employeeRepository.saveAll(employees);
	}

}
