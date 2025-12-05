-- SUBAPLICACIO
INSERT INTO SUBAPLICACIO (coa, dec, dem) VALUES 
('AJB', 'Ajuntament de Barcelona', 'Ajuntament de Barcelona (Catalunya)'),
('FIS', 'Fisioterapia FisioCat', 'Centre de Fisioterapia FisioCat');

-- TECNIC
INSERT INTO TECNIC (coa, pass, nom, ll1, ll2, prf) VALUES 
-- Tencis Ajuntament de Palma
('JGOMEZ', '1234', 'Joan', 'Gómez', 'Marqués','TECNIC_AJB'), -- fa feina de matins
('GSIMONET', '1234', 'Guillermo', 'Simonet', 'Ramon','ADMINISTRADOR_AJB'), -- fa feina de tardes
('AGINARD', '1234', 'Antonio', 'Ginard', 'Reinés','TECNIC_AJB'), -- fa feina de matins
-- Tecnics fisios
('FISIO1', '1234', 'Jaume', 'Carrió', 'Sureda','TECNIC_FIS'),
('FISIO2', '1234', 'Margalida', 'Ramon', 'Gibert','ADMINISTRADOR_FIS');

-- UBICACIO
INSERT INTO UBICACIO (con, nom, nomcar, obs, SUBAPL_COA) VALUES 
(1, 'OAC Avingudes', 'C/ Avinguda Barcelona 2', 'Davant la sortida del Metro L11', 'AJB'),
(2, 'OAC Pl Catalunya', 'Plaça Catalunya 32 baixos', 'Entrada pel Centre Comercial El Corte Inglés', 'AJB'),
-- Ubicacions fisios
(3, 'FisioCat Barcelona', 'Carrer Enric Granados 2', 'Centre ubicat al centre de Barcelona', 'FIS'),
(4, 'FisioCat Tarragona', 'Avinguda Tarraco 3 pis 2A', 'Centre ubicat a les afores de Tarragona', 'FIS');

-- TIPUS_CITA
INSERT INTO TIPUS_CITA (con, dec, dem, tipcitmod, SUBAPL_COA) VALUES 
(1, 'Certificat PH', 'Certificat Padró d''Habitants', 'P', 'AJB'),
(2, 'Certificat de Resi.', 'Certificat de Residència', 'P', 'AJB'),
-- Fisios
(3, 'Primer', 'Primera consulta', 'P', 'FIS'),
(4, 'Segon', 'Consulta successiva', 'P', 'FIS');

-- HORARI
INSERT INTO HORARI (con, dec, dem, SUBAPL_COA, TIPCIT_CON) VALUES
(1, 'OAC-Matí', 'Horari de matí dl-dm-dv 9-14h', 'AJB', 1),
(2, 'OAC-Tarda', 'Horari de tarda dm-dj 15-19h', 'AJB', 1),
-- Fisios
(3, 'Torn de matí', 'Horari de matí de dl-ds 9 a 14h', 'FIS', 3),
(4, 'Torn de tarda', 'Horari de tarda dl-dv 15-19h', 'FIS', 4)
;

-- AGENDA
INSERT INTO AGENDA (con, datini, datfin, UBI_CON, TEC_COA, HORCIT_CON) VALUES 
(1, '2025-11-24', '2025-12-23', 1, 'JGOMEZ', 1),
(2, '2025-11-24', '2026-04-01', 2, 'AGINARD', 2),
(3, '2025-12-24', '2026-04-01', 1, 'GSIMONET', 1),
-- Fisios
(4, '2025-12-01', '2026-04-01', 3, 'FISIO1', 3),
(5, '2025-12-01', '2026-04-01', 4, 'FISIO2', 4);

-- SetmanaTipus: Lunes y Miércoles, 9-10 y 10-11
INSERT INTO SETMANA_TIPUS (HORCIT_CON, DIASET_CON, horini, horfin) VALUES
-- dilluns AJB
(1, 1, '09:00:00', '10:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 1, '10:00:00', '11:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 1, '11:00:00', '12:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 1, '12:00:00', '13:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 1, '13:00:00', '14:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
-- dilluns FIS
(3, 1, '09:00:00', '10:00:00'),  -- mati
(3, 1, '10:00:00', '11:00:00'), 
(3, 1, '11:00:00', '12:00:00'),
(3, 1, '12:00:00', '13:00:00'), 
(3, 1, '13:00:00', '14:00:00'),
(4, 1, '15:00:00', '16:00:00'),  -- tarda
(4, 1, '16:00:00', '17:00:00'), 
(4, 1, '17:00:00', '18:00:00'),
(4, 1, '18:00:00', '19:00:00'), 

-- dimarts
(2, 2, '15:00:00', '16:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 2, '16:00:00', '17:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 2, '17:00:00', '18:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 2, '18:00:00', '19:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
--  FIS
(3, 2, '09:00:00', '10:00:00'),  -- mati
(3, 2, '10:00:00', '11:00:00'), 
(3, 2, '11:00:00', '12:00:00'),
(3, 2, '12:00:00', '13:00:00'), 
(3, 2, '13:00:00', '14:00:00'),
(4, 2, '15:00:00', '16:00:00'),  -- tarda
(4, 2, '16:00:00', '17:00:00'), 
(4, 2, '17:00:00', '18:00:00'),
(4, 2, '18:00:00', '19:00:00'), 

-- dimecres
(1, 3, '09:00:00', '10:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 3, '10:00:00', '11:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 3, '11:00:00', '12:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 3, '12:00:00', '13:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 3, '13:00:00', '14:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
-- FIS
(3, 3, '09:00:00', '10:00:00'),  -- mati
(3, 3, '10:00:00', '11:00:00'), 
(3, 3, '11:00:00', '12:00:00'),
(3, 3, '12:00:00', '13:00:00'), 
(3, 3, '13:00:00', '14:00:00'),
(4, 3, '15:00:00', '16:00:00'),  -- tarda
(4, 3, '16:00:00', '17:00:00'), 
(4, 3, '17:00:00', '18:00:00'),
(4, 3, '18:00:00', '19:00:00'), 
-- dijous
(2, 4, '15:00:00', '16:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 4, '16:00:00', '17:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 4, '17:00:00', '18:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
(2, 4, '18:00:00', '19:00:00'),  -- OAC-tarda horari 2 tardes dm-dj cada hora
-- FIS
(3, 4, '09:00:00', '10:00:00'),  -- mati
(3, 4, '10:00:00', '11:00:00'), 
(3, 4, '11:00:00', '12:00:00'),
(3, 4, '12:00:00', '13:00:00'), 
(3, 4, '13:00:00', '14:00:00'),
(4, 4, '15:00:00', '16:00:00'),  -- tarda
(4, 4, '16:00:00', '17:00:00'), 
(4, 4, '17:00:00', '18:00:00'),
(4, 4, '18:00:00', '19:00:00'), 

-- divendres
(1, 5, '09:00:00', '10:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 5, '10:00:00', '11:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 5, '11:00:00', '12:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 5, '12:00:00', '13:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora
(1, 5, '13:00:00', '14:00:00'),  -- OAC-Matí horari 1 matins dl-dm-dv cada hora

-- FIS
(3, 5, '09:00:00', '10:00:00'),  -- mati
(3, 5, '10:00:00', '11:00:00'), 
(3, 5, '11:00:00', '12:00:00'),
(3, 5, '12:00:00', '13:00:00'), 
(3, 5, '13:00:00', '14:00:00'),
(4, 5, '15:00:00', '16:00:00'),  -- tarda
(4, 5, '16:00:00', '17:00:00'), 
(4, 5, '17:00:00', '18:00:00'),
(4, 5, '18:00:00', '19:00:00'), 

-- dissabte
-- FIS
(3, 6, '09:00:00', '10:00:00'),  -- mati
(3, 6, '10:00:00', '11:00:00'), 
(3, 6, '11:00:00', '12:00:00'),
(3, 6, '12:00:00', '13:00:00'), 
(3, 6, '13:00:00', '14:00:00');



INSERT INTO CITA (con, dathorini, dathorfin, obs, nom, llis, numdoc, tipcit_con, age_con,
                  lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
                  nomcar, tel, ema) VALUES

-- DESEMBRE 2025
(1,  '2025-12-01 09:00:00', '2025-12-01 10:00:00', 'Primera empadronament', 'Laura', 'Martínez García', '39871234A', 1, 1, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Sants 45', 612345601, 'laura.mg@gmail.com'),
(2,  '2025-12-01 11:00:00', '2025-12-01 12:00:00', 'Canvi dins Eixample', 'Marc', 'Puig Torres', '45127890B', 1, 1, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Av. Paral·lel 123', 623456712, 'marc.pt@gmail.com'),
(3,  '2025-12-02 15:00:00', '2025-12-02 16:00:00', 'Vinc de Terrassa', 'Ahmed', 'Rahmani', 'Y9988776F', 1, 2, 'Canvi de domicili des d''un altre municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Marina 78', 667890156, 'ahmed.r@yahoo.com'),
(4,  '2025-12-03 10:00:00', '2025-12-03 11:00:00', 'Alta nadó', 'Sara', 'López Ruiz', '50987654C', 1, 1, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Balmes 89', 634567823, 'sara.lr@hotmail.com'),
(5,  '2025-12-04 17:00:00', '2025-12-04 18:00:00', 'Canvi dins Gràcia', 'Pau', 'Roca Mas', '51234567H', 1, 2, 'Canvi de domicili dins del municipi', '4', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Enric Granados 12', 689012378, 'pau.rm@gmail.com'),
(6,  '2025-12-05 13:00:00', '2025-12-05 14:00:00', 'Me’n vaig a València', 'Joan', 'Ferrer Pons', 'X1239876D', 1, 1, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Pg. de Gràcia 101', 645678934, 'joan.fp@gmail.com'),
(7,  '2025-12-09 16:00:00', '2025-12-09 17:00:00', 'Alta per omissió', 'Marta', 'Giménez Coll', '40987654G', 1, 2, 'Alta per omissió', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Provença 56', 678901267, 'marta.gc@gmail.com'),
(8,  '2025-12-10 09:00:00', '2025-12-10 10:00:00', 'Correcció nom', 'Clàudia', 'Vidal Serra', '38765432E', 1, 1, 'Modificació de dades', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Aragó 234', 656789045, 'claudia.vs@outlook.com'),
(9,  '2025-12-11 18:00:00', '2025-12-11 19:00:00', 'Alta nadó', 'Fatima', 'Zouhair', 'Z1122334I', 1, 2, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Entença 210', 611223389, 'fatima.zh@gmail.com'),
(10, '2025-12-12 12:00:00', '2025-12-12 13:00:00', 'Baixa familiar', 'David', 'Soler Pons', '39876543J', 1, 1, 'Baixa per defunció', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Muntaner 167', 622334490, 'david.sp@gmail.com'),
(11, '2025-12-15 10:00:00', '2025-12-15 11:00:00', 'Empadronament inicial', 'Omar', 'Benali', 'Y5566778L', 1, 1, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Floridablanca 45', 644556612, 'omar.ba@yahoo.es'),
(12, '2025-12-16 15:00:00', '2025-12-16 16:00:00', 'Canvi dins ciutat', 'Elena', 'Puig Camps', '45123987M', 1, 2, 'Canvi de domicili dins del municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Casanova 78', 655667723, 'elena.pc@gmail.com'),
(13, '2025-12-17 11:00:00', '2025-12-17 12:00:00', 'Canvi des de Badalona', 'Rosa', 'Castillo Vega', '27654321K', 1, 1, 'Canvi de domicili des d''un altre municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Calàbria 89', 633445501, 'rosa.cv@hotmail.com'),
(14, '2025-12-18 17:00:00', '2025-12-18 18:00:00', 'Baixa per trasllat', 'Sergi', 'Bosch Torrent', '49234567N', 1, 2, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Diputació 210', 666778834, 'sergi.bt@outlook.com'),
(15, '2025-12-19 13:00:00', '2025-12-19 14:00:00', 'Última setmana', 'Lucía', 'Domínguez Ruiz', 'X8877665O', 1, 1, 'Modificació de dades', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rocafort 123', 677889945, 'lucia.dr@gmail.com'),

-- GENER 2026
(16, '2026-01-07 10:00:00', '2026-01-07 11:00:00', 'Després de Reis', 'Júlia', 'Ferrer Mas', '38765432P', 1, 3, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rosselló 89', 688990056, 'julia.fm@gmail.com'),
(17, '2026-01-08 16:00:00', '2026-01-08 17:00:00', 'Canvi de pis', 'Arnau', 'Vila Pons', '40987654Q', 1, 2, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Viladomat 56', 699001167, 'arnau.vp@gmail.com'),
(18, '2026-01-09 09:00:00', '2026-01-09 10:00:00', 'Vinc de Mataró', 'Nadia', 'El Amrani', 'Y9988776R', 1, 3, 'Canvi de domicili des d''un altre municipi', '4', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Tarragona 123', 611223378, 'nadia.ea@yahoo.com'),
(19, '2026-01-12 09:00:00', '2026-01-12 10:00:00', 'Alta nadó', 'Ivan', 'Petrov Ivanov', 'Z5678901S', 1, 3, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Valencia 444', 622334489, 'ivan.petrov@gmail.com'),
(20, '2026-01-13 15:00:00', '2026-01-13 16:00:00', 'Canvi dins Sarrià', 'Clara', 'Moreno Gil', '51234567T', 1, 2, 'Canvi de domicili dins del municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Major de Sarrià 78', 633445590, 'clara.mg@gmail.com'),
(21, '2026-01-14 11:00:00', '2026-01-14 12:00:00', 'Baixa per trasllat', 'Raúl', 'Molina Vega', 'X8877665U', 1, 3, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rocafort 99', 644556701, 'raul.mv@gmail.com'),
(22, '2026-01-15 16:00:00', '2026-01-15 17:00:00', 'Empadronament inicial', 'Amina', 'Rahmani', 'Y1122334V', 1, 2, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Entença 145', 655667812, 'amina.r@gmail.com'),
(23, '2026-01-19 12:00:00', '2026-01-19 13:00:00', 'Canvi dins Sants', 'Pere', 'Vila Abadal', '39876543W', 1, 3, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Sants 200', 666778923, 'pere.va@gmail.com'),
(24, '2026-01-20 18:00:00', '2026-01-20 19:00:00', 'Alta per omissió', 'Sofia', 'Romero Díaz', '49234567X', 1, 2, 'Alta per omissió', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Rambla Catalunya 89', 677890034, 'sofia.rd@gmail.com'),
(25, '2026-01-21 12:00:00', '2026-01-21 13:00:00', 'Modificació dades', 'Montserrat', 'Trescents Olives', '27654321Y', 1, 3, 'Modificació de dades', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Mallorca 567', 688901145, 'montse.to@hotmail.com'),
(26, '2026-01-22 15:00:00', '2026-01-22 16:00:00', 'Canvi des de l''Hospitalet', 'Mohamed', 'El Amrani', 'Y9876543Z', 1, 2, 'Canvi de domicili des d''un altre municipi', '5', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Marina 56', 699012256, 'mohamed.amrani@yahoo.es'),
(27, '2026-01-27 16:00:00', '2026-01-27 17:00:00', 'Baixa per defunció', 'Rosa', 'Vidal Serra', '27654321A1', 1, 2, 'Baixa per defunció', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Calàbria 210', 611223367, 'rosa.vs@gmail.com'),
(28, '2026-01-28 10:00:00', '2026-01-28 11:00:00', 'Empadronament inicial', 'Pablo', 'López Navarro', '45123987B1', 1, 3, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Casanova 167', 622334478, 'pablo.ln@gmail.com'),
(29, '2026-01-29 15:00:00', '2026-01-29 16:00:00', 'Canvi dins ciutat', 'Núria', 'Bosch Torrent', '51234567C1', 1, 2, 'Canvi de domicili dins del municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Diputació 321', 633445589, 'nuria.bt@gmail.com'),
(30, '2026-01-30 10:00:00', '2026-01-30 11:00:00', 'Alta per naixement', 'Albert', 'Roca Pons', '38765432D1', 1, 3, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Comte d’Urgell 56', 644556690, 'albert.rp@gmail.com'),

-- FEBRER 2026
(31, '2026-02-02 09:00:00', '2026-02-02 10:00:00', 'Canvi de pis', 'Marta', 'Puig Camps', '39876543E1', 1, 3, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Enric Granados 45', 655667801, 'marta.pc@outlook.com'),
(32, '2026-02-03 15:00:00', '2026-02-03 16:00:00', 'Empadronament inicial', 'Ivan', 'Kuznetsov', 'Z6789012F', 1, 2, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Valencia 555', 666778912, 'ivan.k@gmail.com'),
(33, '2026-02-04 10:00:00', '2026-02-04 11:00:00', 'Vinc de Sabadell', 'Aisha', 'Benali', 'Y2233445G', 1, 3, 'Canvi de domicili des d''un altre municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Tarragona 210', 677890023, 'aisha.ba@yahoo.es'),
(34, '2026-02-06 10:00:00', '2026-02-06 11:00:00', 'Alta nadó', 'Jordi', 'Pujol Ferrusola', '39876543H1', 1, 3, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Av. Diagonal 456', 688901134, 'jordi.pf@outlook.com'),
(35, '2026-02-09 13:00:00', '2026-02-09 14:00:00', 'Canvi dins Gràcia', 'Anna', 'Martínez Ruiz', '45123987I1', 1, 3, 'Canvi de domicili dins del municipi', '4', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Balmes 78', 699012245, 'anna.mr@hotmail.com'),
(36, '2026-02-10 16:00:00', '2026-02-10 17:00:00', 'Baixa per trasllat', 'Carlos', 'Hernández Sánchez', 'X1234567J', 1, 2, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Pg. de Gràcia 92', 611223356, 'carlos.hs@gmail.com'),
(37, '2026-02-11 09:00:00', '2026-02-11 10:00:00', 'Empadronament inicial', 'Laura', 'Fernández Torres', '39876123K1', 1, 3, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Aragó 321', 622334467, 'laura.ft@gmail.com'),
(38, '2026-02-12 18:00:00', '2026-02-12 19:00:00', 'Canvi dins Sants', 'Mohamed', 'El Amrani', 'Y9876543L1', 1, 2, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Marina 56', 633445578, 'mohamed.amrani@yahoo.es'),
(39, '2026-02-13 10:00:00', '2026-02-13 11:00:00', 'Alta per omissió', 'Sofia', 'Romero Díaz', '49234567M1', 1, 3, 'Alta per omissió', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'Rambla Catalunya 89', 644556689, 'sofia.rd@gmail.com'),
(40, '2026-02-16 11:00:00', '2026-02-16 12:00:00', 'Modificació dades', 'Pere', 'Vila Abadal', '38765432N1', 1, 3, 'Modificació de dades', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Provença 234', 655667790, 'pere.va@gmail.com'),
(41, '2026-02-17 15:00:00', '2026-02-17 16:00:00', 'Canvi des de Terrassa', 'Montserrat', 'Trescents Olives', '27654321O1', 1, 2, 'Canvi de domicili des d''un altre municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Mallorca 567', 666778901, 'montse.to@hotmail.com'),
(42, '2026-02-19 17:00:00', '2026-02-19 18:00:00', 'Baixa per defunció', 'Ivan', 'Petrov Ivanov', 'Z5678901P1', 1, 2, 'Baixa per defunció', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Valencia 444', 677890012, 'ivan.petrov@gmail.com'),
(43, '2026-02-20 10:00:00', '2026-02-20 11:00:00', 'Empadronament inicial', 'Clara', 'Moreno Gil', '40987654Q1', 1, 3, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Consell de Cent 12', 688901123, 'clara.mg@outlook.com'),
(44, '2026-02-23 12:00:00', '2026-02-23 13:00:00', 'Canvi dins ciutat', 'David', 'Sánchez Pérez', '51234567R1', 1, 3, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Muntaner 89', 699012234, 'david.sp@gmail.com'),
(45, '2026-02-24 17:00:00', '2026-02-24 18:00:00', 'Alta per naixement', 'Amina', 'Rahmani', 'Y1122334S1', 1, 2, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Entença 210', 611223345, 'amina.rahmani@yahoo.com'),

-- MARÇ 2026
(46, '2026-03-02 11:00:00', '2026-03-02 12:00:00', 'Canvi de pis', 'Joan', 'Riera Pons', '38765432T1', 1, 3, 'Canvi de domicili dins del municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rosselló 123', 622334456, 'joan.riera@gmail.com'),
(47, '2026-03-03 15:00:00', '2026-03-03 16:00:00', 'Empadronament inicial', 'Lucía', 'Domínguez Vega', 'X9988776U1', 1, 2, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rocafort 55', 633445567, 'lucia.dv@gmail.com'),
(48, '2026-03-05 16:00:00', '2026-03-05 17:00:00', 'Canvi des de Badalona', 'Sergi', 'Collado Mas', '40987654V1', 1, 2, 'Canvi de domicili des d''un altre municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Viladomat 99', 644556678, 'sergi.cm@hotmail.com'),
(49, '2026-03-06 12:00:00', '2026-03-06 13:00:00', 'Alta nadó', 'Núria', 'Bosch Torrent', '51234567W1', 1, 3, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Diputació 321', 655667789, 'nuria.bt@gmail.com'),
(50, '2026-03-09 09:00:00', '2026-03-09 10:00:00', 'Canvi dins ciutat', 'Omar', 'Benali', 'Z2233445X1', 1, 3, 'Canvi de domicili dins del municipi', '4', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Tarragona 78', 666778890, 'omar.benali@yahoo.es'),
(51, '2026-03-10 15:00:00', '2026-03-10 16:00:00', 'Baixa per trasllat', 'Rosa', 'Vidal Serra', '27654321Y1', 1, 2, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Calàbria 210', 677890001, 'rosa.vs@gmail.com'),
(52, '2026-03-12 16:00:00', '2026-03-12 17:00:00', 'Empadronament inicial', 'Marta', 'Puig Camps', '39876543Z1', 1, 2, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Enric Granados 45', 688901112, 'marta.pc@outlook.com'),
(53, '2026-03-13 09:00:00', '2026-03-13 10:00:00', 'Canvi dins Gràcia', 'Pablo', 'López Navarro', '45123987A2', 1, 3, 'Canvi de domicili dins del municipi', '2', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Casanova 167', 699012223, 'pablo.ln@gmail.com'),
(54, '2026-03-16 11:00:00', '2026-03-16 12:00:00', 'Canvi des de l''Hospitalet', 'Fatima', 'Zouhair', 'Y5566778B2', 1, 3, 'Canvi de domicili des d''un altre municipi', '5', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Floridablanca 89', 611223334, 'fatima.zh@gmail.com'),
(55, '2026-03-17 15:00:00', '2026-03-17 16:00:00', 'Alta per naixement', 'Albert', 'Roca Pons', '38765432C2', 1, 2, 'Alta per naixement', '2', 'Sí, menor de 14 anys', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Comte d’Urgell 56', 622334445, 'albert.rp@gmail.com'),
(56, '2026-03-19 15:00:00', '2026-03-19 16:00:00', 'Modificació dades', 'Elena', 'Castillo Ruiz', '49234567D2', 1, 2, 'Modificació de dades', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ París 187', 633445556, 'elena.cr@hotmail.com'),
(57, '2026-03-20 10:00:00', '2026-03-20 11:00:00', 'Baixa per trasllat', 'Raúl', 'Molina Vega', 'X8877665E2', 1, 3, 'Baixa per trasllat a un altre municipi', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rocafort 123', 644556667, 'raul.mv@gmail.com'),
(58, '2026-03-23 12:00:00', '2026-03-23 13:00:00', 'Empadronament inicial', 'Júlia', 'Ferrer Mas', '38765432F2', 1, 3, 'Empadronament inicial', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Rosselló 89', 655667778, 'julia.fm@gmail.com'),
(59, '2026-03-24 18:00:00', '2026-03-24 19:00:00', 'Canvi dins ciutat', 'Arnau', 'Vila Pons', '40987654G2', 1, 2, 'Canvi de domicili dins del municipi', '3', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Viladomat 56', 666778889, 'arnau.vp@gmail.com'),
(60, '2026-03-26 16:00:00', '2026-03-26 17:00:00', 'Alta per omissió', 'Nadia', 'El Amrani', 'Y9988776H2', 1, 2, 'Alta per omissió', '1', 'No', NULL,NULL,NULL,NULL,NULL,NULL,NULL, 'C/ Tarragona 123', 677890000, 'nadia.ea@yahoo.com');


-- SINCRONITZAR SEQUENCIES

ALTER SEQUENCE IF EXISTS ubicacio_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM UBICACIO);
ALTER SEQUENCE IF EXISTS tipus_cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM TIPUS_CITA);
ALTER SEQUENCE IF EXISTS horari_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM HORARI);
ALTER SEQUENCE IF EXISTS agenda_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM AGENDA);
ALTER SEQUENCE IF EXISTS cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM CITA);