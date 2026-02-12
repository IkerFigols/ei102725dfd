CREATE TABLE INSTRUCTOR(
	idIns		INTEGER,
	name		VARCHAR(20),
	surname		VARCHAR(20),
	email		VARCHAR(30),
	phoneNumber	INTEGER(10),
	expertise	VARCHAR(30),

	CONSTRAINT cp_instrictor PRIMARY KEY (idIns, email)
);
