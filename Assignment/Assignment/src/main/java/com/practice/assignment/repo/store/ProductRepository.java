package com.practice.assignment.repo.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.assignment.entities.store.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
