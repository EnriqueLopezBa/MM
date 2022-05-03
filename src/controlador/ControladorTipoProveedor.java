package controlador;

import dao.TipoProveedorDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.TipoProveedor;

/**
 *
 * @author Enrique
 */
public class ControladorTipoProveedor {

    private static ControladorTipoProveedor instancia;
    private ControladorTipoProveedor() {
    }
    public static ControladorTipoProveedor getInstancia(){
        if (instancia == null) {
            instancia = new ControladorTipoProveedor();
        }
        return instancia;
    }

    
    public ArrayList<TipoProveedor> obtenerListaByCadena(String cadena) {
        return TipoProveedorDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public TipoProveedor obtenerByID(int id) {
        return TipoProveedorDAOImp.getInstancia().obtenerByID(id);
    }

    public TipoProveedor obtenerTipoProveedorByNombre(String nombre) {
        return TipoProveedorDAOImp.getInstancia().obtenerTipoProveedorByNombre(nombre);
    }

    public Mensaje registrar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje eliminar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().eliminar(t);
    }
}
