package com.doszke.learnapp.repositories;

import com.doszke.learnapp.data.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {
    
    UserDAO findByEmail(String email);

    UserDAO findByUserName(String userName);

}
