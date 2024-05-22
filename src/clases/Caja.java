package clases;

import java.util.ArrayList;

public class Caja {
    public ArrayList<Pokemon> listaCaja;
    public String Nombre;

    public ArrayList<Pokemon> getListaCaja() {
        return listaCaja;
    }

    public void setListaCaja(ArrayList<Pokemon> listaCaja) {
        this.listaCaja = listaCaja;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return "Caja{" +
                "listaCaja=" + listaCaja +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }
}