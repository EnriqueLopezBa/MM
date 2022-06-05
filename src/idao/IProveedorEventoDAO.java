package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Proveedor;
import modelo.ProveedorEvento;

/**
 *
 * @author Enrique
 */
public interface IProveedorEventoDAO extends CRUD<ProveedorEvento> {

    Mensaje registrarLote(ArrayList<ProveedorEvento> loteProveedores);

    Mensaje actualizarLote(ArrayList<ProveedorEvento> loteProveedores);
    ArrayList<ProveedorEvento> obtenerListaByIdEvento(int idEvento);
    ArrayList<ProveedorEvento> obtenerListaByIdProveedor(int idProveedor);
    ProveedorEvento obtenerByIdEventoAndIdProveedor(int idEvento, int idProveedor);
    ProveedorEvento obtenerByIdEventoAndIdNegocio(int idEvento, int idNegocio);

}
