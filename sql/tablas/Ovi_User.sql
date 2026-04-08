CREATE TABLE Ovi_User(
    dni             VARCHAR(19),
    birthdayDate    DATE        NOT NULL,
    password        VARCHAR(20) NOT NULL,
    address         VARCHAR(50) NOT NULL,
    legalGuardian   VARCHAR(50),
    accepted        VARCHAR(15) NOT NULL,
    reason          VARCHAR(250),
    userPreferences VARCHAR(250) NOT NULL,

    CONSTRAINT cp_oviUser PRIMARY KEY (dni),
    CONSTRAINT ca_oviUser FOREIGN KEY (dni) REFERENCES PERSON(dni) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT chk_dni CHECK(LENGTH(dni) = 9),
    CONSTRAINT chk_reason CHECK( reason IN ('PENDING','ACCEPTED','REJECTED'))
);
