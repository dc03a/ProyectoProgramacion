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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Equipo: [");
        for (Pokemon pokemon : equipo) {
            builder.append(pokemon.toString());
            builder.append(", ");
        }
        if (!equipo.isEmpty()) {
            builder.setLength(builder.length() - 2);
        }
        builder.append("]");
        return builder.toString();
    }
}
