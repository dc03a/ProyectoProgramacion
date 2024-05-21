package utilidades;

import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conector {
    public static Connection conectar() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/proyectoProgramacion";
        try {
            con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
