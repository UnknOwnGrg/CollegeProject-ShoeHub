package com.CollegeProject.Ecomm.controller;

import com.CollegeProject.Ecomm.model.Category;
import com.CollegeProject.Ecomm.model.UserDtls;
import com.CollegeProject.Ecomm.service.CategoryService;
import com.CollegeProject.Ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(){
        return "user/home";
    }

    //To Get  User details After Login
    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if(p!=null){
            String email = p.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            m.addAttribute("user", userDtls);
        }


        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categorys", allActiveCategory);
    }
}
