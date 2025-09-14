package com.CollegeProject.Ecomm.service.impl;

import com.CollegeProject.Ecomm.model.UserDtls;
import com.CollegeProject.Ecomm.repository.UserRepository;
import com.CollegeProject.Ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDtls saveUser(UserDtls user) {
        user.setRole("ROLE_USER");  //Setting the Role;
        String encodePassword = passwordEncoder.encode(user.getPassword()); //Hashing the Password
        user.setPassword(encodePassword); //Then setting the Password in the Database
        UserDtls savedUser = userRepository.save(user);

        return savedUser;//then save
    }



    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //It will create some random tokens.
    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserDtls findByEmail =  userRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        userRepository.save(findByEmail);
    }

    //it will find the userToken by the help of Jpa UserRepository
    @Override
    public UserDtls getUserByToken(String token) {
        return userRepository.findByResetToken(token);
    }

    //it will update the UserDetails.
    @Override
    public UserDtls updateUser(UserDtls user) {
        return userRepository.save(user);
    }

    @Override
    public long getTotalUserCount() {
        return userRepository.count();
    }

    @Override
    public long getActiveUserCount() {
        // Count users with ROLE_USER (excluding admins)
        return userRepository.findAll().stream()
                .filter(user -> "ROLE_USER".equals(user.getRole()))
                .count();
    }
}
