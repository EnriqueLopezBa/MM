package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message.Tipo;
import Componentes.TabbedPane.*;
import Componentes.tableC.*;
import com.raven.datechooserV2.*;
import controlador.ControladorAbono;
import controlador.ControladorCliente;
import controlador.ControladorCotizacion;
import controlador.ControladorEvento;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorEvento;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.scene.control.ComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import modelo.Abono;
import modelo.Cliente;
import modelo.Cotizacion;
import modelo.Evento;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.ProveedorEvento;
import net.miginfocom.swing.*;

/**
 * @author das
 */
public class pnlAbono extends JPanel {

    private DefaultTableModel mClientes;
    private DefaultTableModel mProveedores;
    private Evento eventoActual = null;
    private Proveedor proveedorActual = null;

    private static pnlAbono instancia;

    public static pnlAbono getInstancia() {
        if (instancia == null) {
            instancia = new pnlAbono();
        }
        return instancia;
    }

    private pnlAbono() {
        initComponents();
        mClientes = (DefaultTableModel) tblCliente.getModel();
        mProveedores = (DefaultTableModel) tblProveedores.getModel();
        txtImporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        for (int i = 0; i < 3; i++) {
            tblCliente.removeColumn(tblCliente.getColumnModel().getColumn(0));
            tblProveedores.removeColumn(tblProveedores.getColumnModel().getColumn(0));
        }
        p.init(new String[]{"idCliente", "Nombre", "Apellido", "Email", "Telefono", "Telefono 2"}, 1, false);
        p2.init(new String[]{"idProveedor", "Nombre", "Empresa", "Telefono", "Telefono 2"}, 1, false);
        p.btnEliminar.setVisible(false);
        p.btnModificar.setVisible(false);
        p2.btnEliminar.setVisible(false);
        p2.btnModificar.setVisible(false);
        ((JLabel) cmbEvento.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) cmbEventoP.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        cargarEventos();
        cargarClientes();
        cargarProveedores();
        tblCliente.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (eventoActual == null) {
                    return;
                }
                actualizarTotalADeber();
            }
        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Constante.setClienteTemporal(ControladorCliente.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0)));

                mClientes.setRowCount(0);
                lblCantADeber.setText("Cant. A Deber:");
                lblTotal.setText("Total: ");
                cargarEventos();

            }

        });
        //PROVEERDOR
        p2.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Constante.filaSeleccionada(p2.tblBuscar)) {
                    return;
                }
                int x = p2.tblBuscar.getSelectedRow();
                Proveedor temp = ControladorProveedor.getInstancia().obtenerByID((int) p2.tblModel.getValueAt(x, 0));
                ArrayList<ProveedorEvento> eventos = ControladorProveedorEvento.getInstancia().obtenerListaByIdProveedor(temp.getIdProveedor());
                for (ProveedorEvento ev : eventos) {
                    Evento evento = ControladorEvento.getInstancia().obtenerByID(ev.getIdEvento());
                    cmbEventoP.removeAllItems();
                    mProveedores.setRowCount(0);
                    cmbEventoP.addItem(evento.getNombreEvento());
                }

            }
        });
        p2.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                cargarProveedores();
            }

        });
    }

    public void cargarEventos() {
        if (Constante.getClienteActivo() == null) {
            return;
        }

        if (cbSoloAdeudo.isSelected()) { //Solo eventos en donde se deba 
            if (materialTabbed1.getSelectedIndex() == 0) { //Panel cliente ACTIVO
                cmbEvento.removeAllItems();
                for (Integer idEvento : ControladorAbono.getInstancia().obtenerEventosConAdeudo(Constante.getClienteActivo().getIdCliente())) {
                    Evento ev = ControladorEvento.getInstancia().obtenerByID(idEvento);
                    cmbEvento.addItem(ev);
                }
                cmbEvento.setRenderer(new MyObjectListCellRenderer());

            }
        } else {
            if (materialTabbed1.getSelectedIndex() == 0) {
                cmbEvento.removeAllItems();
                for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
                    cmbEvento.addItem(ev);
                }
                cmbEvento.setRenderer(new MyObjectListCellRenderer());
            }
        }
    }

    private void cmbEventoItemStateChanged(ItemEvent e) {
        if (cmbEvento.getSelectedIndex() == -1) {
            return;
        }
        eventoActual = (Evento) cmbEvento.getSelectedItem();
        Object asd = ControladorCotizacion.getInstancia().obtenerTotalCotizacionByIDEventoAndisCotFinal(eventoActual.getIdEvento());
        if (asd == null) {
            lbl.setVisible(true);
            lblCantADeber.setText("Cant. A Deber:");
            return;
        }else{
            DecimalFormat decimal = new DecimalFormat("0");
            lblTotal.setText("Total: "+decimal.format(asd));
            actualizarTotalADeber();
            lbl.setVisible(false);
        }
            
        cargarTablaAbono();
        

    }

    private void actualizarTotalADeber() {
        lblCantADeber.setText("Cant. A Deber: $" + ControladorAbono.getInstancia().obtenerCantidadADeber(Constante.getClienteActivo().getIdCliente(), eventoActual.getIdEvento()));
//        lblCantADeber.setText("Cant. A Deber: " + ControladorAbono.getInstancia().obtenerCantidadADeber(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente(), eventoActual.getIdEvento()));
    }

    private void cargarTablaAbono() {
        if (materialTabbed1.getSelectedIndex() == 0) {
            mClientes.setRowCount(0);
            for (Abono ab : ControladorAbono.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())) {
                mClientes.addRow(new Object[]{ab.getIdAbono(), ab.getIdCliente(), ab.getIdEvento(),
                    ab.getImporte(), ab.getFecha(),});
            }
        }

    }

    private void cargarClientes() {
        p.tblModel.setRowCount(0);
        for (Cliente c : ControladorCliente.getInstancia().obtenerClientes(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getApellido(), c.getCorreo(), c.getTelefono(), c.getTelefono2()});
        }
    }

    private void cargarProveedores() {
        p2.tblModel.setRowCount(0);
        for (Proveedor c : ControladorProveedor.getInstancia().obtenerListaByCadena(p2.txtBusqueda.getText())) {
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(c.getIdProveedor());
            if (negocio == null) {
                continue;
            }
            p2.tblModel.addRow(new Object[]{c.getIdProveedor(), c.getNombre(), negocio.getNombreNegocio(), c.getTelefono(), c.getTelefono2()});
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

    private void validaDatos(JComboBox c, JFormattedTextField format) throws MMException {
        if (c.getSelectedIndex() == -1) {
            c.requestFocus();
            throw new MMException("Selecciona un evento");
        }
        if (format.getText().isEmpty()) {
            format.requestFocus();
            throw new MMException("Abono vacio");
        }
        if (ControladorCliente.getInstancia().obtenerClienteActivo() == null) {
            throw new MMException("Sin cliente activo/seleccionado");
        }
        Cliente clienteTemp = ControladorCliente.getInstancia().obtenerClienteActivo();
        int adeber = ControladorAbono.getInstancia().obtenerCantidadADeber(clienteTemp.getIdCliente(), eventoActual.getIdEvento()) - Integer.parseInt(txtImporte.getText().replaceAll(",", ""));
        if (adeber < 0) {
            txtImporte.requestFocus();
            throw new MMException("El importe excede la deuda");
        }

    }

    private void btnRegistrar(ActionEvent e) {
        try {
            validaDatos(cmbEvento, txtImporte);
            Abono abono = new Abono();
            abono.setIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente());
            abono.setIdEvento(eventoActual.getIdEvento());
            abono.setImporte(Integer.parseInt(txtImporte.getText().replaceAll(",", "")));
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DATE, dateChooser1.getSelectedDate().getDay());
            c.set(Calendar.MONDAY, dateChooser1.getSelectedDate().getMonth());
            c.set(Calendar.YEAR, dateChooser1.getSelectedDate().getYear());
            java.util.Date utilDate = c.getTime();
            abono.setFecha(utilDate);
            Mensaje m = ControladorAbono.getInstancia().registrar(abono);
            if (m.getTipoMensaje() == Tipo.OK) {
                mClientes.setRowCount(0);
                cargarTablaAbono();

            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        } catch (MMException ex) {
            Constante.mensaje(ex.getMessage(), Tipo.ERROR);
        }
    }

    private void btnEliminarP(ActionEvent e) {
        if (!Constante.filaSeleccionada(tblCliente)) {
            return;
        }
        int x = tblCliente.getSelectedRow();
        if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este abono?") != 0) {
            return;
        }
        Abono abono = new Abono();
        abono.setIdAbono((int) mClientes.getValueAt(x, 0));
        Mensaje m = ControladorAbono.getInstancia().eliminar(abono);
        if (m.getTipoMensaje() == Tipo.OK) {
            mClientes.setRowCount(0);
            cargarTablaAbono();
        }
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

    }

    private void btnEliminar(ActionEvent e) {
        if (!Constante.filaSeleccionada(tblCliente)) {
            return;
        }
        int x = tblCliente.getSelectedRow();
        if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este abono?") != 0) {
            return;
        }
        Abono abono = new Abono();
        abono.setIdAbono((int) mClientes.getValueAt(x, 0));
        Mensaje m = ControladorAbono.getInstancia().eliminar(abono);
        if (m.getTipoMensaje() == Tipo.OK) {
            mClientes.setRowCount(0);
            cargarTablaAbono();

        }
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
    }

    private void cbSoloAdeudo(ActionEvent e) {
        cargarEventos();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        materialTabbed1 = new MaterialTabbed();
        pnlAbonoCliente = new JPanel();
        panel3 = new JPanel();
        lblTotal = new JLabel();
        lblCantADeber = new JLabel();
        cbSoloAdeudo = new JCheckBox();
        cmbEvento = new JComboBox();
        lbl = new JLabel();
        label2 = new JLabel();
        txtImporte = new JFormattedTextField();
        txtFecha = new JTextField();
        btnRegistrar = new Button();
        scrollPane1 = new JScrollPane();
        tblCliente = new Table();
        p = new pnlCRUD();
        pnlAbonoProveedores = new JPanel();
        panel4 = new JPanel();
        lblTotalP = new JLabel();
        lblCantADeberP = new JLabel();
        checkBox1 = new JCheckBox();
        cmbEventoP = new JComboBox();
        label1 = new JLabel();
        txtImporteP = new JFormattedTextField();
        txtFechaP = new JTextField();
        btnRegistrarP = new Button();
        scrollPane2 = new JScrollPane();
        tblProveedores = new Table();
        p2 = new pnlCRUD();
        popupMenu1 = new JPopupMenu();
        btnEliminar = new JMenuItem();
        popupMenu2 = new JPopupMenu();
        btnEliminar2 = new JMenuItem();
        dateChooser1 = new DateChooserV2();
        dateChooserV21 = new DateChooserV2();

        //======== this ========
        setBackground(Color.white);
        setLayout(new BorderLayout());

        //======== materialTabbed1 ========
        {
            materialTabbed1.setBackground(Color.white);

            //======== pnlAbonoCliente ========
            {
                pnlAbonoCliente.setBackground(Color.white);
                pnlAbonoCliente.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "[grow,fill]" +
                    "[grow,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));

                //======== panel3 ========
                {
                    panel3.setBackground(Color.white);
                    panel3.setLayout(new MigLayout(
                        "fill, insets 0",
                        // columns
                        "[fill]",
                        // rows
                        "[]" +
                        "[]" +
                        "[grow 0,center]" +
                        "[]0" +
                        "[]" +
                        "[]" +
                        "[]"));

                    //---- lblTotal ----
                    lblTotal.setText("Total:");
                    lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
                    lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    panel3.add(lblTotal, "cell 0 0");

                    //---- lblCantADeber ----
                    lblCantADeber.setText("Cant. A Deber: ");
                    lblCantADeber.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    panel3.add(lblCantADeber, "cell 0 0");

                    //---- cbSoloAdeudo ----
                    cbSoloAdeudo.setText("Solo con adeudo");
                    cbSoloAdeudo.setSelected(true);
                    cbSoloAdeudo.addActionListener(e -> cbSoloAdeudo(e));
                    panel3.add(cbSoloAdeudo, "cell 0 1, grow 0 00");

                    //---- cmbEvento ----
                    cmbEvento.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    cmbEvento.addItemListener(e -> cmbEventoItemStateChanged(e));
                    panel3.add(cmbEvento, "cell 0 1, growy, hmax 10%");

                    //---- lbl ----
                    lbl.setText("<html><p style=\"text-align:center\"> (Primero selecciona la Cotizacion final)</p></html>");
                    lbl.setForeground(Color.red);
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    lbl.setVisible(false);
                    panel3.add(lbl, "cell 0 2");

                    //---- label2 ----
                    label2.setText("Cantidad");
                    label2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    panel3.add(label2, "cell 0 3,aligny bottom,growy 0");

                    //---- txtImporte ----
                    txtImporte.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    txtImporte.setHorizontalAlignment(SwingConstants.CENTER);
                    txtImporte.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtAbonoKeyReleased(e);
                        }
                    });
                    panel3.add(txtImporte, "cell 0 4,alignx center,wmax 50%");

                    //---- txtFecha ----
                    txtFecha.setHorizontalAlignment(SwingConstants.CENTER);
                    txtFecha.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    panel3.add(txtFecha, "cell 0 5");

                    //---- btnRegistrar ----
                    btnRegistrar.setText("Registrar");
                    btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    btnRegistrar.addActionListener(e -> btnRegistrar(e));
                    panel3.add(btnRegistrar, "cell 0 6,grow");
                }
                pnlAbonoCliente.add(panel3, "cell 0 0, grow");

                //======== scrollPane1 ========
                {

                    //---- tblCliente ----
                    tblCliente.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "idAbono", "idCliente", "idEvento", "Abono", "Fecha"
                        }
                    ) {
                        boolean[] columnEditable = new boolean[] {
                            false, false, false, false, false
                        };
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    tblCliente.setComponentPopupMenu(popupMenu1);
                    tblCliente.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    scrollPane1.setViewportView(tblCliente);
                }
                pnlAbonoCliente.add(scrollPane1, "cell 1 0,");
                pnlAbonoCliente.add(p, "cell 0 1, spanx");
            }
            materialTabbed1.addTab("Clientes", pnlAbonoCliente);

            //======== pnlAbonoProveedores ========
            {
                pnlAbonoProveedores.setBackground(Color.white);
                pnlAbonoProveedores.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "[grow,fill]" +
                    "[grow,fill]",
                    // rows
                    "[]" +
                    "[]"));

                //======== panel4 ========
                {
                    panel4.setBackground(Color.white);
                    panel4.setLayout(new MigLayout(
                        "fill, insets 0",
                        // columns
                        "[fill]",
                        // rows
                        "[]" +
                        "[]" +
                        "[]0" +
                        "[]" +
                        "[]" +
                        "[]"));

                    //---- lblTotalP ----
                    lblTotalP.setText("Total:");
                    lblTotalP.setHorizontalAlignment(SwingConstants.CENTER);
                    lblTotalP.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    panel4.add(lblTotalP, "cell 0 0,growy, hmax 10%");

                    //---- lblCantADeberP ----
                    lblCantADeberP.setText("Cant. A Deber:");
                    lblCantADeberP.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    panel4.add(lblCantADeberP, "cell 0 0");

                    //---- checkBox1 ----
                    checkBox1.setText("Solo con adeudo");
                    panel4.add(checkBox1, "cell 0 1, grow 0 00");

                    //---- cmbEventoP ----
                    cmbEventoP.addItemListener(e -> cmbEventoItemStateChanged(e));
                    panel4.add(cmbEventoP, "cell 0 1, growy, hmax 10%");

                    //---- label1 ----
                    label1.setText("Cantidad");
                    label1.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    panel4.add(label1, "cell 0 2");

                    //---- txtImporteP ----
                    txtImporteP.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    txtImporteP.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtAbonoKeyReleased(e);
                        }
                    });
                    panel4.add(txtImporteP, "cell 0 3");

                    //---- txtFechaP ----
                    txtFechaP.setHorizontalAlignment(SwingConstants.CENTER);
                    txtFechaP.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    panel4.add(txtFechaP, "cell 0 4");

                    //---- btnRegistrarP ----
                    btnRegistrarP.setText("Registrar");
                    btnRegistrarP.addActionListener(e -> btnRegistrar(e));
                    panel4.add(btnRegistrarP, "cell 0 5,grow");
                }
                pnlAbonoProveedores.add(panel4, "cell 0 0, grow");

                //======== scrollPane2 ========
                {

                    //---- tblProveedores ----
                    tblProveedores.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "idAbono", "idCliente", "idEvento", "Abono", "Fecha"
                        }
                    ) {
                        boolean[] columnEditable = new boolean[] {
                            false, false, false, false, false
                        };
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    scrollPane2.setViewportView(tblProveedores);
                }
                pnlAbonoProveedores.add(scrollPane2, "cell 1 0,");
                pnlAbonoProveedores.add(p2, "cell 0 1, spanx");
            }
            materialTabbed1.addTab("Proveedores", pnlAbonoProveedores);
        }
        add(materialTabbed1, BorderLayout.CENTER);

        //======== popupMenu1 ========
        {

            //---- btnEliminar ----
            btnEliminar.setText("Eliminar");
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminar.addActionListener(e -> btnEliminar(e));
            popupMenu1.add(btnEliminar);
        }

        //======== popupMenu2 ========
        {

            //---- btnEliminar2 ----
            btnEliminar2.setText("Eliminar");
            btnEliminar2.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminar2.addActionListener(e -> btnEliminarP(e));
            popupMenu2.add(btnEliminar2);
        }

        //---- dateChooser1 ----
        dateChooser1.setTextRefernce(txtFecha);

        //---- dateChooserV21 ----
        dateChooserV21.setTextRefernce(txtFechaP);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private MaterialTabbed materialTabbed1;
    private JPanel pnlAbonoCliente;
    private JPanel panel3;
    private JLabel lblTotal;
    private JLabel lblCantADeber;
    private JCheckBox cbSoloAdeudo;
    private JComboBox cmbEvento;
    private JLabel lbl;
    private JLabel label2;
    private JFormattedTextField txtImporte;
    private JTextField txtFecha;
    private Button btnRegistrar;
    private JScrollPane scrollPane1;
    private Table tblCliente;
    private pnlCRUD p;
    private JPanel pnlAbonoProveedores;
    private JPanel panel4;
    private JLabel lblTotalP;
    private JLabel lblCantADeberP;
    private JCheckBox checkBox1;
    private JComboBox cmbEventoP;
    private JLabel label1;
    private JFormattedTextField txtImporteP;
    private JTextField txtFechaP;
    private Button btnRegistrarP;
    private JScrollPane scrollPane2;
    private Table tblProveedores;
    private pnlCRUD p2;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminar2;
    private DateChooserV2 dateChooser1;
    private DateChooserV2 dateChooserV21;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
