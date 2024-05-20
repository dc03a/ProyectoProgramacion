package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CajaDAO {

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

        /*** METODOS PARA CAJAS ***/

        /* para poder crear el objeto Caja a partir de un id_caja */
        public Caja datosCaja(int ID_CAJA){
            boolean resultado = existeCaja(ID_CAJA);
            ArrayList<Pokemon> cajaPok = new ArrayList<>();
            if (resultado){
                Connection con = conectar();


                String sql = "SELECT * FROM caja WHERE ID_CAJA = ?";

                try{
                    PreparedStatement sentencia = con.prepareStatement(sql);
                    sentencia.setInt(1, ID_CAJA);

                    ResultSet rs = sentencia.executeQuery();
                    while (rs.next()){
                        Caja cajaAux = new Caja(ID_CAJA);
                        cajaAux.setNombre(rs.getString("NOMBRE"));
                        cajaAux.setCapacidad();
                        return cajaAux;
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        /* para comprobar el tamanio de una caja */
        public int getTamanioCaja(ArrayList<Pokemon> lista){
            return lista.size();
        }

        /* Para comprobar si hay espacio en una caja */
        public boolean hayEspacio(ArrayList<Pokemon> lista){
            boolean resultado = false;
            if (getTamanioCaja(lista) < 30){
                resultado = true;
            }

            return resultado;
        }

        /* id_caja1 es la caja en la que esta,  id_caja2 es caja a la que movemos el Pokemon */
        public void moverCajaACaja(int id_caja1, int id_caja2, int id_pokemon){

            if(existeCaja(id_caja1)){
                Caja caja1 = new Caja(id_caja1);
                caja1 = datosCaja(id_caja1);
            } else {
                System.out.println("Lo siento, esa caja no existe.");
            }
            if(existeCaja(id_caja2)){
                Caja caja2 = new Caja(id_caja2);
                caja2 = datosCaja(id_caja2);
            }  else {
                System.out.println("Lo siento, esa caja no existe.");
            }

            ArrayList<Pokemon> lista1 = new ArrayList<>();
            lista1 = listaPokemon(id_caja1);
            int long1 = getTamanioCaja(lista1);

            ArrayList<Pokemon> lista2 = new ArrayList<>();
            lista2 = listaPokemon(id_caja2);
            int long2 = getTamanioCaja(lista2);

            if(hayEspacio(lista2)){
                Iterator<Pokemon> it = lista1.iterator();
                int contador = 0;
                while (it.hasNext()){

                    if(contador == long1 ){
                        lista2.add(it.next());
                        it.remove();
                        System.out.println("Tu pokemon ha sido añadido con éxito");
                    }
                        contador++;
                }
            } else {
                System.out.println("Lo siento no hay espacio en esa Caja");
            }
        }

        /*** METODOS PARA CAJAS  ***/

        public int getTamanioEquipo(){
            int longi = 0;

            return longi;
        }

        /* para poder mover de una caja a un equipo */
        public void moverCajaAEquipo( int ID_CAJA, int id_equipo, int id_pokemon){


        }

        public ArrayList<Pokemon> listaEquipo(){
            ArrayList<Pokemon> listEquip = new ArrayList<>();
            Connection con = conectar();

            String sql =  "SELECT * FROM EQUIPO";
            try{
                PreparedStatement sentencia = con.prepareStatement(sql);
                ResultSet rs = sentencia.executeQuery();
                Equipo equip = new Equipo();
                while(rs.next()){

                }

            } catch(SQLException e){
                e.printStackTrace();
            }

            return listEquip;
        }

        /* Para generar la lista de pokemons de la caja que le pasemos */
        public ArrayList<Pokemon> listaPokemon(int ID_CAJA){
            boolean resultado = existeCaja(ID_CAJA);
            ArrayList<Pokemon> cajaPok = new ArrayList<>();
            if(resultado){
                Connection con = conectar();

                String sql = "SELECT * FROM pokemon WHERE ID_CAJA = ?";
                try {
                    PreparedStatement sentencia = con.prepareStatement(sql);
                    sentencia.setInt(1, ID_CAJA);
                    ResultSet rs = sentencia.executeQuery();
                    while (rs.next()){
                        Pokemon pokemonAux = new Pokemon(rs.getInt("ID_POKEMON"));
                        pokemonAux.setNumPokedex(rs.getInt("NUM_POKEDEX"));
                        pokemonAux.setNombre(rs.getString("NOMBRE"));
                        pokemonAux.setId_habilibidad(rs.getInt("ID_HABILIDAD"));
                        pokemonAux.setTipo1(rs.getString("TIPO1"));
                        pokemonAux.setTipo2(rs.getString("TIPO2"));
                        pokemonAux.setNivel(rs.getInt("NIVEL"));
                        pokemonAux.setHP(rs.getInt("HP"));
                        pokemonAux.setAtaque(rs.getInt("ATAQUE"));
                        pokemonAux.setDefensa(rs.getInt("DEFENSA"));
                        pokemonAux.setAtaqueEspecial(rs.getInt("ATAQUE_ESPECIAL"));
                        pokemonAux.setDefensaEspecial(rs.getInt("DEFENSA_ESPECIAL"));
                        pokemonAux.setVelocidad(rs.getInt("VELOCIDAD"));
                        pokemonAux.setId_objeto(rs.getInt("OBJETO"));
                        pokemonAux.setId_Caja(ID_CAJA);
                        pokemonAux.setEstaEnEquipo(rs.getBoolean("EQUIPO"));
                        pokemonAux.setId_mov1(rs.getInt("MOV1"));
                        pokemonAux.setId_mov2(rs.getInt("MOV2"));
                        cajaPok.add(pokemonAux);
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

            return cajaPok;

        }


        /* Para comprobar si el id_caja que le pasamos corresponde a una caja */
        public boolean existeCaja(int ID_CAJA){
            Boolean existe = false;
            Connection con = conectar();
            String sql = "SELECT * FROM caja WHERE id_caja = ? ";
            try {
                PreparedStatement sentencia = con.prepareStatement(sql);
                sentencia.setInt(1, ID_CAJA);
                ResultSet rs = sentencia.executeQuery();
                if (!rs.next()) {
                    System.out.println("Lo siento, no existe una caja con ese id");
                } else {
                    System.out.println(" Esa caja si existe.");
                    existe = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return existe;
        }
    }