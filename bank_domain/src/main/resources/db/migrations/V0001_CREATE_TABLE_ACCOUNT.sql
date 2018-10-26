CREATE TABLE IF NOT EXISTS account
(
  id            SERIAL NOT NULL,
  balance       DOUBLE PRECISION,
  creation_date date,
  CONSTRAINT account_pkey PRIMARY KEY (id)
);