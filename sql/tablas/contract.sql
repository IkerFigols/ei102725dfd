CREATE TABLE Contract (
	idContract		VARCHAR(9),
	startDate		DATE		NOT NULL,	--No estoy seguro de si poner constraint
	endDate			DATE		NOT NULL,	--a las fechas ¿almacenamos contratos antiguos?
	document		VARCHAR(50)	NOT NULL,
	salary			NUMERIC		NOT NULL,
	schedule		VARCHAR(60)	NOT NULL, 	--VARCHAR?
	idSelection		VARCHAR(9),

	CONSTRAINT cp_contract PRIMARY KEY (idContract),
	CONSTRIANT calt_contract UNIQUE (idSelection),
	CONSTRAINT ca_contract FOREIGN KEY (idSelection) 
		REFERENCES Selection(idSelection)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	CONSTRAINT id_longitud_10 CHECK (length(idContract) = 9),
	CONSTRAINT salario_positivo CHECK (salary > 0),
	CONSTRAINT idSelection_longitud_10 CHECK (idSelection IS NULL OR length(idSelection) = 9)
);
