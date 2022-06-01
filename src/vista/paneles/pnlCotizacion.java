/*
 * Created by JFormDesigner on Sun May 29 22:51:41 MDT 2022
 */
package vista.paneles;

import java.awt.event.*;
import Componentes.Sweet_Alert.Message;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.tableC.*;
import controlador.ControladorCotizacion;
import controlador.ControladorEvento;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorEvento;
import independientes.Constante;
import modelo.Cotizacion;
import modelo.Evento;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.ProveedorEvento;
import net.miginfocom.swing.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlCotizacion extends JPanel {

    private static pnlCotizacion instancia;
    private DefaultTableModel m;
    private Evento eventoActual = null;

    public static pnlCotizacion getInstancia() {
        if (instancia == null) {
            instancia = new pnlCotizacion();
        }
        return instancia;
    }

    private pnlCotizacion() {
        initComponents();
        m = (DefaultTableModel) tblCotizacion.getModel();
        tblCotizacion.removeColumn(tblCotizacion.getColumnModel().getColumn(0));
        tblCotizacion.removeColumn(tblCotizacion.getColumnModel().getColumn(0));
    }

    public void cargarEventos() {
        if (Constante.getClienteActivo() == null) {
            Constante.mensaje("Selecciona un cliente", Message.Tipo.ADVERTENCIA);
            Principal.getInstancia().btnCliente.doClick();
            return;
        }
        cmbEvento.removeAllItems();
        for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
            cmbEvento.addItem(ev.getNombreEvento());
        }
    }

    private void cmbEventoItemStateChanged(ItemEvent e) {
        if (Constante.getClienteActivo() == null) {
            Constante.mensaje("Selecciona un cliente", Message.Tipo.ADVERTENCIA);
            Principal.getInstancia().btnCliente.doClick();
            return;
        }
        if (cmbEvento.getSelectedIndex() == -1) {
            return;
        }
        for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
            if (ev.getNombreEvento().equals(cmbEvento.getSelectedItem())) {
                eventoActual = ev;
                break;
            }
        }
        llenarTabla();
    }

    private void llenarTabla() {
        m.setRowCount(0);
        for (Cotizacion cot : ControladorCotizacion.getInstancia().obtenerListaByIDEvento(eventoActual.getIdEvento())) {
            ProveedorEvento pro = ControladorProveedorEvento.getInstancia().obtenerByIdEventoAndIdProveedor(cot.getIdEvento(), cot.getIdProveedor());
            Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(cot.getIdProveedor());
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(proveedor.getIdProveedor());
            m.addRow(new Object[]{cot.getIdEvento(), cot.getIdProveedor(), negocio.getNombreNegocio(), pro.getFechaInicio(), pro.getFechaFinal(), cot.getCotizacion()});
        }
        for (ProveedorEvento pro : ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())) {
            if (yaExiste(pro.getIdProveedor())) {
                continue;
            }
            Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(pro.getIdProveedor());
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(proveedor.getIdProveedor());
            m.addRow(new Object[]{pro.getIdEvento(), pro.getIdProveedor(), negocio.getNombreNegocio(), pro.getFechaInicio(), pro.getFechaFinal(), null});
        }
    }

    private boolean yaExiste(int idProveedor) {
        for (int i = 0; i < m.getRowCount(); i++) {
            if ((int) m.getValueAt(i, 1) == idProveedor) {
                return true;
            }
        }
        return false;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbEvento = new JComboBox();
        scrollPane1 = new JScrollPane();
        tblCotizacion = new Table();
        scrollPane2 = new JScrollPane();
        txtDescripcion = new JTextArea();

        //======== this ========
        setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[fill]" +
            "[fill]"));

        //---- cmbEvento ----
        cmbEvento.addItemListener(e -> cmbEventoItemStateChanged(e));
        add(cmbEvento, "cell 0 0, grow 0 0");

        //======== scrollPane1 ========
        {

            //---- tblCotizacion ----
            tblCotizacion.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null, null, null, null, null},
                },
                new String[] {
                    "idEvento", "idProveedor", "Proveedor", "Inicio", "Fin", "Cotizacion del sistema", "Cotizacion Final"
                }
            ) {
                Class<?>[] columnTypes = new Class<?>[] {
                    Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Integer.class
                };
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false, true
                };
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            scrollPane1.setViewportView(tblCotizacion);
        }
        add(scrollPane1, "cell 0 1");

        //======== scrollPane2 ========
        {

            //---- txtDescripcion ----
            txtDescripcion.setWrapStyleWord(true);
            txtDescripcion.setEditable(false);
            scrollPane2.setViewportView(txtDescripcion);
        }
        add(scrollPane2, "cell 0 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JComboBox cmbEvento;
    private JScrollPane scrollPane1;
    private Table tblCotizacion;
    private JScrollPane scrollPane2;
    private JTextArea txtDescripcion;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
