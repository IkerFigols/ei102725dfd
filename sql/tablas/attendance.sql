CREATE TABLE Attendance (
	idAtt				VARCHAR(9),
	idOviUser			VARCHAR(9),
	idPapPati			VARCHAR(9),
	idActivity			VARCHAR(9) NOT NULL,

	--Clave Primaria
	
	CONSTRAINT cp_attendance PRIMARY KEY (idAtt),
	
	-- Restricciones
	
	CONSTRAINT ck_len_idAtt CHECK (LENGTH(idAtt) = 9),
    CONSTRAINT ck_len_idOviUser CHECK (LENGTH(idOviUser) = 9),
    CONSTRAINT ck_len_idPapPati CHECK (LENGTH(idPapPati) = 9),
    CONSTRAINT ck_len_idActivity CHECK (LENGTH(idActivity) = 9),
	
	-- Claves foráneas
	
	CONSTRAINT ca_Ovi_user FOREIGN KEY (idOviUser)
		REFERENCES Ovi_user (dni)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	CONSTRAINT ca_Pap_pati FOREIGN KEY (idPapPati)
		REFERENCES Pap_pati (dni)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	CONSTRAINT ca_act FOREIGN KEY (idActivity)
		REFERENCES Activity (idActivity)
		ON DELETE RESTRICT
		ON UPDATE CASCADE
);
