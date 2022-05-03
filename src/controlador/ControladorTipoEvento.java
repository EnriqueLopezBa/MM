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

    private static ControladorTipoEvento instancia;
    private ControladorTipoEvento() {
    }
    public static ControladorTipoEvento getInstancia(){
        if (instancia == null) {
            instancia = new ControladorTipoEvento();
        }
        return instancia;
    }

    
    public Mensaje registrar(TipoEvento t) {
        return TipoEventoDAOImp.getInstancia().registrar(t);
    }

    public TipoEvento obtenerByID(int id) {
        return TipoEventoDAOImp.getInstancia().obtenerByID(id);
    }

    public TipoEvento obtenerTipoEventoByNombre(String nombre) {
        return TipoEventoDAOImp.getInstancia().obtenerTipoEventoByNombre(nombre);
    }

    public Mensaje actualizar(TipoEvento t) {
        return TipoEventoDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje eliinar(TipoEvento t) {
        return TipoEventoDAOImp.getInstancia().eliminar(t);
    }

    public ArrayList<TipoEvento> obtenerListaByCadena(String cadena) {
        return TipoEventoDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
}
