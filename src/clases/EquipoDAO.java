package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
