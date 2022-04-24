package idao;

import java.util.ArrayList;
import modelo.LugarImagenes;

/**
 *
 * @author Enrique
 */
public interface ILugarImagenesDAO extends CRUD<LugarImagenes>{
    
    ArrayList<LugarImagenes> obtenerListaByIDLugar(int idLugar);
    LugarImagenes obtenerById2(String id2);
}
