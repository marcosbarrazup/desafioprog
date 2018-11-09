CREATE TABLE IF NOT EXISTS operation
(
  id                  SERIAL NOT NULL,
  date_hour           TIMESTAMP,
  operation_type      INTEGER,
  value               DOUBLE PRECISION,
  destination_account INTEGER,
  source_account      INTEGER,
  CONSTRAINT operation_pkey PRIMARY KEY (id),
  CONSTRAINT operation_value_check CHECK (value >= (1) :: DOUBLE PRECISION),
  CONSTRAINT destination_account FOREIGN KEY (destination_account) REFERENCES account (id),
  CONSTRAINT source_account FOREIGN KEY (source_account) REFERENCES account (id)

);