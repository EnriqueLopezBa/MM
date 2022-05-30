
package modelo;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class ProveedorEvento {
    private int idEvento;
    private int idProveedor;
    private Date horaInicio, horaFinal;


    public ProveedorEvento() {
    }

    public ProveedorEvento(int idEvento, int idProveedor, Date horaInicio, Date horaFinal) {
        this.idEvento = idEvento;
        this.idProveedor = idProveedor;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
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

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }    
}
