package com.dev.pilates.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Class_at {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classes class_id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student_id;
}
