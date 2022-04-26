package idao;

import modelo.TipoProveedor;

/**
 *
 * @author Enrique
 */
public interface ITipoProveedorDAO extends CRUD<TipoProveedor> {
    
    TipoProveedor obtenerTipoProveedorByNombre(String nombre);
}
