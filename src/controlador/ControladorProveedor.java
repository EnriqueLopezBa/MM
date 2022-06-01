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
    
    private static ControladorProveedor instancia;
    private ControladorProveedor() {
    }
    public static ControladorProveedor getInstancia(){
        if (instancia == null) {
            instancia = new ControladorProveedor();
        }
        return instancia;
    }

    
    public ArrayList<Proveedor> obtenerListaByCadena(String cadena) {
        return ProveedorDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Proveedor obtenerByLast() {
        return ProveedorDAOImp.getInstancia().obtenerByLast();
    }
    
    public Proveedor obtenerByID(int id) {
        return ProveedorDAOImp.getInstancia().obtenerByID(id);
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
