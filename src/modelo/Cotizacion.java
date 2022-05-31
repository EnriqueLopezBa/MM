package modelo;

/**
 *
 * @author Enrique
 */
public class Cotizacion {
    private int idEvento, idProveedor, cotizacion;

    public Cotizacion(int idEvento, int idProveedor, int cotizacion) {
        this.idEvento = idEvento;
        this.idProveedor = idProveedor;
        this.cotizacion = cotizacion;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(int cotizacion) {
        this.cotizacion = cotizacion;
    }
    
}
