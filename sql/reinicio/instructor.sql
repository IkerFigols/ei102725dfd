CREATE TABLE Instructor (
	dni			VARCHAR(9),
	expertise	VARCHAR(50),

	--Clave Primaria
	
	CONSTRAINT cp_instructor PRIMARY KEY (dni),
	
	-- Restricciones
	
	CONSTRAINT ck_len_dni CHECK (LENGTH(dni) = 9),
	
	-- Claves foráneas
	
	CONSTRAINT cf_person FOREIGN KEY (dni)
		REFERENCES Person (dni)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);
