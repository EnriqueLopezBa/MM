package idao;

import independientes.Mensaje;
import java.util.ArrayList;
/**
 *
 * @author Enrique
 * @param <T>
 */
public interface CRUD<T> {

    ArrayList<T> obtenerLista();

    ArrayList<T> obtenerListaByCadena(String cadena);

    T obtenerByID(int id);

    Mensaje registrar(T t);

    Mensaje actualizar(T t);

    Mensaje eliminar(T t);
    
    String yaExiste(T t);
}
