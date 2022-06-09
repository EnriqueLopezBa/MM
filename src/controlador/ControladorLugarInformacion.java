package controlador;

import dao.LugarDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.LugarInformacion;

/**
 *
 * @author Enrique
 */
public class ControladorLugarInformacion {
    
    private static ControladorLugarInformacion instancia;
    private ControladorLugarInformacion() {
    }
    public static ControladorLugarInformacion getInstancia(){
        if (instancia == null) {
            instancia = new ControladorLugarInformacion();
        }
        return instancia;
    }

    
    public LugarInformacion obtenerByID(int id) {
        return LugarDAOImp.getInstancia().obtenerByID(id);
    }

    public LugarInformacion obtenerLugarByLast() {
        return LugarDAOImp.getInstancia().obtenerLugarByLast();
    }

    public ArrayList<LugarInformacion> obtenerListaByCadena(String cadena) {
        return LugarDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public LugarInformacion obtenerLugarByCadena(LugarInformacion lug) {
        return LugarDAOImp.getInstancia().obtenerLugarByCadena(lug);
    }

    public ArrayList<LugarInformacion> obtenerListaByIDCIudad(int idCiudad) {
        return LugarDAOImp.getInstancia().obtenerListaByIDCiudad(idCiudad);
    }

    public Mensaje registrar(LugarInformacion et) {
        return LugarDAOImp.getInstancia().registrar(et);
    }

    public Mensaje actualizar(LugarInformacion et) {
        return LugarDAOImp.getInstancia().actualizar(et);
    }

    public Mensaje eliminar(LugarInformacion et) {
        return LugarDAOImp.getInstancia().eliminar(et);
    }

}
