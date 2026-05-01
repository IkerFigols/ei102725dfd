CREATE TABLE Communication(

        idCommunication         VARCHAR(9),
        date                    DATE    NOT NULL,
        information             VARCHAR(200) NOT NULL,
        idSelection             VARCHAR(9) NOT NULL ,
        CONSTRAINT pk_communication PRIMARY KEY (idCommunication),
        CONSTRAINT chk_id CHECK(LENGTH(idCommunication) = 9),
        CONSTRAINT fk_selection FOREIGN KEY (idSelection) REFERENCES Selection(idSelection)
                ON DELETE CASCADE
                ON UPDATE CASCADE
);

