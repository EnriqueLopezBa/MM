package idao;

import java.util.ArrayList;
import modelo.LugarInformacion;

/**
 *
 * @author Enrique
 */
public interface ILugarInformacionDAO extends CRUD<LugarInformacion> {
    ArrayList<LugarInformacion> obtenerListaByIDCiudad(int idCiudad);
    LugarInformacion obtenerLugarByLast();
    LugarInformacion obtenerLugarByCadena(LugarInformacion lug);
}
