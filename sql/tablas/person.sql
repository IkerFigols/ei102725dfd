CREATE TABLE Person (
	dni					VARCHAR(9),
	name				VARCHAR (20),
	surname				VARCHAR(20) NOT NULL,
	phoneNumber			VARCHAR(9) NOT NULL,
	email				VARCHAR(15) NOT NULL,
	gender				VARCHAR(10) NOT NULL,

	--Clave Primaria
	
	CONSTRAINT cp_person PRIMARY KEY (dni),
	
	--Clave alternativa
	
	CONSTRAINT ca_person UNIQUE (email),
	
	-- Restricciones
	
	CONSTRAINT ck_dni CHECK (LENGTH(dni) = 9),
	
);
