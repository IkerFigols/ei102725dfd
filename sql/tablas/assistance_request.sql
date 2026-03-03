CREATE TABLE Assistance_Request(

idAsReq		VARCHAR(10),
data		DATE 	NOT NULL,
tipo		VARCHAR(30) NOT NULL,
userPreferences	VARCHAR(50) NOT NULL,
idOviUser	VARCHAR(10) NOT NULL,

CONSTRAINT cp_assistance_request PRIMARY KEY (idAsReq),
CONSTRAINT ca_idOviUser FOREIGN KEY (idOviUser) 
	REFERENCES Ovi_User (dni)
	ON DELETE RESTRICT
	ON UPDATE CASCADE
);
