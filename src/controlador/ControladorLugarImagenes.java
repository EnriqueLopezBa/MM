package controlador;

import dao.LugarImagenesDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.LugarImagenes;

/**
 *
 * @author Enrique
 */
public class ControladorLugarImagenes {

    public Mensaje registrar(LugarImagenes lugarimagen) {
        return LugarImagenesDAOImp.getInstancia().registrar(lugarimagen);
    }

    public ArrayList<LugarImagenes> obtenerListaByIDLugar(int idLugar) {
        return LugarImagenesDAOImp.getInstancia().obtenerListaByIDLugar(idLugar);
    }

    public ArrayList<LugarImagenes> obtenerListaByIDCiudad(int idCiudad, String etiquetas) {
        return LugarImagenesDAOImp.getInstancia().obtenerListaByIDCiudad(idCiudad, etiquetas);
    }

    public LugarImagenes obtenerById2(String id2) {
        return LugarImagenesDAOImp.getInstancia().obtenerById2(id2);
    }

    public Mensaje eliminar(LugarImagenes t) {
        return LugarImagenesDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(LugarImagenes t) {
        return LugarImagenesDAOImp.getInstancia().actualizar(t);
    }
}
