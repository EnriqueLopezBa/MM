package idao;

import java.util.ArrayList;
import modelo.Lugar;

/**
 *
 * @author Enrique
 */
public interface ILugarDAO extends CRUD<Lugar> {
    ArrayList<Lugar> obtenerListaByIDCiudad(int idCiudad);
    Lugar obtenerLugarByLast();
    Lugar obtenerLugarByCadena(Lugar lug);
}
