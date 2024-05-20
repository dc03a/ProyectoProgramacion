package clases;

import java.sql.*;
import java.util.ArrayList;


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
}
