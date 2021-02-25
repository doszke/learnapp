package com.doszke.learnapp.repositories;

import com.doszke.learnapp.data.dao.CardCategoryDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardCategoryRepository extends CrudRepository<CardCategoryDAO, Long> {

    Optional<CardCategoryDAO> findByName(String datum);

    @Query(value = "SELECT c FROM category c join fetch c.cards WHERE c.name=?1")
    Optional<CardCategoryDAO> findByNameWithCards(String name);

}
