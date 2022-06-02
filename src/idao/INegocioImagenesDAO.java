package idao;

import java.util.ArrayList;
import modelo.NegocioImagenes;

/**
 *
 * @author Enrique
 */
public interface INegocioImagenesDAO extends CRUD<NegocioImagenes> {
    ArrayList<NegocioImagenes> obtenerListabyIdNegocio(int idNegocio);
    ArrayList<NegocioImagenes> obtenerListabyIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor);
    NegocioImagenes obtenerByID2(String id2);
}
