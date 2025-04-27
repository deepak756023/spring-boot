package com.practice.Assignment.entities.hr;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "offices")

public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "office_id")
	private int officeId;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@OneToMany(mappedBy = "office", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Employee> employees = new ArrayList<>();

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Office(int officeId, String address, String city, String state) {
		super();
		this.officeId = officeId;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public Office() {
		super();
	}

	@Override
	public String toString() {
		return "Office [officeId=" + officeId + ", address=" + address + ", city=" + city + ", state=" + state + "]";
	}

}