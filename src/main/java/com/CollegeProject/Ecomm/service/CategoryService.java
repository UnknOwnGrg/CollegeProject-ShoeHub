package com.CollegeProject.Ecomm.service;


import com.CollegeProject.Ecomm.model.Category;

import java.util.List;

public interface CategoryService {

    //yo Duita interlinked hunxa
    public Category saveCategory(Category category);
    public Boolean existCategory(String categoryName);

    public List<Category> getAllCategory();

    public Boolean deleteCategory(int id);

    public Category getCategoryById(int id);

    //it will used to abstract the Active Category data
    public List<Category> getAllActiveCategory();

}
