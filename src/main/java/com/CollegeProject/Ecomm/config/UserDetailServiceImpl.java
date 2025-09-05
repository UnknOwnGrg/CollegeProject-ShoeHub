package com.CollegeProject.Ecomm.config;

import com.CollegeProject.Ecomm.model.UserDtls;
import com.CollegeProject.Ecomm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //to check user exists or not
        UserDtls user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not Found");
        }

        //it will save the new Credentials of New USer.
        return new CustomUser(user);
    }
}
