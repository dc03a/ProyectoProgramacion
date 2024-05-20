package clases;

import java.util.ArrayList;

public class Equipo {
    public int ID_POKEMON;
    public Objetos objeto;
    public ArrayList<Pokemon> EquipoPokemon;

    public Equipo() {
        EquipoPokemon = new ArrayList<>();
    }

    public Equipo(int id, Objetos obj) {
        this.ID_POKEMON = id;
        this.objeto = obj;
        this.EquipoPokemon = new ArrayList<>(6);
    }

    public int getID_POKEMON() {
        return ID_POKEMON;
    }

    public void setID_POKEMON(int ID_POKEMON) {
        this.ID_POKEMON = ID_POKEMON;
    }

    public Objetos getObjeto() {
        return objeto;
    }

    public void setObjeto(Objetos objeto) {
        this.objeto = objeto;
    }

    public ArrayList<Pokemon> getEquipoPokemon() {
        return EquipoPokemon;
    }

    public void setEquipoPokemon(ArrayList<Pokemon> equipoPokemon) {
        EquipoPokemon = equipoPokemon;
    }
}
