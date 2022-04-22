
package modelo;

/**
 *
 * @author Enrique
 */
public class LugarEtiquetas {
    private int idEtiqueta;
    private int idLugar;

    public LugarEtiquetas() {
    }

    public LugarEtiquetas(int idEtiqueta, int idLugar) {
        this.idEtiqueta = idEtiqueta;
        this.idLugar = idLugar;
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }
    
    
}
