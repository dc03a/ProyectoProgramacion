package clases;

public class Pokemon {
    String url = "jdbc:mysql://localhost:3306/proyectoprogramacion";
    String usuario = "root";
    String password = "7645";

    public int ID;
    public int num_pokedex;
    public String Nombre;
    public String habilidad;
    public int nivel;
    public String tipo1;
    public String tipo2;
    public String objeto;
    public int HP;
    public int ataque;
    public int defensa;
    public int ataqueEspecial;
    public int defensaEspecial;
    public int velocidad;
    public String mov1;
    public String mov2;
    public int id_Caja;
    public boolean estaEnEquipo;
    public int id_habilibidad;
    public int id_mov1;
    public int id_mov2;
    public int id_objeto;

    public Pokemon() {
    }

    public Pokemon(int id) {

        this.ID = id;
    }

    public Pokemon(int num_pokedex, String nombre, String habilidad, int nivel, String tipo1, String tipo2,
                   int HP, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad,
                   String s, String mov1, String objeto) {
        setNum_pokedex(num_pokedex);
        setNombre(nombre);
        setHabilidad(habilidad);
        setNivel(nivel);
        setTipo1(tipo1);
        setTipo2(tipo2);
        setHP(HP);
        setAtaque(ataque);
        setDefensa(defensa);
        setAtaqueEspecial(ataqueEspecial);
        setDefensaEspecial(defensaEspecial);
        setVelocidad(velocidad);
        setObjeto(objeto);
    }

    public Pokemon(int idPokemon, String nombre, int hp, int ataque, int defensa, int nivel, int idCaja) {
    }

    public boolean isEstaEnEquipo() {
        return estaEnEquipo;
    }

    public int getId_Caja() {
        return id_Caja;
    }

    public String getMov2() {
        return mov2;
    }

    public void setMov2(String mov2) {
        this.mov2 = mov2;

    }

    public String getMov1() {
        return mov1;
    }

    public void setMov1(String mov1) {
        this.mov1 = mov1;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getHP() {
        return HP;
    }

    public String getObjeto() {
        return objeto;
    }

    public String getTipo2() {
        return tipo2;
    }

    public String getTipo1() {
        return tipo1;
    }

    public int getNivel() {
        return nivel;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getNum_pokedex() {
        return num_pokedex;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /* aqui estan todos los setters usados en CajaDao */

    public void setNumPokedex(int numPokedex) {
        this.num_pokedex = numPokedex;
    }

    public void setId_habilibidad(int hab) {
        this.id_habilibidad = hab;
    }

    public void setId_mov1(int mov1) {
        this.id_mov1 = mov1;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setId_mov2(int mov2) {
        this.id_mov2 = mov2;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

    public void setId_Caja(int id_Caja) {
        this.id_Caja = id_Caja;
    }

    public void setId_objeto(int obj) {
        this.id_objeto = obj;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public void setNum_pokedex(int num_pokedex) {
        this.num_pokedex = num_pokedex;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public void setEstaEnEquipo(boolean estaEnEquipo) {
        this.estaEnEquipo = estaEnEquipo;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}