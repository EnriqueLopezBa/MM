package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Quiz;

/**
 *
 * @author Enrique
 */
public interface IQuizDAO extends CRUD<Quiz> {

    ArrayList<Quiz> obtenerListaByIdClienteAndIdEvento(int idCliente, int idEvento);
    Quiz obtenerByIdPreguntaAndIdEvento(int idPregunta, int idEvento);
    Mensaje eliminarRespuesta(int idPregunta, int idEvento, int idCliente);
}
