package controlador;

import dao.NegocioDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Negocio;

/**
 *
 * @author Enrique
 */
public class ControladorNegocio {

    private static ControladorNegocio instancia;

    private ControladorNegocio() {
    }

    public static ControladorNegocio getInstancia() {
        if (instancia == null) {
            instancia = new ControladorNegocio();
        }
        return instancia;
    }

    public Negocio obtenerByID(int id) {
        return NegocioDAOImp.getInstancia().obtenerByID(id);
    }

    public Negocio obtenerNegocioByIdProvAndNombreNeg(int idProveedor, String nombreNegocio) {
        return NegocioDAOImp.getInstancia().obtenerNegocioByIdProvAndNombreNeg(idProveedor, nombreNegocio);
    }

    public ArrayList<Negocio> obtenerListaByIdProveedor(int idProveedor) {
        return NegocioDAOImp.getInstancia().obtenerListaByIdProveedor(idProveedor);
    }

    public ArrayList<Negocio> obtenerLista() {
        return NegocioDAOImp.getInstancia().obtenerLista();
    }

    public ArrayList<Negocio> obtenerListaByIdTipoProveedor(int idTipoProveedor) {
        return NegocioDAOImp.getInstancia().obtenerListaByIdTipoProveedor(idTipoProveedor);
    }

    public Mensaje eliminar(Negocio t) {
        return NegocioDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(Negocio t) {
        return NegocioDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje registrar(Negocio t) {
        return NegocioDAOImp.getInstancia().registrar(t);
    }
}
