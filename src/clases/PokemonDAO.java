package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import utilidades.*;

public class PokemonDAO {
    private static final String JSON_POKEMON_PATH = "json/pokemon.json";
    private static final String JSON_EQUIPO_PATH = "json/equipo.json";
    private static final String JSON_CAJA_PATH = "json/caja.json";

    private static Connection getConnection() throws SQLException {
        return conector.conectar();
    }

    public static ArrayList<Pokemon> seleccionarTodosLosPokemon() throws SQLException, IOException {
        ArrayList<Pokemon> listaPokemon = new ArrayList<>();
        String query = "SELECT * FROM pokemon";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pokemon pokemon = new Pokemon();
                pokemon.setID(resultSet.getInt("ID"));
                pokemon.setNombre(resultSet.getString("Nombre"));
                pokemon.setHabilidad(resultSet.getString("Habilidad"));
                pokemon.setTipo1(resultSet.getString("Tipo1"));
                pokemon.setTipo2(resultSet.getString("Tipo2"));
                pokemon.setNivel(resultSet.getInt("Nivel"));
                pokemon.setHp(resultSet.getInt("Hp"));
                pokemon.setAtaque(resultSet.getInt("Ataque"));
                pokemon.setDefensa(resultSet.getInt("Defensa"));
                pokemon.setAtaqueEspecial(resultSet.getInt("AtaqueEspecial"));
                pokemon.setDefensaEspecial(resultSet.getInt("DefensaEspecial"));
                pokemon.setVelocidad(resultSet.getInt("Velocidad"));
                pokemon.setMovimiento1(resultSet.getString("Movimiento1"));
                pokemon.setMovimiento2(resultSet.getString("Movimiento2"));
                pokemon.setObjeto(resultSet.getString("Objeto"));
                pokemon.setEstaEnEquipo(resultSet.getBoolean("EstaEnEquipo"));
                pokemon.setEstaEnCaja(resultSet.getBoolean("EstaEnCaja"));
                listaPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Iterar sobre la lista de Pokémon y agregar al equipo o caja según corresponda
        for (Pokemon pokemon : listaPokemon) {
            if (EquipoDAO.estaEnEquipo(pokemon)) {
                Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
                equipo.getEquipo().add(pokemon);
                funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);
            }

            if (CajaDAO.estaEnCaja(pokemon)) {
                Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
                caja.getListaCaja().add(pokemon);
                funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);
            }

            funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
        }

        return listaPokemon;
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