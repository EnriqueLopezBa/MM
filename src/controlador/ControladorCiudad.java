package controlador;

import dao.CiudadDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Ciudad;

/**
 *
 * @author Enrique
 */
public class ControladorCiudad {

    public ArrayList<Ciudad> obtenerLista() {
        return CiudadDAOImp.getInstancia().obtenerLista();
    }

    public ArrayList<Ciudad> obtenerListaByCadena(String cadena) {
        return CiudadDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }
    public ArrayList<Ciudad> obtenerListaByIDEstado(int idEstado) {
        return CiudadDAOImp.getInstancia().obtenerByIDEstado(idEstado);
    }
    public Ciudad obtenerById(int idCiudad){
        return CiudadDAOImp.getInstancia().obtenerByID(idCiudad);
    }

   
    public Mensaje registrar(Ciudad et) {
        return CiudadDAOImp.getInstancia().registrar(et);
    }

    public Mensaje actualizar(Ciudad et) {
        return CiudadDAOImp.getInstancia().actualizar(et);
    }

    public Mensaje eliminar(Ciudad et) {
        return CiudadDAOImp.getInstancia().eliminar(et);
    }

}
