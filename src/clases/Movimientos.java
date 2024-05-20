package clases;

public class Movimientos {
    public int ID_MOVIMIENTO;
    public String[] TIPO = new String[20];
    public String[] DESCRIPCION = new String[255];
    public int POTENCIA;
    public int ACURACY;
    public int PP;
    public String[] clase = new String[12];

    public Movimientos(int ID_MOVIMIENTO) {
        this.ID_MOVIMIENTO = ID_MOVIMIENTO;
    }
}
