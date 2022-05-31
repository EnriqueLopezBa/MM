/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.CotizacionDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Cotizacion;

/**
 *
 * @author Enrique
 */
public class ControladorCotizacion {

    private static ControladorCotizacion instancia;

    private ControladorCotizacion() {
    }

    public static ControladorCotizacion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCotizacion();
        }
        return instancia;
    }

    public ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerListaByIDEvento(idEvento);
    }

    public Mensaje eliminar(Cotizacion t) {
        return CotizacionDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(Cotizacion t) {
        return CotizacionDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje registrar(Cotizacion t) {
        return CotizacionDAOImp.getInstancia().registrar(t);
    }
}
