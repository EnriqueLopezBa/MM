package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Cotizacion;

/**
 *
 * @author Enrique
 */
public interface ICotizacionDAO extends CRUD<Cotizacion>{
    ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento);
        ArrayList<Cotizacion> obtenerListaByNumCotizacionAndIdClienteAndIdEvento(int numCotizacion, int idCliente, int idEvento);
    ArrayList<Cotizacion> obtenerListaByIDEventoAndNumCotizacion(int idEvento, int numCotizacion);
    ArrayList<Cotizacion> obtenerListaByIDEventoAndLastCotizacion(int idEvento);
    int obtenerLastCotizacion(int idEvento);
    int obtenerNumNuevaCotizacion(int idEvento);
    Mensaje registrarLote(ArrayList<Cotizacion> lista);
    Mensaje actualizarLote(ArrayList<Cotizacion> lista);
    String obtenerTotalCotizacionByIDEventoAndNumCotizacion(int idEvento, int numCotizacion);
    ArrayList<Cotizacion> obtenerListaByIdCliete(int idCliente);
    void setCotizacionFinal(int idEvento, int numCotizacion);
    Object obtenerTotalCotizacionByIDEventoAndisCotFinal(int idEvento);
    
}
