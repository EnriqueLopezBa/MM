
package modelo;

/**
 *
 * @author Enrique
 */
public class Usuario {
    private Integer idSuario;
    private Integer idTipoUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String clave;
    public Usuario() {
    }

    public Usuario(Integer idSuario, Integer idTipoUsuario, String nombre, String apellido, String correo, String clave) {
        this.idSuario = idSuario;
        this.idTipoUsuario = idTipoUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
    }

    public Integer getIdSuario() {
        return idSuario;
    }

    public void setIdSuario(Integer idSuario) {
        this.idSuario = idSuario;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
 
 
    
}
