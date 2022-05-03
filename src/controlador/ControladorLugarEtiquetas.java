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

    private static ControladorLugarEtiquetas instancia;
    private ControladorLugarEtiquetas() {
    }
    public static ControladorLugarEtiquetas getInstancia(){
        if (instancia == null) {
            instancia = new ControladorLugarEtiquetas();
        }
        return instancia;
    }

    
    public Mensaje registrarLote(ArrayList<LugarEtiquetas> lote) {
        return LugarEtiquetasDAOImp.getInstancia().registrarLote(lote);
    }

    public Mensaje actualizarLote(ArrayList<LugarEtiquetas> lote, int idLugar) {
        return LugarEtiquetasDAOImp.getInstancia().actualizarLote(lote, idLugar);
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
