/*
 * Created by JFormDesigner on Tue May 17 00:53:48 MDT 2022
 */
package vista.paneles.edit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import Componentes.TextField;
import controlador.ControladorTipoUsuario;
import controlador.ControladorUsuario;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import static javax.swing.JOptionPane.showMessageDialog;
import modelo.TipoUsuario;
import modelo.Usuario;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.paneles.edit.DialogTipoUsuario;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogUsuario extends JDialog {

    private TipoUsuario tipoUsuarioActual;

    public DialogUsuario() {
        super(Principal.getInstancia());
        initComponents();
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setSize(new Dimension(new Double(screensize.getSize().width / 3.5).intValue(), new Double(screensize.getSize().height / 2.5).intValue()));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idUsuario", "idTipoUsuario", "Tipo Usuario", "Nombre", "Email"}, 2, true);
        cargarTipoUsuario();
        llenarTabla();

        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validaDatos();
                    Usuario us = new Usuario();
                    us.setIdTipoUsuario(tipoUsuarioActual.getIdTipoUsuario());
                    us.setNombre(txtNombre.getText());
                    us.setApellido(txtApellido.getText());
                    us.setCorreo(txtCorreo.getText());
                    us.setClave(new String(txtClave.getPassword()));
                    Mensaje m = ControladorUsuario.getInstancia().registrarUsuario(us);
                    if (m.getTipoMensaje() == Tipo.OK) {
                        llenarTabla();
                    }
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                } catch (MMException ee) {
                    Constante.mensaje(ee.getMessage(), Tipo.ERROR);
                }
            }
        });

        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (p.tblBuscar.getSelectedRow() == -1) {
                        p.tblBuscar.requestFocus();
                        Constante.mensaje("Selecciona una fila", Tipo.ERROR);
                        return;
                    }
                    validaDatos();
                    int x = p.tblBuscar.getSelectedRow();
                    Usuario us = new Usuario();
                    us.setIdSuario((int) p.tblModel.getValueAt(x, 0));
                    us.setIdTipoUsuario(tipoUsuarioActual.getIdTipoUsuario());
                    us.setNombre(txtNombre.getText());
                    us.setApellido(txtApellido.getText());
                    us.setCorreo(txtCorreo.getText());
                    us.setClave(new String(txtClave.getPassword()));
                    Mensaje m = ControladorUsuario.getInstancia().actualizarUsuario(us);
                    if (m.getTipoMensaje() == Tipo.OK) {
                        llenarTabla();
                    }
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                } catch (MMException ee) {
                    Constante.mensaje(ee.getMessage(), Tipo.ERROR);
                }

            }
        });
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.tblBuscar.getSelectedRow() == -1) {
                    p.tblBuscar.requestFocus();
                    Constante.mensaje("Selecciona una fila", Tipo.ERROR);

                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar al usuario " + p.tblModel.getValueAt(x, 3) + "?") != 0) {
                    return;
                }
                Usuario us = new Usuario();
                us.setIdSuario((int) p.tblModel.getValueAt(x, 0));
                Mensaje m = ControladorUsuario.getInstancia().eliminarUsuario(us);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
    }

    private void validaDatos() throws MMException {
        if (tipoUsuarioActual == null) {
            cbTipoUsario.requestFocus();
            throw new MMException("Selecciona Tipo de Usuario");
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.requestFocus();
            throw new MMException("Nombre vacio");
        }
        if (txtApellido.getText().isEmpty()) {
            txtApellido.requestFocus();
            throw new MMException("Apellido vacio");
        }
        if (txtCorreo.getText().isEmpty()) {
            txtCorreo.requestFocus();
            throw new MMException("Correo vacio");
        }
        if (txtClave.getPassword().length == 0) {
            txtClave.requestFocus();
            throw new MMException("Clave vacio");
        }
        if (txtClave.getPassword().length < 3) {
            txtClave.requestFocus();
            throw new MMException("Clave muy corta");
        }
    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Usuario u : ControladorUsuario.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            TipoUsuario tipo = ControladorTipoUsuario.getInstancia().obtenerByID(u.getIdTipoUsuario());
            p.tblModel.addRow(new Object[]{u.getIdSuario(), u.getIdTipoUsuario(), tipo.getTipoUsuario(), u.getNombre() + " " + u.getApellido(), u.getCorreo()});
        }
    }

    private void cargarTipoUsuario() {
        cbTipoUsario.removeAllItems();
        for (TipoUsuario ti : ControladorTipoUsuario.getInstancia().obtenerListaByCadena("")) {
            cbTipoUsario.addItem(ti);
        }
        cbTipoUsario.setRenderer(new MyObjectListCellRenderer());
    }

    private void lblEditTipoUsuarioMouseClicked(MouseEvent e) {
        DialogTipoUsuario temp = new DialogTipoUsuario();
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                cargarTipoUsuario();
            }

        });
    }

    private void cbTipoUsarioItemStateChanged(ItemEvent e) {
        if (cbTipoUsario.getSelectedIndex() == -1) {
            return;
        }
        if (tipoUsuarioActual != null && tipoUsuarioActual == cbTipoUsario.getSelectedItem()) {
            return;
        }
        tipoUsuarioActual = (TipoUsuario)cbTipoUsario.getSelectedItem();
//        for (TipoUsuario ti : ControladorTipoUsuario.getInstancia().obtenerListaByCadena("")) {
//            if (ti.getTipoUsuario().equals(cbTipoUsario.getSelectedItem())) {
//                tipoUsuarioActual = ti;
//
//            }
//        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        cbTipoUsario = new JComboBox();
        lblEditTipoUsuario = new JLabel();
        txtNombre = new TextField();
        txtApellido = new TextField();
        txtCorreo = new TextField();
        txtClave = new PasswordField();

        //======== this ========
        setBackground(Color.white);
        setTitle("EDICION DE USUARIOS");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));
        contentPane.add(p, "cell 0 0, spanx");

        //---- cbTipoUsario ----
        cbTipoUsario.addItemListener(e -> cbTipoUsarioItemStateChanged(e));
        contentPane.add(cbTipoUsario, "cell 0 1");

        //---- lblEditTipoUsuario ----
        lblEditTipoUsuario.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditTipoUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditTipoUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditTipoUsuarioMouseClicked(e);
            }
        });
        contentPane.add(lblEditTipoUsuario, "cell 1 1,grow 0 0");

        //---- txtNombre ----
        txtNombre.setLabelText("Nombre");
        contentPane.add(txtNombre, "cell 0 2");

        //---- txtApellido ----
        txtApellido.setLabelText("Apellido");
        contentPane.add(txtApellido, "cell 1 2");

        //---- txtCorreo ----
        txtCorreo.setLabelText("Correo");
        contentPane.add(txtCorreo, "cell 0 3");

        //---- txtClave ----
        txtClave.setLabelText("Clave");
        contentPane.add(txtClave, "cell 1 3");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private JComboBox cbTipoUsario;
    private JLabel lblEditTipoUsuario;
    private TextField txtNombre;
    private TextField txtApellido;
    private TextField txtCorreo;
    private PasswordField txtClave;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
