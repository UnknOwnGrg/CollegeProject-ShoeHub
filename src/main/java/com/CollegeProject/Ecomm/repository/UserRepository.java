package com.CollegeProject.Ecomm.repository;

import com.CollegeProject.Ecomm.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Integer> {
    public UserDtls findByEmail(String email);

    public UserDtls findByResetToken(String token);

}
