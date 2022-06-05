package vista.principales;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import controlador.ControladorCliente;
import independientes.Constante;
import modelo.Cliente;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogUsuario;
import vista.paneles.pnlAbono;
import vista.paneles.pnlAgenda;
import vista.paneles.pnlCliente;
import vista.paneles.pnlCotizacion;
import vista.paneles.pnlEventos;
import vista.paneles.pnlEventosDestacados;
import vista.paneles.pnlProveedores;
import vista.paneles.pnlQuiz;

public class Principal extends JFrame {

    private static Principal instancia;
    private int mouseX, mouseY;

    public boolean admin = false;

    public static Principal getInstancia() {
        if (instancia == null) {
            instancia = new Principal();
        }
        return instancia;
    }

    private Principal() {
        initComponents();
        separator1.setForeground(Color.red);
        separator1.setBackground(Color.red.brighter());
        maximizar();
        new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getClienteActivo();
            }
        }).start();
        lblLogo.setMinimumSize(new Dimension(pnlBienvenida.getWidth(), pnlBienvenida.getHeight()/2));
        Icon imgLogo = new ImageIcon(new ImageIcon(getClass().getResource("/img/logo.png")).getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
        lblLogo.setIcon(imgLogo);
     

    }

    public void recargarPanelActivo() {
        if (pnlContenido.getComponents().length == 0) {
            return;
        }

        if (pnlProveedores.class == pnlContenido.getComponents()[0].getClass()) {
            pnlProveedores.getInstancia().recargar();
        } else if (pnlQuiz.class == pnlContenido.getComponents()[0].getClass()) {
            pnlQuiz.getInstancia().checkAdmin();
        }

    }

    protected void checkAdmin() {
        pnlCliente.getInstancia().checkAdmin();
        pnlQuiz.getInstancia().checkAdmin();
        pnlEventos.getInstancia().checkAdmin();
        pnlProveedores.getInstancia().checkAdmin();
        soyAdmin();
    }

    protected void soyAdmin() {
        if (!Constante.getAdmin()) {
            lblCliente.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            lblCliente.setToolTipText(null);
            return;
        }
        pnlAdmin.setVisible(true);
        lblCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblCliente.setToolTipText("Click para cerrar sesiones");
    }

    public Dimension getFontSize(FontMetrics metrics, Font font, String text) {
        // get the height of a line of text in this font and render context
        int hgt = metrics.getHeight();
        // get the advance of my text in this font and render context
        int adv = metrics.stringWidth(text);
        // calculate the size of a box to hold the text with some padding.
        Dimension size = new Dimension(adv + 2, hgt + 2);
        return size;
    }

    public Font findFont(Component component, Dimension componentSize, Font oldFont, String text) {
        //search up to 100
        Font savedFont = oldFont;
        for (int i = 0; i < 100; i++) {
            Font newFont = new Font(oldFont.getFontName(), oldFont.getStyle(), i);
            Dimension d = getFontSize(component.getFontMetrics(newFont), newFont, text);
            if (componentSize.height < d.height || componentSize.width < d.width) {
                return savedFont;
            }
            savedFont = newFont;
        }
        return oldFont;
    }

    public void getClienteActivo() {
        Cliente cliente = ControladorCliente.getInstancia().obtenerClienteActivo();
        if (cliente != null) {
            if (Constante.getClienteTemporal() != null && ControladorCliente.getInstancia().obtenerClienteActivo2() != null) {
                Cliente cliente2 = ControladorCliente.getInstancia().obtenerClienteActivo2();
                lblCliente.setText("Cliente (SOLO ADMIN): " + cliente.getNombre() + " " + cliente.getApellido() + " (" + cliente.getCorreo() + ") "
                        + " <<>> Cliente ACTIVO:" + cliente2.getNombre() + " " + cliente2.getApellido() + " (" + cliente2.getCorreo() + ")");
            } else if (Constante.getClienteTemporal() != null) {
                lblCliente.setText("Cliente (SOLO ADMIN): " + cliente.getNombre() + "  " + cliente.getApellido() + " (" + cliente.getCorreo() + ")");
            } else {
                lblCliente.setText("Cliente ACTIVO: " + cliente.getNombre() + "  " + cliente.getApellido() + " (" + cliente.getCorreo() + ") ");
            }
            lblCliente.setEnabled(true);
            if (!admin) {
                Principal.getInstancia().btnCliente.setEnabled(false);
                Principal.getInstancia().btnQuiz.setEnabled(true);
                Principal.getInstancia().btnEvento.setEnabled(true);
                Principal.getInstancia().btnProveedores.setEnabled(true);
            } else {
                Principal.getInstancia().btnCliente.setEnabled(true);

            }

        } else {
            lblCliente.setText("*Sin Cliente Activo*");
            lblCliente.setEnabled(false);
            if (!admin) {
                Principal.getInstancia().btnCliente.setEnabled(true);
                Principal.getInstancia().btnQuiz.setEnabled(false);
                Principal.getInstancia().btnEvento.setEnabled(false);
                Principal.getInstancia().btnProveedores.setEnabled(false);
            } else {
                Principal.getInstancia().btnCliente.setEnabled(true);
                Principal.getInstancia().btnQuiz.setEnabled(true);
                Principal.getInstancia().btnEvento.setEnabled(true);
                Principal.getInstancia().btnProveedores.setEnabled(true);
            }
        }
    }

    private void btnCliente(ActionEvent e) {
        cambiarPanel(pnlCliente.getInstancia());
        pnlCliente.getInstancia().checkAdmin();
    }

    private void cambiarPanel(JPanel pnl) {
        pnlContenido.removeAll();
        pnlContenido.add(pnl);
        pnlContenido.revalidate();
        pnlContenido.repaint();

    }

    private void btnEvento(ActionEvent e) {
        cambiarPanel(pnlEventos.getInstancia());
        pnlEventos.getInstancia().checkAdmin();
    }

    private void btnProveedores(ActionEvent e) {
        cambiarPanel(pnlProveedores.getInstancia());
        pnlProveedores.getInstancia().checkAdmin();
        pnlProveedores.getInstancia().recargar();

    }

    private void btnPago(ActionEvent e) {
        cambiarPanel(pnlAbono.getInstancia());
        pnlAbono.getInstancia().cargarEventos();
    }

    private void pnlMenuMouseDragged(MouseEvent e) {
        this.setLocation(this.getX() + e.getX() - mouseX, this.getY() + e.getY() - mouseY);
    }

    private void pnlMenuMousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    private void btnQuiz(ActionEvent e) {
        cambiarPanel(pnlQuiz.getInstancia());
        pnlQuiz.getInstancia().checkAdmin();
    }

    private void btnAgenda(ActionEvent e) {
        cambiarPanel(pnlAgenda.getInstancia());
    }

    private void btnEventosDestacados(ActionEvent e) {
        cambiarPanel(pnlEventosDestacados.getInstancia());
        pnlEventosDestacados.getInstancia().checkAdmin();
    }

    private void maximizar() {
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(screensize.getSize());
        setLocationRelativeTo(null);

    }

    private void btnUsuarios(ActionEvent e) {
        new DialogUsuario().setVisible(true);
    }
    // Creating well formed document

//    private org.w3c.dom.Document createWellFormedHtml(File inputHTML) throws IOException {
//        Document document = Jsoup.parse(inputHTML, "UTF-8");
//        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
//        System.out.println("HTML parsing done...");
//        return new W3CDom().fromJsoup(document);
//    }
//
//    private void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {
//        try ( // base URI to resolve future resources
//                OutputStream os = new FileOutputStream(outputPdf)) {
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.withUri(outputPdf);
//            builder.toStream(os);
//            // add external font
////    builder.useFont(new File(getClass().getClassLoader().getResource("fonts/PRISTINA.ttf").getFile()), "PRISTINA");
//            builder.withW3cDocument(doc, "src/modelo/");
//            builder.run();
//            System.out.println("PDF creation completed");
//        }
//    }
    private void button1(ActionEvent e) {
//        try {
//            // HTML file - Input
//            File inputHTML = new File("src/modelo/index.html");
//            // Converted PDF file - Output
//            String outputPdf = "cotizacion.pdf";
//
//            //create well formed HTML
//            org.w3c.dom.Document doc = createWellFormedHtml(inputHTML);
//            System.out.println("Starting conversion to PDF...");
//            xhtmlToPdf(doc, outputPdf);
//        } catch (IOException ex) {
//            System.out.println("Error while converting HTML to PDF " + ex.getMessage());
//            ex.printStackTrace();
//        }

    }

    private void btnMinimizarMouseClicked(MouseEvent e) {
        this.setState(ICONIFIED);
    }

    private void lblMaximizarMouseClicked(MouseEvent e) {
        maximizar();
    }

    private void lblSalirMouseClicked(MouseEvent e) {
        String[] opciones = new String[]{"Si", "Iniciar Sesion", "Cancelar"};
        int x = JOptionPane.showOptionDialog(null, "¿Seguro que desea salir?", "Seleccione una opcion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        if (x == 0) {
            ControladorCliente.getInstancia().desactivarClienteActivo();
            System.exit(0);
        } else if (x == 1) {
            new Inicio(getInstancia(), true).setVisible(true);
        }

    }

    private void lblClienteMouseClicked(MouseEvent e) {
        if (!admin) {
            return;
        }
        if (Constante.getClienteTemporal() != null && ControladorCliente.getInstancia().obtenerClienteActivo2() != null) {
            String[] opciones = new String[]{"Cliente Temporal", "Cliente Normal", "Ambos"};
            int x = JOptionPane.showOptionDialog(null, "¿Que Cliente desea cerrar?", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (x) {
                case 0:
                    Constante.removeClienteTemporal();
                    recargarPanelActivo();
                    break;
                case 1:
                    ControladorCliente.getInstancia().desactivarClienteActivo();
                    recargarPanelActivo();
                    break;
                case 2:
                    Constante.removeClienteTemporal();
                    ControladorCliente.getInstancia().desactivarClienteActivo();
                    recargarPanelActivo();
                    break;
                default:
                    return;
            }

        } else if (Constante.getClienteTemporal() != null) {
            lblCliente.setEnabled(false);
            Constante.removeClienteTemporal();
            recargarPanelActivo();
        } else if (ControladorCliente.getInstancia().obtenerClienteActivo2() != null) {
            lblCliente.setEnabled(false);
            ControladorCliente.getInstancia().desactivarClienteActivo();
            recargarPanelActivo();
        }
        lblCliente.setEnabled(false);
        lblCliente.setText("Actualizando...");
//                else if (Constante.getClienteTemporal() == null && ControladorCliente.getInstancia().obtenerClienteActivo2() == null) {
//                    Constante.mensaje("Nada por hacer", Message.Tipo.ADVERTENCIA);
//                }

    }

    private void btnCotizacion(ActionEvent e) {
        cambiarPanel(pnlCotizacion.getInstancia());
        pnlCotizacion.getInstancia().cargarEventos();
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlOpciones = new JPanel();
        separator1 = new JPopupMenu.Separator();
        pnlBienvenida = new JPanel();
        lblTitulo = new JLabel();
        lblLogo = new JLabel();
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
        btnMinimizar = new SvgIcon();
        lblMaximizar = new SvgIcon();
        lblSalir = new SvgIcon();
        lblPresupuesto = new JLabel();
        pnlContenido = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setTitle("Marina Meza Project");
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

                //---- lblTitulo ----
                lblTitulo.setText("<html> <H2 align=\"center\"> Bienvenido Marina Meza </H2></html>");
                lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
                lblTitulo.setMaximumSize(new Dimension(150, 70));
                lblTitulo.setMinimumSize(new Dimension(150, 70));
                lblTitulo.setPreferredSize(new Dimension(150, 170));
                lblTitulo.setRequestFocusEnabled(false);
                lblTitulo.setBackground(Color.pink);
                lblTitulo.setVerticalAlignment(SwingConstants.TOP);
                pnlBienvenida.add(lblTitulo, BorderLayout.SOUTH);

                //---- lblLogo ----
                lblLogo.setPreferredSize(new Dimension(150, 0));
                pnlBienvenida.add(lblLogo, BorderLayout.CENTER);
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
                btnCliente.setFont(new Font("Constantia", btnCliente.getFont().getStyle(), btnCliente.getFont().getSize()));
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
                pnlAdmin.setVisible(false);
                pnlAdmin.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "0[]0",
                    // rows
                    "[]" +
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
                btnCotizacion.addActionListener(e -> btnCotizacion(e));
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
            lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblCliente.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lblClienteMouseClicked(e);
                }
            });
            pnlMenu.add(lblCliente, BorderLayout.LINE_START);

            //======== panel1 ========
            {
                panel1.setBackground(Color.pink);
                panel1.setLayout(new BorderLayout());

                //---- btnMinimizar ----
                btnMinimizar.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\minimize.svg"));
                btnMinimizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnMinimizar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnMinimizarMouseClicked(e);
                    }
                });
                panel1.add(btnMinimizar, BorderLayout.WEST);

                //---- lblMaximizar ----
                lblMaximizar.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\maximize.svg"));
                lblMaximizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblMaximizar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lblMaximizarMouseClicked(e);
                    }
                });
                panel1.add(lblMaximizar, BorderLayout.CENTER);

                //---- lblSalir ----
                lblSalir.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\close.svg"));
                lblSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblSalir.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lblSalirMouseClicked(e);
                    }
                });
                panel1.add(lblSalir, BorderLayout.EAST);
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
    public JPanel pnlOpciones;
    private JPopupMenu.Separator separator1;
    private JPanel pnlBienvenida;
    protected JLabel lblTitulo;
    private JLabel lblLogo;
    private JPanel pnlOpcionesCliente;
    public Buttont btnCliente;
    public Buttont btnQuiz;
    public Buttont btnEvento;
    public Buttont btnProveedores;
    public Buttont btnEventosDestacados;
    protected JPanel pnlAdmin;
    private Buttont btnPago;
    private Buttont btnCotizacion;
    private Buttont btnAgenda;
    private Buttont btnUsuarios;
    public JPanel pnlMenu;
    public JLabel lblCliente;
    private JPanel panel1;
    private SvgIcon btnMinimizar;
    private SvgIcon lblMaximizar;
    private SvgIcon lblSalir;
    public JLabel lblPresupuesto;
    public JPanel pnlContenido;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
