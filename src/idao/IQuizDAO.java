package idao;

import java.util.ArrayList;
import modelo.Quiz;

/**
 *
 * @author Enrique
 */
public interface IQuizDAO extends CRUD<Quiz> {

    ArrayList<Quiz> obtenerListaByIdCliente(int idCliente);

    Quiz obtenerByIdPreguntaAndIdEvento(int idPregunta, int idEvento);
}
