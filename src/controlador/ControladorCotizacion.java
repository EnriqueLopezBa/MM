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

    public Object obtenerTotalCotizacionByIDEventoAndisCotFinal(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerTotalCotizacionByIDEventoAndisCotFinal(idEvento);
    }

    public void setCotizacionFinal(int idEvento, int numCotizacion) {
        CotizacionDAOImp.getInstancia().setCotizacionFinal(idEvento, numCotizacion);
    }

    public String obtenerTotalCotizacionByIDEventoAndNumCotizacion(int idEvento, int numCotizacion) {
        return CotizacionDAOImp.getInstancia().obtenerTotalCotizacionByIDEventoAndNumCotizacion(idEvento, numCotizacion);
    }

    public ArrayList<Cotizacion> obtenerListaByIdCliete(int idCliente) {
        return CotizacionDAOImp.getInstancia().obtenerListaByIdCliete(idCliente);
    }

    public int obtenerLastCotizacion(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerLastCotizacion(idEvento);
    }

    public ArrayList<Cotizacion> obtenerListaByIDEventoAndLastCotizacion(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerListaByIDEventoAndLastCotizacion(idEvento);
    }

    public ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerListaByIDEvento(idEvento);
    }

    public ArrayList<Cotizacion> obtenerListaByIDEventoAndNumCotizacion(int idEvento, int numCotizacion) {
        return CotizacionDAOImp.getInstancia().obtenerListaByIDEventoAndNumCotizacion(idEvento, numCotizacion);
    }

    public int obtenerNumNuevaCotizacion(int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerNumNuevaCotizacion(idEvento);
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

    public Mensaje registrarLote(ArrayList<Cotizacion> lista) {
        return CotizacionDAOImp.getInstancia().registrarLote(lista);
    }

    public ArrayList<Cotizacion> obtenerListaByNumCotizacionAndIdClienteAndIdEvento(int numCotizacion, int idCliente, int idEvento) {
        return CotizacionDAOImp.getInstancia().obtenerListaByNumCotizacionAndIdClienteAndIdEvento(numCotizacion, idCliente, idEvento);
    }
}
