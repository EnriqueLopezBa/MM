package vista.principales;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.TextField;
import Componentes.BotonGradiente;
import Componentes.PasswordField;
import controlador.ControladorUsuario;
import modelo.Usuario;

/**
 * @author das
 */
public class Inicio extends JDialog {

    private ControladorUsuario controlador = new ControladorUsuario();
    private Usuario u;

    public Inicio(Principal owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    private void iniciar() {
        u = new Usuario();
        u.setCorreo(txtCorreo.getText());
        u.setClave(txtClave.getText());
        u = controlador.inicioSesion(u);
        this.dispose();

    }

    private void passwordKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            iniciar();
        }
    }

    private void botonGradiente1MouseClicked() {
        iniciar();
    }

    private void label3MouseClicked() {
        // TODO add your code here
    }

    private void thisWindowClosing(WindowEvent e) {
        
        if(u == null ||  u.getIdSuario() == null) {
            System.exit(0);
        }
       
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel2 = new JPanel();
        txtCorreo = new TextField();
        txtClave = new PasswordField();
        botonGradiente1 = new BotonGradiente();
        btnCliente = new JLabel();
        lblError = new JLabel();

        //======== this ========
        setAlwaysOnTop(true);
        setType(Window.Type.POPUP);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
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

            //---- txtCorreo ----
            txtCorreo.setLabelText("Correo");
            txtCorreo.setText("mm@gmail.com");
            txtCorreo.setNextFocusableComponent(txtClave);

            //---- txtClave ----
            txtClave.setLabelText("Contrase\u00f1a");
            txtClave.setNextFocusableComponent(botonGradiente1);
            txtClave.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    passwordKeyPressed(e);
                }
            });

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

            //---- btnCliente ----
            btnCliente.setText("Iniciar como Cliente");
            btnCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnCliente.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    label3MouseClicked();
                }
            });

            //---- lblError ----
            lblError.setText("Datos erroneos");
            lblError.setForeground(Color.red);
            lblError.setVisible(false);

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(panel2Layout.createParallelGroup()
                            .addComponent(txtCorreo, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(botonGradiente1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtClave, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                        .addGap(122, 122, 122))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnCliente))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(lblError)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCliente)
                        .addGap(33, 33, 33)
                        .addComponent(txtCorreo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(txtClave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(lblError)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonGradiente1, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
            );
        }
        contentPane.add(panel2, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel2;
    private TextField txtCorreo;
    private PasswordField txtClave;
    private BotonGradiente botonGradiente1;
    private JLabel btnCliente;
    private JLabel lblError;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
