package modelo;

/**
 *
 * @author Enrique
 */
public class Negocio {

    private int idNegocio, idProveedor, idTipoProveedor;
    private String nombreNegocio;
    private int precioAprox;
    private boolean disponible;
    private String descripcion;

    public Negocio() {
    }

    public Negocio(int idNegocio, int idProveedor, int idTipoProveedor, String nombreNegocio, int precioAprox, boolean disponible, String descripcion) {
        this.idNegocio = idNegocio;
        this.idProveedor = idProveedor;
        this.idTipoProveedor = idTipoProveedor;
        this.nombreNegocio = nombreNegocio;
        this.precioAprox = precioAprox;
        this.disponible = disponible;
        this.descripcion = descripcion;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(int idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public int getPrecioAprox() {
        return precioAprox;
    }

    public void setPrecioAprox(int precioAprox) {
        this.precioAprox = precioAprox;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
