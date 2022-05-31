package idao;

import java.util.ArrayList;
import modelo.Cotizacion;

/**
 *
 * @author Enrique
 */
public interface ICotizacionDAO extends CRUD<Cotizacion>{
    ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento);
}
