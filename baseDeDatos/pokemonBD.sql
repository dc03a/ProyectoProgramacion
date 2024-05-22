DROP DATABASE IF EXISTS proyectoProgramacion;
CREATE DATABASE proyectoProgramacion;
USE proyectoProgramacion;

CREATE TABLE pokemon (
    Id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    Nombre VARCHAR(50) UNIQUE,
    Habilidad VARCHAR(40),
    Tipo1 VARCHAR(20),
    Tipo2 VARCHAR(20),
    Nivel INT,
    Hp INT,
    Ataque INT,
    Defensa INT,
    AtaqueEspecial INT,
    DefensaEspecial INT,
    Velocidad INT,
    Movimiento1 VARCHAR(50),
    Movimiento2 VARCHAR(50),
    Objeto VARCHAR(50),
    estaEnCaja BOOLEAN,
    estaEnEquipo BOOLEAN
);

CREATE TABLE caja (
    IdPokemon INT PRIMARY KEY,
    NombrePokemon VARCHAR(50),
    FOREIGN KEY (IdPokemon) REFERENCES pokemon (Id)
);

CREATE TABLE equipo (
    IdPokemon INT,
    Nombre VARCHAR(50),
    FOREIGN KEY (IdPokemon) REFERENCES pokemon(Id)
);

DELIMITER $$
CREATE TRIGGER insertar
AFTER INSERT ON pokemon
FOR EACH ROW
BEGIN
    DECLARE idPk INT;
    DECLARE nombrePk VARCHAR(100);
    DECLARE eqPk BOOLEAN;
    SET idPk = (SELECT new.Id FROM pokemon WHERE Id = new.Id);
    SET nombrePk = (SELECT new.Nombre FROM pokemon WHERE Id = new.Id);
    SET eqPk = (SELECT new.estaEnEquipo FROM pokemon WHERE Id = new.Id);
    IF eqPk = 1 THEN
        DELETE FROM caja WHERE IdPokemon = idPk;
        INSERT INTO equipo(IdPokemon, Nombre) VALUES(idPk, nombrePk);
    else
        DELETE FROM equipo WHERE IdPokemon = idPk;
        INSERT INTO caja(IdPokemon, NombrePokemon) VALUES(idPk, nombrePk);
    END IF;
END $$
DELIMITER ;

DELIMITER $$



INSERT INTO pokemon (Nombre, Habilidad, Tipo1, Tipo2, Hp, Ataque, Defensa, AtaqueEspecial, DefensaEspecial, Velocidad, Nivel, Movimiento1, Movimiento2, estaEnCaja, estaEnEquipo, Objeto) VALUES
                                                                                                                                                                                              ('Bulbasaur', 'Espesura', 'Planta', 'Veneno', 45, 49, 49, 65, 65, 45, 50, 'Placaje', 'Gruñido', TRUE, FALSE, 'Baya Aranja'),
                                                                                                                                                                                              ('Ivysaur', 'Espesura', 'Planta', 'Veneno', 60, 62, 63, 80, 80, 60, 50, 'Placaje', 'Gruñido', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Venusaur', 'Espesura', 'Planta', 'Veneno', 80, 82, 83, 100, 100, 80, 50, 'Placaje', 'Gruñido', TRUE, FALSE, 'Hierba Mental'),
                                                                                                                                                                                              ('Charmander', 'Mar Llamas', 'Fuego', NULL, 39, 52, 43, 60, 50, 65, 50, 'Arañazo', 'Gruñido', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Charmeleon', 'Mar Llamas', 'Fuego', NULL, 58, 64, 58, 80, 65, 80, 50, 'Arañazo', 'Gruñido', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Charizard', 'Mar Llamas', 'Fuego', 'Volador', 78, 84, 78, 109, 85, 100, 50, 'Arañazo', 'Gruñido', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Squirtle', 'Torrente', 'Agua', NULL, 44, 48, 65, 50, 64, 43, 50, 'Placaje', 'Látigo', TRUE, FALSE, 'Chaleco Asalto'),
                                                                                                                                                                                              ('Wartortle', 'Torrente', 'Agua', NULL, 59, 63, 80, 65, 80, 58, 50, 'Placaje', 'Látigo', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Blastoise', 'Torrente', 'Agua', NULL, 79, 83, 100, 85, 105, 78, 50, 'Placaje', 'Látigo', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Caterpie', 'Polvo Escudo', 'Bicho', NULL, 45, 30, 35, 20, 20, 45, 50, 'Placaje', 'Disparo Demora', TRUE, FALSE, 'Baya Aranja'),
                                                                                                                                                                                              ('Metapod', 'Mudar', 'Bicho', NULL, 50, 20, 55, 25, 25, 30, 50, 'Fortaleza', NULL, TRUE, FALSE, 'Baya Zidra'),
                                                                                                                                                                                              ('Butterfree', 'Ojo Compuesto', 'Bicho', 'Volador', 60, 45, 50, 90, 80, 70, 50, 'Confusión', 'Fortaleza', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Weedle', 'Polvo Escudo', 'Bicho', 'Veneno', 40, 35, 30, 20, 20, 50, 50, 'Picotazo Veneno', 'Disparo Demora', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Kakuna', 'Mudar', 'Bicho', 'Veneno', 45, 25, 50, 25, 25, 35, 50, 'Fortaleza', NULL, TRUE, FALSE, 'Baya Zidra'),
                                                                                                                                                                                              ('Beedrill', 'Enjambre', 'Bicho', 'Veneno', 65, 90, 40, 45, 80, 75, 50, 'Doble Ataque', 'Furia', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Pidgey', 'Vista Lince', 'Normal', 'Volador', 40, 45, 40, 35, 35, 56, 50, 'Tornado', 'Ataque Arena', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Pidgeotto', 'Vista Lince', 'Normal', 'Volador', 63, 60, 55, 50, 50, 71, 50, 'Tornado', 'Ataque Rápido', TRUE, FALSE, 'Chaleco Asalto'),
                                                                                                                                                                                              ('Pidgeot', 'Vista Lince', 'Normal', 'Volador', 83, 80, 75, 70, 70, 101, 50, 'Tornado', 'Ataque Ala', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Rattata', 'Fuga', 'Normal', NULL, 30, 56, 35, 25, 35, 72, 50, 'Placaje', 'Látigo', TRUE, FALSE, 'Baya Aranja'),
                                                                                                                                                                                              ('Raticate', 'Fuga', 'Normal', NULL, 55, 81, 60, 50, 70, 97, 50, 'Hipercolmillo', 'Ataque Rápido', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Spearow', 'Vista Lince', 'Normal', 'Volador', 40, 60, 30, 31, 31, 70, 50, 'Picotazo', 'Gruñido', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Fearow', 'Vista Lince', 'Normal', 'Volador', 65, 90, 65, 61, 61, 100, 50, 'Pico Taladro', 'Furia', TRUE, FALSE, 'Baya Zidra'),
                                                                                                                                                                                              ('Ekans', 'Intimidación', 'Veneno', NULL, 35, 60, 44, 40, 54, 55, 50, 'Constricción', 'Malicioso', TRUE, FALSE, 'Chaleco Asalto'),
                                                                                                                                                                                              ('Arbok', 'Intimidación', 'Veneno', NULL, 60, 85, 69, 65, 79, 80, 50, 'Ácido', 'Mordisco', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Pikachu', 'Electricidad Estática', 'Eléctrico', NULL, 35, 55, 40, 50, 50, 90, 50, 'Impactrueno', 'Gruñido', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Raichu', 'Electricidad Estática', 'Eléctrico', NULL, 60, 90, 55, 90, 80, 110, 50, 'Rayo', 'Ataque Rápido', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Sandshrew', 'Velo Arena', 'Tierra', NULL, 50, 75, 85, 20, 30, 40, 50, 'Arañazo', 'Rizo Defensa', TRUE, FALSE, 'Chaleco Asalto'),
                                                                                                                                                                                              ('Sandslash', 'Velo Arena', 'Tierra', NULL, 75, 100, 110, 45, 55, 65, 50, 'Excavar', 'Terremoto', TRUE, FALSE, 'Baya Zidra'),
                                                                                                                                                                                              ('Nidoran♀', 'Punto Tóxico', 'Veneno', NULL, 55, 47, 52, 40, 40, 41, 50, 'Picotazo Veneno', 'Malicioso', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Nidorina', 'Punto Tóxico', 'Veneno', NULL, 70, 62, 67, 55, 55, 56, 50, 'Picotazo Veneno', 'Doble Patada', TRUE, FALSE, 'Baya Aranja'),
                                                                                                                                                                                              ('Nidoqueen', 'Punto Tóxico', 'Veneno', 'Tierra', 90, 82, 87, 75, 85, 76, 50, 'Fisura', 'Golpe Cabeza', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Nidoran♂', 'Punto Tóxico', 'Veneno', NULL, 46, 57, 40, 40, 40, 50, 50, 'Picotazo Veneno', 'Malicioso', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Nidorino', 'Punto Tóxico', 'Veneno', NULL, 61, 72, 57, 55, 55, 65, 50, 'Picotazo Veneno', 'Doble Patada', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Nidoking', 'Punto Tóxico', 'Veneno', 'Tierra', 81, 102, 77, 85, 75, 85, 50, 'Terremoto', 'Golpe Cabeza', TRUE, FALSE, 'Baya Zidra'),
                                                                                                                                                                                              ('Clefairy', 'Gran Encanto', 'Hada', NULL, 70, 45, 48, 60, 65, 35, 50, 'Canto', 'Beso Amoroso', TRUE, FALSE, 'Chaleco Asalto'),
                                                                                                                                                                                              ('Clefable', 'Gran Encanto', 'Hada', NULL, 95, 70, 73, 95, 90, 60, 50, 'Canto', 'Beso Amoroso', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Vulpix', 'Absorbe Fuego', 'Fuego', NULL, 38, 41, 40, 50, 65, 65, 50, 'Gruñido', 'Ataque Rápido', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Ninetales', 'Absorbe Fuego', 'Fuego', NULL, 73, 76, 75, 81, 100, 100, 50, 'Gruñido', 'Lanzallamas', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Jigglypuff', 'Gran Encanto', 'Normal', 'Hada', 115, 45, 20, 45, 25, 20, 50, 'Canto', 'Beso Amoroso', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Wigglytuff', 'Gran Encanto', 'Normal', 'Hada', 140, 70, 45, 85, 50, 45, 50, 'Canto', 'Beso Amoroso', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Zubat', 'Foco Interno', 'Veneno', 'Volador', 40, 45, 35, 30, 40, 55, 50, 'Chupavidas', 'Supersónico', TRUE, FALSE, 'Periscopio'),
                                                                                                                                                                                              ('Golbat', 'Foco Interno', 'Veneno', 'Volador', 75, 80, 70, 65, 75, 90, 50, 'Chupavidas', 'Confusión', TRUE, FALSE, 'Baya Aranja'),
                                                                                                                                                                                              ('Oddish', 'Clorofila', 'Planta', 'Veneno', 45, 50, 55, 75, 65, 30, 50, 'Absorber', 'Ácido', TRUE, FALSE, 'Restos'),
                                                                                                                                                                                              ('Gloom', 'Clorofila', 'Planta', 'Veneno', 60, 65, 70, 85, 75, 40, 50, 'Absorber', 'Ácido', TRUE, FALSE, 'Baya Atania'),
                                                                                                                                                                                              ('Vileplume', 'Clorofila', 'Planta', 'Veneno', 75, 80, 85, 110, 90, 50, 50, 'Absorber', 'Ácido', TRUE, FALSE, 'Baya Ziuela'),
                                                                                                                                                                                              ('Paras', 'Efecto Espora', 'Bicho', 'Planta', 35, 70, 55, 45, 55, 25, 50, 'Arañazo', 'Gruñido', TRUE, FALSE, 'Garra Rápida'),
                                                                                                                                                                                              ('Parasect', 'Efecto Espora', 'Bicho', 'Planta', 60, 95, 80, 60, 80, 30, 50, 'Arañazo', 'Gruñido', TRUE, FALSE, 'Chaleco Asalto');
CREATE TRIGGER liberar
BEFORE DELETE ON pokemon
FOR EACH ROW
BEGIN
    DECLARE comprobar int;
    SET comprobar = (SELECT COUNT(*) FROM POKEMON);
    IF comprobar = 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar mas Pokemon';
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER mover
BEFORE UPDATE ON pokemon
FOR EACH ROW
BEGIN
    DECLARE idPk INT;
    DECLARE nombrePk VARCHAR(100);
    DECLARE eqPk BOOLEAN;
    SET idPk = (SELECT new.Id FROM pokemon WHERE Id = new.Id);
    SET nombrePk = (SELECT new.Nombre FROM pokemon WHERE Id = new.Id);
    SET eqPk = (SELECT new.estaEnEquipo FROM pokemon WHERE Id = new.Id);
    IF eqPk = 1 THEN
        DELETE FROM caja WHERE IdPokemon = idPk;
        INSERT INTO equipo(IdPokemon, Nombre) VALUES(idPk, nombrePk);
    else
        DELETE FROM equipo WHERE IdPokemon = idPk;
        INSERT INTO caja(IdPokemon, NombrePokemon) VALUES(idPk, nombrePk);
    END IF;
END $$
DELIMITER ;