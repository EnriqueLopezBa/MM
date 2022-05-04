package controlador;

import dao.EventosDestacadosDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.EventosDestacados;

/**
 *
 * @author Enrique
 */
public class ControladorEventosDestacados {

    private static ControladorEventosDestacados instancia;

    private ControladorEventosDestacados() {
    }

    public static ControladorEventosDestacados getInstancia() {
        if (instancia == null) {
            instancia = new ControladorEventosDestacados();
        }
        return instancia;
    }

    public EventosDestacados obtenerByIdEvento(int idEvento) {
        return EventosDestacadosDAOImp.getInstancia().obtenerByIdEvento(idEvento);
    }

    public EventosDestacados obtenerByIdEventoDestacado(String idEventoDestacado) {
        return EventosDestacadosDAOImp.getInstancia().obtenerByIdEventoDestacado(idEventoDestacado);
    }

    public ArrayList<EventosDestacados> obtenerLista() {
        return EventosDestacadosDAOImp.getInstancia().obtenerLista();
    }

    public Mensaje eliminar(EventosDestacados t) {
        return EventosDestacadosDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje registrar(EventosDestacados t) {
        return EventosDestacadosDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizar(EventosDestacados t) {
        return EventosDestacadosDAOImp.getInstancia().actualizar(t);
    }

}
