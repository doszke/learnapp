package com.doszke.learnapp;

import com.doszke.learnapp.data.dao.SubjectDAO;
import com.doszke.learnapp.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LearnappApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnappApplication.class, args);
    }

}
