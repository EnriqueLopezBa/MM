package modelo;

/**
 *
 * @author Enrique
 */
public class TipoEvento {
    private int idTipoEvento;
    private String tematica;

    public TipoEvento() {
    }

    public TipoEvento(int idTipoEvento, String tematica) {
        this.idTipoEvento = idTipoEvento;
        this.tematica = tematica;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    
    
}
