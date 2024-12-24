package com.dev.pilates.specifications;

import com.dev.pilates.entities.Professor;
import org.springframework.data.jpa.domain.Specification;

public class ProfessorSpecifications {
    public static Specification<Professor> usernameContainsIgnoreCase(String username){
        return ((root, _, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), "%"+username.toLowerCase()+"%"));
    }
}
