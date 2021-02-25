package com.doszke.learnapp.services;

import com.doszke.learnapp.data.dao.RoleDAO;
import com.doszke.learnapp.data.dao.UserDAO;
import com.doszke.learnapp.repositories.RoleRepository;
import com.doszke.learnapp.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDAO findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDAO findUserByUserName(String userName) {
        return userRepository.getUserWithRoles(userName).orElse(null);
    }

    public UserDAO saveUser(UserDAO user){
        System.out.println(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        user.setActive(true);
        RoleDAO role = roleRepository.findByRole("USER").orElse(null);
        if (role != null) {
            user.setRoles(new HashSet<RoleDAO>(Arrays.asList(role)));
            return userRepository.save(user);
        }
        return null;
    }

}
