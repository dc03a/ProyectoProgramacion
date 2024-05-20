package clases;

import java.sql.*;

public class ObjetosDAO {
    public ObjetosDAO() {
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

    public static Objetos leerDatosObjeto(int ID) {
        Objetos objeto = null;
        String sen = "SELECT * FROM objetos WHERE ID_OBJETO = " + ID;
        try {
            Connection con = conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                String nombre = res.getString("NOMBRE");
                String descripcion = res.getString("DESCRIPCION");
                objeto = new Objetos(nombre, descripcion);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objeto;
    }

    public static String obtenerObjetoPorNombre(String pokemonNombre) {
        String objeto = null;
        String query = "SELECT obj.NOMBRE AS NOMBRE_OBJETO FROM pokemon pok " +
                "LEFT JOIN objetos obj ON pok.OBJETO = obj.ID_OBJETO " +
                "WHERE pok.NOMBRE = ?";
        try (Connection con = conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, pokemonNombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                objeto = rs.getString("NOMBRE_OBJETO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objeto;
    }
}
