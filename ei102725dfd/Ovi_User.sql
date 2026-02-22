CREATE TABLE Ovi_User(
dni		VARCHAR(10),
name		VARCHAR(20) NOT NULL,
surname		VARCHAR(30) NOT NULL,
birthdayDate	DATE	    NOT NULL,
password	VARCHAR(20) NOT NULL,
email 		VARCHAR(30) NOT NULL,
phoneNumber 	VARCHAR(9)  NOT NULL,
address		VARCHAR(50) NOT NULL,
legalGuardian 	VARCHAR(50),
gender		VARCHAR(10) NOT NULL,

CONSTRAINT  cp_oviUser PRIMARY KEY (dni),
CONSTRAINT  cl_oviUser UNIQUE (email),
CONSTRAINT  chk_phone CHECK ( LENGTH(phoneNumber) = 9 AND phoneNumber ~ '^[0-9]{9}$')
CONSRAINT   chk_legalGuardian CHECK (
                                        (EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM birthdayDate) >= 18 AND legalGuardian IS NULL) OR
                                        (EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM birthdayDate) < 18 AND legalGuardian IS NOT NULL) 
                                    )
);
