package com.CollegeProject.Ecomm.repository;

import com.CollegeProject.Ecomm.model.CartDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartDb, Integer> {
    public CartDb findByProductIdAndUserId(int  productId, int  userId);
}
