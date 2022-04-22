/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package independientes;

import Componentes.Sweet_Alert.Message;

/**
 *
 * @author Enrique
 */
public class Mensaje {
    private Message.Tipo tipoMensaje;
    private String mensaje;

    public Mensaje() {
    }

    public Mensaje(Message.Tipo tipoMensaje, String mensaje) {
        this.tipoMensaje = tipoMensaje;
        this.mensaje = mensaje;
    }

    
    public Message.Tipo getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(Message.Tipo tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
