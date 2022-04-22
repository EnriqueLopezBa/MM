
package controlador;

import dao.UsuarioDAOImp;
import independientes.Mensaje;
import modelo.Usuario;

/**
 *
 * @author Enrique
 */
public class ControladorUsuario {
    


    public Mensaje registrarUsuario(Usuario usuario) {
        return UsuarioDAOImp.getInstancia().registrar(usuario);
    }
    
    public Mensaje actualizarUsuario(Usuario usuario){
        return UsuarioDAOImp.getInstancia().actualizar(usuario);
    }
    
    public Mensaje eliminarUsuario(Usuario usuario){
        return UsuarioDAOImp.getInstancia().eliminar(usuario);
    }
    
    public Usuario inicioSesion(Usuario usuario){
        return UsuarioDAOImp.getInstancia().inicioSesion(usuario.getCorreo(), usuario.getClave());
    }
    
    
}
