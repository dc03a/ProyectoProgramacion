package clases;

import java.sql.*;

public class HabilidadesDAO {
    public HabilidadesDAO() {
        super();
    }


    public static Habilidades leerDatos(int ID) {
        Habilidades habilidad = null;
        String sen = "SELECT * FROM habilidades WHERE ID_HABILIDAD = " + ID;
        try {
            Connection con = PokemonDAO.conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                int idHab = res.getInt("ID_HABILIDAD");
                String nombre = res.getString("NOMBRE");
                String efecto = res.getString("EFECTO");
                habilidad = new Habilidades(idHab, nombre, efecto);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return habilidad;
    }
}