package controlador;

import dao.ClienteDAOImp;
import independientes.Constante;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Cliente;

/**
 *
 * @author Enrique
 */
public class ControladorCliente {

    private static ControladorCliente instancia;

    private ControladorCliente() {
    }

    public static ControladorCliente getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCliente();
        }
        return instancia;
    }

    public ArrayList<Cliente> obtenerClientes(String cadena) {
        return ClienteDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Cliente obtenerByID(int id) {
        return ClienteDAOImp.getInstancia().obtenerByID(id);
    }

    public Cliente obtenerClienteActivo() {
        return Constante.getClienteTemporal() != null ? Constante.getClienteTemporal() : obtenerClienteActivo2();
    }

    public Cliente obtenerClienteActivo2() {
        return ClienteDAOImp.getInstancia().obtenerClienteActivo();
    }

    public void desactivarClienteActivo() {
        ClienteDAOImp.getInstancia().desactivarClienteActivo();
    }

    public void desactivarClienteTemporal() {
        ClienteDAOImp.getInstancia().desactivarClienteTemporal();
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
