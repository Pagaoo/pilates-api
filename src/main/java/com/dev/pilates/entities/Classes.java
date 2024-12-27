package com.dev.pilates.entities;

import com.dev.pilates.ENUMS.ClassesHoursEnum;
import com.dev.pilates.ENUMS.WeekDaysEnum;
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
    private Professor professor_id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student_id;
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

}
