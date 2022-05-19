package vista.principales;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import controlador.ControladorCliente;
import modelo.Cliente;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogUsuario;
import vista.paneles.pnlAbono;
import vista.paneles.pnlAgenda;
import vista.paneles.pnlCliente;
import vista.paneles.pnlEventos;
import vista.paneles.pnlEventosDestacados;
import vista.paneles.pnlProveedores;
import vista.paneles.pnlQuiz;

public class Principal extends JFrame {

    private static Principal instancia;
    private int mouseX, mouseY;

    public boolean admin = false;
    public pnlEventos pnlEventos = null;

    public static Principal getInstancia() {
        if (instancia == null) {
            instancia = new Principal();
        }
        return instancia;
    }

    private Principal() {
        initComponents();
       separator1.setForeground(Color.red); // top line color
       separator1.setBackground(Color.red.brighter());
        maximizar();
        new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (admin) {
                    ((Timer) e.getSource()).stop();
                }
                getClienteActivo();
            }
        }).start();

    }

    public void getClienteActivo() {
        Cliente cliente = ControladorCliente.getInstancia().obtenerClienteActivo();
        if (cliente != null) {
            lblCliente.setText("Cliente activo: " + cliente.getCorreo() + " - " + cliente.getNombre() + " " + cliente.getApellido());
        } else {
            lblCliente.setText("Cliente Activo: ");
        }
    }

    private void thisWindowIconified(WindowEvent e) {
        // TODO add your code here
    }

    private void thisWindowDeiconified(WindowEvent e) {
        // TODO add your code here
    }

    private void label4MouseClicked() {
        // TODO add your code here
    }

    private void btnCliente(ActionEvent e) {
        cambiarPanel(new pnlCliente());
    }

    private void cambiarPanel(JPanel pnl) {
        pnlContenido.removeAll();
        pnlContenido.add(pnl);
        pnlContenido.revalidate();

    }

    private void btnEvento(ActionEvent e) {
        pnlEventos = new pnlEventos();
        cambiarPanel(pnlEventos);
    }

    private void btnProveedores(ActionEvent e) {
        cambiarPanel(new pnlProveedores());
    }

    private void btnPago(ActionEvent e) {
        cambiarPanel(new pnlAbono());
    }

    private void btnModoOscuro(ActionEvent e) {
        // TODO add your code here
    }

    private void pnlMenuMouseDragged(MouseEvent e) {
        this.setLocation(this.getX() + e.getX() - mouseX, this.getY() + e.getY() - mouseY);
    }

    private void pnlMenuMousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    private void btnCerrar(ActionEvent e) {
//        controladorCliente.desactivarClienteActivo();
        System.exit(0);
    }

    private void btnMinimizar(ActionEvent e) {
        this.setState(ICONIFIED);
    }

    private void btnQuiz(ActionEvent e) {
        cambiarPanel(new pnlQuiz());
    }

    private void btnAgenda(ActionEvent e) {
        cambiarPanel(new pnlAgenda());
    }

    private void btnEventosDestacados(ActionEvent e) {
        cambiarPanel(new pnlEventosDestacados());
    }

    private void maximizar() {
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(screensize.getSize());
        setLocationRelativeTo(null);

    }

    private void btnMaximizar(ActionEvent e) {
        maximizar();
    }

    private void btnUsuarios(ActionEvent e) {
       new DialogUsuario().setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlOpciones = new JPanel();
        separator1 = new JPopupMenu.Separator();
        pnlBienvenida = new JPanel();
        label4 = new JLabel();
        pnlOpcionesCliente = new JPanel();
        btnCliente = new Buttont();
        btnQuiz = new Buttont();
        btnEvento = new Buttont();
        btnProveedores = new Buttont();
        btnEventosDestacados = new Buttont();
        pnlAdmin = new JPanel();
        btnPago = new Buttont();
        btnCotizacion = new Buttont();
        btnAgenda = new Buttont();
        btnUsuarios = new Buttont();
        pnlMenu = new JPanel();
        lblCliente = new JLabel();
        panel1 = new JPanel();
        btnCerrar = new JButton();
        btnMinimizar = new JButton();
        btnMaximizar = new JButton();
        lblPresupuesto = new JLabel();
        pnlContenido = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setTitle("Marina Meza Project");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                thisWindowDeiconified(e);
            }
            @Override
            public void windowIconified(WindowEvent e) {
                thisWindowIconified(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== pnlOpciones ========
        {
            pnlOpciones.setBackground(Color.pink);
            pnlOpciones.setMinimumSize(new Dimension(150, 81));
            pnlOpciones.setPreferredSize(new Dimension(150, 81));
            pnlOpciones.setAlignmentY(0.0F);
            pnlOpciones.setLayout(new MigLayout(
                "fill",
                // columns
                "0[fill]",
                // rows
                "[fill]0" +
                "[fill]0" +
                "[fill]0" +
                "[fill]"));
            pnlOpciones.add(separator1, "cell 0 2, growx, growy 0");

            //======== pnlBienvenida ========
            {
                pnlBienvenida.setBackground(Color.pink);
                pnlBienvenida.setLayout(new BorderLayout());

                //---- label4 ----
                label4.setText("<html> <H2 align=\"center\"> Bienvenido Marina Meza </H2></html>");
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                label4.setMaximumSize(new Dimension(150, 70));
                label4.setMinimumSize(new Dimension(150, 70));
                label4.setPreferredSize(new Dimension(150, 170));
                label4.setRequestFocusEnabled(false);
                label4.setBackground(Color.pink);
                label4.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        label4MouseClicked();
                    }
                });
                pnlBienvenida.add(label4, BorderLayout.CENTER);
            }
            pnlOpciones.add(pnlBienvenida, "cell 0 0");

            //======== pnlOpcionesCliente ========
            {
                pnlOpcionesCliente.setBackground(Color.pink);
                pnlOpcionesCliente.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "0[]0",
                    // rows
                    "[grow,fill]0" +
                    "[grow,fill]0" +
                    "[grow,fill]0" +
                    "[grow,fill]0" +
                    "[grow,fill]"));

                //---- btnCliente ----
                btnCliente.setText("Cliente");
                btnCliente.setHoverColor(new Color(102, 153, 255));
                btnCliente.addActionListener(e -> btnCliente(e));
                pnlOpcionesCliente.add(btnCliente, "cell 0 0, growx");

                //---- btnQuiz ----
                btnQuiz.setText("Quiz");
                btnQuiz.setHoverColor(new Color(102, 153, 255));
                btnQuiz.addActionListener(e -> btnQuiz(e));
                pnlOpcionesCliente.add(btnQuiz, "cell 0 1, grow");

                //---- btnEvento ----
                btnEvento.setText("Evento");
                btnEvento.setHoverColor(new Color(102, 153, 255));
                btnEvento.addActionListener(e -> btnEvento(e));
                pnlOpcionesCliente.add(btnEvento, "cell 0 2, grow");

                //---- btnProveedores ----
                btnProveedores.setText("Proveedores");
                btnProveedores.setHoverColor(new Color(102, 153, 255));
                btnProveedores.addActionListener(e -> btnProveedores(e));
                pnlOpcionesCliente.add(btnProveedores, "cell 0 3, grow");

                //---- btnEventosDestacados ----
                btnEventosDestacados.setText("Eventos Destacados");
                btnEventosDestacados.setHoverColor(new Color(102, 153, 255));
                btnEventosDestacados.addActionListener(e -> btnEventosDestacados(e));
                pnlOpcionesCliente.add(btnEventosDestacados, "cell 0 4, grow");
            }
            pnlOpciones.add(pnlOpcionesCliente, "cell 0 1");

            //======== pnlAdmin ========
            {
                pnlAdmin.setBackground(Color.pink);
                pnlAdmin.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "0[]0",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- btnPago ----
                btnPago.setText("Pago/Abono");
                btnPago.setHoverColor(new Color(102, 153, 255));
                btnPago.addActionListener(e -> btnPago(e));
                pnlAdmin.add(btnPago, "cell 0 0, grow");

                //---- btnCotizacion ----
                btnCotizacion.setText("Cotizacion");
                btnCotizacion.setHoverColor(new Color(102, 153, 255));
                pnlAdmin.add(btnCotizacion, "cell 0 1, grow");

                //---- btnAgenda ----
                btnAgenda.setText("Agenda");
                btnAgenda.setHoverColor(new Color(102, 153, 255));
                btnAgenda.addActionListener(e -> btnAgenda(e));
                pnlAdmin.add(btnAgenda, "cell 0 2,grow");

                //---- btnUsuarios ----
                btnUsuarios.setText("Usuarios");
                btnUsuarios.setHoverColor(new Color(102, 153, 255));
                btnUsuarios.addActionListener(e -> btnUsuarios(e));
                pnlAdmin.add(btnUsuarios, "cell 0 3,grow");
            }
            pnlOpciones.add(pnlAdmin, "cell 0 3");
        }
        contentPane.add(pnlOpciones, BorderLayout.WEST);

        //======== pnlMenu ========
        {
            pnlMenu.setBackground(Color.pink);
            pnlMenu.setPreferredSize(new Dimension(245, 50));
            pnlMenu.setMinimumSize(new Dimension(246, 50));
            pnlMenu.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    pnlMenuMouseDragged(e);
                }
            });
            pnlMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnlMenuMousePressed(e);
                }
            });
            pnlMenu.setLayout(new BorderLayout());

            //---- lblCliente ----
            lblCliente.setText("Cliente Activo: ");
            pnlMenu.add(lblCliente, BorderLayout.LINE_START);

            //======== panel1 ========
            {
                panel1.setBackground(Color.pink);
                panel1.setLayout(new BorderLayout());

                //---- btnCerrar ----
                btnCerrar.setBorderPainted(false);
                btnCerrar.setBorder(null);
                btnCerrar.setMaximumSize(new Dimension(30, 21));
                btnCerrar.setMinimumSize(new Dimension(50, 21));
                btnCerrar.setPreferredSize(new Dimension(50, 21));
                btnCerrar.setIcon(new ImageIcon(getClass().getResource("/img/icons8_Close_48px.png")));
                btnCerrar.setOpaque(true);
                btnCerrar.setContentAreaFilled(false);
                btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnCerrar.setMargin(new Insets(5, 30, 2, 30));
                btnCerrar.addActionListener(e -> btnCerrar(e));
                panel1.add(btnCerrar, BorderLayout.EAST);

                //---- btnMinimizar ----
                btnMinimizar.setBorderPainted(false);
                btnMinimizar.setBorder(null);
                btnMinimizar.setMaximumSize(new Dimension(30, 21));
                btnMinimizar.setMinimumSize(new Dimension(50, 21));
                btnMinimizar.setPreferredSize(new Dimension(50, 21));
                btnMinimizar.setIcon(new ImageIcon(getClass().getResource("/img/icons8_minimize_window_48px_1.png")));
                btnMinimizar.setOpaque(true);
                btnMinimizar.setContentAreaFilled(false);
                btnMinimizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnMinimizar.setMargin(new Insets(5, 30, 2, 30));
                btnMinimizar.addActionListener(e -> btnMinimizar(e));
                panel1.add(btnMinimizar, BorderLayout.WEST);

                //---- btnMaximizar ----
                btnMaximizar.setIcon(new ImageIcon(getClass().getResource("/img/icons8_maximize_window_48px.png")));
                btnMaximizar.setBorderPainted(false);
                btnMaximizar.setContentAreaFilled(false);
                btnMaximizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnMaximizar.addActionListener(e -> btnMaximizar(e));
                panel1.add(btnMaximizar, BorderLayout.CENTER);
            }
            pnlMenu.add(panel1, BorderLayout.LINE_END);

            //---- lblPresupuesto ----
            lblPresupuesto.setText("Presupuesto:");
            lblPresupuesto.setHorizontalAlignment(SwingConstants.RIGHT);
            lblPresupuesto.setFont(new Font("Segoe UI", Font.BOLD, 16));
            pnlMenu.add(lblPresupuesto, BorderLayout.CENTER);
        }
        contentPane.add(pnlMenu, BorderLayout.NORTH);

        //======== pnlContenido ========
        {
            pnlContenido.setBackground(Color.white);
            pnlContenido.setName("pnlContenido");
            pnlContenido.setLayout(new BorderLayout());
        }
        contentPane.add(pnlContenido, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(btnCliente);
        buttonGroup1.add(btnQuiz);
        buttonGroup1.add(btnEvento);
        buttonGroup1.add(btnProveedores);
        buttonGroup1.add(btnEventosDestacados);
        buttonGroup1.add(btnPago);
        buttonGroup1.add(btnCotizacion);
        buttonGroup1.add(btnAgenda);
        buttonGroup1.add(btnUsuarios);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println(e.getMessage());
        }
        getInstancia().setVisible(true);
        new Inicio(getInstancia(), true).setVisible(true);
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlOpciones;
    private JPopupMenu.Separator separator1;
    private JPanel pnlBienvenida;
    protected JLabel label4;
    private JPanel pnlOpcionesCliente;
    protected Buttont btnCliente;
    private Buttont btnQuiz;
    public Buttont btnEvento;
    private Buttont btnProveedores;
    private Buttont btnEventosDestacados;
    private JPanel pnlAdmin;
    private Buttont btnPago;
    private Buttont btnCotizacion;
    private Buttont btnAgenda;
    private Buttont btnUsuarios;
    public JPanel pnlMenu;
    public JLabel lblCliente;
    private JPanel panel1;
    private JButton btnCerrar;
    private JButton btnMinimizar;
    private JButton btnMaximizar;
    public JLabel lblPresupuesto;
    protected JPanel pnlContenido;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
