CREATE TABLE Negotiation (
	idNegotiation		VARCHAR(10),
	date			DATE NOT NULL,
	state			VARCHAR(40) NOT NULL,
	idCommunication		VARCHAR(10) NOT NULL,
	idPap			VARCHAR(10) NOT NULL,
	IdAsReq			VARCAHR(10) NOT NULL,

	CONSTRAINT cp_negotiation PRIMARY KEY (idNegotiation),
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
