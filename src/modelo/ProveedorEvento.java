package modelo;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class ProveedorEvento {

    private int idEvento, idProveedor, idNegocio;
    private Date fechaInicio, fechaFinal;
    private String comentario;

    public ProveedorEvento() {
    }

    public ProveedorEvento(int idEvento, int idProveedor, int idNegocio, Date fechaInicio, Date fechaFinal, String comentario) {
        this.idEvento = idEvento;
        this.idProveedor = idProveedor;
        this.idNegocio = idNegocio;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.comentario = comentario;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

  

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

}
