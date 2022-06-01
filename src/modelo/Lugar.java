package modelo;

/**
 *
 * @author Enrique
 */
public class Lugar {

    private int idLugar, idCiudad, idProveedor;
    private String nombreLocal, domicilio;
    private int capacidad;
    private int precio;
   

    public Lugar() {
    }

    public Lugar(int idLugar, int idCiudad, int idProveedor, String nombreLocal, String domicilio, int capacidad, int precio) {
        this.idLugar = idLugar;
        this.idCiudad = idCiudad;
        this.idProveedor = idProveedor;
        this.nombreLocal = nombreLocal;
        this.domicilio = domicilio;
        this.capacidad = capacidad;
        this.precio = precio;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }


    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
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


    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    
  
}
