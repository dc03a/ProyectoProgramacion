package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import utilidades.*;

import javax.swing.*;

public class PokemonDAO {
    private static final String JSON_POKEMON_PATH = "json/pokemon.json";
    private static final String JSON_EQUIPO_PATH = "json/equipo.json";
    private static final String JSON_CAJA_PATH = "json/caja.json";

    public static ArrayList<Pokemon> seleccionarTodosLosPokemon() throws SQLException, IOException {
        ArrayList<Pokemon> listaPokemon = new ArrayList<>();
        String query = "SELECT * FROM pokemon";
        Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);

        try (Connection con = credenciales.conectar();
             PreparedStatement statement = con.prepareStatement(query);
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

                if (pokemon.isEstaEnEquipo() && !equipo.getEquipo().contains(pokemon)) {
                    if (equipo.getEquipo().size() < 6) {
                        equipo.getEquipo().add(pokemon);
                    } else {
                        System.out.println("No se puede añadir más Pokémon. El equipo está lleno.");
                        break;
                    }
                }

                if (CajaDAO.estaEnCaja(pokemon)) {
                    caja.getListaCaja().add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);
        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);

        return listaPokemon;
    }

    public static Pokemon buscarPokemonPorNombre(String nombre) throws IOException, SQLException {
        Pokemon pokemon = new Pokemon();

        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement("SELECT * FROM pokemon WHERE Nombre = ?")) {
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                pokemon.setID(rs.getInt("Id"));
                pokemon.setNombre(rs.getString("Nombre"));
                pokemon.setHabilidad(rs.getString("Habilidad"));
                pokemon.setTipo1(rs.getString("Tipo1"));
                pokemon.setTipo2(rs.getString("Tipo2"));
                pokemon.setNivel(rs.getInt("Nivel"));
                pokemon.setHp(rs.getInt("Hp"));
                pokemon.setAtaque(rs.getInt("Ataque"));
                pokemon.setDefensa(rs.getInt("Defensa"));
                pokemon.setAtaqueEspecial(rs.getInt("AtaqueEspecial"));
                pokemon.setDefensaEspecial(rs.getInt("DefensaEspecial"));
                pokemon.setVelocidad(rs.getInt("Velocidad"));
                pokemon.setMovimiento1(rs.getString("Movimiento1"));
                pokemon.setMovimiento2(rs.getString("Movimiento2"));
                pokemon.setObjeto(rs.getString("Objeto"));
                pokemon.setEstaEnCaja(rs.getBoolean("estaEnCaja"));
                pokemon.setEstaEnEquipo(rs.getBoolean("estaEnEquipo"));
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
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement("UPDATE pokemon SET Nombre = ? WHERE ID = ?")) {
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
        try (Connection con = credenciales.conectar();
        PreparedStatement sentencia = con.prepareStatement("SELECT Objeto FROM pokemon WHERE Nombre = ?")) {
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

    public static ArrayList<String> obtenerObjetosDisponiblesDesdeBD() throws SQLException, IOException {
        ArrayList<String> objetos = new ArrayList<>();
        String query = "SELECT DISTINCT Objeto FROM pokemon";
        try (Connection con = credenciales.conectar();
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String objeto = resultSet.getString("Objeto");
                if (objeto != null && !objeto.isEmpty()) {
                    objetos.add(objeto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objetos;
    }

    public static void cambiarObjetoPokemon(Pokemon pokemon, String nuevoObjeto) throws IOException, SQLException {
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement("UPDATE pokemon SET Objeto = ? WHERE ID = ?")) {
            sentencia.setString(1, nuevoObjeto);
            sentencia.setInt(2, pokemon.getID());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pokemon.setObjeto(nuevoObjeto);
        funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
    }

    public static void asignarObjetoPokemon(Pokemon pokemon, String objeto) throws IOException, SQLException {
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement("UPDATE pokemon SET Objeto = ? WHERE ID = ?")) {
            sentencia.setString(1, objeto);
            sentencia.setInt(2, pokemon.getID());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pokemon.setObjeto(objeto);
        funcionesJSON.escribirPokemonAJSON(pokemon, JSON_POKEMON_PATH);
    }
}