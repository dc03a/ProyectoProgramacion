package clases;

import java.io.*;
import java.sql.*;
import utilidades.*;

public class PokemonDAO {
    private static final String JSON_EQUIPO_PATH = "json/equipo.json";
    private static final String JSON_POKEMON_PATH = "json/pokemon.json";

    private static Connection getConnection() throws SQLException {
        return conector.conectar();
    }

    public static void guardarPokemon(Pokemon pokemon) throws IOException, SQLException {
        try (Connection conn = getConnection();
             PreparedStatement sentencia = conn.prepareStatement("INSERT INTO pokemon (ID, Nombre, Habilidad, Tipo1, Tipo2, Nivel, Hp, Ataque, Defensa, AtaqueEspecial, DefensaEspecial, Velocidad, Movimiento1, Movimiento2, Objeto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            sentencia.setInt(1, pokemon.getID());
            sentencia.setString(2, pokemon.getNombre());
            sentencia.setString(3, pokemon.getHabilidad());
            sentencia.setString(4, pokemon.getTipo1());
            sentencia.setString(5, pokemon.getTipo2());
            sentencia.setInt(6, pokemon.getNivel());
            sentencia.setInt(7, pokemon.getHp());
            sentencia.setInt(8, pokemon.getAtaque());
            sentencia.setInt(9, pokemon.getDefensa());
            sentencia.setInt(10, pokemon.getAtaqueEspecial());
            sentencia.setInt(11, pokemon.getDefensaEspecial());
            sentencia.setInt(12, pokemon.getVelocidad());
            sentencia.setString(13, pokemon.getMovimiento1());
            sentencia.setString(14, pokemon.getMovimiento2());
            sentencia.setString(15, pokemon.getObjeto());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
    }

    public static Pokemon buscarPokemonPorNombre(String nombre) throws IOException, SQLException {
        Pokemon pokemon = null;

        try (Connection conn = getConnection();
             PreparedStatement sentencia = conn.prepareStatement("SELECT * FROM pokemon WHERE Nombre = ?")) {
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                pokemon = new Pokemon();
                pokemon.setID(rs.getInt("id"));
                pokemon.setNombre(rs.getString("nombre"));
                pokemon.setHabilidad(rs.getString("habilidad"));
                pokemon.setTipo1(rs.getString("tipo1"));
                pokemon.setTipo2(rs.getString("tipo2"));
                pokemon.setNivel(rs.getInt("nivel"));
                pokemon.setHp(rs.getInt("hp"));
                pokemon.setAtaque(rs.getInt("ataque"));
                pokemon.setDefensa(rs.getInt("defensa"));
                pokemon.setAtaqueEspecial(rs.getInt("ataque_especial"));
                pokemon.setDefensaEspecial(rs.getInt("defensa_especial"));
                pokemon.setVelocidad(rs.getInt("velocidad"));
                pokemon.setMovimiento1(rs.getString("movimiento1"));
                pokemon.setMovimiento2(rs.getString("movimiento2"));
                pokemon.setObjeto(rs.getString("objeto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (pokemon != null) {
            funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
        }

        return pokemon;
    }

    public static void cambiarApodoPokemon(Pokemon pokemon, String nuevoApodo) throws IOException, SQLException {
        try (Connection conn = getConnection();
             PreparedStatement sentencia = conn.prepareStatement("UPDATE pokemon SET Nombre = ? WHERE ID = ?")) {
            sentencia.setString(1, nuevoApodo);
            sentencia.setInt(2, pokemon.getID());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pokemon.setNombre(nuevoApodo);

        funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
    }

    public static String devolverObjetoPokemon(Pokemon pokemon)  {
        String nombre = pokemon.getNombre();
        try (Connection conn = getConnection();
        PreparedStatement sentencia = conn.prepareStatement("SELECT Objeto FROM pokemon WHERE Nombre = ?")) {
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                return rs.getString("Objeto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("No se ha podido devolver el objeto");
        return null;
    }
}