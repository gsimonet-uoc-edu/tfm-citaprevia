-- SUBAPLICACIO
INSERT INTO SUBAPLICACIO (coa, dec, dem) VALUES 
('AP1', 'Aplicació Principal', 'Gestió de cites'),
('AP2', 'Aplicació Secundària', 'Seguiments');

-- TECNIC
INSERT INTO TECNIC (coa, pass, nom, ll1, ll2, notval, prf) VALUES 
('T1', '1234', 'Joan', 'Garcia', 'Marqués', 'S', 'TECNIC'),
('T2', '1234', 'Maria', 'Ginard', 'Torres', 'S', 'ADMINISTRADOR');

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
    '2025-11-03 09:00:00', '2025-11-03 09:30:00',
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
    '2025-11-04 10:00:00', '2025-11-04 10:30:00',
    'Paciente necesita receta de insulina. Traer informe médico anterior.',
    'Joan Torres Vila', 'S', '12341234Z',
    1, 2,
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
    '2025-11-05 11:00:00', '2025-11-05 11:30:00',
    'Trámite de renovación de DNI. Traer foto reciente.',
    'Laura Gómez Ruiz', 'S', '431762319S',
    2, 1,
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
    '2025-11-06 14:00:00', '2025-11-06 14:30:00',
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
    '2025-11-07 15:00:00', '2025-11-07 15:30:00',
    NULL,
    'Ana Martínez Díaz', 'S', '12334123W',
    2, 2,
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