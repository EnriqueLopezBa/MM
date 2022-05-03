package controlador;

import dao.EtiquetaDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Etiqueta;

/**
 *
 * @author Enrique
 */
public class ControladorEtiqueta {

    private static ControladorEtiqueta instancia;
    private ControladorEtiqueta() {
    }
    public static ControladorEtiqueta getInstancia(){
        if (instancia == null) {
            instancia = new ControladorEtiqueta();
        }
        return instancia;
    }

    
    public ArrayList<Etiqueta> obtenerLista() {
        return EtiquetaDAOImp.getInstancia().obtenerLista();
    }

    public Etiqueta obtenerByID(int id) {
        return EtiquetaDAOImp.getInstancia().obtenerByID(id);
    }

    public Etiqueta obtenerByEtiquetaNombre(String nombre) {
        return EtiquetaDAOImp.getInstancia().obtenerByEtiquetaNombre(nombre);
    }

    public ArrayList<Etiqueta> obtenerListaByCadena(String cadena) {
        return EtiquetaDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Mensaje registrar(Etiqueta et) {
        return EtiquetaDAOImp.getInstancia().registrar(et);
    }

    public Mensaje actualizar(Etiqueta et) {
        return EtiquetaDAOImp.getInstancia().actualizar(et);
    }

    public Mensaje eliminar(Etiqueta et) {
        return EtiquetaDAOImp.getInstancia().eliminar(et);
    }

}
