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

    public Cliente obtenerByID(int id) {
        return ClienteDAOImp.getInstancia().obtenerByID(id);
    }

    public Cliente obtenerClienteActivo() {
        return ClienteDAOImp.getInstancia().obtenerClienteActivo();
    }

    public void desactivarClienteActivo() {
        ClienteDAOImp.getInstancia().desactivarClienteActivo();
    }

    public boolean setClienteActivoById(int idCliente) {
        return ClienteDAOImp.getInstancia().setClienteActivoById(idCliente);
    }

    public Mensaje registrarCliente(Cliente cliente) {
        return ClienteDAOImp.getInstancia().registrar(cliente);
    }

    public Mensaje actualizarCliente(Cliente cliente) {
        return ClienteDAOImp.getInstancia().actualizar(cliente);
    }

    public Mensaje eliminarCliente(Cliente cliente) {
        return ClienteDAOImp.getInstancia().eliminar(cliente);
    }

}
