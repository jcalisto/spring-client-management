DROP TABLE IF EXISTS clients;
CREATE TABLE clients(id serial PRIMARY KEY, name VARCHAR(50), nif VARCHAR(9) unique, address VARCHAR(255), phone VARCHAR(9));