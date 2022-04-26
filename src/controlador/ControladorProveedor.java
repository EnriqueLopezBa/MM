package controlador;

import dao.ProveedorDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Proveedor;

/**
 *
 * @author Enrique
 */
public class ControladorProveedor {

    public ArrayList<Proveedor> obtenerListaByCadena(String cadena) {
        return ProveedorDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public ArrayList<Proveedor> obtenerListaByIdTipoProveedor(int idTipoProveedor) {
        return ProveedorDAOImp.getInstancia().obtenerListaByIdTipoProveedor(idTipoProveedor);
    }

    public Mensaje registrar(Proveedor t) {
        return ProveedorDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizar(Proveedor t) {
        return ProveedorDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje eliminar(Proveedor t) {
        return ProveedorDAOImp.getInstancia().eliminar(t);
    }
}