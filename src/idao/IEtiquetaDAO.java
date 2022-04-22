package idao;

import modelo.Etiqueta;

/**
 *
 * @author Enrique
 */
public interface IEtiquetaDAO extends CRUD<Etiqueta> {

    // Metodos propies de etiqueta
    Etiqueta obtenerByEtiquetaNombre(String nombre);
}
