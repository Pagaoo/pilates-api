ALTER TABLE classes
ADD COLUMN student_id BIGINT;

ALTER TABLE classes
ADD CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE