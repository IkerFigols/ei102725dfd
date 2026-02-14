CREATE TABLE PAP_PATI (
	dni		VARCHAR(9),
	name		VARCHAR(50)	NOT NULL,
	surname		VARCHAR(50)	NOT NULL,
	password	VARCHAR(20)	NOT NULL,	--Contraseña al menos 8 caracteres
	email		VARCHAR(50)	NOT NULL,
	phoneNumber	VARCHAR(9)	NOT NULL,
	address		VARCHAR(50)	NOT NULL,
	tipo		VARCHAR(20)	NOT NULL,	--No hace falta comprobarlo podrá ser un desplegable con opciones
	available	BOOLEAN		NOT NULL,
	training	VARCHAR(50)	NOT NULL,
	experience	VARCHAR(100)	NOT NULL,
	gender		VARCHAR(15)	NOT NULL,
	
	CONSTRAINT cp_PAP_PATI PRIMARY KEY (dni),
	CONSTRAINT calt_PAP_PATI UNIQUE (email),
	CONSTRAINT dni_longitud_9 CHECK (length(dni) = 9),
	CONSTRAINT contr_longitud_mayor_8 CHECK (length(password) >= 8),
	CONSTRAINT telefono_longitud_9 CHECK (length(phoneNumber) = 9)
);
