package com.CollegeProject.Ecomm.repository;

import com.CollegeProject.Ecomm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsActiveTrue();

    //It will help to find the Category in Db.
    List<Product> findByCategory(String category);
}
