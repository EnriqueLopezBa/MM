package controlador;

import dao.NegocioImagenesDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.NegocioImagenes;

/**
 *
 * @author Enrique
 */
public class ControladorNegocioImagenes {

    private static ControladorNegocioImagenes instancia;
    private ControladorNegocioImagenes() {
    }
    public static ControladorNegocioImagenes getInstancia(){
        if (instancia == null) {
            instancia = new ControladorNegocioImagenes();
        }
        return instancia;
    }

    
    public ArrayList<NegocioImagenes> obtenerListabyIdProveedor(int idProveedor) {
        return NegocioImagenesDAOImp.getInstancia().obtenerListabyIdProveedor(idProveedor);
    }

    public NegocioImagenes obtenerByID2(String id2) {
        return NegocioImagenesDAOImp.getInstancia().obtenerByID2(id2);
    }

    public ArrayList<NegocioImagenes> obtenerListabyIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        return NegocioImagenesDAOImp.getInstancia().obtenerListabyIdCiudadAndTipoProveedor(idCiudad, idTipoProveedor);
    }

    public Mensaje registrar(NegocioImagenes lugarimagen) {
        return NegocioImagenesDAOImp.getInstancia().registrar(lugarimagen);
    }

    public Mensaje eliminar(NegocioImagenes t) {
        return NegocioImagenesDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(NegocioImagenes t) {
        return NegocioImagenesDAOImp.getInstancia().actualizar(t);
    }
}
