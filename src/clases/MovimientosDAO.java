package clases;

import java.sql.*;

public class MovimientosDAO {
    public MovimientosDAO(){
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


    public static Movimientos leerMovimiento(int ID){
        Movimientos movimiento = null;
        String sen = "SELECT * FROM movimientos WHERE ID_MOVIMIENTO = " + ID;
        try {
            Connection con = conectar();

            Statement sentencia = con.createStatement();

            ResultSet res = sentencia.executeQuery(sen);

            if (res.next()) {
                String tipo = res.getString("TIPO");
                String descripcion = res.getString("DESCRIPCION");
                int potencia = res.getInt("POTENCIA");
                int acuracy = res.getInt("ACURACY");
                int pp = res.getInt("PP");
                String clase  = res.getString("CLASE");
                movimiento = new Movimientos(tipo, descripcion, potencia, acuracy, pp, clase);
            }
            conectar().close();
        } catch(SQLException e) {
            System.out.print(e);
        }
        return movimiento;
    }
}
