package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import Componentes.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.tableC.*;
import com.raven.datechooserV2.*;
import com.raven.event.EventTimePicker;
import com.raven.swing.*;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorArea;
import controlador.ControladorProveedorEvento;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import independientes.image_slider.*;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Estado;
import modelo.Evento;
import modelo.Lugar;
import modelo.Proveedor;
import modelo.ProveedorArea;
import modelo.ProveedorEvento;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogProveedor;
import vista.paneles.edit.DialogTipoProveedor;
import vista.principales.Principal;

public class pnlProveedores extends JPanel {

    private TipoProveedor tipoProveedorActual;
    private Proveedor proveedorActual;
    private Evento eventoActual;

    public DefaultTableModel m;

    private SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
    private SimpleDateFormat soloFecha = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat soloHora = new SimpleDateFormat("hh:mm aa");

    private static pnlProveedores instancia;

    public static pnlProveedores getInstancia() {
        if (instancia == null) {
            instancia = new pnlProveedores();
        }
        return instancia;
    }

    public void checkAdmin() {
        lblEditProveedor.setVisible(Constante.getAdmin());
        lblEditTipoProveedor.setVisible(Constante.getAdmin());

    }

    private void init() {
        m = (DefaultTableModel) tblProveedor.getModel();
        m.addTableModelListener(new TableModelListener() { // PARA RECALCULAR EL PRESUPUESTO
            @Override
            public void tableChanged(TableModelEvent e) {
                Constante.actualizarPresupuesto();
            }
        });
        cargarNombreEvento();
        cargarTipoProveedor();
        cargarProveedores();
        try {
            tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
            tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
//            tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(4));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("xd");
        }

    }

    public void recargar() {
        cargarNombreEvento();
        cargarTipoProveedor();
        cargarProveedores();
    }

    private pnlProveedores() {
        initComponents();
        init();
        timePicker2.addEventTimePicker(new EventTimePicker() {
            @Override
            public void timeSelected(String string) {
                restar(null);
            }
        });

    }

    private int restar(ProveedorEvento provEve) {
        if (provEve != null) {
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            LocalDateTime fechaYHoraLocal1 = LocalDateTime.parse(provEve.getHoraInicio().toString().substring(0, provEve.getHoraInicio().toString().length() - 5), formateador);
            LocalDateTime fechaYHoraLocal2 = LocalDateTime.parse(provEve.getHoraFinal().toString().substring(0, provEve.getHoraFinal().toString().length() - 5), formateador);
            long minutos = ChronoUnit.MINUTES.between(fechaYHoraLocal1, fechaYHoraLocal2);
            Proveedor pro = ControladorProveedor.getInstancia().obtenerByID(provEve.getIdProveedor());
            int precio = new Double(((double) pro.getPrecioAprox() / 60) * minutos).intValue();
            return precio;
        }
        if (eventoActual == null || proveedorActual == null) {
            return -1;
        }
        if (eventoActual.getFechaInicio() == null) {
            Constante.mensaje("Termina de registrar este evento", Message.Tipo.ADVERTENCIA);
            return -1;
        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String fech = "";
        String fech2 = "";
        if (!form.format(eventoActual.getFechaInicio()).equals(form.format(eventoActual.getFechaFinal()))) {
            fech = rbFechaIni.isSelected() ? rbFechaIni.getText() : rbFechaFin.getText();
            fech2 = rbFechaIni1.isSelected() ? rbFechaIni1.getText() : rbFechaFin1.getText();
        } else {
            fech = form.format(eventoActual.getFechaInicio());
            fech2 = form.format(eventoActual.getFechaInicio());
        }
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm a");
        LocalDateTime fechaYHoraLocal1 = LocalDateTime.parse(fech + " " + timePicker1.getSelectedTime(), formateador);
        LocalDateTime fechaYHoraLocal2 = LocalDateTime.parse(fech2 + " " + timePicker2.getSelectedTime(), formateador);
        long minutos = ChronoUnit.MINUTES.between(fechaYHoraLocal1, fechaYHoraLocal2);
        if (minutos < 0) {
            lblInfoProv.setText("Aprox x Hora: $" + proveedorActual.getPrecioAprox());
            return -1;
        }
        int precio = new Double(((double) proveedorActual.getPrecioAprox() / 60) * minutos).intValue();
        lblInfoProv.setText("Aprox x Hora: $" + proveedorActual.getPrecioAprox() + " -- Precio Calculado: $" + precio);
        return precio;
    }

    private void cargarTipoProveedor() {
        cmbTipoProveedor.removeAllItems();
        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            cmbTipoProveedor.addItem(tipo.getTipoProveedor());
        }
    }

    private void cargarProveedores() {
        if (eventoActual == null || ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()) == null) {
            return;
        }
        cmbProveedor.removeAllItems();
        //Cargar proveedores dependiendo del area donde trabajan los proveedores
        int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
        for (ProveedorArea prov : ControladorProveedorArea.getInstancia().obtenerListaByIdCiudad(idCiudad)) {
            for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
                if (!pro.isDisponible()) {
                    continue;
                }
                if (prov.getIdProveedor() == pro.getIdProveedor()) {
                    cmbProveedor.addItem(pro.getNombreEmpresa());
                }
            }
        }

    }

    private void cargarNombreEvento() {
        if (Constante.getClienteActivo() == null) {
            return;
        }
        cmbNombreEvento.removeAllItems();
        for (Evento e : ControladorEvento.getInstancia().obtenerEventoByIDCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente())) {
            cmbNombreEvento.addItem(e.getNombreEvento());
        }

    }

    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
        if (cmbTipoProveedor.getSelectedIndex() == -1 || eventoActual == null || ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()) == null) {
            return;
        }
        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            if (tipo.getTipoProveedor().equals(cmbTipoProveedor.getSelectedItem().toString())) {
                tipoProveedorActual = tipo;
                cargarProveedores();
                int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
                i.proveedorImagenesByCiudadAndTipoProveedor(idCiudad, tipo.getIdTipoProveedor());
            }
        }
    }

    private void cmbNombreEventoItemStateChanged(ItemEvent e) {
        if (cmbNombreEvento.getSelectedIndex() == -1 || Constante.getClienteActivo() == null) {
            return;
        }
        for (Evento eve : ControladorEvento.getInstancia().obtenerEventoByIDCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente())) {
            if (eve.getNombreEvento().equals(cmbNombreEvento.getSelectedItem().toString())) {
                eventoActual = eve;

                Lugar lugar = ControladorLugar.getInstancia().obtenerByID(eve.getIdLugar());
                if (lugar == null) {
                    continue;
                }
                Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(lugar.getIdCiudad());
                Estado estado = ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado());
                lblInfo.setText(estado.getEstado() + ", " + ciudad.getCiudad());
                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
                if (!form.format(eventoActual.getFechaInicio()).equals(form.format(eventoActual.getFechaFinal()))) {
                    lblFechaIni.setVisible(true);
                    lblFechaFin.setVisible(true);
                    rbFechaIni.setVisible(true);
                    rbFechaFin.setVisible(true);
                    rbFechaIni1.setVisible(true);
                    rbFechaFin1.setVisible(true);
                    rbFechaIni.setText(form.format(eventoActual.getFechaInicio()));
                    rbFechaFin.setText(form.format(eventoActual.getFechaFinal()));
                    rbFechaIni1.setText(form.format(eventoActual.getFechaInicio()));
                    rbFechaFin1.setText(form.format(eventoActual.getFechaFinal()));
                } else {
                    lblFechaIni.setVisible(false);
                    lblFechaFin.setVisible(false);
                    rbFechaIni.setVisible(false);
                    rbFechaFin.setVisible(false);
                    rbFechaIni1.setVisible(false);
                    rbFechaFin1.setVisible(false);
                }
            }
        }
        cargarProveedorEvento();

    }

    private void cargarProveedorEvento() {

        if (eventoActual == null || ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento()) == null) {
            return;
        }
        m.setRowCount(0);
        for (ProveedorEvento pro : ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())) {
            Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(pro.getIdProveedor());
            TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID(proveedor.getIdtipoProveedor());
            ProveedorEvento provE = ControladorProveedorEvento.getInstancia().obtenerByIdEventoAndIdProveedor(eventoActual.getIdEvento(), proveedor.getIdProveedor());
            String timeInicio = localDateFormat.format(provE.getHoraInicio());
            String timeFinal = localDateFormat.format(provE.getHoraFinal());
            m.addRow(new Object[]{tipo.getIdTipoProveedor(), proveedor.getIdProveedor(),
                tipo.getTipoProveedor(), proveedor.getNombreEmpresa(), timeInicio, timeFinal, restar(provE)});
        }
    }

    private void cmbProveedorItemStateChanged(ItemEvent e) {
        if (cmbProveedor.getSelectedIndex() == -1 || tipoProveedorActual == null) {
            return;
        }
        for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
            if (pro.getNombreEmpresa().equals(cmbProveedor.getSelectedItem().toString())) {
                proveedorActual = pro;
                lblInfoProv.setText("Precio Aprox (Hora): " + pro.getPrecioAprox());
            }
        }
    }

    private void lblEditTipoProveedorMouseClicked(MouseEvent e) {
        DialogTipoProveedor temp = new DialogTipoProveedor(Principal.getInstancia());
        temp.setVisible(true);
    }

    private void lblEditProveedorMouseClicked(MouseEvent e) {
        DialogProveedor temp = new DialogProveedor(Principal.getInstancia());
        temp.setVisible(true);
    }

    private String[] getFecha() {
        String[] ar = new String[2];
        if (rbFechaIni.isVisible()) {
            if (rbFechaIni.isSelected()) {
                ar[0] = rbFechaIni.getText();
            } else {
                ar[0] = rbFechaFin.getText();
            }
            if (rbFechaIni1.isSelected()) {
                ar[1] = rbFechaIni1.getText();
            } else {
                ar[1] = rbFechaFin1.getText();
            }
            ar[0] += " " + txtHoraEntrada.getText();
            ar[1] += " " + txtHoraSalida.getText();
            return ar;
        } else {

            ar[0] = soloFecha.format(eventoActual.getFechaInicio()) + " " + txtHoraEntrada.getText();
            ar[1] = soloFecha.format(eventoActual.getFechaInicio()) + " " + txtHoraSalida.getText();
            return ar;
        }

    }

    private void btnAgregarProveedor(ActionEvent e) {
        if (proveedorActual == null || txtHoraEntrada.getText().isEmpty() || txtHoraSalida.getText().isEmpty()) {
            return;
        }
        if (cbOtro.isSelected()) {
            for (int j = 0; j < m.getRowCount(); j++) {
                if (m.getValueAt(j, 3).toString().equals(cmbProveedor.getEditor().getItem().toString())) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                    return;
                }
            }
            m.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), null,
                tipoProveedorActual.getTipoProveedor(), cmbProveedor.getEditor().getItem().toString(), getFecha()[0], getFecha()[1], null});
        } else {
            int precio = restar(null);
            if (precio == -1) {
                Constante.mensaje("Introduzca una fecha/hora correcta", Message.Tipo.ADVERTENCIA);
                return;
            }
            try {
                Date dateInicio = localDateFormat.parse(getFecha()[0]);
                Date dateFinal = localDateFormat.parse(getFecha()[1]);
                if (dateInicio.before(eventoActual.getFechaInicio())) {
                    Constante.mensaje("Su evento inicia a las " + soloHora.format(eventoActual.getFechaInicio()) + "\nModifique la fecha de inicio del proveedor", Message.Tipo.ADVERTENCIA);
                    return;
                } else if (dateFinal.after(eventoActual.getFechaFinal())) {
                    Constante.mensaje("Su evento termina a las " + soloHora.format(eventoActual.getFechaFinal()) + "\nModifique la fecha final del proveedor", Message.Tipo.ADVERTENCIA);
                    return;
                }
            } catch (ParseException ex) {
                Logger.getLogger(pnlProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int j = 0; j < m.getRowCount(); j++) {
                if ((int) m.getValueAt(j, 1) == proveedorActual.getIdProveedor()) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                    return;
                }
            }
            m.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), proveedorActual.getIdProveedor(),
                tipoProveedorActual.getTipoProveedor(), proveedorActual.getNombreEmpresa(), getFecha()[0], getFecha()[1], restar(null)});
            Constante.actualizarPresupuesto();

        }
    }

    private void btnEliminar(ActionEvent e) {
        int x = tblProveedor.getSelectedRow();
        if (x == -1) {
            return;
        }
        m.removeRow(x);
    }

    private void cbOtro(ActionEvent e) {
        if (cbOtro.isSelected()) {
            cmbProveedor.setEditable(true);
        } else {
            cmbProveedor.setEditable(false);
        }

    }

    private void btnFinalizarProveedor(ActionEvent e) {
        if (eventoActual == null || m.getRowCount() == 0) {
            return;
        }
        ArrayList<ProveedorEvento> proE = new ArrayList<>();
        for (int j = 0; j < m.getRowCount(); j++) {
            try {
                // comprobrar que existen proveedores nuevo a registrar
                if (m.getValueAt(j, 1) == null) {
                    Proveedor pro = new Proveedor();
                    pro.setNombreEmpresa(m.getValueAt(j, 3).toString());
                    ControladorProveedor.getInstancia().registrar(pro);
                    m.setValueAt(ControladorProveedor.getInstancia().obtenerByLast().getIdProveedor(), j, 1);
                }
                proE.add(new ProveedorEvento(eventoActual.getIdEvento(), (int) m.getValueAt(j, 1), localDateFormat.parse(m.getValueAt(j, 4).toString()), localDateFormat.parse(m.getValueAt(j, 5).toString())));
                Mensaje m = ControladorProveedorEvento.getInstancia().registrarLote(proE);
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            } catch (ParseException ex) {
                Logger.getLogger(pnlProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void txtHoraEntradaMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHoraEntrada.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHoraEntrada.getLocationOnScreen().y - timePicker1.getHeight();
        timePicker1.showPopup(txtHoraEntrada, x, y);
    }

    private void txtHoraSalidaMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHoraSalida.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHoraSalida.getLocationOnScreen().y - timePicker2.getHeight();
        timePicker2.showPopup(txtHoraSalida, x, y);
    }

    private void rbFechaFin(ActionEvent e) {

    }

    private void rbFechaFinStateChanged(ChangeEvent e) {
        if (rbFechaFin.isSelected()) {
            rbFechaIni1.setEnabled(false);
            rbFechaFin1.setSelected(true);
        } else {
            rbFechaIni1.setEnabled(true);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblTitutlo = new JLabel();
        lblNombreEvento = new JLabel();
        cmbNombreEvento = new JComboBox();
        scrollPane1 = new JScrollPane();
        tblProveedor = new Table();
        i = new ImageSlider();
        svgIcon1 = new SvgIcon();
        lblInfo = new JLabel();
        lblTipoProveedor = new JLabel();
        cmbTipoProveedor = new JComboBox();
        lblEditTipoProveedor = new JLabel();
        lblProveedor = new JLabel();
        cmbProveedor = new JComboBox();
        cbOtro = new JCheckBox();
        lblEditProveedor = new JLabel();
        svgIcon2 = new SvgIcon();
        lblInfoProv = new JLabel();
        lblFechaIni = new JLabel();
        rbFechaIni = new JRadioButton();
        rbFechaFin = new JRadioButton();
        label3 = new JLabel();
        txtHoraEntrada = new JTextField();
        lblFechaFin = new JLabel();
        rbFechaIni1 = new JRadioButton();
        rbFechaFin1 = new JRadioButton();
        label4 = new JLabel();
        txtHoraSalida = new JTextField();
        btnAgregarProveedor = new Button();
        btnFinalizarProveedor = new Button();
        popupMenu1 = new JPopupMenu();
        btnEliminar = new JMenuItem();
        timePicker1 = new TimePicker();
        timePicker2 = new TimePicker();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]para" +
            "[grow,fill]" +
            "[grow,fill]" +
            "[fill]",
            // rows
            "[grow,fill]" +
            "[grow,fill]0" +
            "[]" +
            "[grow,fill]" +
            "[grow,fill]" +
            "[]" +
            "[]" +
            "[]" +
            "[grow,fill]" +
            "[grow,fill]unrel"));

        //---- lblTitutlo ----
        lblTitutlo.setText("Agregar Proveedores");
        lblTitutlo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitutlo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(lblTitutlo, "cell 0 0,spanx, al center");

        //---- lblNombreEvento ----
        lblNombreEvento.setText("Nombre Evento");
        add(lblNombreEvento, "cell 0 1");

        //---- cmbNombreEvento ----
        cmbNombreEvento.addItemListener(e -> cmbNombreEventoItemStateChanged(e));
        add(cmbNombreEvento, "cell 1 1,hmax 6%");

        //======== scrollPane1 ========
        {

            //---- tblProveedor ----
            tblProveedor.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "idTipoProveedor", "idProveedor", "Tipo", "Proveedor", "Inicio", "Fin", "Precio Calculado"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tblProveedor.setComponentPopupMenu(popupMenu1);
            tblProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(tblProveedor);
        }
        add(scrollPane1, "cell 2 1 1 9,grow");

        //---- i ----
        i.setBackground(Color.white);
        add(i, "cell 3 1 1 9,grow 50 100");

        //---- svgIcon1 ----
        svgIcon1.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\info.svg"));
        add(svgIcon1, "cell 0 2,alignx right,growx 0");

        //---- lblInfo ----
        lblInfo.setHorizontalAlignment(SwingConstants.LEFT);
        lblInfo.setFont(lblInfo.getFont().deriveFont(lblInfo.getFont().getStyle() | Font.BOLD));
        add(lblInfo, "cell 1 2");

        //---- lblTipoProveedor ----
        lblTipoProveedor.setText("Tipo Proveeedor");
        add(lblTipoProveedor, "cell 0 3");

        //---- cmbTipoProveedor ----
        cmbTipoProveedor.addItemListener(e -> cmbTipoProveedorItemStateChanged(e));
        add(cmbTipoProveedor, "split 2,cell 1 3,hmax 6%");

        //---- lblEditTipoProveedor ----
        lblEditTipoProveedor.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditTipoProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditTipoProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditTipoProveedorMouseClicked(e);
            }
        });
        add(lblEditTipoProveedor, "cell 1 3,grow 0 0");

        //---- lblProveedor ----
        lblProveedor.setText("Proveedor");
        add(lblProveedor, "cell 0 4");

        //---- cmbProveedor ----
        cmbProveedor.addItemListener(e -> cmbProveedorItemStateChanged(e));
        add(cmbProveedor, "split 2,cell 1 4,hmax 6%");

        //---- cbOtro ----
        cbOtro.setText("Otro");
        cbOtro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cbOtro.addActionListener(e -> cbOtro(e));
        add(cbOtro, "split 2,cell 1 4,grow 0 0");

        //---- lblEditProveedor ----
        lblEditProveedor.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditProveedorMouseClicked(e);
            }
        });
        add(lblEditProveedor, "cell 1 4,grow 0 0");

        //---- svgIcon2 ----
        svgIcon2.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\info.svg"));
        add(svgIcon2, "cell 0 7,alignx right,growx 0");

        //---- lblInfoProv ----
        lblInfoProv.setFont(lblInfoProv.getFont().deriveFont(lblInfoProv.getFont().getStyle() | Font.BOLD));
        add(lblInfoProv, "cell 1 7");

        //---- lblFechaIni ----
        lblFechaIni.setText("Fecha Inicio:");
        add(lblFechaIni, "cell 0 5");

        //---- rbFechaIni ----
        rbFechaIni.setText("text");
        rbFechaIni.setSelected(true);
        add(rbFechaIni, "cell 0 5");

        //---- rbFechaFin ----
        rbFechaFin.setText("text");
        rbFechaFin.addActionListener(e -> rbFechaFin(e));
        rbFechaFin.addChangeListener(e -> rbFechaFinStateChanged(e));
        add(rbFechaFin, "cell 0 5");

        //---- label3 ----
        label3.setText("Hora Entrada");
        add(label3, "cell 1 5,alignx right,growx 0");

        //---- txtHoraEntrada ----
        txtHoraEntrada.setEnabled(false);
        txtHoraEntrada.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtHoraEntradaMouseClicked(e);
            }
        });
        add(txtHoraEntrada, "cell 1 5");

        //---- lblFechaFin ----
        lblFechaFin.setText("Fecha Fin:");
        add(lblFechaFin, "cell 0 6");

        //---- rbFechaIni1 ----
        rbFechaIni1.setText("text");
        rbFechaIni1.setSelected(true);
        add(rbFechaIni1, "cell 0 6");

        //---- rbFechaFin1 ----
        rbFechaFin1.setText("text");
        add(rbFechaFin1, "cell 0 6");

        //---- label4 ----
        label4.setText("Hora Salida");
        add(label4, "cell 1 6,alignx right,growx 0");

        //---- txtHoraSalida ----
        txtHoraSalida.setEnabled(false);
        txtHoraSalida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtHoraSalidaMouseClicked(e);
            }
        });
        add(txtHoraSalida, "cell 1 6");

        //---- btnAgregarProveedor ----
        btnAgregarProveedor.setText("Agregar Proveedor");
        btnAgregarProveedor.addActionListener(e -> btnAgregarProveedor(e));
        add(btnAgregarProveedor, "cell 1 8,hmax 6%");

        //---- btnFinalizarProveedor ----
        btnFinalizarProveedor.setText("Finalizar");
        btnFinalizarProveedor.setColorBackground(new Color(0, 204, 0));
        btnFinalizarProveedor.setColorBackground2(new Color(51, 255, 102));
        btnFinalizarProveedor.addActionListener(e -> btnFinalizarProveedor(e));
        add(btnFinalizarProveedor, "cell 1 9,hmax 6%");

        //======== popupMenu1 ========
        {

            //---- btnEliminar ----
            btnEliminar.setText("Remover");
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminar.addActionListener(e -> btnEliminar(e));
            popupMenu1.add(btnEliminar);
        }

        //---- timePicker1 ----
        timePicker1.setDisplayText(txtHoraEntrada);
        timePicker1.setBackground(Color.pink);

        //---- timePicker2 ----
        timePicker2.setForeground(Color.gray);
        timePicker2.setDisplayText(txtHoraSalida);
        timePicker2.setBackground(Color.pink);

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rbFechaIni);
        buttonGroup1.add(rbFechaFin);

        //---- buttonGroup2 ----
        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(rbFechaIni1);
        buttonGroup2.add(rbFechaFin1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblTitutlo;
    private JLabel lblNombreEvento;
    private JComboBox cmbNombreEvento;
    private JScrollPane scrollPane1;
    private Table tblProveedor;
    private ImageSlider i;
    private SvgIcon svgIcon1;
    private JLabel lblInfo;
    private JLabel lblTipoProveedor;
    private JComboBox cmbTipoProveedor;
    private JLabel lblEditTipoProveedor;
    private JLabel lblProveedor;
    private JComboBox cmbProveedor;
    private JCheckBox cbOtro;
    private JLabel lblEditProveedor;
    private SvgIcon svgIcon2;
    private JLabel lblInfoProv;
    private JLabel lblFechaIni;
    private JRadioButton rbFechaIni;
    private JRadioButton rbFechaFin;
    private JLabel label3;
    private JTextField txtHoraEntrada;
    private JLabel lblFechaFin;
    private JRadioButton rbFechaIni1;
    private JRadioButton rbFechaFin1;
    private JLabel label4;
    private JTextField txtHoraSalida;
    private Button btnAgregarProveedor;
    private Button btnFinalizarProveedor;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
