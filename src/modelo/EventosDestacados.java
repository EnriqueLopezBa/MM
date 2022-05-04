package modelo;

public class EventosDestacados {

    private String idEventoDestacado;
    private int idEvento;
    private byte[] imagen;

    public EventosDestacados() {
    }

    public EventosDestacados(String idEventoDestacado, int idEvento, byte[] imagen) {
        this.idEventoDestacado = idEventoDestacado;
        this.idEvento = idEvento;
        this.imagen = imagen;
    }

    public String getIdEventoDestacado() {
        return idEventoDestacado;
    }

    public void setIdEventoDestacado(String idEventoDestacado) {
        this.idEventoDestacado = idEventoDestacado;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}
