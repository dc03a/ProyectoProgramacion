DROP DATABASE IF EXISTS proyectoProgramacion;
CREATE DATABASE proyectoProgramacion;
USE proyectoProgramacion;

CREATE TABLE objetos (
	ID_OBJETO INT PRIMARY KEY,
	NOMBRE VARCHAR(20),
	DESCRIPCION VARCHAR(100)
);

CREATE TABLE habilidades (
    ID_HABILIDAD INT PRIMARY KEY AUTO_INCREMENT,
    NOMBRE VARCHAR(50),
    EFECTO VARCHAR(255)
);

CREATE TABLE movimientos (
	ID_MOVIMIENTO INT PRIMARY KEY AUTO_INCREMENT,
    TIPO VARCHAR(20),
    DESCRIPCION VARCHAR(255),
    POTENCIA INT,
    ACURACY INT,
    PP INT,
    CLASE VARCHAR(12)
);

CREATE TABLE caja (
	ID_CAJA INT PRIMARY KEY,
	NOMBRE VARCHAR(50),
    CAPACIDAD INT
);

CREATE TABLE pokemon (
    ID_POKEMON INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    NUM_POKEDEX INT,
    NOMBRE VARCHAR(50) UNIQUE,
    ID_HABILIDAD INT,
    TIPO1 VARCHAR(20),
    TIPO2 VARCHAR(20),
    NIVEL INT,
    HP INT,
    ATAQUE INT,
    DEFENSA INT,
    ATAQUE_ESPECIAL INT,
    DEFENSA_ESPECIAL INT,
    VELOCIDAD INT,
    OBJETO INT,
    ID_CAJA INT,
    EQUIPO BOOLEAN,
    MOV1 INT,
    MOV2 INT,
    FOREIGN KEY(MOV1) REFERENCES movimientos(ID_MOVIMIENTO),
    FOREIGN KEY(MOV2) REFERENCES movimientos(ID_MOVIMIENTO),
    FOREIGN KEY(OBJETO) REFERENCES objetos(ID_OBJETO),
    FOREIGN KEY (ID_CAJA) REFERENCES caja(ID_CAJA),
    FOREIGN KEY (ID_HABILIDAD) REFERENCES habilidades(ID_HABILIDAD)
);

CREATE TABLE pokemon_movimientos (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_POKEMON INT,
    ID_MOVIMIENTO INT,
    FOREIGN KEY (ID_POKEMON) REFERENCES pokemon(ID_POKEMON),
    FOREIGN KEY (ID_MOVIMIENTO) REFERENCES movimientos(ID_MOVIMIENTO)
);

CREATE TABLE equipo (
    ID_POKEMON INT,
    OBJETO VARCHAR(50),
    FOREIGN KEY (ID_POKEMON) REFERENCES pokemon(ID_POKEMON)
);



INSERT INTO habilidades (NOMBRE, EFECTO) VALUES
('Espesura', 'Aumenta el poder de los movimientos de tipo Planta cuando los PS del Pokémon están bajos.'),
('Mar Llamas', 'Aumenta el poder de los movimientos de tipo Fuego cuando los PS del Pokémon están bajos.'),
('Torrente', 'Aumenta el poder de los movimientos de tipo Agua cuando los PS del Pokémon están bajos.'),
('Polvo Escudo', 'Impide efectos adicionales de los movimientos del adversario.'),
('Mudar', 'Al finalizar cada turno, tiene un 1/3 de probabilidades de curar un problema de estado.'),
('Ojo Compuesto', 'Aumenta la precisión de los movimientos del Pokémon.'),
('Enjambre', 'Aumenta el poder de los movimientos de tipo Bicho cuando los PS del Pokémon están bajos.'),
('Vista Lince', 'La precisión del Pokémon no puede ser reducida por movimientos del adversario ni por cambios de estadísticas.'),
('Agallas', 'Aumenta el Ataque en un 50% si el Pokémon sufre problemas de estado.'),
('Intimidación', 'Reduce el Ataque del adversario en 1 nivel al entrar al combate.'),
('Estática', 'Cualquier Pokémon que golpee al usuario con un movimiento de contacto tiene una probabilidad de quedar paralizado.'),
('Velo Arena', 'Aumenta la evasión del Pokémon bajo una tormenta de arena.'),
('Punto Tóxico', 'Cualquier Pokémon que golpee al usuario con un movimiento de contacto tiene una probabilidad de quedar envenenado.'),
('Gran Encanto', 'Cualquier Pokémon que golpee al usuario con un movimiento de contacto tiene una probabilidad de quedar enamorado.'),
('Absorbe Fuego', 'El Pokémon es inmune a los movimientos de tipo Fuego. Al ser golpeado por un movimiento de tipo Fuego, su Ataque Especial aumenta en 1 nivel.'),
('Foco Interno', 'El Pokémon es inmune a ser atacado por movimientos que hagan flinching.'),
('Clorofila', 'Aumenta la Velocidad del Pokémon cuando hay sol.'),
('Efecto Espora', 'Cualquier Pokémon que golpee al usuario con un movimiento de contacto tiene una probabilidad de quedar paralizado, envenenado o dormido.');


INSERT INTO pokemon (NUM_POKEDEX, NOMBRE, ID_HABILIDAD, TIPO1, TIPO2, HP, ATAQUE, DEFENSA, ATAQUE_ESPECIAL, DEFENSA_ESPECIAL, VELOCIDAD, NIVEL) VALUES
(1, 'Bulbasaur', 1,'Planta', 'Veneno', 45, 49, 49, 65, 65, 45,50),
(2, 'Ivysaur', 1,'Planta', 'Veneno', 60, 62, 63, 80, 80, 60,50),
(3, 'Venusaur', 1,'Planta', 'Veneno', 80, 82, 83, 100, 100, 80,50),
(4, 'Charmander', 2,'Fuego', NULL, 39, 52, 43, 60, 50, 65,50),
(5, 'Charmeleon', 2,'Fuego', NULL, 58, 64, 58, 80, 65, 80,50),
(6, 'Charizard', 2,'Fuego', 'Volador', 78, 84, 78, 109, 85, 100,50),
(7, 'Squirtle', 3,'Agua', NULL, 44, 48, 65, 50, 64, 43,50),
(8, 'Wartortle', 3,'Agua', NULL, 59, 63, 80, 65, 80, 58,50),
(9, 'Blastoise', 3,'Agua', NULL, 79, 83, 100, 85, 105, 78,50),
(10, 'Caterpie', 4,'Bicho', NULL, 45, 30, 35, 20, 20, 45,50),
(11, 'Metapod', 5,'Bicho', NULL, 50, 20, 55, 25, 25, 30,50),
(12, 'Butterfree', 6,'Bicho', 'Volador', 60, 45, 50, 90, 80, 70,50),
(13, 'Weedle', 7,'Bicho', 'Veneno', 40, 35, 30, 20, 20, 50,50),
(14, 'Kakuna', 7,'Bicho', 'Veneno', 45, 25, 50, 25, 25, 35,50),
(15, 'Beedrill', 7,'Bicho', 'Veneno', 65, 90, 40, 45, 80, 75,50),
(16, 'Pidgey', 8,'Normal', 'Volador', 40, 45, 40, 35, 35, 56,50),
(17, 'Pidgeotto', 8,'Normal', 'Volador', 63, 60, 55, 50, 50, 71,50),
(18, 'Pidgeot', 8,'Normal', 'Volador', 83, 80, 75, 70, 70, 101,50),
(19, 'Rattata', 9,'Normal', NULL, 30, 56, 35, 25, 35, 72,50),
(20, 'Raticate', 9,'Normal', NULL, 55, 81, 60, 50, 70, 97,50),
(21, 'Spearow', 8,'Normal', 'Volador', 40, 60, 30, 31, 31, 70,50),
(22, 'Fearow', 8,'Normal', 'Volador', 65, 90, 65, 61, 61, 100,50),
(23, 'Ekans', 10,'Veneno', NULL, 35, 60, 44, 40, 54, 55,50),
(24, 'Arbok', 10,'Veneno', NULL, 60, 85, 69, 65, 79, 80,50),
(25, 'Pikachu', 11,'Eléctrico', NULL, 35, 55, 40, 50, 50, 90,50),
(26, 'Raichu', 11,'Eléctrico', NULL, 60, 90, 55, 90, 80, 110,50),
(27, 'Sandshrew', 12,'Tierra', NULL, 50, 75, 85, 20, 30, 40,50),
(28, 'Sandslash', 12,'Tierra', NULL, 75, 100, 110, 45, 55, 65,50),
(29, 'Nidoran♀', 13,'Veneno', NULL, 55, 47, 52, 40, 40, 41,50),
(30, 'Nidorina', 13,'Veneno', NULL, 70, 62, 67, 55, 55, 56,50),
(31, 'Nidoqueen', 13,'Veneno', 'Tierra', 90, 82, 87, 75, 85, 76,50),
(32, 'Nidoran♂', 13,'Veneno', NULL, 46, 57, 40, 40, 40, 50,50),
(33, 'Nidorino', 13,'Veneno', NULL, 61, 72, 57, 55, 55, 65,50),
(34, 'Nidoking', 13,'Veneno', 'Tierra', 81, 102, 77, 85, 75, 85,50),
(35, 'Clefairy', 14,'Hada', NULL, 70, 45, 48, 60, 65, 35,50),
(36, 'Clefable', 14,'Hada', NULL, 95, 70, 73, 95, 90, 60,50),
(37, 'Vulpix', 15,'Fuego', NULL, 38, 41, 40, 50, 65, 65,50),
(38, 'Ninetales', 15,'Fuego', NULL, 73, 76, 75, 81, 100, 100,50),
(39, 'Jigglypuff', 14,'Normal', 'Hada', 115, 45, 20, 45, 25, 20,50),
(40, 'Wigglytuff', 14,'Normal', 'Hada', 140, 70, 45, 85, 50, 45,50),
(41, 'Zubat', 16,'Veneno', 'Volador', 40, 45, 35, 30, 40, 55,50),
(42, 'Golbat', 16,'Veneno', 'Volador', 75, 80, 70, 65, 75, 90,50),
(43, 'Oddish', 17,'Planta', 'Veneno', 45, 50, 55, 75, 65, 30,50),
(44, 'Gloom', 17,'Planta', 'Veneno', 60, 65, 70, 85, 75, 40,50),
(45, 'Vileplume', 17,'Planta', 'Veneno', 75, 80, 85, 110, 90, 50,50),
(46, 'Paras', 18,'Bicho', 'Planta', 35, 70, 55, 45, 55, 25,50),
(47, 'Parasect', 18,'Bicho', 'Planta', 60, 95, 80, 60, 80, 30,50),
(48, 'Venonat', 13,'Bicho', 'Veneno', 60, 55, 50, 40, 55, 45,50),
(49, 'Venomoth', 13,'Bicho', 'Veneno', 70, 65, 60, 90, 75, 90,50),
(50, 'Diglett', 12,'Tierra', NULL, 10, 55, 25, 35, 45, 95,50);


INSERT INTO movimientos (TIPO, DESCRIPCION, POTENCIA, ACURACY, PP, CLASE) VALUES
('Planta', 'Látigo Cepa', 35, 100, 15, 'Fisico'),
('Planta', 'Hoja Afilada', 55, 95, 25, 'Fisico'),
('Planta', 'Bomba Germen', 75, 100, 10, 'Fisico'),
('Fuego', 'Ascuas', 40, 100, 25, 'Especial'),
('Fuego', 'Lanzallamas', 90, 100, 15, 'Especial'),
('Fuego', 'Rueda Fuego', 60, 100, 25,'Fisico'),
('Fuego', 'Anillo Ígneo', 65, 85, 15, 'Especial'),
('Agua', 'Pistola Agua', 40, 100, 25, 'Especial'),
('Agua', 'Hidrobomba', 110, 80, 5, 'Especial'),
('Agua', 'Acua Jet', 40, 100, 20,'Fisico'),
('Normal', 'Placaje', 50, 100, 35,'Fisico'),
('Normal', 'Hiperrayo', 150, 90, 5, 'Especial'),
('Normal', 'Derribo', 90, 90, 15,'Fisico'),
('Eléctrico', 'Impactrueno', 50, 100, 20, 'Especial'),
('Eléctrico', 'Rayo', 90, 100, 15, 'Especial'),
('Eléctrico', 'Chispa', 65, 100, 20,'Fisico'),
('Eléctrico', 'Trueno', 110, 70, 10, 'Especial'),
('Tierra', 'Excavar', 80, 100, 10,'Fisico'),
('Tierra', 'Terremoto', 100, 100, 10,'Fisico'),
('Veneno', 'Onda Toxica', 95, 100, 10, 'Especial'),
('Veneno', 'Lanza Mugre', 65, 100, 10,'Fisico'),
('Volador', 'Tornado', 40, 100, 35, 'Especial'),
('Volador', 'Golpe Aéreo', 60, 95, 20,'Fisico'),
('Volador', 'Pájaro Osado', 120, 100, 15,'Fisico'),
('Volador', 'Pico Taladro', 80, 100, 20,'Fisico'),
('Bicho', 'Chupavidas', 80, 100, 10,'Fisico'),
('Bicho', 'Acoso', 40, 100, 15, 'Especial'),
('Roca', 'Lanzarrocas', 50, 90, 15,'Fisico'),
('Roca', 'Desenrrollar', 30, 90, 20,'Fisico'),
('Fantasma', 'Lengüetazo', 30, 100, 30,'Fisico'),
('Fantasma', 'Bola Sombra', 80, 100, 15, 'Especial'),
('Dragón', 'Cola Dragón', 60, 90, 10,'Fisico'),
('Dragón', 'Cometa Draco', 120, 100, 5, 'Especial'),
('Dragón', 'Garra Dragón', 80, 100, 15,'Fisico'),
('Siniestro', 'Mordisco', 60, 100, 25,'Fisico'),
('Siniestro', 'Finta', 60, 100, 20,'Fisico'),
('Siniestro', 'Vendetta', 70, 100, 5,'Fisico'),
('Acero', 'Cabeza de Hierro', 80, 100, 15,'Fisico'),
('Acero', 'Garra Metal', 50, 95, 35,'Fisico'),
('Hielo', 'Viento Hielo', 55, 95, 15, 'Especial'),
('Hielo', 'Rayo Hielo', 90, 100, 10, 'Especial'),
('Lucha', 'Golpe Karate', 50, 100, 25,'Fisico'),
('Lucha', 'Patada Salto', 100, 95, 10,'Fisico'),
('Psíquico', 'Psíquico', 90, 100, 10, 'Especial'),
('Psíquico', 'Confusión', 50, 100, 25, 'Especial'),
('Psíquico', 'Psicorrayo', 65, 100, 20, 'Especial');

INSERT INTO objetos (ID_OBJETO, NOMBRE, DESCRIPCION) VALUES
(1,'Banda Focus', 'Evita que el pokemon muera directamente cuando esta con la vida al maximo'),
(2,'Baya Zidra', 'Cura el 33% de los PS máximos del Pokémon'),
(3,'Baya Aranja', 'Cura 10PS del Pokémon'),
(4,'Baya Meloc', 'Cura el envenenamiento'),
(5,'Casco Dentado', 'Si el usuario es alcanzado por un ataque Fisico el atacante sufre daños'),
(6,'Cuerda Huida','Permite huir de cuevas o lugares aislados de manera rápida'),
(7,'Repelente','Hace que no puedan atacarte Pokémons durante 200 pasos'),
(8,'Superrepelente','Hace que no puedan atacarte Pokémons durante 500 pasos'),
(9,'Hechizo','Potencia los ataques de tipo Fantasma'),
(10,'Gema Fuego','Potencia los ataques de tipo Fuego'),
(11,'Baya Zreza','Cura la paralisis'),
(12,'Cura Total','Cura los problemas de estado'),
(13,'Sombrero Mexicano','Fiesta'),
(14,'Disco Extraño','Es de fabricante desconocido'),
(15,'Escama Bella','Curiosa escama que hace evolucionar a algunos pokemon'),
(16,'Gafas Protectoras','Gafas que protegen de condiciones adversas del clima y polvo lanzado por rivales'),
(17,'Gafas Especiales','Aumenta la potencia de ataques especiales'),
(18,'Garra Rapida','Permite que a veces ataques primero'),
(19,'Huevo Suerte','Aumenta la experiencia al final de cada combate'),
(20,'Polvo Veloz','Aumenta la velocidad a algunos pokemon')
;

INSERT INTO caja (ID_CAJA, NOMBRE, CAPACIDAD) VALUES
(1, 'CAJA 1', 30),
(2, 'CAJA 2', 30),
(3, 'CAJA 3', 30),
(4, 'CAJA 4', 30),
(5, 'CAJA 5', 30);

DELIMITER $$
CREATE TRIGGER liberarDeCaja
BEFORE UPDATE ON pokemon
FOR EACH ROW
BEGIN
	DECLARE comprobar BOOLEAN;
	SET comprobar = (SELECT new.ID_CAJA FROM POKEMON WHERE ID_POKEMON = new.ID_POKEMON);
    IF comprobar = 0 THEN
        UPDATE pokemon SET ID_CAJA = 0, EQUIPO = false;
	END IF;
END $$
DELIMITER ;

DELIMITER //
CREATE TRIGGER meterEquipo
AFTER UPDATE ON pokemon
FOR EACH ROW
BEGIN
	DECLARE comprobar BOOLEAN;
	SET comprobar = (SELECT EQUIPO FROM POKEMON WHERE ID_POKEMON = new.ID_POKEMON);
    IF comprobar = 0 THEN
		INSERT INTO equipo(ID_POKEMON,OBJETO) VALUES (new.ID_POKEMON, (SELECT obj.NOMBRE FROM pokemon pok
		    LEFT JOIN objetos obj ON pok.OBJETO = obj.ID_OBJETO WHERE ID_POKEMON = new.ID_POKEMON));
		UPDATE pokemon SET ID_CAJA = null WHERE ID_POKEMON = new.ID_POKEMON;
	END IF;
END //
DELIMITER ;

DELIMITER //

CREATE TRIGGER meterCaja
    BEFORE UPDATE ON pokemon
    FOR EACH ROW
BEGIN
    DECLARE id INT;
    DECLARE comprobar INT;
    SET id = (SELECT new.ID_CAJA FROM pokemon WHERE ID_POKEMON = new.ID_POKEMON);
    SET comprobar = (SELECT COUNT(*) FROM pokemon WHERE ID_CAJA = id);

    IF comprobar >= 30 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede actualizar el Pokémon porque la caja ya
        tiene 30 o más Pokémon';
    ELSE
        UPDATE pokemon SET EQUIPO = false WHERE ID_POKEMON = new.ID_POKEMON;
    END IF;
END //
DELIMITER ;


UPDATE pokemon SET ID_CAJA = 1 WHERE ID_POKEMON = 1;
UPDATE pokemon SET ID_CAJA = 1 WHERE ID_POKEMON = 2;

SELECT * FROM pokemon WHERE ID_CAJA = 1;
UPDATE pokemon SET ID_CAJA = 0 WHERE ID_POKEMON = 1;
SELECT * FROM pokemon WHERE ID_CAJA = 1;
SELECT * FROM pokemon WHERE ID_CAJA = 0;

UPDATE pokemon SET MOV1 = 1, MOV2 = 2 WHERE ID_POKEMON = 1;

SELECT pok.*, hab.NOMBRE AS HABILIDAD, obj.NOMBRE AS NOMBRE_OBJETO FROM pokemon pok
LEFT JOIN habilidades hab ON pok.ID_HABILIDAD = hab.ID_HABILIDAD
LEFT JOIN objetos obj ON pok.OBJETO = obj.ID_OBJETO WHERE ID_POKEMON = 1;