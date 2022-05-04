
package idao;

import java.util.ArrayList;
import modelo.Evento;

/**
 *
 * @author Enrique
 */
public interface IEventoDAO extends CRUD<Evento>{
    ArrayList<Evento> obtenerEventoByIDCliente(int idCliente);
    ArrayList<Evento> obtenerEventoByAnio(int anio);
}
