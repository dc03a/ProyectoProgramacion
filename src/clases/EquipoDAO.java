package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class EquipoDAO {

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
    public static boolean hayTamanioEquipo(ArrayList<Pokemon> equipo) {
        return equipo.size() < 6;
    }


    public static void moverPokemonEquipoACaja(String nombre){
        String sql = "UPDATE pokemon SET equipo = false, caja = true WHERE NOMBRE = ? ";

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
    public static List<Pokemon> listaPokemonEquipo() {
        List<Pokemon> equipoPokemon = new ArrayList<>();
        String sql = "SELECT * FROM pokemon WHERE equipo = true";
        Connection con = conectar();

        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Pokemon pokemonAux = new Pokemon(rs.getInt("ID_POKEMON"), rs.getString("Nombre"));
                pokemonAux.setNombre(rs.getString("NOMBRE"));
                equipoPokemon.add(pokemonAux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipoPokemon;
    }


    public static void meterPokemonAEquipo(String pokemonNombre, ArrayList<Pokemon> equipo) {
        if (hayTamanioEquipo(equipo)){
            String query = "UPDATE pokemon SET caja = true WHERE NOMBRE = ?";
            try (Connection con = conectar();
                 PreparedStatement sentencia = con.prepareStatement(query)) {
                sentencia.setString(1, pokemonNombre);
                sentencia.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay sitio en el equipo");
        }

    }

    public static void sacarPokemonEquipo(String pokemonNombre) {
        String query = "UPDATE pokemon SET equipo = false WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pokemon obtenerPokemonDeEquipo(boolean estaEnEquipo, String pokemonNombre) {
        Pokemon pokemon = null;
        String query = "SELECT * FROM pokemon WHERE equipo = true AND NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement sentenceia = con.prepareStatement(query)) {
            sentenceia.setString(1, pokemonNombre);
            try (ResultSet res = sentenceia.executeQuery()) {
                if (res.next()) {
                    pokemon = new Pokemon(
                            res.getInt("ID_POKEMON"),
                            res.getString("NOMBRE")
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