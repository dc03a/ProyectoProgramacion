package clases;

public class Objetos {
    public int ID_OBJETO;
    public String NOMBRE;
    public String DESCRIPCION;

    public Objetos(int ID_OBJETO) {
        this.ID_OBJETO = ID_OBJETO;
    }

    public Objetos() {
    }

    public Objetos(String NOMBRE, String DESCRIPCION) {
    }

    public int getID_OBJETO() {
        return ID_OBJETO;
    }

    public void setID_OBJETO(int ID_OBJETO) {
        this.ID_OBJETO = ID_OBJETO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }
}
