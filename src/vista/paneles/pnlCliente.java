package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import controlador.ControladorCliente;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showConfirmDialog;
import modelo.Cliente;
import net.miginfocom.swing.*;

public class pnlCliente extends JPanel {

    private ControladorCliente controlador = new ControladorCliente();
    private pnlCRUD pnlCrud;

    public pnlCliente() {
        initComponents();
        pnlCrud = new pnlCRUD();
        pnlCrud.init(new String[]{"idCliente", "Nombre", "Apellido", "Email", "Telefono", "Telefono 2"}, 1, false);
        pnlEdicion.add(pnlCrud);
        pnlEdicion.revalidate();
        pnlCrud.tblBuscar.setComponentPopupMenu(pnlCrud.jPopupMenu1); //Asignar Popup de cliente
        cargarClientes();

        //Modificar
        pnlCrud.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = pnlCrud.tblBuscar.getSelectedRow();
                    if (x != -1) {
                        validaDatos(txtNombre, txtApellido, txtCorreo, txtTelefono, txtTelefono2);
                        Cliente cliente = new Cliente();
                        cliente.setNombre(txtNombre.getText());
                        cliente.setApellido(txtApellido.getText());
                        cliente.setCorreo(txtCorreo.getText());
                        cliente.setTelefono(txtTelefono.getText());
                        cliente.setTelefono2(txtTelefono2.getText());
                        cliente.setIdCliente((int) pnlCrud.tblModel.getValueAt(x, 0));
                        Mensaje m = controlador.actualizarCliente(cliente);

                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                        if (m.getTipoMensaje() == Message.Tipo.OK) {
                            cargarClientes();
                        }
                    } else {
                        Constante.mensaje("Selecciona una fila", Message.Tipo.ADVERTENCIA);
                    }

                } catch (MMException ee) {
                    showMessageDialog(null, ee.getMessage());
                }
            }
        });

        //txtBusqueda
        pnlCrud.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarClientes();
            }
        });

        //Cargar los datos en los campos
        pnlCrud.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = pnlCrud.tblBuscar.getSelectedRow();
                if (x != -1) {
                    txtNombre.setText(pnlCrud.tblModel.getValueAt(x, 1).toString());
                    txtApellido.setText(pnlCrud.tblModel.getValueAt(x, 2).toString());
                    txtCorreo.setText(pnlCrud.tblModel.getValueAt(x, 3).toString());
                    txtTelefono.setText(pnlCrud.tblModel.getValueAt(x, 4).toString());
                    if (pnlCrud.tblModel.getValueAt(x, 5) != null) {
                        txtTelefono2.setText(pnlCrud.tblModel.getValueAt(x, 5).toString());
                    }
                } else {
                    Constante.mensaje("Selecciona una fila", Message.Tipo.ADVERTENCIA);
                }

            }

        });

        //Boton eliminar
        pnlCrud.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = pnlCrud.tblBuscar.getSelectedRow();
                if (x != -1) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente((int) pnlCrud.tblModel.getValueAt(x, 0));
                    cliente.setNombre(pnlCrud.tblModel.getValueAt(x, 1).toString());
                    if (showConfirmDialog(null, "Seguro que desea a " + cliente.getNombre() + " ? ") != 0) {
                        return;
                    }
                    Mensaje m = controlador.eliminarCliente(cliente);
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    if (m.getTipoMensaje() == Message.Tipo.OK) {
                        cargarClientes();
                    }
                }

            }
        });

    }

    private void cargarClientes() {
        pnlCrud.tblModel.setRowCount(0);
        for (Cliente c : controlador.obtenerClientes(pnlCrud.txtBusqueda.getText())) {
            pnlCrud.tblModel.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getApellido(), c.getCorreo(), c.getTelefono(), c.getTelefono2()});
        }
    }

    private void txtTelefonoKeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || txtTelefono.getText().length() >= 10) {
            e.consume();
        }

    }

    private void txtTelefono2KeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || txtTelefono2.getText().length() >= 10) {
            e.consume();
        }
    }

    private void label1MouseClicked() {
//        try {
//            validaDatos(txtNombre, txtApellido, txtCorreo, txtTelefono, txtTelefono2);
//            update(null, Accion.AGREGAR);
//        } catch (MMException e) {
//            new Message(p, true, e.getMessage(), Message.Tipo.ADVERTENCIA).showAlert();
//        }
    }

    private void btnAceptar(ActionEvent e) {
        try {
            validaDatos(txtNombre, txtApellido, txtCorreo, txtTelefono, txtTelefono2);
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setCorreo(txtCorreo.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setTelefono2(txtTelefono2.getText());
            Mensaje m = controlador.registrarCliente(cliente);
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            if (m.getTipoMensaje() == Message.Tipo.OK) {
                cargarClientes();
            }

        } catch (MMException ee) {
            showMessageDialog(null, ee.getMessage());
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        btnAceptar = new Button();
        panel2 = new JPanel();
        txtNombre = new TextField();
        txtApellido = new TextField();
        txtCorreo = new TextField();
        txtTelefono = new TextField();
        txtTelefono2 = new TextField();
        pnlEdicion = new JPanel();

        //======== this ========
        setBackground(Color.red);
        setOpaque(false);

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder(null, "Datos", TitledBorder.CENTER, TitledBorder.TOP));
            panel1.setBackground(Color.white);
            panel1.setMinimumSize(new Dimension(772, 258));
            panel1.setPreferredSize(new Dimension(772, 258));
            panel1.setMaximumSize(new Dimension(772, 32767));
            panel1.setLayout(new MigLayout(
                "insets 0,hidemode 3,gap 0 0",
                // columns
                "[grow,fill]" +
                "[fill]para",
                // rows
                "[grow,fill]"));

            //---- btnAceptar ----
            btnAceptar.setText("Registrar");
            btnAceptar.addActionListener(e -> btnAceptar(e));
            panel1.add(btnAceptar, "cell 1 0,growx,wmin 20%,height 30%:30%:30%");

            //======== panel2 ========
            {
                panel2.setBackground(Color.white);
                panel2.setLayout(new MigLayout(
                    "fill,insets 0,hidemode 3,gap 10 4",
                    // columns
                    "rel[171,fill]para" +
                    "[210,fill]para",
                    // rows
                    "[fill]8" +
                    "[fill]8" +
                    "[fill]"));

                //---- txtNombre ----
                txtNombre.setLabelText("Nombre");
                txtNombre.setOpaque(false);
                txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                panel2.add(txtNombre, "cell 0 0,aligny null,growy 0");

                //---- txtApellido ----
                txtApellido.setLabelText("Apellido");
                txtApellido.setOpaque(false);
                txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                panel2.add(txtApellido, "cell 1 0,aligny null,growy 0");

                //---- txtCorreo ----
                txtCorreo.setLabelText("Correo Electronico");
                txtCorreo.setOpaque(false);
                txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                panel2.add(txtCorreo, "cell 0 1 2 1,aligny null,growy 0");

                //---- txtTelefono ----
                txtTelefono.setLabelText("Telefono");
                txtTelefono.setOpaque(false);
                txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtTelefono.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        txtTelefonoKeyTyped(e);
                    }
                });
                panel2.add(txtTelefono, "cell 0 2,aligny null,growy 0");

                //---- txtTelefono2 ----
                txtTelefono2.setLabelText("Telefono 2");
                txtTelefono2.setOpaque(false);
                txtTelefono2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtTelefono2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        txtTelefono2KeyTyped(e);
                    }
                });
                panel2.add(txtTelefono2, "cell 1 2,aligny null,growy 0");
            }
            panel1.add(panel2, "cell 0 0,growx,width 80%:80%:80%");
        }

        //======== pnlEdicion ========
        {
            pnlEdicion.setBackground(Color.white);
            pnlEdicion.setBorder(new TitledBorder(null, "Editar", TitledBorder.CENTER, TitledBorder.TOP));
            pnlEdicion.setPreferredSize(new Dimension(772, 349));
            pnlEdicion.setLayout(new BorderLayout());
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(pnlEdicion, GroupLayout.DEFAULT_SIZE, 1361, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(panel1, GroupLayout.DEFAULT_SIZE, 1355, Short.MAX_VALUE)
                            .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(pnlEdicion, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void validaDatos(JTextField nombre, JTextField apellido, JTextField correo, JTextField telefono, JTextField telefono2) throws MMException {
        if (nombre.getText().length() == 0) {
            nombre.requestFocus();
            throw new MMException("Nombre vacio");
        } else if (apellido.getText().length() == 0) {
            apellido.requestFocus();
            throw new MMException("Apellido vacio");
        } else if (correo.getText().length() == 0) {
            correo.requestFocus();
            throw new MMException("Correo vacio");
        } else if (!correo.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            correo.requestFocus();
            throw new MMException("Correo invalido");
        } else if (telefono.getText().length() == 0) {
            telefono.requestFocus();
            throw new MMException("Telefono vacio");
        } else if (telefono.getText().length() != 10) {
            telefono.requestFocus();
            throw new MMException("Numero invalido");
        } else if (telefono2.getText().length() != 0 && telefono2.getText().length() != 10) {
            telefono2.requestFocus();
            throw new MMException("Telefono invalido");
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private Button btnAceptar;
    private JPanel panel2;
    public TextField txtNombre;
    public TextField txtApellido;
    public TextField txtCorreo;
    public TextField txtTelefono;
    public TextField txtTelefono2;
    private JPanel pnlEdicion;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
