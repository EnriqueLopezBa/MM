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
import com.raven.event.EventTimePicker;
import com.raven.swing.*;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorNegocioArea;
import controlador.ControladorProveedorEvento;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import independientes.image_slider.*;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import modelo.Ciudad;
import modelo.Estado;
import modelo.Evento;
import modelo.Lugar;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.NegocioArea;
import modelo.ProveedorEvento;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogProveedor;
import vista.paneles.edit.DialogTipoProveedor;
import vista.principales.Principal;

public class pnlProveedores extends JPanel {

    private TipoProveedor tipoProveedorActual;

    private Negocio negocioActual;
    private Evento eventoActual;

    public DefaultTableModel m;

    private SimpleDateFormat todoFechaAMPM = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
    private SimpleDateFormat todoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat soloFecha = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat soloHora = new SimpleDateFormat("hh:mm aa");

    private static pnlProveedores instancia;

    public static pnlProveedores getInstancia() {
        if (instancia == null) {
            instancia = new pnlProveedores();
        }
        return instancia;
    }

    private pnlProveedores() {
        initComponents();
        m = (DefaultTableModel) tblProveedor.getModel();
        init();
        timePicker2.addEventTimePicker(new EventTimePicker() {
            @Override
            public void timeSelected(String string) {
                restar(null);
            }
        });
        timePicker1.addEventTimePicker(new EventTimePicker() {
            @Override
            public void timeSelected(String string) {
                restar(null);
            }
        });

    }

    private void init() {

        m.addTableModelListener(new TableModelListener() { // PARA RECALCULAR EL PRESUPUESTO
            @Override
            public void tableChanged(TableModelEvent e) {
                Constante.actualizarPresupuesto(eventoActual);
            }
        });

//        tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
//        tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
    }

    public void recargar() {
        cargarNombreEvento();
        cargarTipoProveedor();
//        cargarNegocios();
    }

    private void cargarNombreEvento() {
        if (Constante.getClienteActivo() == null) {
            return;
        }
        cmbNombreEvento.removeAllItems();
        for (Evento e : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
            cmbNombreEvento.addItem(e);
        }
        cmbNombreEvento.setRenderer(new MyObjectListCellRenderer());
    }

    private void cargarTipoProveedor() {
        if (eventoActual == null) {
            return;
        }
        cmbTipoProveedor.removeAllItems();

        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            if (tipo.getTipoProveedor().equalsIgnoreCase("local")) {
                continue;
            }
            cmbTipoProveedor.addItem(tipo);
        }
        cmbTipoProveedor.setRenderer(new MyObjectListCellRenderer());
    }

    private void cargarNegocios() {
        if (tipoProveedorActual == null) {
            return;
        }

        cmbNegocio.removeAllItems();
        //Cargar proveedores dependiendo del area donde trabajan los proveedores
        int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();

        for (NegocioArea prov : ControladorNegocioArea.getInstancia().obtenerListaByIdCiudadAndTipoProveedor(idCiudad, tipoProveedorActual.getIdTipoProveedor())) {
            Negocio nec = ControladorNegocio.getInstancia().obtenerByID(prov.getIdNegocio());
            if (!ControladorProveedor.getInstancia().obtenerByID(nec.getIdProveedor()).isDisponible() || !nec.isDisponible()) { // si el proveedor no se encuentra disponible
                continue;
            }
            cmbNegocio.addItem(nec);

        }
        cmbNegocio.setRenderer(new MyObjectListCellRenderer());

    }

    public void checkAdmin() {
        lblEditProveedor.setVisible(Constante.getAdmin());
        lblEditTipoProveedor.setVisible(Constante.getAdmin());

    }

    private int restar(ProveedorEvento provEve) {
        if (provEve != null) {
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            LocalDateTime fechaYHoraLocal1 = LocalDateTime.parse(provEve.getFechaInicio().toString().substring(0, provEve.getFechaInicio().toString().length() - 5), formateador);
            LocalDateTime fechaYHoraLocal2 = LocalDateTime.parse(provEve.getFechaFinal().toString().substring(0, provEve.getFechaFinal().toString().length() - 5), formateador);
            long minutos = ChronoUnit.MINUTES.between(fechaYHoraLocal1, fechaYHoraLocal2);
            Proveedor pro = ControladorProveedor.getInstancia().obtenerByID(provEve.getIdProveedor());
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(pro.getIdProveedor());
            int precio = new Double(((double) negocio.getPrecioAprox() / 60) * minutos).intValue();
            return precio;
        }
        if (eventoActual == null || negocioActual == null) {
            return -1;
        }
        if (eventoActual.getFechaInicio() == null) {
            Constante.mensaje("Termina de registrar este evento", Message.Tipo.ADVERTENCIA);
            return -1;
        }

        String fech = "";
        String fech2 = "";
        if (!soloFecha.format(eventoActual.getFechaInicio()).equals(soloFecha.format(eventoActual.getFechaFinal()))) {
            fech = rbFechaIni.isSelected() ? rbFechaIni.getText() : rbFechaFin.getText();
            fech2 = rbFechaIni1.isSelected() ? rbFechaIni1.getText() : rbFechaFin1.getText();
        } else {
            fech = soloFecha.format(eventoActual.getFechaInicio());
            fech2 = soloFecha.format(eventoActual.getFechaInicio());
        }
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm a");
        LocalDateTime fechaYHoraLocal1 = LocalDateTime.parse(fech + " " + timePicker1.getSelectedTime(), formateador);
        LocalDateTime fechaYHoraLocal2 = LocalDateTime.parse(fech2 + " " + timePicker2.getSelectedTime(), formateador);
        long minutos = ChronoUnit.MINUTES.between(fechaYHoraLocal1, fechaYHoraLocal2);
        if (minutos <= 0) {
            lblInfoProv.setText("Aprox x Hora: $" + negocioActual.getPrecioAprox());
            return -1;
        }
        int precio = new Double(((double) negocioActual.getPrecioAprox() / 60) * minutos).intValue();
        lblInfoProv.setText("Aprox x Hora: $" + negocioActual.getPrecioAprox() + " -- Precio Calculado: $" + precio);
        return precio;

    }

    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
        if (cmbTipoProveedor.getSelectedIndex() == -1 || eventoActual == null) {
            return;
        }
        tipoProveedorActual = (TipoProveedor) cmbTipoProveedor.getSelectedItem();
        int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
        i.init(ScrollBar.VERTICAL);
        i.negocioImagenesByCiudadAndTipoProveedor(idCiudad, tipoProveedorActual.getIdTipoProveedor());
        cargarNegocios();
    }

    private void cmbNombreEventoItemStateChanged(ItemEvent e) {
        if (cmbNombreEvento.getSelectedIndex() == -1 || Constante.getClienteActivo() == null) {
            return;
        }
        eventoActual = (Evento) cmbNombreEvento.getSelectedItem();
        Lugar lugar = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar());
        Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(lugar.getIdCiudad());
        Estado estado = ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado());
        lblInfo.setText(estado.getEstado() + ", " + ciudad.getCiudad());
        if (!soloFecha.format(eventoActual.getFechaInicio()).equals(soloFecha.format(eventoActual.getFechaFinal()))) {
            lblFechaIni.setVisible(true);
            lblFechaFin.setVisible(true);
            rbFechaIni.setVisible(true);
            rbFechaFin.setVisible(true);
            rbFechaIni1.setVisible(true);
            rbFechaFin1.setVisible(true);
            rbFechaIni.setText(soloFecha.format(eventoActual.getFechaInicio()));
            rbFechaFin.setText(soloFecha.format(eventoActual.getFechaFinal()));
            rbFechaIni1.setText(soloFecha.format(eventoActual.getFechaInicio()));
            rbFechaFin1.setText(soloFecha.format(eventoActual.getFechaFinal()));
        } else {
            lblFechaIni.setVisible(false);
            lblFechaFin.setVisible(false);
            rbFechaIni.setVisible(false);
            rbFechaFin.setVisible(false);
            rbFechaIni1.setVisible(false);
            rbFechaFin1.setVisible(false);
        }

    }

    private void cargarProveedorEvento() { //Cargar tabla
//        ArrayList<ProveedorEvento> proveedores = ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento());
//        if (eventoActual == null || proveedores == null) {
//            return;
//        }
//        m.setRowCount(0);
//        for (ProveedorEvento pro : proveedores) {
//            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(pro.getIdNegocio());
//            TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID(negocio.getIdTipoProveedor());
//            String timeInicio = todoFechaAMPM.format(pro.getFechaInicio());
//            String timeFinal = todoFechaAMPM.format(pro.getFechaFinal());
//            m.addRow(new Object[]{tipo.getIdTipoProveedor(), negocio.getIdProveedor(),
//                tipo.getTipoProveedor(), negocio.getNombreNegocio(), timeInicio, timeFinal, restar(pro)});
//        }
    }

    private void cmbNegocioItemStateChanged(ItemEvent e) {
        if (cmbNegocio.getSelectedIndex() == -1 || tipoProveedorActual == null) {
            return;
        }
        negocioActual = (Negocio) cmbNegocio.getSelectedItem();
        if (negocioActual.getDescripcion() == null) {
            scrollPane3.setVisible(false);
        } else {
            scrollPane3.setVisible(true);
            txtDescripcion.setText(negocioActual.getDescripcion());
        }

        lblInfoProv.setText("Precio Aprox (Hora): " + negocioActual.getPrecioAprox());

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
        if (negocioActual == null || txtHoraEntrada.getText().isEmpty() || txtHoraSalida.getText().isEmpty()) {
            return;
        }
        if (cbOtro.isSelected()) {
            for (int j = 0; j < m.getRowCount(); j++) {
                if (m.getValueAt(j, 3).toString().equals(cmbNegocio.getEditor().getItem().toString())) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                    return;
                }
            }
            m.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), null,
                tipoProveedorActual.getTipoProveedor(), cmbNegocio.getEditor().getItem().toString(), getFecha()[0], getFecha()[1], null});
        } else {
            int precio = restar(null);
            if (precio == -1) {
                Constante.mensaje("Introduzca una fecha/hora correcta", Message.Tipo.ADVERTENCIA);
                return;
            }
            try {
                Date dateInicio = todoFechaAMPM.parse(getFecha()[0]);
                Date dateFinal = todoFechaAMPM.parse(getFecha()[1]);
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
                if ((int) m.getValueAt(j, 1) == negocioActual.getIdNegocio()) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                    return;
                }
            }
            m.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), negocioActual.getIdNegocio(),
                tipoProveedorActual.getTipoProveedor(), negocioActual.getNombreNegocio(), getFecha()[0], getFecha()[1], restar(null)});
            Constante.actualizarPresupuesto(eventoActual);

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
            cmbNegocio.setEditable(true);
            cmbNegocio.getEditor().setItem("");
        } else {
            cmbNegocio.setEditable(false);
            cargarNegocios();
        }

    }

    private void btnFinalizarProveedor(ActionEvent e) {
        if (eventoActual == null) {
            return;
        }
        if (m.getRowCount() == 0) {
            if (!ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento()).isEmpty()) {
                Mensaje m = null;
                for (ProveedorEvento pro : ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())) {
                    m = ControladorProveedorEvento.getInstancia().eliminar(pro);
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                cargarProveedorEvento();
                return;
            } else {
                return;
            }

        }
        ArrayList<ProveedorEvento> proE = new ArrayList<>();
        for (int j = 0; j < m.getRowCount(); j++) {
            try {
                // comprobrar que existen proveedores nuevo a registrar
                if (m.getValueAt(j, 1) == null) {
                    //se añade un nuevo negocio
                    Negocio negocio = new Negocio();
                    negocio.setIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor());
                    negocio.setNombreNegocio(m.getValueAt(j, 3).toString());
                    ControladorNegocio.getInstancia().registrar(negocio);
                    m.setValueAt(ControladorNegocio.getInstancia().obtenerNegocioByLast().getIdNegocio(), j, 1);
                }
                Negocio negocio = ControladorNegocio.getInstancia().obtenerByID((int) m.getValueAt(j, 1));
                Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(negocio.getIdProveedor());
                ProveedorEvento pro = new ProveedorEvento();
                if (proveedor != null) {
                    pro.setIdProveedor(proveedor.getIdProveedor());
                }// si es == null significa que este es un nuevo negocio agregado por el cliente

                pro.setIdEvento(eventoActual.getIdEvento());
                pro.setIdNegocio((int) m.getValueAt(j, 1));
                pro.setFechaInicio(todoFechaAMPM.parse(m.getValueAt(j, 4).toString()));
                pro.setFechaFinal(todoFechaAMPM.parse(m.getValueAt(j, 5).toString()));
                pro.setComentario(txtComentario.getText());
                proE.add(pro);
            } catch (ParseException ex) {
                Logger.getLogger(pnlProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Mensaje m = ControladorProveedorEvento.getInstancia().registrarLote(proE);
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

    }

    private void txtHoraEntradaMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHoraEntrada.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHoraEntrada.getLocationOnScreen().y - timePicker1.getHeight();
        timePicker1.showPopup(txtHoraEntrada, x, y);
        restar(null);
    }

    private void txtHoraSalidaMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHoraSalida.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHoraSalida.getLocationOnScreen().y - timePicker2.getHeight();
        timePicker2.showPopup(txtHoraSalida, x, y);
        restar(null);
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

    private void txtComentarioFocusGained(FocusEvent e) {
        if (txtComentario.getText().equals("Si deseas algo en especifico de este proveedoor, puedes escribirlo aqui")) {
            txtComentario.setText("");
        }
    }

    private void txtComentarioFocusLost(FocusEvent e) {
        if (txtComentario.getText().isEmpty()) {
            txtComentario.setText("Si deseas algo en especifico de este proveedoor, puedes escribirlo aqui");
        }
    }

    private void tblProveedorMouseClicked(MouseEvent e) {
//        int x = tblProveedor.getSelectedRow();
//        if (x == -1) {
//            return;
//        }
//
//        TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID((int) m.getValueAt(x, 0));
//        Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID((int) m.getValueAt(x, 1));
//        ProveedorEvento pro = ControladorProveedorEvento.getInstancia().obtenerByIdEventoAndIdProveedor(eventoActual.getIdEvento(), proveedor.getIdProveedor());
//
//        if (rbFechaFin.isVisible()) {
//            rbFechaIni.setText(soloFecha.format(pro.getFechaInicio()));
//            rbFechaFin.setText(soloFecha.format(pro.getFechaFinal()));
//            rbFechaIni1.setText(soloFecha.format(pro.getFechaInicio()));
//            rbFechaFin1.setText(soloFecha.format(pro.getFechaFinal()));
//        } else {
//            timePicker1.setSelectedTime(pro.getFechaInicio());
//            timePicker2.setSelectedTime(pro.getFechaFinal());
//        }
//        cmbTipoProveedor.setSelectedItem(tipo.getTipoProveedor());
//        cmbProveedor.setSelectedItem(proveedor.getNombreEmpresa());
//        txtDescripcion.setText(proveedor.getDescripcion());
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
        cmbNegocio = new JComboBox();
        cbOtro = new JCheckBox();
        lblEditProveedor = new JLabel();
        scrollPane3 = new JScrollPane();
        txtDescripcion = new JEditorPane();
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
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        txtComentario = new JTextArea();
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
            "[10%:10%,grow,fill]para" +
            "[10%:12%,grow,fill]" +
            "[20%:20%,grow,fill]" +
            "[30%:30%,grow,fill]",
            // rows
            "[grow,fill]" +
            "[grow,fill]0" +
            "[]" +
            "[grow,fill]" +
            "[grow,fill]" +
            "[10%:10%,fill]" +
            "[]" +
            "[]" +
            "[]" +
            "[fill]" +
            "[grow,fill]" +
            "[grow,fill]unrel"));

        //---- lblTitutlo ----
        lblTitutlo.setText("Agregar Proveedores");
        lblTitutlo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitutlo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(lblTitutlo, "cell 0 0 4 1,alignx center");

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
                    "idTipoProveedor", "idNegocio", "Tipo", "Negocio", "Inicio", "Fin", "Precio Calculado"
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
            tblProveedor.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tblProveedorMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(tblProveedor);
        }
        add(scrollPane1, "cell 2 1 1 11,grow");

        //---- i ----
        i.setBackground(Color.white);
        add(i, "cell 3 1 1 11,grow 50 100");

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
        lblProveedor.setText("Negocio");
        add(lblProveedor, "cell 0 4");

        //---- cmbNegocio ----
        cmbNegocio.addItemListener(e -> cmbNegocioItemStateChanged(e));
        add(cmbNegocio, "split 2,cell 1 4,hmax 6%");

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

        //======== scrollPane3 ========
        {

            //---- txtDescripcion ----
            txtDescripcion.setEditable(false);
            scrollPane3.setViewportView(txtDescripcion);
        }
        add(scrollPane3, "cell 0 5, spanx 2, wmax 30%, align center");

        //---- svgIcon2 ----
        svgIcon2.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\info.svg"));
        add(svgIcon2, "cell 0 8,alignx right,growx 0");

        //---- lblInfoProv ----
        lblInfoProv.setFont(lblInfoProv.getFont().deriveFont(lblInfoProv.getFont().getStyle() | Font.BOLD));
        add(lblInfoProv, "cell 1 8");

        //---- lblFechaIni ----
        lblFechaIni.setText("Fecha Inicio:");
        add(lblFechaIni, "cell 0 6");

        //---- rbFechaIni ----
        rbFechaIni.setText("1");
        rbFechaIni.setSelected(true);
        add(rbFechaIni, "cell 0 6");

        //---- rbFechaFin ----
        rbFechaFin.setText("2");
        rbFechaFin.addActionListener(e -> rbFechaFin(e));
        rbFechaFin.addChangeListener(e -> rbFechaFinStateChanged(e));
        add(rbFechaFin, "cell 0 6");

        //---- label3 ----
        label3.setText("Hora Entrada");
        add(label3, "cell 1 6,alignx right,growx 0");

        //---- txtHoraEntrada ----
        txtHoraEntrada.setEnabled(false);
        txtHoraEntrada.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtHoraEntradaMouseClicked(e);
            }
        });
        add(txtHoraEntrada, "cell 1 6");

        //---- lblFechaFin ----
        lblFechaFin.setText("Fecha Fin:");
        add(lblFechaFin, "cell 0 7");

        //---- rbFechaIni1 ----
        rbFechaIni1.setText("1");
        rbFechaIni1.setSelected(true);
        add(rbFechaIni1, "cell 0 7");

        //---- rbFechaFin1 ----
        rbFechaFin1.setText("2");
        add(rbFechaFin1, "cell 0 7");

        //---- label4 ----
        label4.setText("Hora Salida");
        add(label4, "cell 1 7,alignx right,growx 0");

        //---- txtHoraSalida ----
        txtHoraSalida.setEnabled(false);
        txtHoraSalida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtHoraSalidaMouseClicked(e);
            }
        });
        add(txtHoraSalida, "cell 1 7");

        //---- label1 ----
        label1.setText("Comentario");
        add(label1, "cell 0 9");

        //======== scrollPane2 ========
        {

            //---- txtComentario ----
            txtComentario.setText("Si deseas algo en especifico de este proveedoor, puedes escribirlo aqui");
            txtComentario.setWrapStyleWord(true);
            txtComentario.setLineWrap(true);
            txtComentario.setRows(5);
            txtComentario.setFont(txtComentario.getFont().deriveFont(txtComentario.getFont().getStyle() | Font.BOLD, txtComentario.getFont().getSize() + 2f));
            txtComentario.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtComentarioFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    txtComentarioFocusLost(e);
                }
            });
            scrollPane2.setViewportView(txtComentario);
        }
        add(scrollPane2, "cell 1 9");

        //---- btnAgregarProveedor ----
        btnAgregarProveedor.setText("Agregar Proveedor");
        btnAgregarProveedor.addActionListener(e -> btnAgregarProveedor(e));
        add(btnAgregarProveedor, "cell 1 10,hmax 6%");

        //---- btnFinalizarProveedor ----
        btnFinalizarProveedor.setText("Finalizar");
        btnFinalizarProveedor.setColorBackground(new Color(0, 204, 0));
        btnFinalizarProveedor.setColorBackground2(new Color(51, 255, 102));
        btnFinalizarProveedor.addActionListener(e -> btnFinalizarProveedor(e));
        add(btnFinalizarProveedor, "cell 1 11,hmax 6%");

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
    private JComboBox cmbNegocio;
    private JCheckBox cbOtro;
    private JLabel lblEditProveedor;
    private JScrollPane scrollPane3;
    private JEditorPane txtDescripcion;
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
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTextArea txtComentario;
    private Button btnAgregarProveedor;
    private Button btnFinalizarProveedor;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
