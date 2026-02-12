CREATE TABLE ACTIVITY(
	idActivity		INTEGER,
	title			VARCHAR(20),
	tipo			VARCHAR(20),
	description		VARCHAR(100),
	fecha			DATE,
	address			VARCHAR(20),
	capacity		INTEGER,
	sponsor			VARCHAR(20),
	idInstructor		INTEGER

	CONSTRAINT cp_activity PRIMARY KEY(idActivity, title),
	CONSTRAINT ca_activity FOREIGN KEY (idInstructor) REFERENCES INSTRUCTOR(idIns)
		ON DELETE RESTRICT
		ON UPDATE CASCADE
);
