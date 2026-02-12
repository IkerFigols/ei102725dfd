CREATE TABLE CONTRACT (
	idContract		INTEGER,
	startDate		DATE,
	endDate			DATE,
	document		VARCHAR(50),
	salary			NUMERIC,
	schedule		VARCHAR(40), 	--VARCHAR?
	idNegotiation		INTEGER,

	CONSTRAINT cp_contract PRIMARY KEY (idContract),
	CONSTRAINT ca_contract FOREIGN KEY (idNegotiation) 
		REFERENCES NEGOTIATION(idNegotiation)
		ON DELETE SET NULL
		ON UPDATE CASCADE
);
