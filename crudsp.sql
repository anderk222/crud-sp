CREATE DATABASE crudsp;


-- SQUEMA 
CREATE SCHEMA IF NOT EXISTS bank;


-- ENTIDADES DE LA BASE DE DATOS
CREATE TABLE
  IF NOT EXISTS bank.user(
    id bigserial PRIMARY KEY,
    nombre varchar(150) NOT NULL,
    telefono varchar(15) NOT NULL
  );


CREATE TABLE IF NOT EXISTS bank.bank_account(
  id bigserial PRIMARY KEY,
  saldo numeric(20, 2) DEFAULT 0.00 NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT bank_account_user_fk FOREIGN KEY(user_id) REFERENCES bank.user(id)
);


-- FUNCIONES DE bank.user

CREATE
OR REPLACE FUNCTION bank.get_users() RETURNS SETOF bank.user AS $$BEGIN RETURN QUERY
SELECT
  *
FROM
  bank.user;


END $$LANGUAGE plpgsql;

SELECT * FROM bank.get_users();



CREATE OR REPLACE FUNCTION bank.get_user(bigint) RETURNS bank.user AS $$
DECLARE usuario RECORD;
BEGIN

SELECT
  * INTO usuario
FROM
  bank.user
WHERE
  id = $1;

RETURN usuario;

END $$LANGUAGE plpgsql;

SELECT * FROM bank.get_user(2);



CREATE
OR REPLACE FUNCTION bank.save_user(varchar(150), varchar(15)) RETURNS bank.user AS $$
DECLARE 
usuario bank.user;
BEGIN 
INSERT INTO
  bank.user(nombre, telefono)
VALUES
  ($1, $2) RETURNING * INTO usuario;
  
  RETURN usuario;

END $$LANGUAGE plpgsql;

SELECT * FROM bank.save_user('Anderson Macias','593991104623');



CREATE
OR REPLACE FUNCTION bank.update_user(varchar(150), varchar(15), bigint) RETURNS bank.user AS $$
DECLARE 
usuario bank.user;
BEGIN
UPDATE
  bank.user
SET
  nombre = $1,
  telefono = $2
WHERE
  id = $3 RETURNING * INTO usuario;

RETURN usuario;

END $$LANGUAGE plpgsql;

SELECT * FROM bank.update_user('Leon', '593991104623',4);



CREATE
OR REPLACE FUNCTION bank.delete_user(bigint) RETURNS bank.user AS $$
DECLARE usuario bank.user;
BEGIN
DELETE FROM
  bank.user
WHERE
  id = $1 RETURNING * INTO usuario;
  
  RETURN usuario;


END $$LANGUAGE plpgsql;

SELECT * FROM bank.delete_user(4);



-- FUNCIONES DE CUENTA DE USUARIO
CREATE
OR REPLACE FUNCTION bank.get_accounts() RETURNS SETOF bank.bank_account AS $$BEGIN RETURN QUERY
SELECT
  *
FROM
  bank.bank_account;


END $$LANGUAGE plpgsql;

SELECT * FROM bank.get_accounts();


DROP FUNCTION IF EXISTS bank.get_account;

CREATE
OR REPLACE FUNCTION bank.get_account(bigint) RETURNS bank.bank_account AS $$
DECLARE 
acc bank.bank_account;
BEGIN 
SELECT 
  * INTO acc
FROM
  bank.bank_account
WHERE
  id = $1;
RETURN acc;

END $$LANGUAGE plpgsql;

SELECT * FROM bank.get_account(2);



CREATE
OR REPLACE FUNCTION bank.get_account_by_user(bigint) RETURNS bank.bank_account 
AS $$
DECLARE 
acc bank.bank_account;
BEGIN
SELECT
  * INTO acc
FROM
  bank.bank_account
WHERE
  id = $1;


RETURN acc;


END $$ LANGUAGE plpgsql;

SELECT * FROM bank.get_account(1);


CREATE
OR REPLACE FUNCTION bank.create_account(bigint) RETURNS bank.bank_account AS
$$
DECLARE 
acc bank.bank_account;
BEGIN

INSERT INTO bank.bank_account(user_id) VALUES($1) RETURNING * INTO acc;

RETURN acc;
END $$ LANGUAGE plpgsql;

SELECT * FROM bank.create_account(2);
SELECT * FROM bank.create_account(3);



CREATE OR REPLACE FUNCTION bank.add_money(numeric(20,2), bigint) 
RETURNS bank.bank_account 
AS
$$
DECLARE 
acc bank.bank_account;
old_saldo numeric(20,2);
BEGIN

SELECT saldo INTO old_saldo FROM bank.bank_account WHERE id=$2;
UPDATE bank.bank_account SET saldo=old_saldo+$1 WHERE id=$2 RETURNING * INTO acc;

RETURN acc;

COMMIT;
END $$ LANGUAGE plpgsql;

SELECT * FROM bank.add_money(20.00, 2);

DROP FUNCTION bank.transaction;

CREATE OR REPLACE FUNCTION bank.transaction(sender bigint, receiver bigint, cantidad numeric(20,2))
RETURNS SETOF bank.bank_account AS
$$
 DECLARE 
  saldo_sender numeric(20,2);
  saldo_receiver numeric(20,2);
 BEGIN
 

  SELECT saldo INTO saldo_sender FROM bank.bank_account WHERE id=$1;

  SELECT saldo INTO saldo_receiver FROM bank.bank_account WHERE id=$2;

  IF saldo_sender < cantidad THEN

   RAISE EXCEPTION 'El enviador no tiene el saldo necesario';

  END IF;

  UPDATE bank.bank_account SET saldo=saldo_sender-cantidad WHERE id=$1;
  UPDATE bank.bank_account SET saldo=saldo_receiver+cantidad WHERE id=$2; 

  RETURN QUERY SELECT * FROM bank.bank_account WHERE id=$1 OR id=$2;

END $$ LANGUAGE plpgsql;



SELECT * FROM bank.transaction(1,2,5.12);