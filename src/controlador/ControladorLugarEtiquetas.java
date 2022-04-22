package controlador;

import dao.LugarEtiquetasDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.LugarEtiquetas;

/**
 *
 * @author Enrique
 */
public class ControladorLugarEtiquetas {

    public Mensaje registrarLote(ArrayList<LugarEtiquetas> lote) {
        return LugarEtiquetasDAOImp.getInstancia().registrarLote(lote);
    }

    public Mensaje actualizarLote(ArrayList<LugarEtiquetas> lote) {
        return LugarEtiquetasDAOImp.getInstancia().actualizarLote(lote);
    }

    public Mensaje eliminar(LugarEtiquetas t) {
        return LugarEtiquetasDAOImp.getInstancia().eliminar(t);
    }

    public LugarEtiquetas obtenerByID(int idEtiqueta) {
        return LugarEtiquetasDAOImp.getInstancia().obtenerByID(idEtiqueta);
    }

    public ArrayList<LugarEtiquetas> obtenerEtiquetasByIDLugar(int idLugar) {
        return LugarEtiquetasDAOImp.getInstancia().obtenerEtiquetasByIDLugar(idLugar);
    }
}
