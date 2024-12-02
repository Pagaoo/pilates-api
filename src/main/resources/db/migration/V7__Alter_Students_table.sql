ALTER TABLE students
ADD CONSTRAINT fk_role_delete_restriction FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT;