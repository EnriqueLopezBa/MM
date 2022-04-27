package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.ProveedorArea;

/**
 *
 * @author Enrique
 */
public interface IProveedorAreaDAO extends CRUD<ProveedorArea>{
    
    ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad);
    ArrayList<ProveedorArea> obtenerListaByIdProveedor(int idProveedor);
    ProveedorArea obtenerByIdCiudadAndIdProveedor(int idCiudad, int idProveedor);
    Mensaje registrarLote(ArrayList<ProveedorArea> lote);
    Mensaje actualizarLote(ArrayList<ProveedorArea> lote, int idProveedor);
 
    
}
