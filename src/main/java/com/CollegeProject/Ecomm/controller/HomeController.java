package com.CollegeProject.Ecomm.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.CollegeProject.Ecomm.model.Category;
import com.CollegeProject.Ecomm.model.Product;
import com.CollegeProject.Ecomm.model.UserDtls;
import com.CollegeProject.Ecomm.service.CategoryService;
import com.CollegeProject.Ecomm.service.ProductService;
import com.CollegeProject.Ecomm.service.UserService;
import com.CollegeProject.Ecomm.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //To Get  User details After Login
    @ModelAttribute
    //Principal Object Saves the Object of the Security
    public void getUserDetails(Principal p, Model m) {
       //Meaans that user id logged in
        if(p != null ){
            String email = p.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            m.addAttribute("user", userDtls);
        }

        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categorys", allActiveCategory);
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //to Render on Ui we use Model Object
    @GetMapping("/products")
    public String products(Model m , @RequestParam(value="category", defaultValue="") String category) {
        List<Category>  categories = categoryService.getAllActiveCategory();
        List<Product>  products= productService.getAllActiveProducts(category);

        //To render in UI
        m.addAttribute("categories", categories);
        m.addAttribute("products", products);
        m.addAttribute("paramValue", category);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model m) {
        Product product = productService.getProductById(id);
        m.addAttribute("product", product);
        return "view_product";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session ) throws IOException {
        //It will check if the file isEmpty then named it as default.jpg other wise save it's original name
        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();

        user.setProfileImage(imageName);
        UserDtls saveUser = userService.saveUser(user);

        if(!ObjectUtils.isEmpty(saveUser)){

            if(!file.isEmpty()){
                File saveFile =  new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+ File.separator + "profile_img" + File.separator
                + file.getOriginalFilename());
                System.out.println("User Image : "+path);
                Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);
            }

            session.setAttribute("succMsg", "Saved Successfully");
        }else {
            session.setAttribute("errorMsg", "Something went wrong on Server");
        }
        return "redirect:/register";
    }


    //Forgot Password Logic or Code
    @GetMapping("/forgot-password")
    public String showForgotPassword(){
        return "forgot_password";
    }
    
    //To send mail
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        UserDtls userByEmail = userService.getUserByEmail(email);

        if(ObjectUtils.isEmpty(userByEmail)){
            session.setAttribute("errorMsg", "Invalid email");
        } else {
            String resetToken = UUID.randomUUID().toString();
            userService.updateUserResetToken(email, resetToken);

            //Generate Url
            String url = CommonUtil.generateUrl(request)+"/reset-password?token="+resetToken;

            //To check Mail has been send or not.
           Boolean sendMail =  commonUtil.sendMail(url, email);

           if(sendMail){
               session.setAttribute("succMsg", "Please check your mail, Password reset link has been sent.");
           }else{
               session.setAttribute("errorMsg", "Something went wrong on Server mail not Sent.");
           }
        }
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token, HttpSession session, Model m ) {

        UserDtls userBytoken = userService.getUserByToken(token);
                //To check that User value or token is null or not
                if(ObjectUtils.isEmpty(userBytoken)){

                    m.addAttribute("msg", "Link is Invalid or Expired");

                    //we don't have to write this logic all again n again we can make a default page for that
//                    session.setAttribute("errrorMsg", "Invalid token or Url");
                    return "message";
                }

                //It will be sent out as hidden to pass it in the page
                m.addAttribute("token", token);

        return "reset_password";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,@RequestParam String password,  HttpSession session, Model m) {

        UserDtls userByToken = userService.getUserByToken(token);
        //Password must encoded and stored
        if(userByToken == null){

            //If token fails than it will throw this message
            m.addAttribute("errorMsg", "Link is Invalid or Expired");

            //Pass it into the Error page
            return "message";

        } else {
            //If token is found then it will encode the password
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null); //then it token will be null
            userService.updateUser(userByToken);    //Token will be update

            //to  show the message
            session.setAttribute("succMsg", "Password Changed Successfully.");
           m.addAttribute("msg", "Password Changed Successfully.");

            return "message";
        }
    }

}