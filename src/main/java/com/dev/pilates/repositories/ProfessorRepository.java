package com.dev.pilates.repositories;

import com.dev.pilates.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>, JpaSpecificationExecutor<Professor> {
    Professor findProfessorByUsername(String username);
    Professor findProfessorByEmail(String email);
    Professor findProfessorById(Long professorId);
}
