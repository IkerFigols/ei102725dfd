CREATE TABLE ACTIVITY(
	idActivity		VARCHAR(10),
	title			VARCHAR(100),
	tipo			VARCHAR(30),
	description		VARCHAR(100),
	date			DATE,
	address			VARCHAR(20),
	capacity		INTEGER,
	sponsor			VARCHAR(20),
	idInstructor		INTEGER,

	CONSTRAINT cp_activity PRIMARY KEY(idActivity),
	CONSTRAINT calt_activity UNIQUE (title),
	CONSTRAINT ca_activity FOREIGN KEY (idInstructor) REFERENCES INSTRUCTOR(idIns)
		ON DELETE RESTRICT
		ON UPDATE CASCADE
);
