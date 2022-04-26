package controlador;

import dao.EventoDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Evento;

/**
 *
 * @author Enrique
 */
public class ControladorEvento {

    public ArrayList<Evento> obtenerListaByCadena(String cadena) {
        return EventoDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public ArrayList<Evento> obtenerEventoByIDCliente(int idCliente) {
        return EventoDAOImp.getInstancia().obtenerEventoByIDCliente(idCliente);
    }

    public Mensaje registrar(Evento t) {
        return EventoDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizar(Evento t) {
        return EventoDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje eliminar(Evento t) {
        return EventoDAOImp.getInstancia().eliminar(t);
    }
}
