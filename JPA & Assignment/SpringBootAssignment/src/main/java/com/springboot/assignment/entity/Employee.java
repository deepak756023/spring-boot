package com.springboot.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id")
	private int employeeId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "job_title")
	private String jobTitle;

	@Column(name = "salary")
	private int salary;


//	@Column(name = "office_id")
//	private int officeId;

	@ManyToOne
	@JoinColumn(name = "office_id")
	private Office office;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	

	

//	public int getOfficeId() {
//		return officeId;
//	}
//
//	public void setOfficeId(int officeId) {
//		this.officeId = officeId;
//	}

	public Employee(int employeeId, String firstName, String lastName, String jobTitle, int salary, int reportsTo,
			Office office) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.salary = salary;
		
		this.office = office;
	}

	public Employee() {
		super();
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", jobTitle=" + jobTitle + ", salary=" + salary + ", office=" + office
				+ "]";
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

}
