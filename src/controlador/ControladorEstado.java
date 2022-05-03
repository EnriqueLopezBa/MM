package controlador;

import dao.EstadoDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Estado;

/**
 *
 * @author Enrique
 */
public class ControladorEstado {

    private static ControladorEstado instancia;
    private ControladorEstado() {
    }
    
    public static ControladorEstado getInstancia(){
        if (instancia == null) {
            instancia = new ControladorEstado();
        }
        return instancia;
    }


    public ArrayList<Estado> obtenerListaByCadena(String cadena) {
        return EstadoDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
    public Estado obtenerByID(int idEstado){
        return EstadoDAOImp.getInstancia().obtenerByID(idEstado);
    }

    public Mensaje registrar(Estado et) {
        return EstadoDAOImp.getInstancia().registrar(et);
    }

    public Mensaje actualizar(Estado et) {
        return EstadoDAOImp.getInstancia().actualizar(et);
    }

    public Mensaje eliminar(Estado et) {
        return EstadoDAOImp.getInstancia().eliminar(et);
    }
}
