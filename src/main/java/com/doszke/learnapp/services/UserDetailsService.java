package com.doszke.learnapp.services;

import com.doszke.learnapp.data.dao.RoleDAO;
import com.doszke.learnapp.data.dao.UserDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    public UserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userService.findUserByUserName(username);
        List<GrantedAuthority> authorities = getUserAuthority(user);
        return buildUserForAuthentication(user, authorities);
    }

    @Transactional
    protected List<GrantedAuthority> getUserAuthority(UserDAO user) {
        Hibernate.initialize(user.getRoles());
        System.out.println(user.getRoles());
        Set<RoleDAO> userRoles = user.getRoles();
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (RoleDAO role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(UserDAO user, List<GrantedAuthority> authorities) {
        return new User(user.getUserName(), user.getPassword(), user.getActive(), true,
                true, true, authorities);
    }

}
