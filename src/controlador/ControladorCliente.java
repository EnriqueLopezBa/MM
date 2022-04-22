package controlador;

import dao.ClienteDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Cliente;

/**
 *
 * @author Enrique
 */
public class ControladorCliente {

    public ArrayList<Cliente> obtenerClientes(String cadena) {
        return ClienteDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Mensaje registrarCliente(Cliente cliente) {
        return ClienteDAOImp.getInstancia().registrar(cliente);
    }
    
    public Mensaje actualizarCliente(Cliente cliente){
        return ClienteDAOImp.getInstancia().actualizar(cliente);
    }
    
    public Mensaje eliminarCliente(Cliente cliente){
        return ClienteDAOImp.getInstancia().eliminar(cliente);
    }

}
