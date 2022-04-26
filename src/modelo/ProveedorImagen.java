
package modelo;


public class ProveedorImagen {
    private int idProveedor;
    private String id2;
    private byte[] imagen;
    private String descripcion;

    public ProveedorImagen() {
    }

    public ProveedorImagen(int idProveedor, String id2, byte[] imagen, String descripcion) {
        this.idProveedor = idProveedor;
        this.id2 = id2;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }


    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
}
