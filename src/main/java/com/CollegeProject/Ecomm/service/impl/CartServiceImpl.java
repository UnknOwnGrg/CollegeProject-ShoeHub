package com.CollegeProject.Ecomm.service.impl;

import com.CollegeProject.Ecomm.model.CartDb;
import com.CollegeProject.Ecomm.model.Product;
import com.CollegeProject.Ecomm.model.UserDtls;
import com.CollegeProject.Ecomm.repository.CartRepository;
import com.CollegeProject.Ecomm.repository.ProductRepository;
import com.CollegeProject.Ecomm.repository.UserRepository;
import com.CollegeProject.Ecomm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public CartDb saveCart(int productId, int userId) {
        UserDtls userDtls = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();

        //Quantity Logic/Custom Logic to find It is present in the Database or not.
        CartDb cartDbStatus = cartRepository.findByProductIdAndUserId(productId, userId);

        //to insert
        CartDb cartDb = null;
        if(ObjectUtils.isEmpty(cartDbStatus)){
            cartDb = new CartDb();
            cartDb.setProduct(product);
            cartDb.setUser(userDtls);
            cartDb.setQuantity(1);
            cartDb.setTotalPrice(1*product.getPrice());
        } else{
            cartDb = cartDbStatus;
            cartDb.setQuantity(cartDb.getQuantity()+1);     //to increase the quanitity in cartDb.
            cartDb.setTotalPrice(cartDb.getQuantity() * cartDb.getProduct().getPrice());
        }

        CartDb saveCartDb = cartRepository.save(cartDb);
        return saveCartDb;
    }

    @Override
    public List<CartDb> getCartsByUser(int userId) {
        return List.of();
    }
}
