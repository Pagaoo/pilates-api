ALTER TABLE classes
DROP CONSTRAINT fk_professor;

ALTER TABLE classes
ADD CONSTRAINT fk_professor
FOREIGN KEY (professor_id) REFERENCES professors(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;
