package com.doszke.learnapp.repositories;

import com.doszke.learnapp.data.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

    Optional<UserDAO> findByEmail(String email);

    Optional<UserDAO> findByUserName(String userName);

    @Query(value = "SELECT u FROM user u join fetch u.roles WHERE u.userName=?1")
    Optional<UserDAO> test(String userName);

}
