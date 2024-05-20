package clases;

public class Habilidades {
    public int ID_HABILIDAD;
    public String NOMBRE;
    public String EFECTO;

    public Habilidades(int ID_HABILIDAD) {
        this.ID_HABILIDAD = ID_HABILIDAD;
    }

    public Habilidades() {

    }

    public Habilidades(String NOMBRE, String EFECTO) {
        this.NOMBRE = NOMBRE;
        this.EFECTO = EFECTO;
    }

    public Habilidades(int ID, String NOMBRE, String EFECTO) {
        this.ID_HABILIDAD = ID;
        this.NOMBRE = NOMBRE;
        this.EFECTO = EFECTO;
    }

    public int getID_HABILIDAD() {
        return ID_HABILIDAD;
    }

    public void setID_HABILIDAD(int ID_HABILIDAD) {
        this.ID_HABILIDAD = ID_HABILIDAD;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getEFECTO() {
        return EFECTO;
    }

    public void setEFECTO(String EFECTO) {
        this.EFECTO = EFECTO;
    }
}
