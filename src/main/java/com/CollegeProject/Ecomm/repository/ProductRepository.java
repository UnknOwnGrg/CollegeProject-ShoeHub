package com.CollegeProject.Ecomm.repository;

import com.CollegeProject.Ecomm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsActiveTrue();

    //It will help to find the Category in Db.
    List<Product> findByCategory(String category);
    
    // Find active products by category name
    @Query("SELECT p FROM Product p WHERE p.category = :categoryName AND p.isActive = true")
    List<Product> findByCategoryNameAndIsActiveTrue(@Param("categoryName") String categoryName);
}
