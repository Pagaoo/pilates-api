package com.dev.pilates.repositories;

import com.dev.pilates.entities.Class_at;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Class_atRepository extends JpaRepository<Class_at, Long> {
}
