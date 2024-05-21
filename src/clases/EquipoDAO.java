package clases;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EquipoDAO {
    public EquipoDAO() {
        super();
    }

    public static ArrayList<Pokemon> listaPokemon() {
        String sen = "SELECT * FROM equipo";
        ArrayList<Pokemon> listaPokemon = new ArrayList<>();
        try {
            Connection con = PokemonDAO.conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);

            PokemonDAO pokeDAO = new PokemonDAO();

            while (res.next()) {
                int id_pokemon = res.getInt("ID_POKEMON");
                Pokemon pokemon = pokeDAO.leerDatos(id_pokemon);
                if (pokemon != null) {
                    listaPokemon.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPokemon;
    }

    public static String[] obtenerEquipoDesdeBD() {
        List<String> equipo = new ArrayList<>();
        String query = "SELECT NOMBRE FROM pokemon WHERE EQUIPO = true"; // Siempre se obtiene el equipo con ID 1
        try (Connection con = PokemonDAO.conectar();
             Statement sent = con.createStatement();
             ResultSet res = sent.executeQuery(query)) {
            while (res.next()) {
                equipo.add(res.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipo.toArray(new String[0]);
    }

    public static boolean hayEspacioEnEquipo() {
        int capMaxim = 6;
        int cantPokemon = obtenerEquipoDesdeBD().length;

        return cantPokemon < capMaxim;
    }

    public static void aniadirAEquipoDesdeBD(String nombrePokemon) {
        String query = "UPDATE pokemon SET EQUIPO = ? WHERE NOMBRE = ?";
        try {
            Connection con = PokemonDAO.conectar();
            PreparedStatement sentencia = con.prepareStatement(query);
            sentencia.setBoolean(1, true);
            sentencia.setString(2,nombrePokemon);
            int resultado = sentencia.executeUpdate();
            if (resultado > 0) {
                System.out.println("El pokemon " + nombrePokemon + " ha sido anadado");
            } else {
                System.out.println("El pokemon " + nombrePokemon + " no se ha anadado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
