package com.CollegeProject.Ecomm.service;

import com.CollegeProject.Ecomm.model.UserDtls;

public interface UserService {

    public UserDtls saveUser(UserDtls user);

    //To get which user has been logged in
    public UserDtls getUserByEmail(String email);

    void updateUserResetToken(String email, String resetToken);

    public UserDtls getUserByToken(String token);

    //TO update the UserDetails
    //used after saving the password credentials
    public UserDtls updateUser(UserDtls user);
}
