CREATE TABLE Assistance_Request(

idAsReq		VARCHAR(9),
data		DATE 	NOT NULL,
tipo		VARCHAR(30) NOT NULL,
userPreferences	VARCHAR(50) NOT NULL,
idOviUser	VARCHAR(9) NOT NULL,

CONSTRAINT cp_assistance_request PRIMARY KEY (idAsReq),
CONSTRAINT ca_idOviUser FOREIGN KEY (idOviUser) 
	REFERENCES Ovi_User (dni)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,
CONSTRAINT chk_id CHECK(LENGTH(idAsReq) = 9)
CONSTRAINT chk_ovi CHECK(LENGTH(idOviUser) = 9)
);
