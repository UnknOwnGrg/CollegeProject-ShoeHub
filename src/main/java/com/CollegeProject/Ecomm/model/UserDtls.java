package com.CollegeProject.Ecomm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserDtls {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String mobileNumber;
    private String email ;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String password;
    private String profileImage;

    private String role;

    //For Email
    private String reset_token;
}
