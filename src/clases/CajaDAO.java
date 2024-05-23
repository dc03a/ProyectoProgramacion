package clases;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import utilidades.*;

public class CajaDAO {
    private static final String JSON_CAJA_PATH = "json/caja.json";
    private static final String JSON_EQUIPO_PATH = "json/caja.json";

    /* Para generar la lista de pokemons de la caja que le pasemos */
    public static ArrayList<Pokemon> listaPokemon() throws IOException {
        ArrayList<Pokemon> cajaPok = new ArrayList<>();
        String sql = "SELECT * FROM pokemon WHERE estaEnCaja = 1";

        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement(sql);
             ResultSet rs = sentencia.executeQuery(sql);) {
            while (rs.next()) {
                Pokemon pokemonAux = new Pokemon();
                pokemonAux.setID(rs.getInt("ID"));
                pokemonAux.setNombre(rs.getString("Nombre"));
                pokemonAux.setHabilidad(rs.getString("Habilidad"));
                pokemonAux.setTipo1(rs.getString("Tipo1"));
                pokemonAux.setTipo2(rs.getString("Tipo2"));
                pokemonAux.setNivel(rs.getInt("Nivel"));
                pokemonAux.setHp(rs.getInt("Hp"));
                pokemonAux.setAtaque(rs.getInt("Ataque"));
                pokemonAux.setDefensa(rs.getInt("Defensa"));
                pokemonAux.setAtaqueEspecial(rs.getInt("AtaqueEspecial"));
                pokemonAux.setDefensaEspecial(rs.getInt("DefensaEspecial"));
                pokemonAux.setVelocidad(rs.getInt("Velocidad"));
                pokemonAux.setMovimiento1(rs.getString("Movimiento1"));
                pokemonAux.setMovimiento2(rs.getString("Movimiento2"));
                pokemonAux.setObjeto(rs.getString("Objeto"));
                pokemonAux.setEstaEnEquipo(rs.getBoolean("EstaEnEquipo"));
                pokemonAux.setEstaEnCaja(rs.getBoolean("EstaEnCaja"));
                pokemonAux.setApodo(rs.getString("Apodo"));
                cajaPok.add(pokemonAux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Caja caja = new Caja();
        caja.setListaCaja(cajaPok);
        funcionesJSON.escribirCajaAJSON(caja,JSON_CAJA_PATH );

        return cajaPok;
    }

    public static void meterPokemonACaja(String pokemonNombre) throws IOException, SQLException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        Pokemon pokemon = PokemonDAO.buscarPokemonPorNombre(pokemonNombre);

        if (pokemon == null) {
            throw new IllegalArgumentException("El Pokémon con el nombre " + pokemonNombre + " no existe.");
        }

        caja.getListaCaja().add(pokemon);
        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);

        String query = "UPDATE pokemon SET estaEnCaja = 1, estaEnEquipo = 0 WHERE Id = ?";
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement(query);) {
            sentencia.setInt(1, pokemon.getID());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sacarPokemonCaja(String pokemonNombre) throws IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        boolean quitado = caja.getListaCaja().removeIf(pokemon -> pokemon.getNombre().equalsIgnoreCase(pokemonNombre));

        if (!quitado) {
            throw new IllegalArgumentException("El Pokémon con el nombre " + pokemonNombre + " no está en la caja.");
        }

        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);

        String query = "UPDATE pokemon SET estaEnCaja = 0, estaEnEquipo = 1 WHERE Nombre = ?";
        try (Connection con = credenciales.conectar();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void liberarPokemon(Pokemon pokemon) throws SQLException, IOException {
        if (!seEncuentraestaEnCaja(pokemon.getNombre())) {
            System.out.println("El Pokémon no está en la caja. No se puede liberar desde aquí.");
            return;
        }

        String query = "UPDATE pokemon SET estaEnEquipo = 0, estaEnCaja = 0 WHERE Nombre = ?";
        try (Connection con = credenciales.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemon.getNombre());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);

        ArrayList<Pokemon> listaCaja = getCaja().getListaCaja();
        listaCaja.removeIf(p -> p.getNombre().equalsIgnoreCase(pokemon.getNombre()));
        caja.setListaCaja(listaCaja);

        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);
    }

    private static boolean seEncuentraestaEnCaja(String pokemonNombre) {
        String query = "SELECT * FROM pokemon WHERE Nombre = ? AND estaEnCaja = 1";
        try (Connection con = credenciales.conectar(); PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, pokemonNombre);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean estaEnCaja(Pokemon pokemon) throws SQLException, IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_EQUIPO_PATH);
        if (caja != null) {
            return caja.getListaCaja().contains(pokemon);
        } else {
            return false;
        }
    }

    public static void setCaja (ArrayList<Pokemon> lista) throws IOException {
        Caja caja = new Caja();
        caja.setListaCaja(lista);
        funcionesJSON.escribirCajaAJSON(caja, JSON_CAJA_PATH);
    }

    public static Caja getCaja () throws IOException {
        Caja caja = funcionesJSON.leerCajaDeJSON(JSON_CAJA_PATH);
        if (caja != null) {
            System.out.println("La caja se va a devolver vacía.");
        }
        return caja;
    }
}