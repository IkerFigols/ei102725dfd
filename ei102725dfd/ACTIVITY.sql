CREATE TABLE Activity(
	idActivity		VARCHAR(10),
	title			VARCHAR(100)	NOT NULL,
	tipo			VARCHAR(30)	NOT NULL, --El tipo no hará falta comprobarlo, puede ser un desplegable
	description		VARCHAR(100)	NOT NULL,
	date			DATE		NOT NULL,
	address			VARCHAR(20)	NOT NULL,
	capacity		INTEGER,
	sponsor			VARCHAR(20),
	idInstructor		VARCHAR(10)		NOT NULL,

	CONSTRAINT cp_activity PRIMARY KEY(idActivity),
	CONSTRAINT calt_activity UNIQUE (title),
	CONSTRAINT ca_activity FOREIGN KEY (idInstructor) REFERENCES INSTRUCTOR(idIns)
		ON DELETE RESTRICT
		ON UPDATE CASCADE,
	CONSTRAINT id_longitud_10 CHECK (length(idActivity) = 10),
	CONSTRAINT capacidad_positiva CHECK (capacity > 0),
	CONSTRAINT idInstructor_longitud_10 CHECK (length(idInstructor) = 10)

);
