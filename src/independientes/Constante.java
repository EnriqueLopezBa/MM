package independientes;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import controlador.ControladorProveedor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JTable;
import modelo.Cliente;
import modelo.Evento;
import modelo.Lugar;
import modelo.Proveedor;
import vista.paneles.pnlEventos;
import vista.paneles.pnlProveedores;
import vista.principales.Principal;

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

    public static Object getInterfazActiva() {
        if (Principal.getInstancia().pnlContenido.getComponents().length == 0) {
            return null;
        }
        return Principal.getInstancia().pnlContenido.getComponents()[0].getClass();
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

    public static void setPresupuesto(int cantidad) {
        presupuesto = cantidad;
    }

    public static int getPresupuesto() {
        return presupuesto;
    }

    public static void actualizarPresupuesto(Evento event) {
        Lugar lugar = pnlEventos.getInstancia().lugarActual;
        if (presupuesto <= 0) {
            if (event != null) {
                presupuesto = ControladorEvento.getInstancia().obtenerByID(event.getIdEvento()).getPresupuesto();
            } else if(!pnlEventos.getInstancia().txtPresupuesto.getText().isEmpty()) {
                presupuesto = Integer.parseInt(pnlEventos.getInstancia().txtPresupuesto.getText().replaceAll(",", ""));
            }
            if (presupuesto <= 0) {
                return;
            }
        }
        int presupuestoTemp = presupuesto;
        if (lugar != null) {
            presupuestoTemp -= lugar.getPrecio();
        }
        for (int i = 0; i < pnlProveedores.getInstancia().m.getRowCount(); i++) {
            if (pnlProveedores.getInstancia().m.getValueAt(i, 6) != null) {
                presupuestoTemp -= (int) pnlProveedores.getInstancia().m.getValueAt(i, 6);
            }
        }
        Principal.getInstancia().lblPresupuesto.setText("Presupuesto: " + presupuestoTemp);
    }

    public static Cliente getClienteActivo() {
        return ControladorCliente.getInstancia().obtenerClienteActivo();
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
 
}
