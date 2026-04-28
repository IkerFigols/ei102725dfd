CREATE TABLE Pap_Pati (
		dni				VARCHAR(9)	NOT NULL,
		address				VARCHAR(100) 	NOT NULL,
		type				VARCHAR(10) 	NOT NULL,	--Será un tipo fijo
		available			BOOLEAN 		NOT NULL,
		training			VARCHAR(200) 	NOT NULL,
		document			VARCHAR(100) 	NOT NULL,
		reason				VARCHAR(250), 			--SOLO null si no se ha 
		state				VARCHAR	(15)		NOT NULL,	--Indica si el pap pati ha sido aceptado por el technician
		papPatiPreferences		VARCHAR(250)	NOT NULL,	--Las preferencias del pap pati
		
		CONSTRAINT cp_pap_pati PRIMARY KEY (dni),
		CONSTRAINT ca_pap_pati FOREIGN KEY (dni) REFERENCES PERSON(dni)
			ON DELETE CASCADE
			ON UPDATE CASCADE,
		CONSTRAINT chk_dni CHECK(length(dni) = 9),
		CONSTRAINT chk_state CHECK( state IN ('PENDING','APPROVED','REJECTED')),
		CONSTRAINT chk_reason CHECK( (reason IS NULL AND state IN ('APPROVED', 'PENDING')) OR
(reason IS NOT NULL AND state = 'REJECTED')),
		CONSTRAINT chk_type CHECK( type IN ('PAP', 'PATI', 'PAP/PATI'))	
);	
