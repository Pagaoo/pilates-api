package com.dev.pilates.entities;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role_id;
    @Column(nullable = false)
    private Boolean is_active;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(nullable = false)
    private LocalDateTime updated_at;


    public StudentRequestDTO toStudentRequestDTO() {
        return new StudentRequestDTO(
                this.firstName,
                this.lastName,
                this.role_id.getId(),
                this.is_active,
                this.created_at,
                this.updated_at
        );
    }

    public StudentResponseDTO toStudentResponseDTO() {
        return new StudentResponseDTO(
                this.id,
                this.firstName,
                this.lastName,
                this.role_id.getId(),
                this.is_active
        );
    }
}
