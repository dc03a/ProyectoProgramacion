package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

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


    public static ArrayList<String> getNombresPokemons(Equipo equipo) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Pokemon pokemon : equipo.getEquipo()) {
            nombres.add(pokemon.getNombre());
        }
        return nombres;
    }

    public static void agregarPokemon(Pokemon pokemon) throws IOException, SQLException {
        Equipo equipo = getEquipo();
        if (!equipoLleno(equipo.getEquipo())) {
            equipo.getEquipo().add(pokemon);
            funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);

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

        Equipo equipo = funcionesJSON.leerEquipoDeJSON(JSON_EQUIPO_PATH);
        ArrayList<Pokemon> listaPokemons = equipo.getEquipo();

        // Verificar si el Pokémon está presente en el equipo JSON y eliminarlo
        Iterator<Pokemon> it = listaPokemons.iterator();
        while(it.hasNext()){
            Pokemon p = it.next();
            if(p.getID() == pokemon.getID()){
                it.remove();
            }
            System.out.println("El Pokémon fue eliminado del equipo en el archivo JSON.");
            funcionesJSON.escribirEquipoAJSON(equipo, JSON_EQUIPO_PATH);
            return; // Salir del método después de eliminar el Pokémon
        }


    }


    public static boolean estaEnEquipo(Pokemon pokemon) throws IOException {
        return pokemon.isEstaEnEquipo();
    }
}