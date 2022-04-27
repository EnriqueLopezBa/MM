package idao;

import java.util.ArrayList;
import modelo.Abono;

/**
 *
 * @author Enrique
 */
public interface IAbonoDAO extends CRUD<Abono> {
    
    ArrayList<Abono> obtenerListaByIdEvento(int idEvento);
}
