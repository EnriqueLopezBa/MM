package idao;

import java.util.ArrayList;
import modelo.Pregunta;

/**
 *
 * @author Enrique
 */
public interface IPreguntaDAO extends CRUD<Pregunta>{
    ArrayList<Pregunta> obtenerListaByCadenaAndIsEncuesta(String cadena, boolean encuesta);
}
