package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import Componentes.tableC.*;
import java.text.ParseException;
import com.raven.datechooser.*;
import controlador.ControladorAbono;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import independientes.Constante;
import independientes.Mensaje;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import modelo.Abono;
import modelo.Cliente;
import modelo.Evento;
import net.miginfocom.swing.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlAbono extends JPanel {

    private DefaultTableModel m;
    private ControladorCliente controladorCliente = new ControladorCliente();
    private ControladorAbono controladorAbono = new ControladorAbono();
    private ControladorEvento controladorEvento = new ControladorEvento();

    private Evento eventoActual = null;

    public pnlAbono() {
        initComponents();
        m = (DefaultTableModel) tbl.getModel();

        txtImporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        p.init(new String[]{"idCliente", "Nombre", "Apellido", "Email", "Telefono", "Telefono 2"}, 1, false);
        p.btnModificar.setVisible(false);
        p.btnEliminar.setVisible(false);
        tbl.removeColumn(tbl.getColumnModel().getColumn(0));
        tbl.removeColumn(tbl.getColumnModel().getColumn(0));
        tbl.removeColumn(tbl.getColumnModel().getColumn(0));
        cargarClientes();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarClientes();
            }
        });
        
        addComponentListener(new ComponentAdapter() {
            @Override
            protected void finalize() throws Throwable {
                super.finalize(); //To change body of generated methods, choose Tools | Templates.
                Constante.clienteTemporal = null;
                if (controladorCliente.obtenerClienteActivo() != null) {
                    Principal.getInstancia().lblCliente.setText("Cliente Activo: " + controladorCliente.obtenerClienteActivo().getCorreo());
                }

            }

        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();

                Cliente temp = controladorCliente.obtenerByID((int) p.tblModel.getValueAt(x, 0));
                Constante.clienteTemporal = temp;
                Principal.getInstancia().lblCliente.setText("Cliente Activo (SOLO ADMIN) : " + temp.getCorreo() + " - " + temp.getNombre());
                cmbEvento.removeAllItems();
                for (Evento ev : controladorEvento.obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
                    cmbEvento.addItem(ev.getNombreEvento());
                }

            }
        });
    }

    private void cargarTablaAbono() {

        ArrayList<Abono> temp = controladorAbono.obtenerListaByIdEvento(eventoActual.getIdEvento());
        if (temp == null) {
            return;
        }
        m.setRowCount(0);
        for (Abono ab : temp) {
            m.addRow(new Object[]{ab.getIdAbono(), ab.getIdCliente(), ab.getIdEvento(), ab.getImporte(), ab.getCantidadADeber(), ab.getFecha()});
        }
    }

    private void cargarClientes() {
        p.tblModel.setRowCount(0);
        for (Cliente c : controladorCliente.obtenerClientes(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getApellido(), c.getCorreo(), c.getTelefono(), c.getTelefono2()});
        }
    }

    private void txtAbonoKeyReleased(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar())) {
            txtImporte.setText("");
            return;
        }

        try {
            txtImporte.commitEdit();
            txtImporte.setValue(txtImporte.getValue());
            txtImporte.setCaretPosition(txtImporte.getText().length());
        } catch (ParseException ee) {
        }
    }

    private void btnRegistrar(ActionEvent e) {
        if (Constante.clienteTemporal == null) {
            Constante.mensaje("Sin cliente activo(ADMIN)", Message.Tipo.ERROR);
            return;
        }
        if (eventoActual == null) {
            Constante.mensaje("Selecciona un evento", Message.Tipo.ERROR);
            return;
        }
        int x = p.tblBuscar.getSelectedRow();
        Abono abono = new Abono();
        abono.setIdCliente((int) valorTabla(x, 0));
        abono.setIdEvento(eventoActual.getIdEvento());
        abono.setImporte(Integer.parseInt(txtImporte.getText().replaceAll(",", "")));
        abono.setCantidadADeber(eventoActual.getPresupuesto() - abono.getImporte());
        abono.setFecha(getFecha());
        Mensaje m = controladorAbono.registrar(abono);
        if (m.getTipoMensaje() == Tipo.OK) {
            cargarTablaAbono();

        }
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

    }

    public Date getFecha() {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, dateChooser.getSelectedDate().getYear());
        c1.set(Calendar.MONTH, dateChooser.getSelectedDate().getMonth() - 1);
        c1.set(Calendar.DAY_OF_MONTH, dateChooser.getSelectedDate().getDay());

        return c1.getTime();
    }

    private Object valorTabla(int fila, int columna) {
        return p.tblModel.getValueAt(fila, columna);
    }

    private void cmbEventoItemStateChanged(ItemEvent e) {
        if (cmbEvento.getSelectedIndex() == -1) {
            return;
        }
        for (Evento ev : controladorEvento.obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
            if (ev.getNombreEvento().equals(cmbEvento.getSelectedItem().toString())) {
                eventoActual = ev;
                lblTotal.setText("Total : " + eventoActual.getPresupuesto());
                cargarTablaAbono();
                break;
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        lblTotal = new JLabel();
        cmbEvento = new JComboBox();
        txtImporte = new JFormattedTextField();
        txtFecha = new JTextField();
        btnRegistrar = new Button();
        scrollPane1 = new JScrollPane();
        tbl = new Table();
        p = new pnlCRUD();
        dateChooser = new DateChooser();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]"));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill, insets 0",
                // columns
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- lblTotal ----
            lblTotal.setText("1Total:");
            lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
            lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
            panel1.add(lblTotal, "cell 0 0");

            //---- cmbEvento ----
            cmbEvento.addItemListener(e -> cmbEventoItemStateChanged(e));
            panel1.add(cmbEvento, "cell 0 1");

            //---- txtImporte ----
            txtImporte.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtImporte.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtAbonoKeyReleased(e);
                }
            });
            panel1.add(txtImporte, "cell 0 2");

            //---- txtFecha ----
            txtFecha.setHorizontalAlignment(SwingConstants.CENTER);
            txtFecha.setFont(new Font("Segoe UI", Font.BOLD, 18));
            panel1.add(txtFecha, "cell 0 3");

            //---- btnRegistrar ----
            btnRegistrar.setText("Registrar");
            btnRegistrar.addActionListener(e -> btnRegistrar(e));
            panel1.add(btnRegistrar, "cell 0 4,grow");
        }
        add(panel1, "cell 0 0, grow");

        //======== scrollPane1 ========
        {

            //---- tbl ----
            tbl.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "idAbono", "idCliente", "idEvento", "Abono", "Total a deber", "Fecha"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            scrollPane1.setViewportView(tbl);
        }
        add(scrollPane1, "cell 1 0,");
        add(p, "cell 0 1, spanx");

        //---- dateChooser ----
        dateChooser.setTextRefernce(txtFecha);
        dateChooser.setDateFormat("yyyy-MM-dd");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel lblTotal;
    private JComboBox cmbEvento;
    private JFormattedTextField txtImporte;
    private JTextField txtFecha;
    private Button btnRegistrar;
    private JScrollPane scrollPane1;
    private Table tbl;
    private pnlCRUD p;
    private DateChooser dateChooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
