package com.doszke.learnapp.repositories;

import com.doszke.learnapp.data.dao.CardDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<CardDAO, Long>{

    Optional<CardDAO> findByFrontAndBack(String datum, String datum1);

}