package utilidades;

import clases.*;
import com.google.gson.Gson;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class funcionesJSON {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void escribirEquipoAJSON(Equipo equipo, String rutaArchivo) throws IOException {
        String equipoJSON = gson.toJson(equipo);
        FileWriter writer = new FileWriter(rutaArchivo);
        writer.write(equipoJSON);
        writer.close();
        System.out.println("Equipo guardado en el archivo: " + rutaArchivo);
    }

    public static Equipo leerEquipoDeJSON(String rutaArchivo) throws IOException {
        FileReader reader = new FileReader(rutaArchivo);
        Equipo equipo = gson.fromJson(reader, Equipo.class);
        reader.close();
        return equipo;
    }

    public static void escribirCajaAJSON(Caja caja, String rutaArchivo) throws IOException {
        String cajaJSON = gson.toJson(caja);
        FileWriter writer = new FileWriter(rutaArchivo);
        writer.write(cajaJSON);
        writer.close();
        System.out.println("Caja guardada en el archivo: " + rutaArchivo);
    }

    public static Caja leerCajaDeJSON(String rutaArchivo) throws IOException {
        FileReader reader = new FileReader(rutaArchivo);
        Caja caja = gson.fromJson(reader, Caja.class);
        reader.close();
        return caja;
    }

    public static void escribirPokemonAJSON(Pokemon pokemon, String rutaArchivo) throws IOException {
        String pokemonJSON = gson.toJson(pokemon);
        FileWriter writer = new FileWriter(rutaArchivo);
        writer.write(pokemonJSON);
        writer.close();
        System.out.println("Pokemon guardado en el archivo: " + rutaArchivo);
    }

    public static Pokemon leerPokemonDeJSON(String rutaArchivo) throws IOException {
        FileReader reader = new FileReader(rutaArchivo);
        Pokemon pokemon = gson.fromJson(reader, Pokemon.class);
        reader.close();
        return pokemon;
    }
}
