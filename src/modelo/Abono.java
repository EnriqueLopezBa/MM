package modelo;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class Abono {
    private int idAbono, idCliente, idEvento, importe;
    private Date fecha;

    public Abono() {
    }

    public Abono(int idAbono, int idCliente, int idEvento, int importe, Date fecha) {
        this.idAbono = idAbono;
        this.idCliente = idCliente;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
