-- SUBAPLICACIO
INSERT INTO SUBAPLICACIO (coa, dec, dem) VALUES 
('AP1', 'Aplicació Principal', 'Gestió de cites'),
('AP2', 'Aplicació Secundària', 'Seguiments');

-- TECNIC
INSERT INTO TECNIC (coa, nom, ll1, ll2, notval) VALUES 
('T1', 'Joan', 'Garcia', 'Marqués', 'S'),
('T2', 'Maria', 'Ginard', 'Torres', 'N');

-- UBICACIO
INSERT INTO UBICACIO (con, nom, obs) VALUES 
(1, 'Centre A', 'Principal'),
(2, 'Centre B', 'Secundari');

-- TIPUS_CITA
INSERT INTO TIPUS_CITA (con, dec, dem, notval, cap, gespri, tipcitmod, SUBAPL_COA) VALUES 
(1, 'Consulta Inicial', 'Primera visita', 'S', 30, 'S', 'P', 'AP1'),
(2, 'Consulta Successiva', 'Segina visita', 'S', 30, 'S', 'P', 'AP1'),
(3, 'Seguiment', 'Control', 'N', 15, 'N', 'T', 'AP2');

-- HORARI
-- Horario: Matí (con = 1)
INSERT INTO HORARI (con, dec, dem, notval, SUBAPL_COA, TIPCIT_CON) VALUES
(1, 'Matí', 'Horari de matí', 'S', 'AP1', 1),
(2, 'Tarda', '14-18h', 'N', 'AP2', 2);

-- AGENDA
INSERT INTO AGENDA (con, datini, datfin, UBI_CON, TEC_COA, HORCIT_CON, gespri, usucre, usumod, datcre, datmod, seqmod) VALUES 
(1, '2025-11-01', '2025-11-30', 1, 'T1', 1, 'S', 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
(2, '2025-11-01', '2025-11-07', 2, 'T2', 2, 'N', 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);

-- SetmanaTipus: Lunes y Miércoles, 9-10 y 10-11
INSERT INTO SETMANA_TIPUS (HORCIT_CON, DIASET_CON, horini, horfin, TIPCIT_CON) VALUES
(1, 1, '09:00:00', '10:00:00', 1),  -- Lunes 9-10
(1, 1, '10:00:00', '11:00:00', 1),  -- Lunes 10-11
(1, 3, '09:00:00', '10:00:00', 1);  -- Miércoles 9-10

-- CITA
INSERT INTO CITA (con, dathorini, dathorfin, obs, TIPCIT_CON, AGE_CON, lit1er, nomcar, tel, ema) VALUES 
(1, '2025-11-03 09:30:00', '2025-11-03 10:00:00', 'Primera visita', 1, 1, 'Pacient A', 'Cardiologia', 123456789, 'a@ex.com'),
(2, '2025-11-04 14:30:00', '2025-11-04 15:00:00', 'Seguiment', 2, 2, 'Pacient B', 'General', 987654321, 'b@ex.com');

-- ========================================
-- SINCRONIZAR SECUENCIAS PARA H2
-- ========================================
ALTER SEQUENCE IF EXISTS ubicacio_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM UBICACIO);
ALTER SEQUENCE IF EXISTS tipus_cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM TIPUS_CITA);
ALTER SEQUENCE IF EXISTS horari_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM HORARI);
ALTER SEQUENCE IF EXISTS agenda_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM AGENDA);
ALTER SEQUENCE IF EXISTS cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM CITA);