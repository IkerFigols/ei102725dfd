CREATE TABLE Assistance_Request(

idAsReq		VARCHAR(9),
data		DATE 			NOT NULL,
description 	VARCHAR(250)		NOT NULL,
idOviUser	VARCHAR(9) 		NOT NULL,
state		VARCHAR(15)		NOT NULL,
reason 		VARCHAR(250),

CONSTRAINT cp_assistance_request PRIMARY KEY (idAsReq),
CONSTRAINT ca_idOviUser FOREIGN KEY (idOviUser) 
	REFERENCES Ovi_User (dni)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,
CONSTRAINT chk_id CHECK(LENGTH(idAsReq) = 9),
CONSTRAINT chk_ovi CHECK(LENGTH(idOviUser) = 9),
CONSTRAINT chk_reason CHECK( (reason IS NULL AND state IN ('APROBADA', 'PENDIENTE')) OR
(reason IS NOT NULL AND state = 'RECHAZADA'))
);

