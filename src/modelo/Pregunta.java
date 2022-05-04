package modelo;

/**
 *
 * @author Enrique
 */
public class Pregunta {
    private int idPregunta;
    private String pregunta;
    private boolean escuestaSatisfaccion;
    private String opciones;

    public Pregunta() {
    }

    
    public Pregunta(int idPregunta, String pregunta, boolean escuestaSatisfaccion, String opciones) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.escuestaSatisfaccion = escuestaSatisfaccion;
        this.opciones = opciones;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isEscuestaSatisfaccion() {
        return escuestaSatisfaccion;
    }

    public void setEscuestaSatisfaccion(boolean escuestaSatisfaccion) {
        this.escuestaSatisfaccion = escuestaSatisfaccion;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    
}
