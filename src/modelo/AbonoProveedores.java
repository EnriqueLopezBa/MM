package modelo;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class AbonoProveedores {
    private int idAbono, idProveedor, idEvento, importe;
    private Date fecha;

    public AbonoProveedores() {
    }

    public AbonoProveedores(int idAbono, int idProveedor, int idEvento, int importe, Date fecha) {
        this.idAbono = idAbono;
        this.idProveedor = idProveedor;
        this.idEvento = idEvento;
        this.importe = importe;
        this.fecha = fecha;
    }
  

    public int getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdCliente(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

 
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}