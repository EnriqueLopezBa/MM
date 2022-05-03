package vista.paneles;

import java.beans.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
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
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorTipoEvento;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import independientes.image_slider.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import modelo.Ciudad;
import modelo.Estado;
import modelo.Etiqueta;
import modelo.Evento;
import modelo.Lugar;
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

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Estado estadoActual = null;
    public Ciudad ciudadActual = null;
    public TipoEvento tipoEventoActual = null;
    public Lugar lugarActual = null;
            
    public frmEtiquetas frm;
  

    public pnlEventos() {
        initComponents();
        txtPresupuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtCantInvitados.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        setMinDate();
        cargarEtiquetas();
        cargarTipoEvento();
        cargarEstado();
        cargarCiudad();
        cargarLugares();
        i.init(ScrollBar.VERTICAL);
        modoAdmin();
    }

    private void modoAdmin() {
        btnAdmin.setVisible(Constante.getAdmin());
        lblEditEstado.setVisible(Constante.getAdmin());
        lblEditEtiqueta.setVisible(Constante.getAdmin());
        lblEditLugar.setVisible(Constante.getAdmin());
        lblEditTipoEvento.setVisible(Constante.getAdmin());
        lblEditciudad.setVisible(Constante.getAdmin());
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
            cmbTipoEvento.addItem(e.getTematica());
        }
    }

    public void cargarEstado() {
        cmbEstado.removeAllItems();
        for (Estado e : ControladorEstado.getInstancia().obtenerListaByCadena("")) {
            cmbEstado.addItem(e.getEstado());
        }
    }

    public void cargarCiudad() {
        if (estadoActual == null) {
            return;
        }
        cmbCiudad.removeAllItems();
        for (Ciudad c : ControladorCiudad.getInstancia().obtenerListaByIDEstado(estadoActual.getIdEstado())) {
            cmbCiudad.addItem(c.getCiudad());
        }
    }

    public void cargarLugares() {
        if (ciudadActual == null) {
            return;
        }
        cmbLugar.removeAllItems();
        for (Lugar c : ControladorLugar.getInstancia().obtenerListaByIDCIudad(ciudadActual.getIdCiudad())) {
            cmbLugar.addItem(c.getNombreLocal());
        }
    }

    private void btnAdmin(ActionEvent e) {
        DialogEvento temp = new DialogEvento(Principal.getInstancia(), this);
        temp.setVisible(true);
    }

    private void cmbTipoEvento(ActionEvent e) {
        if (cmbTipoEvento.getSelectedIndex() != -1) {
            for(TipoEvento evento : ControladorTipoEvento.getInstancia().obtenerListaByCadena("")){
                if (evento.getTematica().equals(cmbTipoEvento.getSelectedItem().toString())) {
                    tipoEventoActual = evento;
                }
            }
        }
    }

    private void cmbEstado(ActionEvent e) {
        if (cmbEstado.getSelectedIndex() != -1) {
            for (Estado estado : ControladorEstado.getInstancia().obtenerListaByCadena("")) {
                if (estado.getEstado().equals(cmbEstado.getSelectedItem().toString())) {
                    estadoActual = estado;
                }
            }//

            cargarCiudad();
        }
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

    private void cmbCiudad() {
        if (cmbCiudad.getSelectedItem() != null && estadoActual != null) {
            for (Ciudad ciudad : ControladorCiudad.getInstancia().obtenerListaByIDEstado(estadoActual.getIdEstado())) {
                if (ciudad.getCiudad().equals(cmbCiudad.getSelectedItem().toString())) {
                    ciudadActual = ciudad;
                    cargarLugarImagenes();
                    cargarLugares();
                    break;
                }
            }
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

    private void cmbLugar(ActionEvent e) {
        if (cbOtro.isSelected()) {
            return;
        }
        if (cmbLugar.getSelectedIndex() != -1) {
            for(Lugar lu : ControladorLugar.getInstancia().obtenerListaByIDCIudad(ciudadActual.getIdCiudad())){
                if (lu.getNombreLocal().equals(cmbLugar.getSelectedItem().toString())) {
                    lugarActual = lu;
                }
            }
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
            validarDatos(txtPresupuesto, txtCantInvitados, cmbLugar, txtNombreEvento);
            Evento evento = new Evento();
            if (Constante.clienteTemporal != null) {
                evento.setIdCliente(Constante.clienteTemporal.getIdCliente());
            }else{
                evento.setIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente());
            }
            evento.setIdTipoEvento(ControladorTipoEvento.getInstancia().obtenerListaByCadena(cmbTipoEvento.getSelectedItem().toString()).get(0).getIdTipoEvento());
            evento.setFecha(getFecha());
            evento.setNoInvitados(Integer.parseInt(txtCantInvitados.getText().replaceAll(",", "")));
            evento.setPresupuesto(Integer.parseInt(txtPresupuesto.getText().replaceAll(",", "")));
            evento.setEstilo(txtEstilo.getText());
            evento.setNombreEvento(txtNombreEvento.getText());
            Lugar lu = new Lugar();
            lu.setIdCiudad(ciudadActual.getIdCiudad());
            if (cbOtro.isSelected()) {
                lu.setNombreLocal(cmbLugar.getEditor().getItem().toString());
                ControladorLugar.getInstancia().registrar(lu);
                evento.setIdLugar(ControladorLugar.getInstancia().obtenerLugarByLast().getIdLugar());
            } else {
                lu.setNombreLocal(cmbLugar.getSelectedItem().toString());
                evento.setIdLugar(ControladorLugar.getInstancia().obtenerLugarByCadena(lu).getIdLugar());
            }

            Mensaje m = ControladorEvento.getInstancia().registrar(evento);
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        } catch (MMException ee) {
            Constante.mensaje(ee.getMessage(), Message.Tipo.ERROR);
        }
    }

    private void validarDatos(JTextField txtPresupuesto, JTextField txtNoInvitados, JComboBox cmbLugar, JTextField txtNombreEvento) throws MMException {
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
        if (txtNombreEvento.getText().isEmpty()) {
            txtNombreEvento.requestFocus();
            throw new MMException("Nombre de evento vacio");
        }
        if (ControladorCliente.getInstancia().obtenerClienteActivo() == null) {
            throw new MMException("Sin cliente activo");
        }
    }

    private void setMinDate() {
        LocalDate d11 = LocalDate.parse(format.format(new Date()) + "", DateTimeFormatter.ISO_LOCAL_DATE);
        d11 = d11.plusDays(7);
        dateChooser.setSelectedDate(new SelectedDate(d11.getDayOfMonth(), d11.getMonthValue(), d11.getYear()));
    }

    private void dateChooserPropertyChange(PropertyChangeEvent e) {
        if (txtFecha.getText().length() > 0) {
            LocalDate d1 = LocalDate.parse(format.format(new Date()) + "", DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(txtFecha.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            long diffDays = diff.toDays();
            if (diffDays < 7) {
                Constante.mensaje("Minimo 1 semana de anticipacion", Message.Tipo.ADVERTENCIA);
                setMinDate();
            }
        }
    }

    public Date getFecha() {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, dateChooser.getSelectedDate().getYear());
        c1.set(Calendar.MONTH, dateChooser.getSelectedDate().getMonth() - 1);
        c1.set(Calendar.DAY_OF_MONTH, dateChooser.getSelectedDate().getDay());
        return c1.getTime();
    }

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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        lblNombreEvento = new JLabel();
        lblPresupuesto = new JLabel();
        lblCantInvitados = new JLabel();
        lblCaracteristicas = new JLabel();
        lblFecha = new JLabel();
        lblTipoEvento = new JLabel();
        lblEstado = new JLabel();
        lblCiudad = new JLabel();
        txtNombreEvento = new JTextField();
        txtPresupuesto = new JFormattedTextField();
        txtCantInvitados = new JFormattedTextField();
        pnlListEtiquetas = new JPanel();
        txtFecha = new JTextField();
        btnAdmin = new JButton();
        i = new ImageSlider();
        lblEstilo = new JLabel();
        txtEstilo = new JTextField();
        lblEditEtiqueta = new JLabel();
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
        dateChooser = new DateChooser();

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
            "[fill]" +
            "[fill]unrel"));

        //---- label1 ----
        label1.setText("Agregar Evento");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(label1, "cell 0 0, spanx, grow");

        //---- lblNombreEvento ----
        lblNombreEvento.setText("Nombre Evento");
        add(lblNombreEvento, "cell 0 1");

        //---- lblPresupuesto ----
        lblPresupuesto.setText("Presupuesto");
        lblPresupuesto.setFont(lblPresupuesto.getFont().deriveFont(lblPresupuesto.getFont().getSize() + 1f));
        add(lblPresupuesto, "cell 0 2");

        //---- lblCantInvitados ----
        lblCantInvitados.setText("Cant. Invitados");
        lblCantInvitados.setFont(lblCantInvitados.getFont().deriveFont(lblCantInvitados.getFont().getSize() + 1f));
        add(lblCantInvitados, "cell 0 3");

        //---- lblCaracteristicas ----
        lblCaracteristicas.setText("Caracteristicas de evento");
        lblCaracteristicas.setFont(lblCaracteristicas.getFont().deriveFont(lblCaracteristicas.getFont().getSize() + 1f));
        add(lblCaracteristicas, "cell 0 5");

        //---- lblFecha ----
        lblFecha.setText("Fecha");
        lblFecha.setFont(lblFecha.getFont().deriveFont(lblFecha.getFont().getSize() + 1f));
        add(lblFecha, "cell 0 6");

        //---- lblTipoEvento ----
        lblTipoEvento.setText("Tipo de evento");
        lblTipoEvento.setFont(lblTipoEvento.getFont().deriveFont(lblTipoEvento.getFont().getSize() + 1f));
        add(lblTipoEvento, "cell 0 7");

        //---- lblEstado ----
        lblEstado.setText("Estado");
        lblEstado.setFont(lblEstado.getFont().deriveFont(lblEstado.getFont().getSize() + 1f));
        add(lblEstado, "cell 0 8");

        //---- lblCiudad ----
        lblCiudad.setText("Ciudad");
        lblCiudad.setFont(lblCiudad.getFont().deriveFont(lblCiudad.getFont().getSize() + 1f));
        add(lblCiudad, "cell 0 9");

        //---- txtNombreEvento ----
        txtNombreEvento.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtNombreEvento.setHorizontalAlignment(SwingConstants.CENTER);
        txtNombreEvento.setNextFocusableComponent(txtPresupuesto);
        add(txtNombreEvento, "cell 1 1");

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

        //---- txtFecha ----
        txtFecha.setHorizontalAlignment(SwingConstants.CENTER);
        txtFecha.setFont(txtFecha.getFont().deriveFont(txtFecha.getFont().getSize() + 3f));
        txtFecha.setNextFocusableComponent(cmbTipoEvento);
        add(txtFecha, "cell 1 6");

        //---- btnAdmin ----
        btnAdmin.setIcon(new ImageIcon(getClass().getResource("/img/admin.png")));
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorderPainted(false);
        btnAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdmin.addActionListener(e -> btnAdmin(e));
        add(btnAdmin, "cell 2 1,grow 0 0");

        //---- i ----
        i.setBackground(Color.white);
        add(i, "cell 3 1 1 11");

        //---- lblEstilo ----
        lblEstilo.setText("Estilo");
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

        //---- cmbEstado ----
        cmbEstado.setNextFocusableComponent(cmbCiudad);
        cmbEstado.addActionListener(e -> cmbEstado(e));
        add(cmbEstado, "cell 1 8");

        //---- cmbCiudad ----
        cmbCiudad.setNextFocusableComponent(cmbLugar);
        cmbCiudad.addActionListener(e -> cmbCiudad());
        add(cmbCiudad, "cell 1 9");

        //---- cmbTipoEvento ----
        cmbTipoEvento.setNextFocusableComponent(cmbEstado);
        cmbTipoEvento.addActionListener(e -> cmbTipoEvento(e));
        add(cmbTipoEvento, "cell 1 7");

        //---- cmbLugar ----
        cmbLugar.setNextFocusableComponent(btnAceptar);
        cmbLugar.addActionListener(e -> cmbLugar(e));
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
        lblLugar.setFont(lblLugar.getFont().deriveFont(lblLugar.getFont().getSize() + 1f));
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

        //---- dateChooser ----
        dateChooser.setTextRefernce(txtFecha);
        dateChooser.setDateFormat("yyyy-MM-dd");
        dateChooser.addPropertyChangeListener(e -> dateChooserPropertyChange(e));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel lblNombreEvento;
    private JLabel lblPresupuesto;
    private JLabel lblCantInvitados;
    private JLabel lblCaracteristicas;
    private JLabel lblFecha;
    private JLabel lblTipoEvento;
    private JLabel lblEstado;
    private JLabel lblCiudad;
    public JTextField txtNombreEvento;
    public JFormattedTextField txtPresupuesto;
    public JFormattedTextField txtCantInvitados;
    public JPanel pnlListEtiquetas;
    public JTextField txtFecha;
    private JButton btnAdmin;
    public ImageSlider i;
    private JLabel lblEstilo;
    public JTextField txtEstilo;
    private JLabel lblEditEtiqueta;
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
    public DateChooser dateChooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
