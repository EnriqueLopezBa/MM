package vista.paneles;

import java.awt.event.*;
import java.beans.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.raven.datechooser.*;
import com.raven.swing.*;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorProveedorEvento;
import controlador.ControladorTipoEvento;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import independientes.image_slider.*;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Ciudad;
import modelo.Estado;
import modelo.Etiqueta;
import modelo.Evento;
import modelo.Lugar;
import modelo.ProveedorEvento;
import modelo.TipoEvento;
import net.miginfocom.swing.MigLayout;
import vista.paneles.edit.DialogCiudad;
import vista.paneles.edit.DialogEstado;
import vista.paneles.edit.DialogEtiqueta;
import vista.paneles.edit.DialogEvento;
import vista.paneles.edit.DialogLugar;
import vista.paneles.edit.DialogTipoEvento;
import vista.principales.Principal;

public class pnlEventos extends JPanel {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
    private SimpleDateFormat todoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat soloFecha = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat soloHora = new SimpleDateFormat("hh:mm aa");

    private Evento eventoActual = null;
    public Estado estadoActual = null;
    public Ciudad ciudadActual = null;
    public TipoEvento tipoEventoActual = null;
    public Lugar lugarActual = null;

    public frmEtiquetas frm;

    private static pnlEventos instancia;

    public static pnlEventos getInstancia() {
        if (instancia == null) {
            instancia = new pnlEventos();
        }
        return instancia;
    }

    public void checkAdmin() {
        btnAdmin.setVisible(Constante.getAdmin());
        lblEditEstado.setVisible(Constante.getAdmin());
        lblEditEtiqueta.setVisible(Constante.getAdmin());
        lblEditLugar.setVisible(Constante.getAdmin());
        lblEditTipoEvento.setVisible(Constante.getAdmin());
        lblEditciudad.setVisible(Constante.getAdmin());
        lblNombreEvento.setVisible(Constante.getAdmin());
//        txtNombreEvento.setVisible(Constante.getAdmin());
        setMinDate();
        init();
    }

    private void init() {
        cargarEtiquetas();
        cargarTipoEvento();
        cargarEstado();
        cargarCiudad();
        cargarLugares();
        cargarEventos();
    }

    private pnlEventos() {
        initComponents();
        txtPresupuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtCantInvitados.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        i.init(ScrollBar.VERTICAL);

    }

    public void cargarEventos() {
        if (Constante.getClienteActivo() == null) {
            return;
        }
        cmbEventos.removeAllItems();
        if (!Constante.getAdmin()) {// si es cliente, solo apareceran eventos vigentes
            for (Evento eve : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
                if (eve.getFechaInicio() == null) {
                    cmbEventos.addItem(eve);
                    continue;
                }
                if (eve.getFechaInicio() != null && eve.getFechaInicio().after(new Date())) {
                    cmbEventos.addItem(eve);
                }
            }
        } else {//si eres adm apareceran todos
            for (Evento eve : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
                cmbEventos.addItem(eve);
            }
        }
        cmbEventos.setRenderer(new MyObjectListCellRenderer());

    }

    public void cargarEtiquetas() {
        pnlListEtiquetas.removeAll();
        frm = new frmEtiquetas();
        pnlListEtiquetas.add(frm);
        frm.init(new Etiqueta());
        pnlListEtiquetas.revalidate();
        pnlListEtiquetas.repaint();

    }

    public void cargarTipoEvento() {
        cmbTipoEvento.removeAllItems();
        for (TipoEvento e : ControladorTipoEvento.getInstancia().obtenerListaByCadena("")) {
            cmbTipoEvento.addItem(e);
        }
        cmbTipoEvento.setRenderer(new MyObjectListCellRenderer());
    }

    public void cargarEstado() {
        cmbEstado.removeAllItems();
        for (Estado e : ControladorEstado.getInstancia().obtenerListaByCadena("")) {
            cmbEstado.addItem(e);
        }
        cmbEstado.setRenderer(new MyObjectListCellRenderer());
    }

    public void cargarCiudad() {
        cmbCiudad.removeAllItems();
        for (Ciudad c : ControladorCiudad.getInstancia().obtenerListaByIDEstado(estadoActual.getIdEstado())) {
            cmbCiudad.addItem(c);
        }
        cmbCiudad.setRenderer(new MyObjectListCellRenderer());
        cargarLugares();
    }

    public void cargarLugares() {
        if (ciudadActual == null) {
            return;
        }
        cmbLugar.removeAllItems();
        for (Lugar c : ControladorLugar.getInstancia().obtenerListaByIDCIudad(ciudadActual.getIdCiudad())) {
            cmbLugar.addItem(c);
        }
        cmbLugar.setRenderer(new MyObjectListCellRenderer());
    }
    DialogEvento dialogEvento = null;

    private void btnAdmin(ActionEvent e) {
        dialogEvento = new DialogEvento(Principal.getInstancia());
        dialogEvento.setVisible(true);
    }

    private void cmbTipoEvento(ActionEvent e) {
        if (cmbTipoEvento.getSelectedIndex() == -1) {
            return;
        }
        tipoEventoActual = (TipoEvento) cmbTipoEvento.getSelectedItem();
    }

    private void cmbEstado(ActionEvent e) {
        if (cmbEstado.getSelectedIndex() == -1) {
            return;
        }
        estadoActual = (Estado) cmbEstado.getSelectedItem();
        cargarCiudad();
    }

    private void lblEditEstadoMouseClicked() {
        DialogEstado temp = new DialogEstado(Principal.getInstancia());
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarEstado();
            }

        });
    }

    private void lblEditTipoEventoMouseClicked(MouseEvent e) {
        DialogTipoEvento temp = new DialogTipoEvento(Principal.getInstancia());
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarTipoEvento();
            }
        });
    }

    private void txtPresupuestoKeyReleased(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar())) {
            txtPresupuesto.setText("");
            return;
        }

        try {
            txtPresupuesto.commitEdit();
            txtPresupuesto.setValue(txtPresupuesto.getValue());
            txtPresupuesto.setCaretPosition(txtPresupuesto.getText().length());
            Constante.setPresupuesto(Integer.parseInt(txtPresupuesto.getText().replaceAll(",", "")));
            Constante.actualizarPresupuesto(null);
        } catch (ParseException ee) {
        }
    }

    private void txtCantInvitadosKeyReleased(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar())) {
            txtCantInvitados.setText("");
            return;
        }
        try {
            txtCantInvitados.commitEdit();
            txtCantInvitados.setValue(txtCantInvitados.getValue());
            txtCantInvitados.setCaretPosition(txtCantInvitados.getText().length());
        } catch (ParseException ee) {
        }
    }

    private void cargarLugarImagenes() {
        i.lugarImagenesByIDCiudad(ciudadActual.getIdCiudad(), frm.listModel);
    }

    private void lblEditciudadMouseClicked() {
        DialogCiudad temp = new DialogCiudad(Principal.getInstancia());
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarCiudad();
            }

        });
    }

    private void setMinDate() {
        String fechaString = fechaInicio.getSelectedDate().getYear() + "-" + fechaInicio.getSelectedDate().getMonth() + "-" + fechaInicio.getSelectedDate().getDay();
        Date fechaa = null;
        try {
            fechaa = soloFecha.parse(fechaString);
            LocalDate d11 = LocalDate.parse(soloFecha.format(fechaa), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d1 = LocalDate.parse(soloFecha.format(new Date()) + "", DateTimeFormatter.ISO_LOCAL_DATE);
            d1 = d1.plusDays(7);

            while (d11.isBefore(d1)) {
                d11 = d11.plusDays(1);
            }

            Date date = Date.from(d11.atStartOfDay(ZoneId.systemDefault()).toInstant());

            String dateFormat = soloFecha.format(date);
            List<Date> diasNoDisponibles = new ArrayList<>();
            for (Evento evento : ControladorEvento.getInstancia().obtenerEventoByAnio(fechaInicio.getSelectedDate().getYear())) {
                String ff = soloFecha.format(evento.getFechaInicio());
                diasNoDisponibles.add(soloFecha.parse(ff));
            }
            while (diasNoDisponibles.contains(soloFecha.parse(dateFormat))) {
                for (Date dd : diasNoDisponibles) {
                    if (soloFecha.format(dd).equals(dateFormat)) {
                        d11 = d11.plusDays(1);
                        date = Date.from(d11.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        dateFormat = soloFecha.format(date);
                    }
                }
            }

            pnlEventos.getInstancia().fechaInicio.setSelectedDate(new SelectedDate(d11.getDayOfMonth(), d11.getMonthValue(), d11.getYear()));
            pnlEventos.getInstancia().fechaFinal.setSelectedDate(new SelectedDate(d11.getDayOfMonth(), d11.getMonthValue(), d11.getYear()));
        } catch (ParseException ex) {
            Logger.getLogger(Dates.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void lblEditLugarMouseClicked(MouseEvent e) {
        DialogLugar temp = new DialogLugar(Principal.getInstancia());
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarLugares();
            }

        });
    }

    private void cbOtro(ActionEvent e) {
        cmbLugar.setEditable(cbOtro.isSelected());
        cmbLugar.getEditor().setItem("");
        cmbLugar.requestFocus();
    }

    private void btnAceptar(ActionEvent e) {
        try {
            validarDatos(txtPresupuesto, txtCantInvitados, cmbLugar);

            eventoActual.setIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente());
            eventoActual.setIdTipoEvento(tipoEventoActual.getIdTipoEvento());
            eventoActual.setFechaInicio(obtenerFecha(txtFechaInicio, txtHorarioInicio));
            eventoActual.setFechaFinal(obtenerFecha(txtFechaFinal, txtHorarioFinal));
            eventoActual.setNoInvitados(Integer.parseInt(txtCantInvitados.getText().replaceAll(",", "")));
            eventoActual.setPresupuesto(Integer.parseInt(txtPresupuesto.getText().replaceAll(",", "")));
            eventoActual.setEstilo(txtEstilo.getText());

            Lugar lu = new Lugar();
            lu.setIdCiudad(ciudadActual.getIdCiudad());
            if (cbOtro.isSelected()) {
                if (cmbLugar.getEditor().getItem().toString().isEmpty()) {
                    Constante.mensaje("Inserte el nombre del lugar donde quiere que sea su evento", Tipo.ADVERTENCIA);
                    cmbLugar.requestFocus();
                    return;
                }
                lu.setNombreLocal(cmbLugar.getEditor().getItem().toString());
                ControladorLugar.getInstancia().registrar(lu);
                eventoActual.setIdLugar(ControladorLugar.getInstancia().obtenerLugarByLast().getIdLugar());
            } else {
                lu.setNombreLocal(cmbLugar.getSelectedItem().toString());
                eventoActual.setIdLugar(lugarActual.getIdLugar());
            }
            Mensaje m = ControladorEvento.getInstancia().actualizar(eventoActual);
            if (m.getTipoMensaje() == Tipo.OK) {
                if (!ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento()).isEmpty()) {
                    Constante.mensaje("verifica las fechas de los proveedores", Tipo.ADVERTENCIA);
                }
                //Actualizo las fechas de los proveedores del evento forzosamente
                for (ProveedorEvento pro : ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())) {
                    if (pro.getFechaInicio().before(eventoActual.getFechaInicio())) {
                        pro.setFechaInicio(eventoActual.getFechaInicio());
                        ControladorProveedorEvento.getInstancia().actualizar(pro);
                    }
                    if (pro.getFechaFinal().after(eventoActual.getFechaFinal())) {
                        pro.setFechaFinal(eventoActual.getFechaInicio());
                        ControladorProveedorEvento.getInstancia().actualizar(pro);
                    }
                }
                if (dialogEvento != null) {
                    dialogEvento.llenarTabla();
                }
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        } catch (MMException ee) {
            Constante.mensaje(ee.getMessage(), Message.Tipo.ERROR);
        }
    }

    private void validarDatos(JTextField txtPresupuesto, JTextField txtNoInvitados, JComboBox cmbLugar) throws MMException {
        if (eventoActual == null) {
            cmbEventos.requestFocus();
            throw new MMException("Selecciona un evento");
        }

        if (txtPresupuesto.getText().isEmpty()) {
            txtPresupuesto.requestFocus();
            throw new MMException("Prepuesto vacio");
        }
        if (txtNoInvitados.getText().isEmpty()) {
            txtNoInvitados.requestFocus();
            throw new MMException("Numero de invitados vacio");
        }
        if (cmbLugar.isEditable()) {
            if (cmbLugar.getEditor().getItem().toString().isEmpty()) {
                cmbLugar.requestFocus();
                throw new MMException("Lugar vacio");
            }
        }

        //Validar fecha
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm a");
        LocalDateTime fechaYHoraLocal1 = LocalDateTime.parse(txtFechaInicio.getText() + " " + timePickerInicio.getSelectedTime(), formateador);
        LocalDateTime fechaYHoraLocal2 = LocalDateTime.parse(soloFecha.format(new Date()) + " " + soloHora.format(new Date()), formateador);

        long dias = ChronoUnit.DAYS.between(fechaYHoraLocal2, fechaYHoraLocal1);
        if (dias < 7) {
            txtFechaInicio.requestFocus();
            setMinDate();
            throw new MMException("Minimo 1 semana de anticipacion");

        }

        Date fechaInicioo = obtenerFecha(txtFechaInicio, txtHorarioInicio);
        Date fechaFinall = obtenerFecha(txtFechaFinal, txtHorarioFinal);
        if (fechaFinall.before(fechaInicioo) || fechaFinall.equals(fechaInicioo)) {
            throw new MMException("Cambie la fecha final!");
        }
        if (ControladorCliente.getInstancia().obtenerClienteActivo() == null) {
            throw new MMException("Sin cliente activo");
        }
    }

    public Date obtenerFecha(JTextField fecha, JTextField hora) {
        try {
            String fech = fecha.getText() + " " + hora.getText();
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(sdf.parse(fech));
            return calendario.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(pnlEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    private void setMinDate() {
//        LocalDate d11 = LocalDate.parse(soloFecha.format(new Date()) + "", DateTimeFormatter.ISO_LOCAL_DATE);
//        d11 = d11.plusDays(1);
//        dateChooser.setSelectedDate(new SelectedDate(d11.getDayOfMonth(), d11.getMonthValue(), d11.getYear()));
//    }
    private void lblEditEtiquetaMousePressed(MouseEvent e) {
        DialogEtiqueta temp = new DialogEtiqueta(Principal.getInstancia());
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarEtiquetas();
            }
        });
    }

    private void txtHorarioInicioMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHorarioInicio.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHorarioInicio.getLocationOnScreen().y - timePickerInicio.getHeight();
        timePickerInicio.showPopup(txtHorarioInicio, x, y);
    }

    private void txtHorarioFinalMouseClicked(MouseEvent e) {
        int x = getPointerInfo().getLocation().x - txtHorarioFinal.getLocationOnScreen().x;
        int y = getPointerInfo().getLocation().y - txtHorarioFinal.getLocationOnScreen().y - timePickerFinal.getHeight();
        timePickerFinal.showPopup(txtHorarioFinal, x, y);
    }

    private void txtHorarioInicioKeyPressed(KeyEvent e) {
        // TODO add your code here
    }

    private void cmbEventosItemStateChanged(ItemEvent e) {
        if (cmbEventos.getSelectedIndex() == -1) {
            return;
        }
        eventoActual = (Evento) cmbEventos.getSelectedItem();
    }

    private void cmbLugarItemStateChanged(ItemEvent e) {
        if (cbOtro.isSelected() || cmbLugar.getSelectedIndex() == -1) {
            return;
        }
        lugarActual = (Lugar) cmbLugar.getSelectedItem();
        Constante.actualizarPresupuesto(null);
    }

    private void cmbCiudadItemStateChanged(ItemEvent e) {
        if (cmbCiudad.getSelectedIndex() == -1) {
            if (cmbLugar.getSelectedIndex() != -1) {
                cmbLugar.removeAllItems();
            }
            return;
        }
        ciudadActual = (Ciudad) cmbCiudad.getSelectedItem();
        cargarLugarImagenes();
        cargarLugares();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblTitulo = new JLabel();
        cmbEventos = new JComboBox();
        lblNombreEvento = new JLabel();
        lblPresupuesto = new JLabel();
        lblCantInvitados = new JLabel();
        lblCaracteristicas = new JLabel();
        lblTipoEvento = new JLabel();
        lblEstado = new JLabel();
        lblCiudad = new JLabel();
        txtPresupuesto = new JFormattedTextField();
        txtCantInvitados = new JFormattedTextField();
        pnlListEtiquetas = new JPanel();
        btnAdmin = new JButton();
        i = new ImageSlider();
        lblEstilo = new JLabel();
        txtEstilo = new JTextField();
        lblEditEtiqueta = new JLabel();
        panel1 = new JPanel();
        lblFecha = new JLabel();
        txtFechaInicio = new JTextField();
        txtHorarioInicio = new JTextField();
        lblFecha2 = new JLabel();
        txtFechaFinal = new JTextField();
        txtHorarioFinal = new JTextField();
        cmbEstado = new JComboBox();
        cmbCiudad = new JComboBox();
        cmbTipoEvento = new JComboBox();
        cmbLugar = new JComboBox();
        lblEditEstado = new JLabel();
        lblEditTipoEvento = new JLabel();
        lblEditciudad = new JLabel();
        lblLugar = new JLabel();
        lblEditLugar = new JLabel();
        cbOtro = new JCheckBox();
        btnAceptar = new Button();
        fechaInicio = new DateChooser();
        fechaFinal = new DateChooser();
        timePickerInicio = new TimePicker();
        timePickerFinal = new TimePicker();

        //======== this ========
        setBackground(Color.white);
        setPreferredSize(new Dimension(1350, 850));
        setMinimumSize(new Dimension(1350, 850));
        setLayout(new MigLayout(
            "fill,insets 0,hidemode 3,gap 4 4",
            // columns
            "para[fill]" +
            "[294:n,grow 30,fill]" +
            "[grow 5,fill]" +
            "[grow 5,fill]0",
            // rows
            "[]unrel" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]unrel" +
            "[fill]unrel"));

        //---- lblTitulo ----
        lblTitulo.setText("Agregar Evento");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitulo, "cell 0 0, spanx, grow");

        //---- cmbEventos ----
        cmbEventos.addItemListener(e -> cmbEventosItemStateChanged(e));
        add(cmbEventos, "cell 1 1");

        //---- lblNombreEvento ----
        lblNombreEvento.setText("Nombre Evento");
        lblNombreEvento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblNombreEvento, "cell 0 1");

        //---- lblPresupuesto ----
        lblPresupuesto.setText("Presupuesto");
        lblPresupuesto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblPresupuesto, "cell 0 2");

        //---- lblCantInvitados ----
        lblCantInvitados.setText("Cant. Invitados");
        lblCantInvitados.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblCantInvitados, "cell 0 3");

        //---- lblCaracteristicas ----
        lblCaracteristicas.setText("Caracteristicas de evento");
        lblCaracteristicas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblCaracteristicas, "cell 0 5");

        //---- lblTipoEvento ----
        lblTipoEvento.setText("Tipo de evento");
        lblTipoEvento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblTipoEvento, "cell 0 7");

        //---- lblEstado ----
        lblEstado.setText("Estado");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblEstado, "cell 0 8");

        //---- lblCiudad ----
        lblCiudad.setText("Ciudad");
        lblCiudad.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblCiudad, "cell 0 9");

        //---- txtPresupuesto ----
        txtPresupuesto.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtPresupuesto.setHorizontalAlignment(SwingConstants.CENTER);
        txtPresupuesto.setNextFocusableComponent(txtCantInvitados);
        txtPresupuesto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtPresupuestoKeyReleased(e);
            }
        });
        add(txtPresupuesto, "cell 1 2");

        //---- txtCantInvitados ----
        txtCantInvitados.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtCantInvitados.setHorizontalAlignment(SwingConstants.CENTER);
        txtCantInvitados.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtCantInvitadosKeyReleased(e);
            }
        });
        add(txtCantInvitados, "cell 1 3");

        //======== pnlListEtiquetas ========
        {
            pnlListEtiquetas.setBackground(Color.white);
            pnlListEtiquetas.setLayout(new BorderLayout());
        }
        add(pnlListEtiquetas, "cell 1 5");

        //---- btnAdmin ----
        btnAdmin.setIcon(new ImageIcon(getClass().getResource("/img/admin.png")));
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorderPainted(false);
        btnAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdmin.addActionListener(e -> btnAdmin(e));
        add(btnAdmin, "cell 2 1,grow 0 0");
        add(i, "cell 3 1 1 11");

        //---- lblEstilo ----
        lblEstilo.setText("Estilo");
        lblEstilo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblEstilo, "cell 0 4");

        //---- txtEstilo ----
        txtEstilo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtEstilo.setHorizontalAlignment(SwingConstants.CENTER);
        add(txtEstilo, "cell 1 4");

        //---- lblEditEtiqueta ----
        lblEditEtiqueta.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditEtiqueta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditEtiqueta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lblEditEtiquetaMousePressed(e);
            }
        });
        add(lblEditEtiqueta, "cell 2 5,grow 0 0");

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill",
                // columns
                "0[fill]" +
                "[fill]para" +
                "[fill]" +
                "[fill]" +
                "[fill]0",
                // rows
                "0[]0"));

            //---- lblFecha ----
            lblFecha.setText("Fecha Inicio");
            lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panel1.add(lblFecha, "cell 0 0");

            //---- txtFechaInicio ----
            txtFechaInicio.setHorizontalAlignment(SwingConstants.CENTER);
            txtFechaInicio.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtFechaInicio.setNextFocusableComponent(cmbTipoEvento);
            panel1.add(txtFechaInicio, "cell 0 0");

            //---- txtHorarioInicio ----
            txtHorarioInicio.setHorizontalAlignment(SwingConstants.CENTER);
            txtHorarioInicio.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtHorarioInicio.setEditable(false);
            txtHorarioInicio.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    txtHorarioInicioKeyPressed(e);
                }
            });
            txtHorarioInicio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    txtHorarioInicioMouseClicked(e);
                }
            });
            panel1.add(txtHorarioInicio, "cell 1 0");

            //---- lblFecha2 ----
            lblFecha2.setText("Fecha Final");
            lblFecha2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblFecha2.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(lblFecha2, "cell 2 0");

            //---- txtFechaFinal ----
            txtFechaFinal.setHorizontalAlignment(SwingConstants.CENTER);
            txtFechaFinal.setFont(new Font("Segoe UI", Font.BOLD, 18));
            panel1.add(txtFechaFinal, "cell 3 0");

            //---- txtHorarioFinal ----
            txtHorarioFinal.setHorizontalAlignment(SwingConstants.CENTER);
            txtHorarioFinal.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtHorarioFinal.setEditable(false);
            txtHorarioFinal.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    txtHorarioFinalMouseClicked(e);
                }
            });
            panel1.add(txtHorarioFinal, "cell 4 0");
        }
        add(panel1, "cell 0 6, sx 2");

        //---- cmbEstado ----
        cmbEstado.setNextFocusableComponent(cmbCiudad);
        cmbEstado.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbEstado.addActionListener(e -> cmbEstado(e));
        add(cmbEstado, "cell 1 8");

        //---- cmbCiudad ----
        cmbCiudad.setNextFocusableComponent(cmbLugar);
        cmbCiudad.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbCiudad.addItemListener(e -> cmbCiudadItemStateChanged(e));
        add(cmbCiudad, "cell 1 9");

        //---- cmbTipoEvento ----
        cmbTipoEvento.setNextFocusableComponent(cmbEstado);
        cmbTipoEvento.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbTipoEvento.addActionListener(e -> cmbTipoEvento(e));
        add(cmbTipoEvento, "cell 1 7");

        //---- cmbLugar ----
        cmbLugar.setNextFocusableComponent(btnAceptar);
        cmbLugar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbLugar.addItemListener(e -> cmbLugarItemStateChanged(e));
        add(cmbLugar, "cell 1 10");

        //---- lblEditEstado ----
        lblEditEstado.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditEstado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditEstado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditEstadoMouseClicked();
            }
        });
        add(lblEditEstado, "cell 2 8,grow 0 0");

        //---- lblEditTipoEvento ----
        lblEditTipoEvento.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditTipoEvento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditTipoEvento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditTipoEventoMouseClicked(e);
            }
        });
        add(lblEditTipoEvento, "cell 2 7,grow 0 0");

        //---- lblEditciudad ----
        lblEditciudad.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditciudad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditciudad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditciudadMouseClicked();
            }
        });
        add(lblEditciudad, "cell 2 9,grow 0 0");

        //---- lblLugar ----
        lblLugar.setText("Lugar");
        lblLugar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblLugar, "cell 0 10");

        //---- lblEditLugar ----
        lblEditLugar.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditLugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditLugar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditLugarMouseClicked(e);
            }
        });
        add(lblEditLugar, "split 2,cell 2 10,grow 0 0");

        //---- cbOtro ----
        cbOtro.setText("Otro");
        cbOtro.addActionListener(e -> cbOtro(e));
        add(cbOtro, "cell 2 10");

        //---- btnAceptar ----
        btnAceptar.setText("Aceptar Evento");
        btnAceptar.addActionListener(e -> btnAceptar(e));
        add(btnAceptar, "cell 1 11,growy");

        //---- fechaInicio ----
        fechaInicio.setTextRefernce(txtFechaInicio);
        fechaInicio.setDateFormat("yyyy-MM-dd");
        fechaInicio.setForeground(Color.pink);

        //---- fechaFinal ----
        fechaFinal.setTextRefernce(txtFechaFinal);
        fechaFinal.setDateFormat("yyyy-MM-dd");
        fechaFinal.setForeground(Color.pink);

        //---- timePickerInicio ----
        timePickerInicio.setForeground(new Color(153, 102, 255));
        timePickerInicio.setDisplayText(txtHorarioInicio);

        //---- timePickerFinal ----
        timePickerFinal.setForeground(new Color(153, 102, 255));
        timePickerFinal.setDisplayText(txtHorarioFinal);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblTitulo;
    public JComboBox cmbEventos;
    private JLabel lblNombreEvento;
    private JLabel lblPresupuesto;
    private JLabel lblCantInvitados;
    private JLabel lblCaracteristicas;
    private JLabel lblTipoEvento;
    private JLabel lblEstado;
    private JLabel lblCiudad;
    public JFormattedTextField txtPresupuesto;
    public JFormattedTextField txtCantInvitados;
    public JPanel pnlListEtiquetas;
    private JButton btnAdmin;
    public ImageSlider i;
    private JLabel lblEstilo;
    public JTextField txtEstilo;
    private JLabel lblEditEtiqueta;
    private JPanel panel1;
    private JLabel lblFecha;
    public JTextField txtFechaInicio;
    public JTextField txtHorarioInicio;
    private JLabel lblFecha2;
    public JTextField txtFechaFinal;
    public JTextField txtHorarioFinal;
    public JComboBox cmbEstado;
    public JComboBox cmbCiudad;
    public JComboBox cmbTipoEvento;
    public JComboBox cmbLugar;
    private JLabel lblEditEstado;
    private JLabel lblEditTipoEvento;
    private JLabel lblEditciudad;
    private JLabel lblLugar;
    private JLabel lblEditLugar;
    public JCheckBox cbOtro;
    private Button btnAceptar;
    public DateChooser fechaInicio;
    public DateChooser fechaFinal;
    public TimePicker timePickerInicio;
    public TimePicker timePickerFinal;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
