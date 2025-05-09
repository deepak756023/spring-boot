package com.practice.assignment.repo.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.assignment.entities.store.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
