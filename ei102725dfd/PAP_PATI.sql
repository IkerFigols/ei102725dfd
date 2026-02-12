CREATE TABLE PAP_PATI (
	dni		VARCHAR(9),
	name		VARCHAR(50),
	surname		VARCHAR(50),
	password	VARCHAR(20),
	email		VARCHAR(50),
	phoneNumber	INTEGER,
	address		VARCHAR(50),
	tipo		VARCHAR(20),
	available	BOOLEAN,
	training	VARCHAR(50),
	experience	VARCHAR(100),
	gender		VARCHAR(15),
	
	CONSTRAINT cp_PAP_PATI PRIMARY KEY (dni),
	CONSTRAINT calt_PAP_PATI UNIQUE (email),
);
