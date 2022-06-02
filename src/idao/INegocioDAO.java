package idao;

import java.util.ArrayList;
import modelo.Negocio;

/**
 *
 * @author Enrique
 */
public interface INegocioDAO extends CRUD<Negocio> {
    Negocio obtenerNegocioByIdProvAndNombreNeg(int idProveedor, String nombreNegocio);
    ArrayList<Negocio> obtenerListaByIdProveedor(int idProveedor);
    ArrayList<Negocio> obtenerListaByIdTipoProveedor(int idTipoProveedor);
    Negocio obtenerNegocioByLast();
}
