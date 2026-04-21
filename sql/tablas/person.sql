CREATE TABLE Person (
	dni					VARCHAR(9),
	name				VARCHAR (50) NOT NULL,
	surname				VARCHAR(50) NOT NULL,
	phoneNumber			VARCHAR(9) NOT NULL,
	email				VARCHAR(15) NOT NULL,
	gender				VARCHAR(10) NOT NULL,
	password			VARCHAR(100) NOT NULL,
	location			VARCHAR (50) NOT NULL, 

	--Clave Primaria
	
	CONSTRAINT cp_person PRIMARY KEY (dni),
	
	--Clave alternativa
	
	CONSTRAINT ca_person UNIQUE (email),
	
	-- Restricciones
	
	CONSTRAINT ck_dni CHECK (LENGTH(dni) = 9),
	CONSTRAINT ck_pass CHECK (LENGTH (password) >= 8)
	
);
