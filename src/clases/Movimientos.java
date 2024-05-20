package clases;

public class Movimientos {
    public int ID_MOVIMIENTO;
    public String TIPO;
    public String DESCRIPCION;
    public int POTENCIA;
    public int ACURACY;
    public int PP;
    public String clase;

    public Movimientos(int ID_MOVIMIENTO) {
        this.ID_MOVIMIENTO = ID_MOVIMIENTO;
    }

    public Movimientos() {
    }

    public Movimientos(String TIPO, String DESCRIPCION, int POTENCIA, int ACCURACY, int PP, String clase) {

    }

    public int getID_MOVIMIENTO() {
        return ID_MOVIMIENTO;
    }

    public void setID_MOVIMIENTO(int ID_MOVIMIENTO) {
        this.ID_MOVIMIENTO = ID_MOVIMIENTO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getPOTENCIA() {
        return POTENCIA;
    }

    public void setPOTENCIA(int POTENCIA) {
        this.POTENCIA = POTENCIA;
    }

    public int getACURACY() {
        return ACURACY;
    }

    public void setACURACY(int ACURACY) {
        this.ACURACY = ACURACY;
    }

    public int getPP() {
        return PP;
    }

    public void setPP(int PP) {
        this.PP = PP;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }
}
