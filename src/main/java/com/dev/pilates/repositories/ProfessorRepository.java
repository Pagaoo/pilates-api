package com.dev.pilates.repositories;

import com.dev.pilates.dtos.professor.ProfessorResponseDTO;
import com.dev.pilates.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<ProfessorResponseDTO> findProfessorsByUsername(String username);
    Professor findProfessorByUsername(String username);
}
