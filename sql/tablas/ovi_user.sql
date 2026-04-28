CREATE TABLE Ovi_User(
    dni             VARCHAR(9),
    address         VARCHAR(100) NOT NULL,
    legalGuardian   VARCHAR(50),
    state        	VARCHAR(15) NOT NULL,
    reason          VARCHAR(250),

    CONSTRAINT cp_oviUser PRIMARY KEY (dni),
    CONSTRAINT ca_oviUser FOREIGN KEY (dni) REFERENCES PERSON(dni) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT chk_dni CHECK(LENGTH(dni) = 9),
    CONSTRAINT chk_state CHECK( state IN ('PENDING','APPROVED','REJECTED')),
    CONSTRAINT chk_reason CHECK( (reason IS NULL AND state IN ('APPROVED', 'PENDING')) OR
(reason IS NOT NULL AND state = 'REJECTED'))
);
