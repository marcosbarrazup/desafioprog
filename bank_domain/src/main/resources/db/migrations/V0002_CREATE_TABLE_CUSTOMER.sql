CREATE TABLE  IF NOT EXISTS customer
(
  id            SERIAL NOT NULL,
  cpf           CHAR(11),
  creation_date date,
  name          VARCHAR(64),
  account_id    INTEGER,
  CONSTRAINT account_id FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT customer_pkey PRIMARY KEY (id)
);
