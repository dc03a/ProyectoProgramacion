package utilidadesJSON;

import clases.*;
import com.google.gson.Gson;
import com.google.gson.*;

import javax.swing.event.ChangeListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class funcionesJSON {

    public static Pokemon leerDatosPokemonDesdeJSON(String ruta) {
        Gson gson = new Gson();
        Pokemon pokemon = null;

        try (FileReader reader = new FileReader(ruta)) {
            pokemon = gson.fromJson(reader, Pokemon.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    public static String escribirDatosPokemonJSON(int id) {
        Pokemon pokemon = PokemonDAO.leerDatos(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(pokemon);
    }

    public static Movimientos leerDatosMovimientoJSON(String ruta) {
        Gson gson = new Gson();
        Movimientos movimientos = null;

        try (FileReader reader = new FileReader(ruta)) {
            movimientos = gson.fromJson(reader, Movimientos.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    public String escribirDatosMovimientoJSON(int id) {
        Movimientos movimiento = MovimientosDAO.leerMovimiento(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(movimiento);
    }

    public static Caja leerDatosCajaJSON(String ruta) {
        Gson gson = new Gson();
        Caja caja = null;

        try (FileReader reader = new FileReader(ruta)) {
            caja = gson.fromJson(reader, Caja.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return caja;
    }

    public static String escribirDatosCajaJSON(int id) {
        Caja caja = CajaDAO.datosCaja(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(caja);
    }

    public static ArrayList<Equipo> leerDatosEquipoJSON(String ruta) {
        Gson gson = new Gson();
        ArrayList<Equipo> equipo = null;

        try (FileReader reader = new FileReader(ruta)) {
            equipo = gson.fromJson(reader, ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equipo;
    }

    public static void escribirDatosEquipoJSON(String ruta) {
        ArrayList<Pokemon> equipo = EquipoDAO.listaPokemon();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(equipo);

        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Habilidades leerDatosHabilidadesJSON(String ruta) {
        Gson gson = new Gson();
        Habilidades habilidades = null;

        try (FileReader reader = new FileReader(ruta)) {
            habilidades = gson.fromJson(reader, Habilidades.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return habilidades;
    }

    public static String escribirDatosHabilidadJSON(int id) {
        Habilidades habilidad = HabilidadesDAO.leerDatos(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(habilidad);
    }

    public static void escribirDatosHabilidadJSON(String ruta, Habilidades habilidad) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(habilidad);

        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
