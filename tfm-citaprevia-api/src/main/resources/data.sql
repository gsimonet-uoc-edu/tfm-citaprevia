-- SUBAPLICACIO
INSERT INTO SUBAPLICACIO (coa, dec, dem) VALUES 
('AP1', 'Aplicació Principal', 'Gestió de cites'),
('AP2', 'Aplicació Secundària', 'Seguiments');

-- TECNIC
INSERT INTO TECNIC (coa, pass, nom, ll1, ll2, prf) VALUES 
('T1', '1234', 'Joan', 'Garcia', 'Marqués','TECNIC_AP1'),
('T2', '1234', 'Maria', 'Ginard', 'Torres','ADMINISTRADOR_AP1'),
('T3', '1234', 'Guillem', 'Simonet', 'Ramon','TECNIC_AP1');

-- UBICACIO
INSERT INTO UBICACIO (con, nom, nomcar, obs) VALUES 
(1, 'Centre A', 'C/ Avinguda Barcelona 2', 'Observacions A'),
(2, 'Centre B', 'Plaça Catalunya 32 baixos', 'Observacions B');

-- TIPUS_CITA
INSERT INTO TIPUS_CITA (con, dec, dem, tipcitmod, SUBAPL_COA) VALUES 
(1, 'Consulta Inicial', 'Primera visita', 'P', 'AP1'),
(2, 'Consulta Successiva', 'Segina visita', 'P', 'AP1'),
(3, 'Seguiment', 'Control', 'T', 'AP2');

-- HORARI
-- Horario: Matí (con = 1)
INSERT INTO HORARI (con, dec, dem, SUBAPL_COA, TIPCIT_CON) VALUES
(1, 'Matí', 'Horari de matí', 'AP1', 1),
(2, 'Tarda', '13-18h', 'AP1', 1);

-- AGENDA
INSERT INTO AGENDA (con, datini, datfin, UBI_CON, TEC_COA, HORCIT_CON) VALUES 
(1, '2025-11-01', '2025-11-30', 1, 'T1', 1),
(2, '2025-11-01', '2025-11-07', 2, 'T2', 2),
(3, '2025-12-01', '2025-12-15', 1, 'T3', 2);

-- SetmanaTipus: Lunes y Miércoles, 9-10 y 10-11
INSERT INTO SETMANA_TIPUS (HORCIT_CON, DIASET_CON, horini, horfin) VALUES
(1, 1, '09:00:00', '10:00:00'),  -- horari 1 dilluns 9-10
(1, 1, '10:00:00', '11:00:00'),  -- horari 1 dilluns 10-11
(1, 3, '09:00:00', '10:00:00'),  -- horari 1 dimecres 10-11
(2, 1, '13:00:00', '14:00:00'),  -- horari 2 dilluns 13-14
(2, 2, '13:00:00', '14:00:00'),	-- horari 2 dimarts 13-14
(2, 3, '13:00:00', '14:00:00'),	-- horari 2 dimecres 13-14
(2, 4, '13:00:00', '14:00:00'),	-- horari 2 dijous 13-14
(2, 5, '13:00:00', '14:00:00');	-- horari 2 divendres 13-14
-- src/main/resources/data.sql

-- ===================================================================
-- INSERTS CORREGIDOS PARA CITA (H2) - 22 COLUMNAS
-- ===================================================================

-- Cita 1
INSERT INTO CITA (
    con, dathorini, dathorfin, obs, nom, llis, numdoc,
    tipcit_con, age_con,
    lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
    nomcar, tel, ema
) VALUES (
    1,
    '2025-11-03 09:00:00', '2025-11-03 10:00:00',
    'Paciente con dolor de cabeza recurrente',
    'Maria Pérez García', 'S', '43186268L',
    1, 1,
    '12345678A', '1985-03-15', 'C/ Major, 123', '934567890', 'maria@email.com',
    NULL, NULL, NULL, NULL, NULL,
    'Pérez', 934567890, 'maria@email.com'
);

-- Cita 2 (CORREGIDA: 22 valores)
INSERT INTO CITA (
    con, dathorini, dathorfin, obs, nom, llis, numdoc,
    tipcit_con, age_con,
    lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
    nomcar, tel, ema
) VALUES (
    2,
    '2025-11-05 09:00:00', '2025-11-05 10:00:00',
    'Paciente necesita receta de insulina. Traer informe médico anterior.',
    'Joan Torres Vila', 'S', '12341234Z',
    1, 1,
    '87654321B', '1978-07-22', 'Av. Diagonal 456', '933221100', 'joan.torres@email.com',
    'Diabetes tipo 2', 'Metformina 850mg', 'Control glucosa', NULL, NULL,
    'Torres', 933221100, 'joan.torres@email.com'
);

-- Cita 3
INSERT INTO CITA (
    con, dathorini, dathorfin, obs, nom, llis, numdoc,
    tipcit_con, age_con,
    lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
    nomcar, tel, ema
) VALUES (
    3,
    '2025-12-02 13:00:00', '2025-12-02 14:00:00',
    'Trámite de renovación de DNI. Traer foto reciente.',
    'Laura Gómez Ruiz', 'S', '431762319S',
    1, 3,
    '11223344C', NULL, NULL, NULL, 'laura.alt@email.com',
    'Renovación DNI', 'Foto 3x4', 'Impreso cumplimentado', NULL, NULL,
    'Gómez', 932112233, 'laura.gomez@email.com'
);

-- Cita 4
INSERT INTO CITA (
    con, dathorini, dathorfin, obs, nom, llis, numdoc,
    tipcit_con, age_con,
    lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
    nomcar, tel, ema
) VALUES (
    4,
    '2025-11-12 09:00:00', '2025-11-12 10:00:00',
    'Primera visita. Paciente nuevo.',
    'Carlos Sánchez López', 'S', '45423464Y',
    1, 1,
    '55667788D', '1990-11-30', 'C/ Gran Via 789', '931234567', 'carlos.sanchez@email.com',
    'Seguro privado', 'Alergia a penicilina', 'Toma ibuprofeno', 'Peso: 78kg', 'Altura: 178cm',
    'Sánchez', 931234567, 'carlos.sanchez@email.com'
);

-- Cita 5
INSERT INTO CITA (
    con, dathorini, dathorfin, obs, nom, llis, numdoc,
    tipcit_con, age_con,
    lit1er, lit2on, lit3er, lit4rt, lit05e, lit06e, lit07e, lit08e, lit09e, lit10e,
    nomcar, tel, ema
) VALUES (
    5,
    '2025-12-04 13:00:00', '2025-12-04 14:00:00',
    NULL,
    'Ana Martínez Díaz', 'S', '12334123W',
    1, 3,
    '99887766E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
    'Martínez', 934567123, 'ana.martinez@email.com'
);
-- ========================================
-- SINCRONIZAR SECUENCIAS PARA H2
-- ========================================
ALTER SEQUENCE IF EXISTS ubicacio_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM UBICACIO);
ALTER SEQUENCE IF EXISTS tipus_cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM TIPUS_CITA);
ALTER SEQUENCE IF EXISTS horari_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM HORARI);
ALTER SEQUENCE IF EXISTS agenda_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM AGENDA);
ALTER SEQUENCE IF EXISTS cita_seq RESTART WITH (SELECT COALESCE(MAX(con), 0) + 1 FROM CITA);