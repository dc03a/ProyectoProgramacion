package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import com.google.gson.*;
import utilidades.*;

public class EquipoDAO {
    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";

    private static Connection getConnection() throws SQLException {
        return conector.conectar();
    }

    public static Equipo getEquipo() throws IOException, SQLException {
        if (new File(JSON_EQUIPO_PATH).exists()) {
            return leerEquipoDeJSON(JSON_EQUIPO_PATH);
        }

        Equipo equipo = new Equipo();
        try (Connection conn = getConnection();
             Statement sentencia = conn.createStatement()) {
            ResultSet rs = sentencia.executeQuery("SELECT * FROM pokemon WHERE estaEnEquipo = 1");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon();
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
                equipo.getEquipo().add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);

        return equipo;
    }

    public static boolean equipoLleno(ArrayList<Pokemon> lista) {
        return lista.size() < 6;
    }

    public static void guardarEquipo(Equipo equipo) throws IOException {
        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);

        // Update the team in the database (optional)
        // You'll need to implement this based on your database schema
    }

    public static void agregarPokemon(Pokemon pokemon) throws IOException, SQLException {
        getEquipo().getEquipo().add(pokemon);
        guardarEquipo(getEquipo());

        // Update the database (optional)
        // You'll need to implement this based on your database schema
    }

    public static void quitarPokemon(Pokemon pokemon) throws IOException, SQLException {
        // Remove the Pokemon from the team in memory
        getEquipo().getEquipo().remove(pokemon);

        // Save the updated team to JSON
        guardarEquipo(getEquipo());

        // Update the database (optional)
        // You'll need to implement this based on your database schema
    }

    // ... other methods for managing the team in the database and JSON files
}
