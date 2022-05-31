package controlador;

import dao.ProveedorEventoDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.ProveedorEvento;

/**
 *
 * @author Enrique
 */
public class ControladorProveedorEvento {

    private static ControladorProveedorEvento instancia;

    private ControladorProveedorEvento() {
    }

    public static ControladorProveedorEvento getInstancia() {
        if (instancia == null) {
            instancia = new ControladorProveedorEvento();
        }
        return instancia;
    }

    public ProveedorEvento obtenerByIdEventoAndIdProveedor(int idEvento, int idProveedor) {
        return ProveedorEventoDAOImp.getInstancia().obtenerByIdEventoAndIdProveedor(idEvento, idProveedor);
    }

    public Mensaje eliminar(ProveedorEvento t) {
        return ProveedorEventoDAOImp.getInstancia().eliminar(t);
    }

    public ArrayList<ProveedorEvento> obtenerListaByIdProveedor(int idProveedor) {
        return ProveedorEventoDAOImp.getInstancia().obtenerListaByIdProveedor(idProveedor);
    }

    public Mensaje registrar(ProveedorEvento t) {
        return ProveedorEventoDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizarLote(ArrayList<ProveedorEvento> loteProveedores) {
        return ProveedorEventoDAOImp.getInstancia().actualizarLote(loteProveedores);
    }

    public Mensaje registrarLote(ArrayList<ProveedorEvento> loteProveedores) {
        return ProveedorEventoDAOImp.getInstancia().registrarLote(loteProveedores);
    }

    public ArrayList<ProveedorEvento> obtenerListaByIdEvento(int idEvento) {
        return ProveedorEventoDAOImp.getInstancia().obtenerListaByIdEvento(idEvento);
    }
}
