package utilidades;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class credenciales {
    private static final String ARCHIVO_CREDENCIALES = "credenciales.txt";

    public static void escribirCredenciales(String usuario, String contrasena) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CREDENCIALES))) {
            writer.write(usuario);
            writer.newLine();
            writer.write(contrasena);
        }
    }

    public static String[] leerCredenciales() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CREDENCIALES))) {
            String usuario = reader.readLine();
            String contrasena = reader.readLine();
            return new String[]{usuario, contrasena};
        }
    }

    public static Connection conectar() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/proyectoProgramacion";
        try {
            String[] credenciales = leerCredenciales();
            con = DriverManager.getConnection(url, credenciales[0], credenciales[1]);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return con;
    }
}
