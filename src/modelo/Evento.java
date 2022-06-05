package modelo;

import java.util.Date;

public class Evento {

    private int idEvento;
    private int idCliente;
    private int idTipoEvento;
    private int idLugar;
    private Date fechaInicio, fechaFinal;
    private int noInvitados, presupuesto;
    private String estilo, nombreEvento;

    public Evento() {
    }

    public Evento(int idEvento, int idCliente, int idTipoEvento, int idLugar, Date fechaInicio, Date fechaFinal, int noInvitados, int presupuesto, String estilo, String nombreEvento) {
        this.idEvento = idEvento;
        this.idCliente = idCliente;
        this.idTipoEvento = idTipoEvento;
        this.idLugar = idLugar;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.noInvitados = noInvitados;
        this.presupuesto = presupuesto;
        this.estilo = estilo;
        this.nombreEvento = nombreEvento;
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

}
