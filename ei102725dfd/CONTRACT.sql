CREATE TABLE Contract (
	idContract		VARCHAR(9),
	startDate		DATE		NOT NULL,	--No estoy seguro de si poner constraint
	endDate			DATE		NOT NULL,	--a las fechas ¿almacenamos contratos antiguos?
	document		VARCHAR(50)	NOT NULL,
	salary			NUMERIC		NOT NULL,
	schedule		VARCHAR(60)	NOT NULL, 	--VARCHAR?

	CONSTRAINT cp_contract PRIMARY KEY (idContract),
	CONSTRAINT id_longitud_9 CHECK (length(idContract) = 9),
	CONSTRAINT salario_positivo CHECK (salary > 0)
);
