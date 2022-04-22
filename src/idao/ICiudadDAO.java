package idao;

import java.util.ArrayList;
import modelo.Ciudad;

/**
 *
 * @author Enrique
 */
public interface ICiudadDAO extends CRUD<Ciudad> {
    
    ArrayList<Ciudad> obtenerByIDEstado(int idEstado);
}
