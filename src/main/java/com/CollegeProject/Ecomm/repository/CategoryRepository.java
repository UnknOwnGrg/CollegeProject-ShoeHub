package com.CollegeProject.Ecomm.repository;

import com.CollegeProject.Ecomm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //It just provides a query that will say that if user exists then true or false
    public Boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
}
