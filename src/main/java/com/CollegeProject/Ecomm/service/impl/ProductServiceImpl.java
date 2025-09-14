package com.CollegeProject.Ecomm.service.impl;

import com.CollegeProject.Ecomm.model.Product;
import com.CollegeProject.Ecomm.repository.ProductRepository;
import com.CollegeProject.Ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(int id) {
        Product product = productRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(product)) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    //For Product Edit
    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Product dbProduct = getProductById(product.getId());

        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());     //Update Title
        dbProduct.setDescription(product.getDescription()); //Update Description
        dbProduct.setCategory(product.getCategory());   //Update Category
        dbProduct.setPrice(product.getPrice());     //Update Price
        dbProduct.setStock(product.getStock());     //Update Stock
        dbProduct.setImage(imageName);      //Updaet Image
        dbProduct.setIsActive(product.getIsActive());   //Update Status

        Product updateProduct = productRepository.save(dbProduct);  //It will save the Product finally.

        if (!ObjectUtils.isEmpty(updateProduct)) {

            if (!image.isEmpty()) {

                //To upload the Image in the computer files
                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + image.getOriginalFilename());
                    System.out.println(path);
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return product;
        }

        return null;
    }

    //To Get All Active products in UI
    @Override
    public List<Product> getAllActiveProducts(String category) {
        if (ObjectUtils.isEmpty(category) || category.trim().isEmpty()) {
            return productRepository.findByIsActiveTrue();
        } else {
            return productRepository.findByCategoryNameAndIsActiveTrue(category.trim());
        }
    }

    @Override
    public long getTotalProductCount() {
        return productRepository.count();
    }

    @Override
    public long getActiveProductCount() {
        return productRepository.findByIsActiveTrue().size();
    }
}