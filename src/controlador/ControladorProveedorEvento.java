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
