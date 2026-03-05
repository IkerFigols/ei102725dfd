REATE TABLE Pap_Pati (
		dni				VARCHAR(9)	NOT NULL,
		password			VARCHAR(20) 	NOT NULL,
		address				VARCHAR(40) 	NOT NULL,
		type				VARCHAR(10) 	NOT NULL,	--Será un tipo fijo
		available			BOOLEAN 	NOT NULL,
		training			VARCHAR(200) 	NOT NULL,
		document			VARCHAR(150) 	NOT NULL,
		
		CONSTRAINT cp_pap_pati PRIMARY KEY (dni),
		CONSTRAINT ca_pap_pati FOREIGN KEY (dni) REFERENCES PERSON(dni)
			ON DELETE CASCADE,
			ON UPDATE CASCADE,
		CONSTRAINT chk_pass CHECK(length(dni) > 6),
		CONSTRAINT chk_dni CHECK(length(dni) = 9)
);	
