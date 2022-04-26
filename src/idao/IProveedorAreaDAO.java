package idao;

import java.util.ArrayList;
import modelo.ProveedorArea;

/**
 *
 * @author Enrique
 */
public interface IProveedorAreaDAO extends CRUD<ProveedorArea>{
    
    ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad);
}
