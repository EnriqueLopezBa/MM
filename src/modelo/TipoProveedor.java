package modelo;

public class TipoProveedor {

    private int idTipoProveedor;
    private String tipoProveedor;

    public TipoProveedor() {
    }

    
    public TipoProveedor(int idTipoProveedor, String tipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
        this.tipoProveedor = tipoProveedor;
    }

    public String getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public int getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(int idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

}
