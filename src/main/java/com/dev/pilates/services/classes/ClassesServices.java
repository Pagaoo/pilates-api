package com.dev.pilates.services.classes;

import com.dev.pilates.entities.Classes;
import com.dev.pilates.repositories.ClassesRepository;
import org.springframework.stereotype.Service;

@Service
public class ClassesServices {
    private final ClassesRepository classesRepository;

    public ClassesServices(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    public Classes save(Classes classes) {
        return classesRepository.save(classes);
    }
}
