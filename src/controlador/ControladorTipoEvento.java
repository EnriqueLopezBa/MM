
package controlador;

import dao.TipoEventoDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.TipoEvento;

/**
 *
 * @author Enrique
 */
public class ControladorTipoEvento {
    
    
    public Mensaje registrar(TipoEvento t){
      return  TipoEventoDAOImp.getInstancia().registrar(t);
    }
    
    public Mensaje actualizar(TipoEvento t){
      return  TipoEventoDAOImp.getInstancia().actualizar(t);
    }
    public Mensaje eliinar(TipoEvento t){
      return  TipoEventoDAOImp.getInstancia().eliminar(t);
    }
    public ArrayList<TipoEvento> obtenerListaByCadena(String cadena){
      return  TipoEventoDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
}
