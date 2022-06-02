package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.NegocioArea;

/**
 *
 * @author Enrique
 */
public interface INegocioAreaDAO extends CRUD<NegocioArea>{
    
    ArrayList<NegocioArea> obtenerListaByIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor);
    ArrayList<NegocioArea> obtenerListaByIdNegocio(int idNegocio);
    NegocioArea obtenerByIdCiudadAndIdNegocio(int idCiudad, int idNegocio);
    Mensaje registrarLote(ArrayList<NegocioArea> lote);
    Mensaje actualizarLote(ArrayList<NegocioArea> lote);
    NegocioArea obtenerNegocioByNombre(String nombre);
    NegocioArea obtenerNegocioByLast();
 
    
}
