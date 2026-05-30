CREATE TABLE Assistance_Request(

	idAsReq			VARCHAR(9),
	date			DATE 			NOT NULL,
    tittle          VARCHAR(50)     NOT NULL,
	description 	VARCHAR(250)	NOT NULL,
	idOviUser		VARCHAR(9) 		NOT NULL,
	state			VARCHAR(30)		NOT NULL,
	reason 			VARCHAR(250),
	experience      NUMERIC,
	drivingLicense  BOOLEAN,
	province        VARCHAR(50)     NOT NULL,
	shiftPreference VARCHAR(50)     NOT NULL,    --MORNING / AFTERNOON / ANY
    age             NUMERIC,

CONSTRAINT cp_assistance_request PRIMARY KEY (idAsReq),
CONSTRAINT ca_idOviUser FOREIGN KEY (idOviUser) 
	REFERENCES Ovi_User (dni)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,
CONSTRAINT chk_id CHECK(LENGTH(idAsReq) = 9),
CONSTRAINT chk_ovi CHECK(LENGTH(idOviUser) = 9),
CONSTRAINT chk_reason CHECK( (reason IS NULL AND state IN ('APPROVED', 'PENDING', 'CLOSED_WITH_CONTRACT', 'CLOSED_WITH_CONTRACT_DONE')) OR
(reason IS NOT NULL AND state = 'REJECTED')),
CONSTRAINT chk_shiftPreference CHECK (shiftPreference IN ('MORNING', 'AFTERNOON', 'ANY'))
);

