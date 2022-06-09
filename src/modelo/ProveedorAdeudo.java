/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Enrique
 */
public class ProveedorAdeudo {

    private int idEvento, idNegocio, idProveedor, cotizacion, adeudo;

    public ProveedorAdeudo() {
    }

    public ProveedorAdeudo(int idEvento, int idNegocio, int idProveedor, int cotizacion, int adeudo) {
        this.idEvento = idEvento;
        this.idNegocio = idNegocio;
        this.idProveedor = idProveedor;
        this.cotizacion = cotizacion;
        this.adeudo = adeudo;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
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

    public int getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(int adeudo) {
        this.adeudo = adeudo;
    }

}
