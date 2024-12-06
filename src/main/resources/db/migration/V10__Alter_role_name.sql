UPDATE roles
SET role = CONCAT('ROLE_', role)
WHERE role NOT LIKE 'ROLE_%'