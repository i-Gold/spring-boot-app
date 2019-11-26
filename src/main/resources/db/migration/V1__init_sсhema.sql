CREATE TABLE IF NOT EXISTS authorities (
  id bigserial NOT NULL,
  name varchar(255) NOT NULL,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS roles (
   id bigserial NOT NULL,
   name varchar(255) NOT NULL,
   primary key (id)
);

CREATE TABLE IF NOT EXISTS roles_authorities (
  role_id bigserial NOT NULL,
  authority_id bigserial NOT NULL,
  primary key (role_id, authority_id)
);

CREATE TABLE IF NOT EXISTS users (
  id bigserial NOT NULL,
  account_expired SMALLINT,
  account_locked SMALLINT,
  credentials_expired SMALLINT,
  email varchar(255),
  enabled SMALLINT,
  password varchar(255),
  username varchar(255),
  country varchar(255),
  primary key (id)
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id bigserial NOT NULL,
  role_id bigserial NOT NULL,
  primary key (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove SMALLINT
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BYTEA,
  refresh_token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication BYTEA
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code VARCHAR(255),
  authentication BYTEA
);

ALTER TABLE authorities ADD CONSTRAINT UKnb3atvjf9ov5d0egnuk47o5e unique (name);
ALTER TABLE roles ADD CONSTRAINT UKofx66keruapi6vyqpv6f2or37 unique (name);
ALTER TABLE users ADD CONSTRAINT UKfnranlqhubvw04boopn028e6 unique (username, email);
ALTER TABLE roles_authorities ADD CONSTRAINT FKt69njxcgfcto5wcrd9ocmb35h FOREIGN KEY (authority_id) REFERENCES authorities (id);
ALTER TABLE roles_authorities ADD CONSTRAINT FKq3iqpff34tgtkvnn545a648cb FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE users_roles ADD CONSTRAINT FKj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE users_roles ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO authorities(id, name) VALUES (1, 'USA') ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;
INSERT INTO authorities(id, name) VALUES (2, 'CANADIAN') ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;

INSERT INTO roles(id, name) VALUES (1, 'USER') ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;
INSERT INTO roles(id, name) VALUES (2, 'ADMIN') ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;

INSERT INTO roles_authorities(role_id, authority_id) VALUES (1, 1);
INSERT INTO roles_authorities(role_id, authority_id) VALUES (1, 2);

INSERT INTO users (id, account_expired, account_locked, credentials_expired, email, enabled, password, username, country)
VALUES ('1', '0', '0', '0', 'kamara.richard10@gmail.com', '1', /*spring-security-oauth2-read-client-password1234*/
'$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km', 'richard23', 'State of California');

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
 VALUES ('application-oauth2-read-client', 'resource-server-rest-api',
 /*spring-security-oauth2-read-client-password1234*/'$2a$04$WGq2P9egiOYoOF  emBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km',
 'read', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000)
ON CONFLICT (client_id) DO UPDATE SET client_id = EXCLUDED.client_id;

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
 VALUES ('application-oauth2-read-write-client', 'resource-server-rest-api',
 /*spring-security-oauth2-read-write-client-password1234*/'$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W',
 'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000)
ON CONFLICT (client_id) DO UPDATE SET client_id = EXCLUDED.client_id;