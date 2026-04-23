CREATE TABLE Activity (
	idActivity 		VARCHAR(9)	NOT NULL,
	activityType		VARCHAR(20)	NOT NULL,
	tittle			VARCHAR(50)	NOT NULL,
	description		VARCHAR(300)	NOT NULL,
	date 			DATE		NOT NULL,
	address			VARCHAR(50)	NOT NULL,
	capacity		NUMERIC,
	sponsor			VARCHAR(20),
	idInstructor		VARCHAR(9)	NOT NULL,

	CONSTRAINT cp_activity PRIMARY KEY (idActivity),
	CONSTRAINT ca_instructor FOREIGN KEY (idInstructor) REFERENCES Instructor (dni)
		ON DELETE RESTRICT
		ON UPDATE CASCADE,
	CONSTRAINT chk_id_activity CHECK (length(idActivity) = 9),
	CONSTRAINT chk_id_instructor CHECK (length(idInstructor) = 9),
	CONSTRAINT chk_capacity CHECK (capacity > 0)
);
