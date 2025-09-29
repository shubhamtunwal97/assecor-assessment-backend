package com.assecor.assessment.repository;

import com.assecor.assessment.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT p FROM PersonEntity p WHERE LOWER(p.color) = LOWER(:color)")
    List<PersonEntity> findByColorIgnoreCase(@Param("color") String color);
}