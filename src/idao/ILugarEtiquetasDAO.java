
package idao;

import independientes.Mensaje;
import java.util.ArrayList;
import modelo.LugarEtiquetas;

/**
 *
 * @author Enrique
 */
public interface ILugarEtiquetasDAO extends CRUD<LugarEtiquetas> {
    Mensaje registrarLote(ArrayList<LugarEtiquetas> lote);
    Mensaje actualizarLote(ArrayList<LugarEtiquetas> lote);
    ArrayList<LugarEtiquetas> obtenerEtiquetasByIDLugar(int idLugar);
}
