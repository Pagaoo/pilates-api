package com.dev.pilates.specifications;

import com.dev.pilates.entities.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {
    public static Specification<Student> studentNameContainsIgnoreCase(String firstName) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.like(root.get("firstName"), "%" + firstName.toLowerCase() + "%");
    }
}
