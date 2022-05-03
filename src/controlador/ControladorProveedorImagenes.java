package controlador;

import dao.ProveedorImagenesDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.ProveedorImagenes;

/**
 *
 * @author Enrique
 */
public class ControladorProveedorImagenes {

    private static ControladorProveedorImagenes instancia;
    private ControladorProveedorImagenes() {
    }
    public static ControladorProveedorImagenes getInstancia(){
        if (instancia == null) {
            instancia = new ControladorProveedorImagenes();
        }
        return instancia;
    }

    
    public ArrayList<ProveedorImagenes> obtenerListabyIdProveedor(int idProveedor) {
        return ProveedorImagenesDAOImp.getInstancia().obtenerListabyIdProveedor(idProveedor);
    }

    public ProveedorImagenes obtenerByID2(String id2) {
        return ProveedorImagenesDAOImp.getInstancia().obtenerByID2(id2);
    }

    public ArrayList<ProveedorImagenes> obtenerobtenerListabyIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        return ProveedorImagenesDAOImp.getInstancia().obtenerListabyIdCiudadAndTipoProveedor(idCiudad, idTipoProveedor);
    }

    public Mensaje registrar(ProveedorImagenes lugarimagen) {
        return ProveedorImagenesDAOImp.getInstancia().registrar(lugarimagen);
    }

    public Mensaje eliminar(ProveedorImagenes t) {
        return ProveedorImagenesDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(ProveedorImagenes t) {
        return ProveedorImagenesDAOImp.getInstancia().actualizar(t);
    }
}
