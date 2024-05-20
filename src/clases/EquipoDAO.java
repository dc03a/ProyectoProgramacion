package clases;

import java.sql.*;
import java.util.ArrayList;


public class EquipoDAO {
    public EquipoDAO(){
        super();
    }


    public static Connection conectar(){
        Connection con = null;

        String url= "jdbc:mysql://localhost:3306/proyectoprogramacion";

        try {
            con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException ex) {
            System.out.println("Error al conectar al SGBD");
        }
        return con;
    }
    public static ArrayList<Pokemon> listaPokemon(ArrayList<Pokemon> equipoPokemon){
        String sen = "SELECT * FROM equipo";
        ArrayList<Pokemon> listaPokemon = new ArrayList<>();
        try {
            Pokemon pokemon = null;
            PokemonDAO pokeDAO = null;
            Connection con = conectar();

            Statement sentencia = con.createStatement();

            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                int id_pokemon = res.getInt("ID_POKEMON");
                String objeto = res.getString("OBJETO");
                pokemon = pokeDAO.leerDatos(id_pokemon);
                listaPokemon.add(pokemon);
            }
            conectar().close();
        } catch(SQLException e){
            System.out.print(e);
        }
        return listaPokemon;
    }
}
