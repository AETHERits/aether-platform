-- Seed data for H2 in-memory database (dev profile)
-- Spring Boot executes this automatically after schema creation
-- [HEL-501] Apertura incidente operativo: populate the "Base" select

INSERT INTO colonie (id_colonia, nome) VALUES (1, 'Ares Prime');
INSERT INTO colonie (id_colonia, nome) VALUES (2, 'Syrtis Base');
INSERT INTO colonie (id_colonia, nome) VALUES (3, 'Hellas Station');