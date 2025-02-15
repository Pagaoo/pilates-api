package com.dev.pilates.entities;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

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
    @NotBlank(message = "O primeiro nome não pode estar vazio")
    @Size(min = 3, message = "O primeiro nome tem que ter no minímo 3 caracteres.")
    private String firstName;
    @Column(length = 50, nullable = false)
    @NotBlank(message = "O sobrenome não pode estar vazio")
    @Size(min = 3, message = "O sobrenome tem que ter no minímo 3 caracteres.")
    private String lastName;
    @Column(nullable = false)
    @NotNull(message = "O status do aluno não pode estar vazio")
    private Boolean is_active;
    @ManyToMany(mappedBy = "students")
    private List<Classes> classesList;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
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
                this.is_active
        );
    }

    public StudentResponseDTO toStudentResponseDTO() {
        return new StudentResponseDTO(
                this.id,
                this.firstName,
                this.lastName,
                this.is_active
        );
    }
}
