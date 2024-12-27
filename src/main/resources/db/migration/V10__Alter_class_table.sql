DO $$ BEGIN
    IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'weekdays_type') THEN
      CREATE TYPE weekdays_type AS ENUM ('Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta');
    END IF;
END $$;

ALTER TABLE classes
ADD COLUMN weekday weekdays_type NOT NULL;