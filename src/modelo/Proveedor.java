package modelo;

public class Proveedor {

    private int idProveedor;
    private int idtipoProveedor;
    private String nombre, nombreEmpresa, telefono, telefono2;
    private int precioAprox;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, int idtipoProveedor, String nombre, String nombreEmpresa, String telefono, String telefono2, int precioAprox) {
        this.idProveedor = idProveedor;
        this.idtipoProveedor = idtipoProveedor;
        this.nombre = nombre;
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.precioAprox = precioAprox;
    }



    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public int getPrecioAprox() {
        return precioAprox;
    }

    public void setPrecioAprox(int precioAprox) {
        this.precioAprox = precioAprox;
    }

    public int getIdtipoProveedor() {
        return idtipoProveedor;
    }

    public void setIdtipoProveedor(int idtipoProveedor) {
        this.idtipoProveedor = idtipoProveedor;
    }

    

    
}
