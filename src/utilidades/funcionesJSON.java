package utilidades;

import clases.*;
import com.google.gson.Gson;
import com.google.gson.*;
import java.io.*;
import java.sql.SQLException;

public class funcionesJSON {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static void escribirJson(Object objeto, String rutaArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            String json = gson.toJson(objeto);
            bw.write(json);
            System.out.println("Archivo JSON escrito correctamente: " + rutaArchivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T leerObjetoDeJSON(String rutaArchivo, Class<T> claseObjeto) throws IOException {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            return gson.fromJson(reader, claseObjeto);
        }
    }

    public static void escribirEquipoAJSON(Equipo equipo, String rutaArchivo) throws IOException {
        Equipo equipoExistente = leerObjetoDeJSON(rutaArchivo, Equipo.class);

        if (equipoExistente == null) {
            escribirJson(equipo, rutaArchivo);
            System.out.println("Equipo guardado en el archivo: " + rutaArchivo);
        } else {
            boolean cambiosRealizados = false;
            for (Pokemon nuevoPokemon : equipo.getEquipo()) {
                boolean encontrado = false;
                for (Pokemon existentePokemon : equipoExistente.getEquipo()) {
                    if (nuevoPokemon.getID() == existentePokemon.getID()) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    equipoExistente.getEquipo().add(nuevoPokemon);
                    cambiosRealizados = true;
                }
            }

            if (cambiosRealizados) {
                escribirJson(equipoExistente, rutaArchivo);
                System.out.println("Equipo actualizado y guardado en el archivo: " + rutaArchivo);
            } else {
                System.out.println("El objeto ya está presente en el archivo.");
            }
        }
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

    public static void escribirPokemonAJSON(Pokemon pokemon, String rutaArchivo) throws IOException, SQLException {
        escribirJson(pokemon, rutaArchivo);
        System.out.println("Pokemon guardado en el archivo: " + rutaArchivo);
    }

    public static Pokemon leerPokemonDeJSON(String rutaArchivo) throws IOException {
        return leerObjetoDeJSON(rutaArchivo, Pokemon.class);
    }
}
