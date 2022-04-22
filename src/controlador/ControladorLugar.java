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
    
    
     public ArrayList<Lugar> obtenerLista(){
        return LugarDAOImp.getInstancia().obtenerLista();
    }
     
    public ArrayList<Lugar> obtenerListaByCadena(String cadena){
        return LugarDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
    public ArrayList<Lugar> obtenerListaByIDCIudad(int idCiudad){
        return LugarDAOImp.getInstancia().obtenerListaByIDCiudad(idCiudad);
    }

    public Mensaje registrar(Lugar et){
        return LugarDAOImp.getInstancia().registrar(et);
    }
    public Mensaje actualizar(Lugar et){
        return LugarDAOImp.getInstancia().actualizar(et);
    }
    public Mensaje eliminar(Lugar et){
        return LugarDAOImp.getInstancia().eliminar(et);
    }
    
}
