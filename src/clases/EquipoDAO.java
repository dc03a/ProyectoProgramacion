package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EquipoDAO {
    public EquipoDAO() {
        super();
    }

    public static Connection conectar() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/proyectoprogramacion";

        try {
            con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException ex) {
            System.out.println("Error al conectar al SGBD");
        }
        return con;
    }

    public static ArrayList<Pokemon> listaPokemon() {
        String sen = "SELECT * FROM equipo";
        ArrayList<Pokemon> listaPokemon = new ArrayList<>();
        try {
            Connection con = conectar();
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
        try (Connection con = conectar();
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
}
