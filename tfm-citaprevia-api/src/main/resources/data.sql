-- Inserció a SUBAPLICACIO
INSERT INTO SUBAPLICACIO (coa, dec, dem) VALUES 
('AP1', 'Aplicació Principal', 'Aplicació per a gestió de cites mèdiques'),
('AP2', 'Aplicació Secundària', 'Aplicació per a seguiments mèdics');

-- Inserció a TECNIC
INSERT INTO TECNIC (coa, nom, ll1, ll2, notval) VALUES 
('T1', 'Joan', 'Garcia', 'Marqués', 'S'),
('T2', 'Maria', 'Ginard', 'Torres', 'N');

-- Inserció a UBICACIO
INSERT INTO UBICACIO (nom, obs) VALUES 
('Centre Mèdic A', 'Centre mèdic principal a la ciutat'),
('Centre Mèdic B', 'Centre mèdic secundari a la perifèria');

-- Inserció a TIPUS_CITA
INSERT INTO TIPUS_CITA (dec, dem, notval, cap, gespri, tipcitmod, SUBAPL_COA) VALUES 
('Consulta Inicial', 'Consulta inicial amb el metge', 'S', 30, 'S', 'P', 'AP1'),
('Consulta Inicial2', 'Consulta inicial amb el metge2', 'S', 30, 'S', 'P', 'AP1'),
('Seguiment', 'Visita de seguiment mèdic', 'N', 15, 'N', 'T', 'AP2');

-- Inserció a HORARI
INSERT INTO HORARI (dec, dem, notval, SUBAPL_COA, TIPCIT_CON) VALUES 
('Torn de Matí', 'Horari de 9 a 13 h', 'S', 'AP1', 1),
('Torn de nit', 'Horari de 0 a 8 h', 'S', 'AP1', 1),
('Torn de Tarda', 'Horari de 14 a 18 h', 'N', 'AP2', 2);

-- Inserció a AGENDA
INSERT INTO AGENDA (datini, datfin, UBI_CON, TEC_COA, HORCIT_CON, gespri, usucre, usumod, datcre, datmod, seqmod) VALUES 
('2025-11-01', '2025-11-07', 1, 'T1', 1, 'S', 'admin', 'admin', '2025-10-23 10:00:00', '2025-10-23 10:00:00', 1),
('2025-11-08', '2025-11-14', 2, 'T2', 2, 'N', 'admin', 'admin', '2025-10-23 11:00:00', '2025-10-23 11:00:00', 2);

-- Inserció a SETMANA_TIPUS
INSERT INTO SETMANA_TIPUS (HORCIT_CON, DIASET_CON, horini, horfin, TIPCIT_CON) VALUES 
(1, 1, '2025-11-01 09:00:00', '2025-11-01 10:00:00', 1),
(2, 2, '2025-11-08 14:00:00', '2025-11-08 15:00:00', 2);

-- Inserció a CITA
INSERT INTO CITA (dathorini, dathorfin, obs, TIPCIT_CON, AGE_CON, lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e, nomcar, tel, ema) VALUES 
('2025-11-01 09:30:00', '2025-11-01 10:00:00', 'Primera revisió mèdica', 1, 1, 'Pacient A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Cardiologia', 123456789, 'pacientA@exemple.com'),
('2025-11-08 14:30:00', '2025-11-08 15:00:00', 'Visita de seguiment', 2, 2, 'Pacient B', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Medicina General', 987654321, 'pacientB@exemple.com');