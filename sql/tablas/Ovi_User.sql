CREATE TABLE Ovi_User(
    dni             VARCHAR(19),
    birthdayDate    DATE        NOT NULL,
    password        VARCHAR(20) NOT NULL,
    address         VARCHAR(50) NOT NULL,
    legalGuardian   VARCHAR(50),
    accepted        BOOLEAN     NOT NULL,
    reason          VARCHAR(250),
    userPreferences VARCHAR(250) NOT NULL,

    CONSTRAINT cp_oviUser PRIMARY KEY (dni),
    CONSTRAINT ca_oviUser FOREIGN KEY (dni) REFERENCES PERSON(dni) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT chk_legalGuardian CHECK (
        (EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM birthdayDate) >= 18 AND legalGuardian IS NULL) OR
        (EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM birthdayDate) < 18 AND legalGuardian IS NOT NULL)
    )
    CONSTRAINT chk_dni CHECK(LENGTH(dni) = 9),
    CONSTRAINT chk_reason CHECK( (reason IS NULL AND accepted IS TRUE)  OR
    (reason IS NOT NULL AND accepted IS FALSE))
);