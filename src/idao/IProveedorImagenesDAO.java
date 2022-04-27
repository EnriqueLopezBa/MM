package idao;

import java.util.ArrayList;
import modelo.ProveedorImagenes;

/**
 *
 * @author Enrique
 */
public interface IProveedorImagenesDAO extends CRUD<ProveedorImagenes> {
    ArrayList<ProveedorImagenes> obtenerListabyIdProveedor(int idProveedor);
    ArrayList<ProveedorImagenes> obtenerListabyIdCiudad(int idCiudad);
}
