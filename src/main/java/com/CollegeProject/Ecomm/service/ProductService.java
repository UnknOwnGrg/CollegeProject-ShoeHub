package com.CollegeProject.Ecomm.service;

import com.CollegeProject.Ecomm.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Boolean deleteProduct(int id);

    //It will get Any Product by It's Id
    public Product getProductById(int id);

    public Product updateProduct(Product product, MultipartFile file);

    //to Get all the Products that Are Active which means
    //Products that are on Sales.
    public List<Product> getAllActiveProducts(String category);

    // Get total count of products
    public long getTotalProductCount();

    // Get count of active products
    public long getActiveProductCount();
}
