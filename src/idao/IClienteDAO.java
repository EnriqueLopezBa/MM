
package idao;

import modelo.Cliente;

/**
 *
 * @author Enrique
 */
public interface IClienteDAO extends CRUD<Cliente> {
    //INDICAR METODOS ESPECIFICOS DEL CLIENTE
    Cliente obtenerClienteActivo();
    boolean setClienteActivoById(int idCliente);
}
