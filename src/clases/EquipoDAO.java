package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import utilidades.*;

public class EquipoDAO {
    private static final String JSON_EQUIPO_PATH = "json/equipo.json";
    private static final String JSON_POKEMON_PATH = "json/pokemon.json";

    public static Equipo getEquipo() throws IOException, SQLException {
        Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        if (equipo == null) {
            equipo = new Equipo();
        }
        return equipo;
    }

    public static void setEquipo(ArrayList<Pokemon> lista) throws IOException, SQLException {
        Equipo equipo = new Equipo();
        equipo.setEquipo(lista);
        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);
    }

    public static boolean equipoLleno(ArrayList<Pokemon> lista) {
        return lista.size() == 6;
    }

    public static void guardarEquipo(Equipo equipo) throws IOException {
        funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);

        for (Pokemon pokemon : equipo.getEquipo()) {
            String query = "UPDATE pokemon SET estaEnEquipo = 1 WHERE Id = ?";
            try (Connection con = credenciales.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
                sentencia.setInt(1, pokemon.getID());
                sentencia.setString(2, pokemon.getNombre());
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getNombresPokemons(Equipo equipo) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Pokemon pokemon : equipo.getEquipo()) {
            nombres.add(pokemon.getNombre());
        }
        return nombres;
    }

    public static void agregarPokemon(Pokemon pokemon) throws IOException, SQLException {
        Equipo equipo = getEquipo();
        if (equipoLleno(equipo.getEquipo())) {
            equipo.getEquipo().add(pokemon);
            guardarEquipo(equipo);

            String query = "UPDATE pokemon SET estaEnEquipo = 1, estaEnCaja = 0 WHERE Id = ?";
            try (Connection con = credenciales.conectar();
                 PreparedStatement sentencia = con.prepareStatement(query)) {
                sentencia.setInt(1, pokemon.getID());
                int filasActualizadas = sentencia.executeUpdate();
                if (filasActualizadas == 0) {
                    System.out.println("No se pudo actualizar el estado del Pokémon en la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El equipo está lleno");
        }
    }

    public static void quitarPokemon(Pokemon pokemon) throws IOException, SQLException {
        Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        ArrayList<Pokemon> listaPokemons = equipo.getEquipo();

        for (int i = 0; i < listaPokemons.size(); i++) {
            Pokemon p = listaPokemons.get(i);
            if (p.getID() == pokemon.getID()) {
                listaPokemons.remove(i);
                break;
            }
        }

        guardarEquipo(equipo);

        String query = "UPDATE pokemon SET estaEnEquipo = 0, estaEnCaja = 1 WHERE Id = ?";
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setInt(1, pokemon.getID());
            int filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas == 0) {
                System.out.println("No se pudo actualizar el estado del Pokémon en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean estaEnEquipo(Pokemon pokemon) throws IOException {
        return pokemon.isEstaEnEquipo();
    }
}