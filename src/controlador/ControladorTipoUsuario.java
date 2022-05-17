package controlador;

import dao.TipoUsuarioDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.TipoUsuario;

/**
 *
 * @author Enrique
 */
public class ControladorTipoUsuario {

    private static ControladorTipoUsuario instancia;

    private ControladorTipoUsuario() {
    }

    public static ControladorTipoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorTipoUsuario();
        }
        return instancia;
    }

    public ArrayList<TipoUsuario> obtenerListaByCadena(String cadena) {
        return TipoUsuarioDAOImp.getInstacia().obtenerListaByCadena(cadena);
    }

    public TipoUsuario obtenerByID(int id) {
        return TipoUsuarioDAOImp.getInstacia().obtenerByID(id);
    }

    public Mensaje registrar(TipoUsuario t) {
        return TipoUsuarioDAOImp.getInstacia().registrar(t);
    }

    public Mensaje actualizar(TipoUsuario t) {
        return TipoUsuarioDAOImp.getInstacia().actualizar(t);
    }

    public Mensaje eliminar(TipoUsuario t) {
        return TipoUsuarioDAOImp.getInstacia().eliminar(t);
    }
}
