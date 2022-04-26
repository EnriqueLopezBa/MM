package modelo;

/**
 *
 * @author Enrique
 */
public class ProveedorArea {
    private int idProveedor;
    private int idCiudad;

    public ProveedorArea() {
    }

    public ProveedorArea(int idProveedor, int idCiudad) {
        this.idProveedor = idProveedor;
        this.idCiudad = idCiudad;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }
    
    
}
