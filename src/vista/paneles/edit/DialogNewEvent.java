package vista.paneles.edit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import Componentes.DropShadow.*;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorEvento;
import independientes.Constante;
import independientes.Mensaje;
import static javax.swing.JOptionPane.showMessageDialog;
import modelo.Evento;
import net.miginfocom.swing.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogNewEvent extends JDialog {

    public DialogNewEvent(JFrame owner) {
        super(owner);
        initComponents();
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.getContentPane().setBackground(new Color(231, 245, 254));
        super.setMinimumSize(new Dimension(new Double(screensize.getSize().width / 3.5).intValue(), new Double(screensize.getSize().height / 1.6).intValue()));
        super.setLocationRelativeTo(null);
    }


    private void btnAceptar(ActionEvent e) {
        aceptar();
    }

    private void aceptar() {
        Evento evento = new Evento();
        evento.setIdCliente(Constante.getClienteActivo().getIdCliente());
        if (txtNombreEvento.getText().isEmpty()) {
            Constante.mensaje("Ingresa un nombre", Message.Tipo.ADVERTENCIA);
            txtNombreEvento.requestFocus();
            return;
        }

        evento.setNombreEvento(txtNombreEvento.getText());
        Mensaje m = ControladorEvento.getInstancia().registrar(evento);
        if (m.getTipoMensaje() == Tipo.OK) {
            dispose();
            Principal.getInstancia().btnQuiz.doClick();
        }
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

    }

    private void txtNombreEventoKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            aceptar();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panelShadow2 = new PanelShadow();
        lblIcono = new SvgIcon();
        lblTitulo = new JLabel();
        lblTexto = new JLabel();
        txtNombreEvento = new JTextField();
        panelShadow1 = new PanelShadow();
        btnAceptar = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(204, 204, 255));
        setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panelShadow2 ========
        {
            panelShadow2.setBackground(Color.white);
            panelShadow2.setShadowSize(1);
            panelShadow2.setShadowColor(new Color(0, 124, 235));
            panelShadow2.setForeground(Color.magenta);
            panelShadow2.setShadowOpacity(0.0F);
            panelShadow2.setLayout(new MigLayout(
                "fill",
                // columns
                "25[fill]20",
                // rows
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[]"));

            //---- lblIcono ----
            lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
            lblIcono.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\saludo.svg"));
            lblIcono.setPorcentaje(10);
            panelShadow2.add(lblIcono, "cell 0 0");

            //---- lblTitulo ----
            lblTitulo.setText("<html> <h2 align=\"center\">\u00a1Hola! <br>Gracias por su prefencia!</h2></html>");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            panelShadow2.add(lblTitulo, "cell 0 1,align center top,grow 0 0");

            //---- lblTexto ----
            lblTexto.setText("Ahora es turno de asignar un nombre a tu evento!");
            lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
            lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            panelShadow2.add(lblTexto, "cell 0 2");

            //---- txtNombreEvento ----
            txtNombreEvento.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtNombreEvento.setHorizontalAlignment(SwingConstants.CENTER);
            txtNombreEvento.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    txtNombreEventoKeyPressed(e);
                }
            });
            panelShadow2.add(txtNombreEvento, "cell 0 3");

            //======== panelShadow1 ========
            {
                panelShadow1.setBackground(new Color(66, 100, 236));
                panelShadow1.setShadowColor(new Color(153, 153, 255));
                panelShadow1.setShadowOpacity(0.8F);
                panelShadow1.setForeground(Color.green);
                panelShadow1.setRoundBorderSize(25);
                panelShadow1.setShadowSize(20);
                panelShadow1.setLayout(new BorderLayout());

                //---- btnAceptar ----
                btnAceptar.setText("Aceptar");
                btnAceptar.setBorderPainted(false);
                btnAceptar.setContentAreaFilled(false);
                btnAceptar.setBackground(Color.pink);
                btnAceptar.setForeground(Color.white);
                btnAceptar.setDoubleBuffered(true);
                btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 16));
                btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnAceptar.addActionListener(e -> btnAceptar(e));
                panelShadow1.add(btnAceptar, BorderLayout.CENTER);
            }
            panelShadow2.add(panelShadow1, "cell 0 4,height 30%!");
        }
        contentPane.add(panelShadow2, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private PanelShadow panelShadow2;
    private SvgIcon lblIcono;
    private JLabel lblTitulo;
    private JLabel lblTexto;
    private JTextField txtNombreEvento;
    private PanelShadow panelShadow1;
    private JButton btnAceptar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
