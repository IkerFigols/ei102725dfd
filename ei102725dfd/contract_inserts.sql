
INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idNegotiation)
VALUES ('CONTRATO01', '2024-01-01', '2024-12-31', '/docs/contrato_juan.pdf', 30000.00, 'L-V 09:00-18:00', NULL);
INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idNegotiation)
VALUES ('CONTRATO02', '2024-02-15', '2025-02-15', '/docs/contrato_ana.docx', 55000.50, 'Turno Rotativo', 'NEGOCIA001');    --Suponiendo que existe la negoaciacion especificada
INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idNegotiation)
VALUES ('CONTRATO03', '2024-06-01', '2024-09-01', '/docs/temporal_luis.pdf', 1200.00, 'Fines de semana', NULL);
INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idNegotiation)
VALUES ('CONTRATO04', '2020-01-01', '2020-12-31', '/archivo/old_c04.pdf', 28000.00, 'L-V 08:00-15:00', NULL);
INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idNegotiation)
VALUES ('CONTRATO05', '2024-03-01', '2026-03-01', '/docs/directivo_sofia.pdf', 75000.00, 'Flexible', 'NEGOCIA002');        --Suponiendo que existe la negoaciacion especificada 
