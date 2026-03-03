CREATE TABLE Negotiation (
	idSelection			VARCHAR(9),
	date				DATE NOT NULL,
	state				VARCHAR(40) NOT NULL,
	idCommunication		VARCHAR(9) NOT NULL,
	idPap				VARCHAR(9) NOT NULL,
	IdAsReq				VARCHAR(9) NOT NULL,

	--Clave Primaria
	
	CONSTRAINT cp_selection PRIMARY KEY (idSelection),
	
	-- Restricciones
	
	CONSTRAINT ck_len_idSel CHECK (LENGTH(idSelection) = 9),
    CONSTRAINT ck_len_idCom CHECK (LENGTH(idCommunication) = 9),
    CONSTRAINT ck_len_idPap CHECK (LENGTH(idPap) = 9),
    CONSTRAINT ck_len_idAsR CHECK (LENGTH(idAsReq) = 9),
	
	-- Claves foráneas
	
	CONSTRAINT ca_communication FOREIGN KEY (idCommunication)
		REFERENCES Communication (idCommunication)
		ON DELETE RESTRICT
		ON UPDATE CASCADE,
	CONSTRAINT ca_Pap_pati FOREIGN KEY (idPap)
		REFERENCES Pap_pati (dni)
		ON DELETE RESTRICT
		ON UPDATE CASCADE,
	CONSTRAINT ca_as_req FOREIGN KEY (idAsReq)
		REFERENCES Assistance_request (idAsReq)
		ON DELETE RESTRICT
		ON UPDATE CASCADE
);
