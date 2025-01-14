package com.dev.pilates.repositories;

import com.dev.pilates.entities.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
    Classes findClassesById(Long id);
}
