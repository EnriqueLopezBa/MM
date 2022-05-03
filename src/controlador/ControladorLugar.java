package controlador;

import dao.LugarDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Lugar;

/**
 *
 * @author Enrique
 */
public class ControladorLugar {
    
    private static ControladorLugar instancia;
    private ControladorLugar() {
    }
    public static ControladorLugar getInstancia(){
        if (instancia == null) {
            instancia = new ControladorLugar();
        }
        return instancia;
    }

    
    public Lugar obtenerByID(int id) {
        return LugarDAOImp.getInstancia().obtenerByID(id);
    }

    public Lugar obtenerLugarByLast() {
        return LugarDAOImp.getInstancia().obtenerLugarByLast();
    }

    public ArrayList<Lugar> obtenerListaByCadena(String cadena) {
        return LugarDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Lugar obtenerLugarByCadena(Lugar lug) {
        return LugarDAOImp.getInstancia().obtenerLugarByCadena(lug);
    }

    public ArrayList<Lugar> obtenerListaByIDCIudad(int idCiudad) {
        return LugarDAOImp.getInstancia().obtenerListaByIDCiudad(idCiudad);
    }

    public Mensaje registrar(Lugar et) {
        return LugarDAOImp.getInstancia().registrar(et);
    }

    public Mensaje actualizar(Lugar et) {
        return LugarDAOImp.getInstancia().actualizar(et);
    }

    public Mensaje eliminar(Lugar et) {
        return LugarDAOImp.getInstancia().eliminar(et);
    }

}
