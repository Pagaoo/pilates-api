package com.dev.pilates.entities;

import com.dev.pilates.ENUMS.ClassesHoursEnum;
import com.dev.pilates.ENUMS.WeekDaysEnum;
import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.dtos.classes.ClassesResponseDTO;
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
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
    @ManyToMany
    @JoinTable(
            name = "classes_students",
            joinColumns = @JoinColumn(name = "classes_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeekDaysEnum weekday;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassesHoursEnum class_hour;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(nullable = false)
    private LocalDateTime updated_at;


    public ClassesRequestDTO toClassesRequestDTO() {
        return new ClassesRequestDTO(
                this.id,
                this.professor.getId(),
                this.students.stream().map(Student::getId).toList(),
                this.weekday,
                this.class_hour
        );
    }

    public ClassesResponseDTO toClassesResponseDTO() {
        return new ClassesResponseDTO(
                this.id,
                this.professor.getId(),
                this.students.stream().map(Student::getId).toList(),
                this.weekday,
                this.class_hour
        );
    }
}
