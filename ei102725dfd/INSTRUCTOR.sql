CREATE TABLE Instructor(
	idIns		VARCHAR(10),
	name		VARCHAR(20)	NOT NULL,
	surname		VARCHAR(20)	NOT NULL,
	email		VARCHAR(30)	NOT NULL,	--clave alt
	phoneNumber	VARCHAR(9)	NOT NULL,
	expertise	VARCHAR(30)	NOT NULL,	-- si no es experto en nada se pone que no es experto en nada

	CONSTRAINT cp_instrictor PRIMARY KEY (idIns),
	CONSTRAINT calt_instructor UNIQUE (email),
	CONSTRAINT id_longitud_10 CHECK (length(idIns) = 10),
	CONSTRAINT tlf_longitud_9 CHECK (length(phoneNumber) = 9)
);
