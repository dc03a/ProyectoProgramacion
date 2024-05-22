package clases;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import utilidades.*;

public class CajaDAO {
    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";
    private static final String JSON_POKEMON_PATH = "json/pokemon.json";

    /* para comprobar el tamanio de una caja */
    public static boolean getTamanioCaja(ArrayList<Pokemon> lista) {
        return lista.size() < 30;
    }

    public static void moverPokemonCajaAEquipo(String nombre) throws IOException, SQLException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        Pokemon pokemon = null;

        for (Pokemon pok : caja.getListaCaja()) {
            if (pok.getNombre().equalsIgnoreCase(nombre)) {
                pokemon = pok;
                break;
            }
        }

        if (pokemon == null) {
            throw new IllegalArgumentException("El Pokémon con nombre " + nombre + " no se encuentra en la caja.");
        }

        caja.getListaCaja().remove(pokemon);
        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);
        EquipoDAO.agregarPokemon(pokemon);
    }

    /* Para generar la lista de pokemons de la caja que le pasemos */
    public static ArrayList<Pokemon> listaPokemon() throws IOException {
        ArrayList<Pokemon> cajaPok = new ArrayList<>();
        String sql = "SELECT * FROM pokemon WHERE estaEnCaja = 1";

        try (Connection con = conector.conectar();
             PreparedStatement sentencia = con.prepareStatement(sql);
             ResultSet rs = sentencia.executeQuery(sql);) {
            while (rs.next()) {
                Pokemon pokemonAux = new Pokemon(rs.getInt("Id"), rs.getString("Nombre"));
                pokemonAux.setNombre(rs.getString("Nombre"));
                cajaPok.add(pokemonAux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Caja caja = new Caja();
        caja.setListaCaja(cajaPok);
        funcionesJSON.escribirCajaAJSON(caja,JSON_CAJA_PATH );

        return cajaPok;
    }

    public static void meterPokemonACaja(String pokemonNombre) throws IOException, SQLException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        Pokemon pokemon = PokemonDAO.buscarPokemonPorNombre(pokemonNombre);

        if (pokemon == null) {
            throw new IllegalArgumentException("El Pokémon con el nombre " + pokemonNombre + " no existe.");
        }

        caja.getListaCaja().add(pokemon);
        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);

        String query = "UPDATE pokemon SET estaEnCaja = 1, estaEnEquipo = 0 WHERE ID = ?";
        try (Connection con = conector.conectar();
             PreparedStatement sentencia = con.prepareStatement(query);) {
            sentencia.setInt(1, pokemon.getID());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sacarPokemonCaja(String pokemonNombre) throws IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        boolean quitado = caja.getListaCaja().removeIf(pokemon -> pokemon.getNombre().equalsIgnoreCase(pokemonNombre));

        if (!quitado) {
            throw new IllegalArgumentException("El Pokémon con el nombre " + pokemonNombre + " no está en la caja.");
        }

        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);

        String query = "UPDATE pokemon SET estaEnCaja = 0, estaEnEquipo = 1 WHERE NOMBRE = ?";
        try (Connection con = conector.conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pokemon obtenerPokemonDeCaja(String pokemonNombre) throws IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);

        for (Pokemon pokemon : caja.getListaCaja()) {
            if (pokemon.getNombre().equalsIgnoreCase(pokemonNombre)) {
                return pokemon;
            }
        }
        return null;
    }

    public static void liberarPokemon(String nombrePokemon) throws SQLException, IOException {
        if (!seEncuentraestaEnCaja(nombrePokemon)) {
            System.out.println("El Pokémon no está en la caja. No se puede liberar desde aquí.");
            return;
        }

        String query = "UPDATE pokemon SET estaEnEquipo = 0, estaEnCaja = 0 WHERE NOMBRE = ?";
        try (Connection con = conector.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, nombrePokemon);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean seEncuentraestaEnCaja(String pokemonNombre) {
        String query = "SELECT * FROM pokemon WHERE Nombre = ? AND estaEnCaja = 1";
        try (Connection con = conector.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean estaEnCaja(Pokemon pokemon) throws SQLException, IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_EQUIPO_PATH);
        if (caja != null) {
            return caja.getListaCaja().contains(pokemon);
        } else {
            return false;
        }
    }
}