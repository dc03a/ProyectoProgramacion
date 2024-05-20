package clases;

import java.sql.*;
import clases.Pokemon;

public class PokemonDAO {
    public PokemonDAO() {
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


    public static Pokemon leerDatos(int ID) {
        Pokemon pokemon = null;
        String sen = "SELECT pok.*, hab.NOMBRE AS HABILIDAD, obj.NOMBRE AS NOMBRE_OBJETO " +
                "FROM pokemon pok LEFT JOIN habilidades hab ON pok.ID_HABILIDAD = hab.ID_HABILIDAD " +
                "LEFT JOIN objetos obj ON pok.OBJETO = obj.ID_OBJETO WHERE ID_POKEMON =" + ID;
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
                int velocidad = res.getInt("VELOCIDAD");
                String objeto = res.getNString("NOMBRE_OBJETO");

                pokemon = new Pokemon(num_pokedex, Nombre, habilidad,nivel,tipo1,tipo2, HP, ataque,defensa,ataqueEspecial,defensaEspecial,velocidad,objeto);
            }
            conectar().close();
        } catch(SQLException e) {
            System.out.print(e);
        }
        return pokemon;

    }


    public String getMov1() {
        try {
            String sen = "SELECT mov.DESCRIPCION as NOMBRE FROM pokemon pok LEFT JOIN movimientos mov ON pok.MOV1 = mov.ID_MOVIMIENTO WHERE ID_POKEMON = " + ID;
            Connection con = conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);
            while (res.next()) {
                mov1 = res.getNString("NOMBRE");
            }

        }catch(SQLException e) {
            System.out.print(e);
        }
        return mov1;
    }


    public void setMov1(int mov1) {
        PreparedStatement statement = null;

        try {
            String sen = "UPDATE pokemon SET MOV1 = " + mov1 + " WHERE ID_POKEMON = " + ID;
            Connection con = conectar();
            statement = con.prepareStatement(sen);
            statement.executeUpdate();

        }catch(SQLException e) {
            System.out.print(e);
        }
    }

    public String getMov2() {
        try {
            String sen = "SELECT mov.DESCRIPCION as NOMBRE FROM pokemon pok LEFT JOIN movimientos mov ON pok.MOV2 = mov.ID_MOVIMIENTO WHERE ID_POKEMON = " + ID;
            Connection con = conectar();
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);
            while (res.next()) {
                mov2 = res.getNString("NOMBRE");
            }

        }catch(SQLException e) {
            System.out.print(e);
        }
        return mov2;
    }


    public void setMov2(int mov2) {
        PreparedStatement statement = null;
        try {
            String sen = "UPDATE pokemon SET MOV2 = " + mov2 + " WHERE ID_POKEMON = " + ID;
            Connection con = conectar();
            statement = con.prepareStatement(sen);
            statement.executeUpdate();

        }catch(SQLException e) {
            System.out.print(e);
        }
    }

    public int getId_Caja() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print(e);
        }
        try {
            String sen = "SELECT * FROM pokemon WHERE ID_POKEMON = " + ID;
            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);
            while (res.next()) {
                id_Caja = res.getInt("ID_CAJA");
            }

        }catch(SQLException e) {
            System.out.print(e);
        }
        return id_Caja;
    }

    public void setId_Caja(int id_Caja) {
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print(e);
        }
        try {
            String sen = "UPDATE pokemon SET ID_CAJA = " + id_Caja + " WHERE ID_POKEMON = " + ID;
            Connection con = DriverManager.getConnection(url, usuario, password);
            statement = con.prepareStatement(sen);
            statement.executeUpdate();

        }catch(SQLException e) {
            System.out.print(e);
        }
    }

    public boolean isEstaEnEquipo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print(e);
        }
        try {
            String sen = "SELECT * FROM pokemon WHERE ID_POKEMON = " + ID;
            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(sen);
            while (res.next()) {
                estaEnEquipo = res.getBoolean("EQUIPO");
            }

        }catch(SQLException e) {
            System.out.print(e);
        }
        return estaEnEquipo;
    }

    public void setEstaEnEquipo(boolean estaEnEquipo) {
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print(e);
        }
        try {
            int cantidad = 0;
            String consulta = "SELECT COUNT(*) AS CONTEO FROM equipo";
            String sen = "UPDATE pokemon SET EQUIPO = " + estaEnEquipo + " WHERE ID_POKEMON = " + ID;
            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement sentencia = con.createStatement();
            ResultSet res = sentencia.executeQuery(consulta);
            while (res.next()) {
                cantidad = res.getInt("CONTEO");
            }
            if (cantidad < 6) {
                statement = con.prepareStatement(sen);
                statement.executeUpdate();
                System.out.print(getNombre() + " ha sido añadido a el equipo\n");
            } else {
                System.out.print("Ya hay 6 pokemon en el equipo\n");
            }

        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}