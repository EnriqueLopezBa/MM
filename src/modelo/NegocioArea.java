package modelo;

/**
 *
 * @author Enrique
 */
public class NegocioArea {

    private int idNegocio;
    private int idCiudad;

    public NegocioArea() {
    }

    public NegocioArea(int idNegocio, int idCiudad) {
        this.idNegocio = idNegocio;
        this.idCiudad = idCiudad;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

}
