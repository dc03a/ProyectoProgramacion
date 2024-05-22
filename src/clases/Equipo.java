package clases;

import java.util.ArrayList;

public class Equipo {
    public ArrayList<Pokemon> equipo;
    public String Nombre;

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    public void setEquipo(ArrayList<Pokemon> equipo) {
        this.equipo = equipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Equipo: [");
        for (Pokemon pokemon : equipo) {
            builder.append(pokemon.toString());
            builder.append(", ");
        }
        if (!equipo.isEmpty()) {
            builder.setLength(builder.length() - 2); // Eliminar la última coma y espacio
        }
        builder.append("]");
        return builder.toString();
    }
}
