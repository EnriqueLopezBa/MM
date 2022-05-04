package vista.principales;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import controlador.ControladorCliente;
import modelo.Cliente;
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
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(screensize.getSize());
        setLocationRelativeTo(null);

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
        }else{
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlOpciones = new JPanel();
        panel6 = new JPanel();
        label4 = new JLabel();
        btnCliente = new Buttont();
        btnEvento = new Buttont();
        btnAgenda = new Buttont();
        btnProveedores = new Buttont();
        btnPago = new Buttont();
        btnQuiz = new Buttont();
        btnEventosDestacados = new Buttont();
        pnlMenu = new JPanel();
        lblCliente = new JLabel();
        panel1 = new JPanel();
        btnCerrar = new JButton();
        btnMinimizar = new JButton();
        lblPresupuesto = new JLabel();
        pnlContenido = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
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
            pnlOpciones.setLayout(null);

            //======== panel6 ========
            {
                panel6.setBackground(Color.pink);
                panel6.setLayout(new BorderLayout());

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
                panel6.add(label4, BorderLayout.CENTER);
            }
            pnlOpciones.add(panel6);
            panel6.setBounds(new Rectangle(new Point(0, 0), panel6.getPreferredSize()));

            //---- btnCliente ----
            btnCliente.setText("Cliente");
            btnCliente.setHoverColor(new Color(102, 153, 255));
            btnCliente.addActionListener(e -> btnCliente(e));
            pnlOpciones.add(btnCliente);
            btnCliente.setBounds(0, 170, 150, 35);

            //---- btnEvento ----
            btnEvento.setText("Evento");
            btnEvento.setHoverColor(new Color(102, 153, 255));
            btnEvento.addActionListener(e -> btnEvento(e));
            pnlOpciones.add(btnEvento);
            btnEvento.setBounds(0, 240, 150, 35);

            //---- btnAgenda ----
            btnAgenda.setText("Agenda");
            btnAgenda.setHoverColor(new Color(102, 153, 255));
            btnAgenda.addActionListener(e -> btnAgenda(e));
            pnlOpciones.add(btnAgenda);
            btnAgenda.setBounds(0, 420, 150, 35);

            //---- btnProveedores ----
            btnProveedores.setText("Proveedores");
            btnProveedores.setHoverColor(new Color(102, 153, 255));
            btnProveedores.addActionListener(e -> btnProveedores(e));
            pnlOpciones.add(btnProveedores);
            btnProveedores.setBounds(0, 275, 150, 35);

            //---- btnPago ----
            btnPago.setText("Pago/Abono");
            btnPago.setHoverColor(new Color(102, 153, 255));
            btnPago.addActionListener(e -> btnPago(e));
            pnlOpciones.add(btnPago);
            btnPago.setBounds(0, 385, 150, 35);

            //---- btnQuiz ----
            btnQuiz.setText("Quiz");
            btnQuiz.setHoverColor(new Color(102, 153, 255));
            btnQuiz.addActionListener(e -> btnQuiz(e));
            pnlOpciones.add(btnQuiz);
            btnQuiz.setBounds(0, 205, 150, 35);

            //---- btnEventosDestacados ----
            btnEventosDestacados.setText("Eventos Destacados");
            btnEventosDestacados.setHoverColor(new Color(102, 153, 255));
            btnEventosDestacados.addActionListener(e -> btnEventosDestacados(e));
            pnlOpciones.add(btnEventosDestacados);
            btnEventosDestacados.setBounds(0, 335, 150, 35);
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
                btnCerrar.setIcon(new ImageIcon(getClass().getResource("/img/close.png")));
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
                btnMinimizar.setIcon(new ImageIcon(getClass().getResource("/img/minimize.png")));
                btnMinimizar.setOpaque(true);
                btnMinimizar.setContentAreaFilled(false);
                btnMinimizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnMinimizar.setMargin(new Insets(5, 30, 2, 30));
                btnMinimizar.addActionListener(e -> btnMinimizar(e));
                panel1.add(btnMinimizar, BorderLayout.WEST);
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
        buttonGroup1.add(btnEvento);
        buttonGroup1.add(btnAgenda);
        buttonGroup1.add(btnProveedores);
        buttonGroup1.add(btnPago);
        buttonGroup1.add(btnQuiz);
        buttonGroup1.add(btnEventosDestacados);
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
    private JPanel panel6;
    protected JLabel label4;
    protected Buttont btnCliente;
    public Buttont btnEvento;
    private Buttont btnAgenda;
    private Buttont btnProveedores;
    private Buttont btnPago;
    private Buttont btnQuiz;
    private Buttont btnEventosDestacados;
    public JPanel pnlMenu;
    public JLabel lblCliente;
    private JPanel panel1;
    private JButton btnCerrar;
    private JButton btnMinimizar;
    public JLabel lblPresupuesto;
    protected JPanel pnlContenido;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
