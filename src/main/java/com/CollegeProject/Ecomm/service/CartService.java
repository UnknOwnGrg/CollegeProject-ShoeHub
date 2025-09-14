package com.CollegeProject.Ecomm.service;

import com.CollegeProject.Ecomm.model.CartDb;

import java.util.List;

public interface CartService {
    //to save the Carts
    public CartDb saveCart(int productId, int userId);

    //to show the carts
    public List<CartDb> getCartsByUser(int userId);
}
