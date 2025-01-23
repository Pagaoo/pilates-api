ALTER TABLE professors
ADD CONSTRAINT password_non_empty CHECK ( password <> '' )