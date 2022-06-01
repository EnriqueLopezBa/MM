package vista.principales;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.TextField;
import Componentes.BotonGradiente;
import Componentes.PasswordField;
import controlador.ControladorUsuario;
import independientes.Constante;
import modelo.Usuario;
import net.miginfocom.swing.*;

/**
 * @author das
 */
public class Inicio extends JDialog {

    private Usuario u;

    public Inicio(Principal owner, boolean modal) {
        super(owner, modal);
        initComponents();
        super.getContentPane().setBackground(Color.white);
    }

    private void iniciar() {
        u = new Usuario();
        u.setCorreo(txtCorreo.getText());
        u.setClave(new String(txtClave.getPassword()));
        u = ControladorUsuario.getInstancia().inicioSesion(u);
        if (u == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lblError.setVisible(true);
                        Thread.sleep(2000);
                        lblError.setVisible(false);
                    } catch (Exception e) {
                    }
                }
            }).start();

        } else {
            Principal.getInstancia().admin = true;
            if (Constante.getInterfazActiva() != null) {
                 Principal.getInstancia().checkAdmin();
            }
            this.dispose();
            Principal.getInstancia().lblTitulo.setText("<html> <H2 align=\"center\"> Bienvenido " + u.getNombre() + " " + u.getApellido() + " </H2></html>");

        }

    }

    private void passwordKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            iniciar();
        }
    }

    private void botonGradiente1MouseClicked() {
        iniciar();
    }

    private void thisWindowClosing(WindowEvent e) {

    }

    private void thisWindowClosed(WindowEvent e) {
        if (u == null || u.getIdSuario() == null) {
            Principal.getInstancia().admin = false;
        }
        Principal.getInstancia().checkAdmin();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel2 = new JPanel();
        txtCorreo = new TextField();
        txtClave = new PasswordField();
        botonGradiente1 = new BotonGradiente();
        lblError = new JLabel();

        //======== this ========
        setAlwaysOnTop(true);
        setType(Window.Type.POPUP);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("INICIAR SESION");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setName("cc");
            panel2.setLayout(new MigLayout(
                "flowy",
                // columns
                "[grow, align center]",
                // rows
                "[fill]rel" +
                "[fill]para" +
                "[fill]"));

            //---- txtCorreo ----
            txtCorreo.setLabelText("Correo");
            txtCorreo.setText("mm@gmail.com");
            txtCorreo.setNextFocusableComponent(txtClave);
            panel2.add(txtCorreo, "cell 0 0, w 80%");

            //---- txtClave ----
            txtClave.setLabelText("Contrase\u00f1a");
            txtClave.setNextFocusableComponent(botonGradiente1);
            txtClave.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    passwordKeyPressed(e);
                }
            });
            panel2.add(txtClave, "cell 0 1, w 80%");

            //---- botonGradiente1 ----
            botonGradiente1.setPTexto("Aceptar");
            botonGradiente1.setPBordesRedondo(true);
            botonGradiente1.setPNivelRedondo1(40);
            botonGradiente1.setPGradiante1(new Color(255, 204, 255));
            botonGradiente1.setPGradiante2(new Color(255, 204, 204));
            botonGradiente1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            botonGradiente1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    botonGradiente1MouseClicked();
                }
            });
            panel2.add(botonGradiente1, "cell 0 2, w 70%, h 30%!");

            //---- lblError ----
            lblError.setText("Datos erroneos");
            lblError.setForeground(Color.red);
            lblError.setVisible(false);
            panel2.add(lblError, "cell 0 2");
        }
        contentPane.add(panel2, BorderLayout.CENTER);
        setSize(362, 262);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel2;
    private TextField txtCorreo;
    private PasswordField txtClave;
    private BotonGradiente botonGradiente1;
    private JLabel lblError;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
