package idao;

import java.util.ArrayList;
import modelo.Proveedor;
import modelo.ProveedorAdeudo;

/**
 *
 * @author Enrique
 */
public interface IProveedorDAO extends CRUD<Proveedor> {


    Proveedor obtenerByLast();
    ArrayList<ProveedorAdeudo> obtenerProveedoresConEventos();
    Object obtenerTotalCotizacionByIDEventoAndIDNegocio(int idEvent, int idNegocio);
 
}
