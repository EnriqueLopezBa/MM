package modelo;

/**
 *
 * @author Enrique
 */
public class Ciudad {

    private int idCiudad;
    private int idEstado;
    private String ciudad;

    public Ciudad() {
    }

    public Ciudad(int idCiudad, int idEstado, String ciudad) {
        this.idCiudad = idCiudad;
        this.idEstado = idEstado;
        this.ciudad = ciudad;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }



}
