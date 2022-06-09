package modelo;

/**
 *
 * @author Enrique
 */
public class LugarInformacion {

    private int idNegocio;
    private String domicilio;
    private int capacidad;

    public LugarInformacion() {
    }

    public LugarInformacion(int idNegocio, String domicilio, int capacidad) {
        this.idNegocio = idNegocio;
        this.domicilio = domicilio;
        this.capacidad = capacidad;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}
