package clases;

import java.util.ArrayList;

public class Equipo {
    public int ID_POKEMON;
    public Objetos objeto;
    public ArrayList<Pokemon> EquipoPokemon;

    public Equipo(){
        EquipoPokemon = new ArrayList<>();
    }

    public Equipo(int id, Objetos obj) {
        this.ID_POKEMON = id;
        this.objeto = obj;
        this.EquipoPokemon = new ArrayList<>(6);
    }
}
