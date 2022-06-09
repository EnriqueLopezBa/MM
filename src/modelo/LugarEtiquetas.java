
package modelo;

/**
 *
 * @author Enrique
 */
public class LugarEtiquetas {
    private int idEtiqueta;
    private int idNegocio;

    public LugarEtiquetas() {
    }

    public LugarEtiquetas(int idEtiqueta, int idNegocio) {
        this.idEtiqueta = idEtiqueta;
        this.idNegocio = idNegocio;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }



    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

 
    
    
}
