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

    public ArrayList<ProveedorImagenes> obtenerListabyIdProveedor(int idProveedor) {
        return ProveedorImagenesDAOImp.getInstancia().obtenerListabyIdProveedor(idProveedor);
    }

    public ArrayList<ProveedorImagenes> obtenerListabyIdCiudad(int idCiudad) {
        return ProveedorImagenesDAOImp.getInstancia().obtenerListabyIdCiudad(idCiudad);
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
