package clases;

import java.sql.*;

import clases.Pokemon;

public class PokemonDAO {
    public PokemonDAO() {
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


    public static Pokemon leerDatos(int ID) {
        Pokemon pokemon = null;
        String sen = "SELECT pok.*, hab.NOMBRE AS HABILIDAD, obj.NOMBRE AS NOMBRE_OBJETO, mov1.DESCRIPCION" +
                " AS MOVI1, mov2.DESCRIPCION AS MOVI2 FROM pokemon pok LEFT JOIN habilidades hab ON pok.ID" +
                "_HABILIDAD = hab.ID_HABILIDAD LEFT JOIN objetos obj ON pok.OBJETO = obj.ID_OBJETO" +
                " LEFT JOIN movimientos mov1 ON pok.MOV1 = mov1.ID_MOVIMIENTO LEFT JOIN movimientos mov2 ON" +
                " pok.MOV2 = mov2.ID_MOVIMIENTO WHERE ID_POKEMON = " + ID;
        try {
            Connection con = conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                int num_pokedex = res.getInt("NUM_POKEDEX");
                String Nombre = res.getString("NOMBRE");
                String habilidad = res.getNString("HABILIDAD");
                int nivel = res.getInt("NIVEL");
                String tipo1 = res.getNString("TIPO1");
                String tipo2 = res.getNString("TIPO2");
                int HP = res.getInt("HP");
                int ataque = res.getInt("ATAQUE");
                int defensa = res.getInt("DEFENSA");
                int ataqueEspecial = res.getInt("ATAQUE_ESPECIAL");
                int defensaEspecial = res.getInt("DEFENSA_ESPECIAL");
                String mov1 = res.getNString("MOVI1");
                String mov2 = res.getNString("MOVI2");
                int velocidad = res.getInt("VELOCIDAD");
                String objeto = res.getNString("NOMBRE_OBJETO");

                pokemon = new Pokemon(num_pokedex, Nombre, habilidad, nivel, tipo1, tipo2, HP,
                        ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, objeto, mov1, mov2);
            }
            conectar().close();
        } catch (SQLException e) {
            System.out.print(e);
        }
        return pokemon;
    }


    public static void dejarPokemonEnBD(String nombre) {
        String query = "UPDATE pokemon SET EQUIPO = 0 WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cambiarApodoEnBD(String nombreActual, String nuevoNombre) {
        String query = "UPDATE pokemon SET NOMBRE = ? WHERE NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nombreActual);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}