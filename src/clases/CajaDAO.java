package clases;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utilidades.*;

public class CajaDAO {

    /*** METODOS PARA CAJAS ***/

    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";

    private static Connection conectar() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/proyectoProgramacion";
        try {
            con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /* para comprobar el tamanio de una caja */
    public static boolean getTamanioCaja(ArrayList<Pokemon> lista) {
        return lista.size() < 30;
    }

    public static void moverPokemonCajaAEquipo(String nombre) throws IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);

        Pokemon pokemon = null;
        for (Pokemon p : caja.getListaCaja()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                pokemon = p;
                break;
            }
        }

        if (pokemon == null) {
            throw new IllegalArgumentException("El Pokémon con el nombre " + nombre + " no se encuentra en la caja.");
        }

        caja.getListaCaja().remove(pokemon);
        equipo.getEquipo().add(pokemon);

        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);
        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);

        String sql = "UPDATE pokemon SET estaEnequipo = true AND estaEnCaja = false WHERE NOMBRE = ? ";
        try{
            Connection con = conectar();
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /* Para generar la lista de pokemons de la caja que le pasemos */
    public static ArrayList<Pokemon> listaPokemon() throws IOException {
        ArrayList<Pokemon> cajaPok = new ArrayList<>();
        String sql = "SELECT * FROM pokemon WHERE estaEnCaja = true";

        try (Connection con = conectar();
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

        String query = "UPDATE pokemon SET estaEnCaja = true, estaEnEquipo = false WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentencia = con.prepareStatement(query);) {
            sentencia.setString(1, pokemonNombre);
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

        String query = "UPDATE pokemon SET estaEnCaja = false WHERE NOMBRE = ?";
        try (Connection con = conectar();
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
        if (!estaEnCaja(nombrePokemon)) {
            System.out.println("El Pokémon no está en la caja. No se puede liberar desde aquí.");
            return;
        }

        String query = "UPDATE pokemon SET estaEnEquipo = false, estaEnCaja = false WHERE NOMBRE = ?";
        try (Connection con = conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, nombrePokemon);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean estaEnCaja(String pokemonNombre) {
        String query = "SELECT * FROM pokemon WHERE Nombre = ? AND estaEnCaja = true";
        try (Connection con = conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}