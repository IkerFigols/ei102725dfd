CREATE TABLE Communication( 

idCommuninaction	VARCHAR(9),
data			DATE	NOT NULL,
information		VARCHAR(50) NOT NULL,

CONSTRAINT pk_communication PRIMARY KEY (idCommunication)
CONSTRAINT chk_id CHECK(LENGTH(idCommunication) = 9)
);
