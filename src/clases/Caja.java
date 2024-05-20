package clases;

import java.sql.*;
import java.util.ArrayList;

public class Caja {

    public ArrayList<Pokemon> caja = new ArrayList<>();
    public int ID_CAJA;
    public String NOMBRE;
    public int CAPACIDAD = 30;

    public Caja(int ID_CAJA) {
        this.ID_CAJA = ID_CAJA;
    }

    public void setCapacidad() {
        this.CAPACIDAD = 30;
    }

    public void setNombre(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getNombreCaja() {
        String nombre = "";

        return nombre;
    }

    public int getID_CAJA() {
        return ID_CAJA;
    }

}