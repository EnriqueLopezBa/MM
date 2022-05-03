
package modelo;

public class Quiz {
 
    private int idPregunta, idCliente;
    private String respuesta;

    public Quiz(int idPregunta, int idCliente, String respuesta) {
        this.idPregunta = idPregunta;
        this.idCliente = idCliente;
        this.respuesta = respuesta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    
}
