CREATE TABLE Contract (
	idContract		VARCHAR(9),
	startDate		DATE		NOT NULL,	--No estoy seguro de si poner constraint
	endDate			DATE		NOT NULL,	--a las fechas ¿almacenamos contratos antiguos?
	document		VARCHAR(50)	NOT NULL,
	salary			NUMERIC		NOT NULL,
	schedule		VARCHAR(60)	NOT NULL, 	--VARCHAR?
	idNegotiation		VARCHAR(9),

	CONSTRAINT cp_contract PRIMARY KEY (idContract),
	CONSTRAINT ca_contract FOREIGN KEY (idNegotiation) 
		REFERENCES NEGOTIATION(idNegotiation)
		ON DELETE SET NULL
		ON UPDATE CASCAD,
	CONSTRAINT id_longitud_10 CHECK (length(idContract) = 9),
	CONSTRAINT salario_positivo CHECK (salary > 0),
	CONSTRAINT idNegociacion_longitud_10 CHECK (idNegotiation IS NULL OR length(idNegotiation) = 10)
);
