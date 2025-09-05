package com.CollegeProject.Ecomm.service;

import com.CollegeProject.Ecomm.model.UserDtls;

public interface UserService {

    public UserDtls saveUser(UserDtls user);

    //To get which user has been logged in
    public UserDtls getUserByEmail(String email);

    void updateUserResetToken(String email, String resetToken);
}
