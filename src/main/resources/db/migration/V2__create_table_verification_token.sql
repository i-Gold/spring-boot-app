CREATE TABLE IF NOT EXISTS verification_token (
  id bigserial NOT NULL,
  expiry_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  token varchar(255) NOT NULL,
  user_id bigserial NOT NULL, primary key (id)
);

ALTER TABLE verification_token ADD CONSTRAINT FK3asw9wnv76uxu3kr1ekq4i1ld FOREIGN KEY (user_id) REFERENCES users (id);