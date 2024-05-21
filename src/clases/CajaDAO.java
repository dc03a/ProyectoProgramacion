package clases;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import utilidadesJSON.*;

public class CajaDAO {

    /*** METODOS PARA CAJAS ***/

    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pokemon_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static Gson gson;

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

    public static void moverPokemonCajaAEquipo(String nombre){
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
    public static List<Pokemon> listaPokemon() throws IOException {
        Caja caja = new Caja();
        ArrayList<Pokemon> cajaPok = new ArrayList<>();
        caja.setListaCaja(cajaPok);
        String sql = "SELECT * FROM pokemon WHERE estaEnCaja = true";
            Connection con = conectar();

            try (PreparedStatement sentencia = con.prepareStatement(sql)) {
                ResultSet rs = sentencia.executeQuery(sql);
                while (rs.next()) {
                    Pokemon pokemonAux = new Pokemon(rs.getInt("Id"), rs.getString("Nombre"));
                    pokemonAux.setNombre(rs.getString("Nombre"));
                    cajaPok.add(pokemonAux);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        funcionesJSON.escribirCajaAJSON(caja,JSON_CAJA_PATH );
        return cajaPok;
    }

    public static void meterPokemonACaja(String pokemonNombre) {
        String query = "UPDATE pokemon SET estaEnCaja = true, estaEnEquipo = false WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sacarPokemonCaja(String pokemonNombre) {
        String query = "UPDATE pokemon SET estaEnCaja = false WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pokemon obtenerPokemonDeCaja(boolean estaEnCaja, String pokemonNombre) {
        Pokemon pokemon = null;
        String query = "SELECT * FROM pokemon WHERE estaEnCaja = true AND NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentenceia = con.prepareStatement(query)) {
            sentenceia.setString(1, pokemonNombre);
            try (ResultSet res = sentenceia.executeQuery()) {
                if (res.next()) {
                    pokemon = new Pokemon(
                            res.getInt("Id"),
                            res.getString("Nombre")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    public static void liberarPokemon(String nombrePokemon) {
        String query = "DELETE FROM pokemon WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, nombrePokemon);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}