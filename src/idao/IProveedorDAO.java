package idao;

import java.util.ArrayList;
import modelo.Proveedor;

/**
 *
 * @author Enrique
 */
public interface IProveedorDAO extends CRUD<Proveedor> {

    ArrayList<Proveedor> obtenerListaByIdTipoProveedor(int idTipoProveedor);
    Proveedor obtenerByLast();
}
