package com.dev.pilates.entities;

import com.dev.pilates.dtos.professor.ProfessorRequestDTO;
import com.dev.pilates.dtos.professor.ProfessorResponseDTO;
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

//a coluna is_admin foi retirada do modelo de dados, por enquanto não há sentido de existir

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "professors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 60, nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(nullable = false)
    private LocalDateTime updated_at;

    public ProfessorResponseDTO toProfessorResponseDTO() {
        return new ProfessorResponseDTO(
                this.username,
                this.email,
                this.role.getId()
        );
    }

    public ProfessorRequestDTO toProfessorRequestDTO() {
        return new ProfessorRequestDTO(
                this.username,
                this.email,
                this.password,
                this.role.getId()
        );
    }
}
