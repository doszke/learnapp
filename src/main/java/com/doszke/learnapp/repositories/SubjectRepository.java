package com.doszke.learnapp.repositories;

import com.doszke.learnapp.data.dao.SubjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectDAO, Long> {

    @Query(value = "SELECT s FROM subject s join fetch s.categories WHERE s.name=?1")
    Optional<SubjectDAO> findByNameWithCards(String name);

}
