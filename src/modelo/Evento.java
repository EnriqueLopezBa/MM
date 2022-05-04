package modelo;

import java.util.Date;

public class Evento {

    private int idEvento;
    private int idCliente;
    private int idTipoEvento;
    private int idLugar;
    private Date fecha;
    private int noInvitados, presupuesto;
    private String estilo, nombreEvento;
    private int precioFinal;
    
    public Evento() {
    }

//    public Evento(int idEvento, int idCliente, int idTipoEvento, int idLugar, Date fecha, int noInvitados, int presupuesto, String estilo, String nombreEvento) {
//        this.idEvento = idEvento;
//        this.idCliente = idCliente;
//        this.idTipoEvento = idTipoEvento;
//        this.idLugar = idLugar;
//        this.fecha = fecha;
//        this.noInvitados = noInvitados;
//        this.presupuesto = presupuesto;
//        this.estilo = estilo;
//        this.nombreEvento = nombreEvento;
//    }

    public Evento(int idEvento, int idCliente, int idTipoEvento, int idLugar, Date fecha, int noInvitados, int presupuesto, String estilo, String nombreEvento, int precioFinal) {
        this.idEvento = idEvento;
        this.idCliente = idCliente;
        this.idTipoEvento = idTipoEvento;
        this.idLugar = idLugar;
        this.fecha = fecha;
        this.noInvitados = noInvitados;
        this.presupuesto = presupuesto;
        this.estilo = estilo;
        this.nombreEvento = nombreEvento;
        this.precioFinal = precioFinal;
    }
    

    
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNoInvitados() {
        return noInvitados;
    }

    public void setNoInvitados(int noInvitados) {
        this.noInvitados = noInvitados;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public int getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(int precioFinal) {
        this.precioFinal = precioFinal;
    }

 
    


}
