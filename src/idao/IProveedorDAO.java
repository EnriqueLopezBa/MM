package idao;

import java.util.ArrayList;
import modelo.Proveedor;

/**
 *
 * @author Enrique
 */
public interface IProveedorDAO extends CRUD<Proveedor> {


    Proveedor obtenerByLast();
 
}
