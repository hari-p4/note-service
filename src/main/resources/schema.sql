DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS notes;

CREATE TABLE users (
  id SERIAL NOT NULL PRIMARY KEY,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  create_time TIMESTAMPTZ DEFAULT NOW(),
  last_update_time TIMESTAMPTZ DEFAULT NOW(),
  unique(email)
);

CREATE TABLE notes (
  id SERIAL NOT NULL PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  note VARCHAR(1000),
  create_time TIMESTAMPTZ DEFAULT NOW(),
  last_update_time TIMESTAMPTZ DEFAULT NOW(),
  user_id INTEGER REFERENCES users(id)
);

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.last_update_time = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_set_timestamp_user()
RETURNS TRIGGER AS $$
BEGIN
  NEW.last_update_time = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON notes
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp_user
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp_user();