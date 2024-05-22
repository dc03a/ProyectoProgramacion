package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import utilidades.*;

public class EquipoDAO {
    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";

    public static Equipo getEquipo() throws IOException, SQLException {
        if (new File(JSON_EQUIPO_PATH).exists()) {
            return funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        }

        Equipo equipo = new Equipo();
        try (Connection conn = conector.conectar();
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
        Equipo equipoAux = equipo;
        equipoAux = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        for (Pokemon pokemon : equipoAux.getEquipo()) {
            if (pokemon.isEstaEnEquipo()) {
                String query = "UPDATE pokemon SET estaEnEquipo = true WHERE Id = ?";
                try (Connection con = conector.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
                    sentencia.setInt(1, pokemon.getID());
                    sentencia.setString(2, pokemon.getNombre());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        funcionesJSON.escribirEquipoAJSON(equipoAux, JSON_EQUIPO_PATH);
    }

    public static void agregarPokemon(Pokemon pokemon) throws IOException, SQLException {
        getEquipo().getEquipo().add(pokemon);
        guardarEquipo(getEquipo());

        String query = "UPDATE pokemon SET estaEnEquipo = true WHERE Id = ?";
        try (Connection con = conector.conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void quitarPokemon(Pokemon pokemon) throws IOException, SQLException {
        getEquipo().getEquipo().remove(pokemon);
        guardarEquipo(getEquipo());

        String query = "UPDATE pokemon SET estaEnEquipo = false WHERE Id = ?";
        try (Connection con = conector.conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean estaEnEquipo(String pokemonNombre) throws SQLException, IOException {
        Equipo equipo = getEquipo();

        for (Pokemon pokemon : equipo.getEquipo()) {
            if (pokemon.getNombre().equalsIgnoreCase(pokemonNombre)) {
                return true;
            }
        }
        return false;
    }
}
