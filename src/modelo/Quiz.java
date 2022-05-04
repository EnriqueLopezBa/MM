
package modelo;

public class Quiz {
 
    private int idPregunta, idCliente, idEvento;
    private String respuesta, opciones;

    public Quiz() {
    }

    public Quiz(int idPregunta, int idCliente, int idEvento, String respuesta, String opciones) {
        this.idPregunta = idPregunta;
        this.idCliente = idCliente;
        this.idEvento = idEvento;
        this.respuesta = respuesta;
        this.opciones = opciones;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    
    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
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
