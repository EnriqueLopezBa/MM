package idao;

import modelo.EventosDestacados;

/**
 *
 * @author Enrique
 */
public interface IEventosDestacadosDAO extends CRUD<EventosDestacados>{
    EventosDestacados obtenerByIdEventoDestacado (String idEventoDestacado);
    EventosDestacados obtenerByIdEvento(int idEvento);
}
