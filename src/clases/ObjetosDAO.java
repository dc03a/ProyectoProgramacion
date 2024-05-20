package clases;

import java.sql.*;

public class ObjetosDAO {
    public ObjetosDAO(){
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
    public static Objetos leerMovimiento(int ID){
        Objetos objeto = null;
        String sen = "SELECT * FROM objetos WHERE ID_OBJETO = " + ID;
        try {
            Connection con = conectar();

            Statement sentencia = con.createStatement();

            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                String nombre = res.getString("NOMBRE");
                String descripcion = res.getString("DESCRIPCION");
                objeto = new Objetos(nombre,descripcion);
            }
            conectar().close();
        } catch(SQLException e) {
            System.out.print(e);
        }
        return objeto;
    }
}
