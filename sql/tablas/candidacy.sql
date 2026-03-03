CREATE TABLE Candidacy(

idCandidacy	VARCHAR(9),
status		VARCHAR(40) NOT NULL,
dateAdded	DATE NOT NULL,
idPapPati	VARCHAR(9) NOT NULL,
idPapPatiList	VARCHAR(9) NOT NULL,



CONSTRAINT cp_candidacy PRIMARY KEY (idCandidacy),
CONSTRAINT ck_len_idCan CHECK (LENGTH(idCandidacy) = 9),
CONSTRAINT ck_len_idPapPati CHECK (LENGTH(idPapPati) = 9),
CONSTRAINT ck_len_idPapPatiList CHECK (LENGTH(idPapPatiList) = 9),
CONSTRAINT  ca_PapPati	 FOREIGN KEY (idPapPati) 
	REFERENCES PapPati(dni)
	ON DELETE  RESTRICT 
	ON UPDATE  CASCADE,
CONSTRAINT  ca_PapPatiList FOREIGN KEY (idPapPatiList)
	REFERENCES PapPatiList(idPap)
	ON DELETE  CASCADE
	ON UPDATE  CASCADE
);
