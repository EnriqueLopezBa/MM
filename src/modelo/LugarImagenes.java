
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
    private boolean predeterminada;

    public LugarImagenes() {
    }

    public LugarImagenes(int idLugar, String id2, byte[] imagen, String descripcion, boolean predeterminada) {
        this.idLugar = idLugar;
        this.id2 = id2;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.predeterminada = predeterminada;
    }

   

    public boolean isPredeterminada() {
        return predeterminada;
    }

    public void setPredeterminada(boolean predeterminada) {
        this.predeterminada = predeterminada;
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
