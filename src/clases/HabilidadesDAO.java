package clases;

import java.sql.*;

public class HabilidadesDAO {
    public HabilidadesDAO(){
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


    public static Habilidades leerDatos(int ID){
        Habilidades habilidad = null;
        String sen = "SELECT * FROM habilidades WHERE ID_HABILIDAD = " + ID;
        try {
            Connection con = conectar();

            Statement sentencia = con.createStatement();

            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                String nombre = res.getString("NOMBRE");
                String efecto = res.getString("EFECTO");
                habilidad = new Habilidades(nombre, efecto);
            }
            conectar().close();
        } catch(SQLException e){
            System.out.print(e);
        }
        return habilidad;
    }
}