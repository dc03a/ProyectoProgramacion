package utilidades;

import clases.*;
import com.google.gson.Gson;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class funcionesJSON {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static boolean escribirJson(Object objeto, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            String json = gson.toJson(objeto);
            bw.write(json);
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
            return false;
        }
    }

    public static <T> T leerObjetoDeJSON(String rutaArchivo, Class<T> claseObjeto) throws IOException {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            return gson.fromJson(reader, claseObjeto);
        }
    }

    public static void escribirEquipoAJSON(Equipo equipo, String rutaArchivo) throws IOException {
        escribirJson(equipo, rutaArchivo);
        System.out.println("Equipo guardado en el archivo: " + rutaArchivo);
    }

    public static Equipo leerEquipoDeJSON(String rutaArchivo) throws IOException {
        return leerObjetoDeJSON(rutaArchivo, Equipo.class);
    }

    public static void escribirCajaAJSON(Caja caja, String rutaArchivo) throws IOException {
        escribirJson(caja, rutaArchivo);
        System.out.println("Caja guardada en el archivo: " + rutaArchivo);
    }

    public static Caja leerCajaDeJSON(String rutaArchivo) throws IOException {
        return leerObjetoDeJSON(rutaArchivo, Caja.class);
    }

    public static void escribirPokemonAJSON(Pokemon pokemon, String rutaArchivo) throws IOException {
        escribirJson(pokemon, rutaArchivo);
        System.out.println("Pokemon guardado en el archivo: " + rutaArchivo);
    }

    public static Pokemon leerPokemonDeJSON(String rutaArchivo) throws IOException {
        return leerObjetoDeJSON(rutaArchivo, Pokemon.class);
    }
}
