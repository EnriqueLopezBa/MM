/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package independientes;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCliente;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JTable;
import modelo.Cliente;
import vista.principales.Principal;

/**
 *
 * @author Enrique
 */
public class Constante {

    private static Cliente clienteTemporal;
 
    public static void mensaje(String texto, Tipo tipo) {
        new Message(Principal.getInstancia(), true, texto, tipo).showAlert();
    }

    public static boolean filaSeleccionada(JTable tbl) {
        if (tbl.getSelectedRow() != -1) {
            return true;
        }
        mensaje("Seleccionada una fila", Tipo.ERROR);
        return false;
    }

    public static boolean getAdmin() {
        return Principal.getInstancia().admin;
    }

  
    public static Cliente getClienteTemporal() {
        return clienteTemporal;
    }

    public static void setClienteTemporal(Cliente cliente) {
        clienteTemporal = cliente;
    }

    public static void removeClienteTemporal() {
        clienteTemporal = null;
    }

    private static int presupuesto = 0;

    public static int getPresupuesto() {
        return presupuesto;
    }

    public static void iniciarPresupuesto(int cantidad) {
        presupuesto = cantidad;
        Principal.getInstancia().lblPresupuesto.setText("Presupuesto: " + presupuesto);
    }

    public static void setPresupuesto(int cantidad, boolean restar) {
        if (presupuesto == 0) {
            return;
        }
        if (restar) {
            presupuesto -= cantidad;
        } else {
            presupuesto += cantidad;
        }
        Principal.getInstancia().lblPresupuesto.setText("Presupuesto: " + presupuesto);
    }

    public static void setPresupuesto(int cantidad) {
        presupuesto = cantidad;
    }

    public static Cliente getClienteActivo() {
        return ControladorCliente.getInstancia().obtenerClienteActivo();
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
