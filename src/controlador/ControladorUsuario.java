package controlador;

import dao.UsuarioDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Usuario;

/**
 *
 * @author Enrique
 */
public class ControladorUsuario {

    private static ControladorUsuario instancia;

    private ControladorUsuario() {
    }

    public static ControladorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorUsuario();
        }
        return instancia;
    }

    public Usuario obtenerByID(int id) {
        return UsuarioDAOImp.getInstancia().obtenerByID(id);
    }

    public Mensaje registrarUsuario(Usuario usuario) {
        return UsuarioDAOImp.getInstancia().registrar(usuario);
    }

    public Mensaje actualizarUsuario(Usuario usuario) {
        return UsuarioDAOImp.getInstancia().actualizar(usuario);
    }

    public Mensaje eliminarUsuario(Usuario usuario) {
        return UsuarioDAOImp.getInstancia().eliminar(usuario);
    }

    public Usuario inicioSesion(Usuario usuario) {
        return UsuarioDAOImp.getInstancia().inicioSesion(usuario.getCorreo(), usuario.getClave());
    }

    public ArrayList<Usuario> obtenerListaByCadena(String cadena) {
        return UsuarioDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
}
