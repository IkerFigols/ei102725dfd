CREATE TABLE INSTRUCTOR(
	idIns		VARCHAR(10),
	name		VARCHAR(20),
	surname		VARCHAR(20),
	email		VARCHAR(30),
	phoneNumber	INTEGER,
	expertise	VARCHAR(30),

	CONSTRAINT cp_instrictor PRIMARY KEY (idIns),
	CONSTRAINT calt_instructor UNIQUE (email)
);
