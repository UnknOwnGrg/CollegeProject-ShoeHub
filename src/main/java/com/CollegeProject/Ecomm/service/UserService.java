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

    // Get total count of users
    public long getTotalUserCount();

    // Get count of active users (users with ROLE_USER)
    public long getActiveUserCount();
}
