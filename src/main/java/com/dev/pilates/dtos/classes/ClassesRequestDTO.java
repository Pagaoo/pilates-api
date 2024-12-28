package com.dev.pilates.dtos.classes;

import com.dev.pilates.ENUMS.ClassesHoursEnum;
import com.dev.pilates.ENUMS.WeekDaysEnum;
import com.dev.pilates.entities.Classes;
import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Student;

import java.time.LocalDateTime;
import java.util.List;

public record ClassesRequestDTO(Long id, Long professorId, List<Long> studentsId, WeekDaysEnum weekDaysEnum, ClassesHoursEnum classesHoursEnum) {
    public Classes toClasses(Professor professor, List<Student> students) {
        Classes classes = new Classes();
        classes.setId(this.id);
        classes.setProfessor(professor);
        classes.setStudents(students);
        classes.setWeekday(this.weekDaysEnum);
        classes.setClass_hour(this.classesHoursEnum);
        classes.setCreated_at(LocalDateTime.now());
        classes.setUpdated_at(LocalDateTime.now());
        return classes;
    }
}
