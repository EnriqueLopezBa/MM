
package modelo;

/**
 *
 * @author Enrique
 */
public class LugarImagenes {
    private int idLugar;
    private String id2;
    private byte[] imagen;
    private String descripcion;

    public LugarImagenes() {
    }

    public LugarImagenes(int idLugar, String id2, byte[] imagen, String descripcion) {
        this.idLugar = idLugar;
        this.id2 = id2;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
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
