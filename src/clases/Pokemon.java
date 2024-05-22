package clases;

public class Pokemon {
    public int ID;
    public String Nombre;
    public String Habilidad;
    public String Tipo1;
    public String Tipo2;
    public int Nivel = 50;
    public int Hp;
    public int Ataque;
    public int Defensa;
    public int AtaqueEspecial;
    public int DefensaEspecial;
    public int Velocidad;
    public String Movimiento1;
    public String Movimiento2;
    public String Objeto;
    public boolean estaEnEquipo;
    public boolean estaEnCaja;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getHabilidad() {
        return Habilidad;
    }

    public void setHabilidad(String habilidad) {
        Habilidad = habilidad;
    }

    public String getTipo1() {
        return Tipo1;
    }

    public void setTipo1(String tipo1) {
        Tipo1 = tipo1;
    }

    public String getTipo2() {
        return Tipo2;
    }

    public void setTipo2(String tipo2) {
        Tipo2 = tipo2;
    }

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        this.Nivel = nivel;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    public int getAtaque() {
        return Ataque;
    }

    public void setAtaque(int ataque) {
        Ataque = ataque;
    }

    public int getDefensa() {
        return Defensa;
    }

    public void setDefensa(int defensa) {
        Defensa = defensa;
    }

    public int getAtaqueEspecial() {
        return AtaqueEspecial;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        AtaqueEspecial = ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return DefensaEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        DefensaEspecial = defensaEspecial;
    }

    public int getVelocidad() {
        return Velocidad;
    }

    public void setVelocidad(int velocidad) {
        Velocidad = velocidad;
    }

    public String getMovimiento1() {
        return Movimiento1;
    }

    public void setMovimiento1(String movimiento1) {
        Movimiento1 = movimiento1;
    }

    public String getMovimiento2() {
        return Movimiento2;
    }

    public void setMovimiento2(String movimiento2) {
        Movimiento2 = movimiento2;
    }

    public String getObjeto() {
        return Objeto;
    }

    public void setObjeto(String objeto) {
        Objeto = objeto;
    }

    public boolean isEstaEnEquipo() {
        return estaEnEquipo;
    }

    public void setEstaEnEquipo(boolean estaEnEquipo) {
        this.estaEnEquipo = estaEnEquipo;
    }

    public boolean isEstaEnCaja() {
        return estaEnCaja;
    }

    public void setEstaEnCaja(boolean estaEnCaja) {
        this.estaEnCaja = estaEnCaja;
    }

    public Pokemon() {
    }

    public Pokemon(int id, String nombre) {
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "ID=" + ID +
                ", Nombre='" + Nombre + '\'' +
                ", Habilidad='" + Habilidad + '\'' +
                ", Tipo1='" + Tipo1 + '\'' +
                ", Tipo2='" + Tipo2 + '\'' +
                ", Nivel=" + Nivel +
                ", Hp=" + Hp +
                ", Ataque=" + Ataque +
                ", Defensa=" + Defensa +
                ", AtaqueEspecial=" + AtaqueEspecial +
                ", DefensaEspecial=" + DefensaEspecial +
                ", Velocidad=" + Velocidad +
                ", Movimiento1='" + Movimiento1 + '\'' +
                ", Movimiento2='" + Movimiento2 + '\'' +
                ", Objeto='" + Objeto + '\'' +
                ", estaEnEquipo=" + estaEnEquipo +
                ", estaEnCaja=" + estaEnCaja +
                '}';
    }
}