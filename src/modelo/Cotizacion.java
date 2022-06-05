package modelo;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class Cotizacion {

    private int idEvento, idNegocio, cotizacion;
    private String comentario;
    private int numCotizacion;
    private Date fechaCotizacion;
    private boolean cotFinal;

    public Cotizacion() {
    }

    public Cotizacion(int idEvento, int idNegocio, int cotizacion, String comentario, int numCotizacion, Date fechaCotizacion, boolean cotFinal) {
        this.idEvento = idEvento;
        this.idNegocio = idNegocio;
        this.cotizacion = cotizacion;
        this.comentario = comentario;
        this.numCotizacion = numCotizacion;
        this.fechaCotizacion = fechaCotizacion;
        this.cotFinal = cotFinal;
    }

    public boolean isCotFinal() {
        return cotFinal;
    }

    public void setCotFinal(boolean cotFinal) {
        this.cotFinal = cotFinal;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(int cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNumCotizacion() {
        return numCotizacion;
    }

    public void setNumCotizacion(int numCotizacion) {
        this.numCotizacion = numCotizacion;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

}
