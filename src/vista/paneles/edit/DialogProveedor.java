package vista.paneles.edit;

import javax.swing.event.*;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorArea;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Ciudad;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.ProveedorArea;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogProveedor extends JDialog {

    private frmProveedor frmProv;
    private frmNegocio frmNeg;
    final Dimension screensize;
    private MigLayout mig;

    public DialogProveedor(Principal owner) {
        super(owner);
        initComponents();
        mig = (MigLayout) panel1.getLayout();
        frmProv = fProveedor;
        super.getContentPane().setBackground(Color.white);
        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setSize(new Dimension(new Double(screensize.getWidth() / 2).intValue(), new Double(screensize.getHeight() / 1.2).intValue()));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idProveedor", "Nombre", "Telefono", "Telefono 2", "Disponible"}, 1, true);
        llenarTabla();
        frmProv.init();
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                int x = p.tblBuscar.getSelectedRow();
                if (x == -1) {
                    return;
                }
                if (rbAgregarProveedor.isSelected()) {
                    frmProv.txtNombreCompleto.setText(valorTabla(x, 1));
                    frmProv.txtTelefono.setText(valorTabla(x, 2));
                    frmProv.txtTelefono2.setText(valorTabla(x, 3));
                    frmProv.cbDisponible.setSelected((valorTabla(x, 4).equals("Si")));
                } else {
                    TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 2));
                    Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 1));
                    frmNeg.cmbTipoProveedor.getModel().setSelectedItem(tipo);
                    frmNeg.cmbProveedor.getModel().setSelectedItem(proveedor);
                    frmNeg.txtNombreNegocio.setText(p.tblModel.getValueAt(x, 3).toString());
                    frmNeg.txtPrecioAprox.setText(p.tblModel.getValueAt(x, 4).toString());
                    frmNeg.cbDisponible.setSelected((p.tblModel.getValueAt(x, 5).toString().equals("Si")));
                    Negocio ne = ControladorNegocio.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0));
                    if (ne.getDescripcion() == null) {
                        frmNeg.txtDescripcion.setText("Puedes añadir una descripcion del negocio");
                    } else {
                        frmNeg.txtDescripcion.setText(ne.getDescripcion());
                    }

                }

            }

        });
        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbAgregarProveedor.isSelected()) {
                    try {
                        validaDatosProveedor();
                        Proveedor proveedor = new Proveedor();
                        proveedor.setNombre(frmProv.txtNombreCompleto.getText());
                        proveedor.setTelefono(frmProv.txtTelefono.getText());
                        proveedor.setTelefono2(frmProv.txtTelefono2.getText());
                        proveedor.setDisponible(frmProv.cbDisponible.isSelected());
                        Mensaje m = ControladorProveedor.getInstancia().registrar(proveedor);
                        if (m.getTipoMensaje() == Tipo.OK) { //Suponiendo que se agregó el proveedor correctamente
                            llenarTabla();
                        }
                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());// Proveedor Agregado
                    } catch (MMException ex) {
                        Constante.mensaje(ex.getMessage(), Tipo.ERROR);
                    }
                } else {
                    try {
                        validaDatosNegocio();
                        Negocio negocio = new Negocio();
                        negocio.setIdProveedor(frmNeg.proveedorActual.getIdProveedor());
                        negocio.setIdTipoProveedor(frmNeg.tipoProveedorActual.getIdTipoProveedor());
                        negocio.setNombreNegocio(frmNeg.txtNombreNegocio.getText());
                        negocio.setPrecioAprox(Integer.parseInt(frmNeg.txtPrecioAprox.getText()));
                        negocio.setDescripcion(frmNeg.txtDescripcion.getText());
                        negocio.setDisponible(frmNeg.cbDisponible.isSelected());
                        Mensaje m = ControladorNegocio.getInstancia().registrar(negocio);
                        if (m.getTipoMensaje() == Tipo.OK) {
                            llenarTabla();
                        }
                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    } catch (MMException ex) {
                        Constante.mensaje(ex.getMessage(), Tipo.ADVERTENCIA);
                    }

                }

            }
        });

        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (rbAgregarProveedor.isSelected()) {
                    try {
                        validaDatosProveedor();
                        Proveedor proveedor = new Proveedor();
                        proveedor.setNombre(frmProv.txtNombreCompleto.getText());
                        proveedor.setTelefono(frmProv.txtTelefono.getText());
                        proveedor.setTelefono2(frmProv.txtTelefono2.getText());
                        proveedor.setDisponible(frmProv.cbDisponible.isSelected());
                        proveedor.setIdProveedor((int) p.tblModel.getValueAt(x, 0));
                        Mensaje m = ControladorProveedor.getInstancia().actualizar(proveedor);
                        if (m.getTipoMensaje() == Tipo.OK) {
                            llenarTabla();
                        }
                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    } catch (MMException ex) {
                        Constante.mensaje(ex.getMessage(), Tipo.ADVERTENCIA);
                    }
                } else {
                    try {
                        validaDatosNegocio();
                        Negocio negocio = new Negocio();
                        negocio.setIdProveedor(frmNeg.proveedorActual.getIdProveedor());
                        negocio.setIdTipoProveedor(frmNeg.tipoProveedorActual.getIdTipoProveedor());
                        negocio.setNombreNegocio(frmNeg.txtNombreNegocio.getText());
                        negocio.setPrecioAprox(Integer.parseInt(frmNeg.txtPrecioAprox.getText()));
                        negocio.setDescripcion(frmNeg.txtDescripcion.getText());
                        negocio.setDisponible(frmNeg.cbDisponible.isSelected());
                        negocio.setIdNegocio((int) p.tblModel.getValueAt(x, 0));
                        Mensaje m = ControladorNegocio.getInstancia().actualizar(negocio);
                        if (m.getTipoMensaje() == Tipo.OK) {
                            llenarTabla();
                        }
                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    } catch (MMException ex) {
                        Constante.mensaje(ex.getMessage(), Tipo.ADVERTENCIA);
                    }
                }

            }
        });
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (rbAgregarProveedor.isSelected()) {
                    if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar " + p.tblModel.getValueAt(x, 1) + "?") != 0) {
                        return;
                    }
                    Proveedor pro = new Proveedor();
                    pro.setIdProveedor(Integer.parseInt(valorTabla(x, 0)));
                    Mensaje m = ControladorProveedor.getInstancia().eliminar(pro);
                    if (m.getTipoMensaje() == Tipo.OK) {
                        llenarTabla();
                    }
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar " + p.tblModel.getValueAt(x, 3) + "?") != 0) {
                        return;
                    }
                    Negocio negocio = new Negocio();
                    negocio.setIdNegocio((int) p.tblModel.getValueAt(x, 0));
                    Mensaje m = ControladorNegocio.getInstancia().eliminar(negocio);
                    if (m.getTipoMensaje() == Tipo.OK) {
                        llenarTabla();
                    }
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                }

            }
        });

//                            ArrayList<ProveedorArea> lote = new ArrayList<>();
//                    Ciudad ciudad = null;
//                    for (Object objeto : fProveedorArea.listModel.toArray()) {
//                        ciudad = ControladorCiudad.getInstancia().obtenerByNombre((String) objeto);
//                        if (ciudad != null) {
//                            lote.add(new ProveedorArea(proveedor.getIdProveedor(), ciudad.getIdCiudad()));
//                        }
//                    }
//
//                    Mensaje mm = ControladorProveedorArea.getInstancia().actualizarLote(lote, proveedor.getIdProveedor());
//        fProveedorArea.init(new ProveedorArea());
//        llenarTabla();
//
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                llenarTabla();
            }

        });
        // BOTON para acceder a la galeria de proveedores
        frmNeg.btnGaleria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID((int)p.tblModel.getValueAt(x, 0));
                DialogProveedorImagenes dia = new DialogProveedorImagenes(Principal.getInstancia(), proveedor);
                dia.setVisible(true);
            }
        });
    }

    private void validaDatosNegocio() throws MMException {
        if (frmNeg.tipoProveedorActual == null) {
            frmNeg.cmbTipoProveedor.requestFocus();
            throw new MMException("Selecciona un tipo de proveedor");
        }
        if (frmNeg.proveedorActual == null) {
            frmNeg.cmbTipoProveedor.requestFocus();
            throw new MMException("Selecciona un negocio");
        }
        if (frmNeg.txtNombreNegocio.getText().isEmpty()) {
            frmNeg.txtNombreNegocio.requestFocus();
            throw new MMException("Nombre de negocio vacio");
        }
        if (frmNeg.txtPrecioAprox.getText().isEmpty()) {
            frmNeg.txtPrecioAprox.requestFocus();
            throw new MMException("Precio Aprox Vacio");
        }

//        if (frmNeg.txt) {
//            
//        }
    }

    private void validaDatosProveedor() throws MMException {
        if (fProveedor.txtNombreCompleto.getText().isEmpty()) {
            fProveedor.txtNombreCompleto.requestFocus();
            throw new MMException("Nombre vacio");
        }
        if (fProveedor.txtTelefono.getText().isEmpty()) {
            fProveedor.txtTelefono.requestFocus();
            throw new MMException("Telefono vacio");
        }
        if (fProveedor.txtTelefono.getText().length() != 10) {
            fProveedor.txtTelefono.requestFocus();
            throw new MMException("Telefono incompleto");
        }
    }

    private String valorTabla(int fila, int columna) {
        try {
            return p.tblModel.getValueAt(fila, columna).toString();
        } catch (NullPointerException e) {
        }
        return "";
    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        if (rbAgregarProveedor.isSelected()) {
            for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
                p.tblModel.addRow(new Object[]{pro.getIdProveedor(), pro.getNombre(), pro.getTelefono(), pro.getTelefono2(), (pro.isDisponible()) ? "Si" : "No"});
            }
        } else {
            for (Negocio neg : ControladorNegocio.getInstancia().obtenerLista()) {
                p.tblModel.addRow(new Object[]{neg.getIdNegocio(), neg.getIdProveedor(), neg.getIdTipoProveedor(), neg.getNombreNegocio(), neg.getPrecioAprox(), (neg.isDisponible()) ? "Si" : "No"});
            }
        }

    }

    private void llenarAreaDeProveedor(int idProveedor) {
//        fProveedorArea.listModel.clear();
//     
//        for (ProveedorArea prov : ControladorProveedorArea.getInstancia().obtenerListaByIdProveedor(idProveedor)) {
//            fProveedorArea.listModel.addElement(ControladorCiudad.getInstancia().obtenerById(prov.getIdCiudad()).getCiudad());
//        }

    }

    private void rbAgregarProveedor(ActionEvent e) {
        for (Component c : panel1.getComponents()) {
            if (c instanceof frmNegocio) {
                panel1.remove(c);
                break;
            } else if (c instanceof frmProveedor) {
                return;
            }
        }
        p.init(new String[]{"idProveedor", "Nombre", "Telefono", "Telefono 2", "Disponible"}, 1, true);
        mig.setRowConstraints("[80%,fill][][grow,fill]");
//        super.setSize(new Dimension(new Double(screensize.getWidth() / 2).intValue(), new Double(screensize.getHeight() / 2).intValue()));
//        p.setMinimumSize(new Dimension(getSize().width - 20, getSize().height / 2));
        llenarTabla();
        frmProv = new frmProveedor();
        frmProv.init();
        panel1.add(frmProv, "cell 0 2");
        panel1.revalidate();
        panel1.repaint();
    }

    private void rbAgregarNegocio(ActionEvent e) {
        for (Component c : panel1.getComponents()) {
            if (c instanceof frmProveedor) {
                panel1.remove(c);
                break;
            } else if (c instanceof frmNegocio) {
                return;
            }
        }
        frmNeg = new frmNegocio();
        mig.setRowConstraints("[40%,fill][][grow,fill]");
        frmNeg.init();
        p.init(new String[]{"idNegocio", "idProveedor", "idTipoProveedor", "Nombre Negocio", "Precio Aprox", "Disponible"}, 0, true);
        llenarTabla();
        panel1.add(frmNeg, "cell 0 2");
        panel1.revalidate();
        panel1.repaint();
    }

    private void rbAgregarProveedorStateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        p = new pnlCRUD();
        rbAgregarProveedor = new JRadioButton();
        rbAgregarNegocio = new JRadioButton();
        fProveedor = new frmProveedor();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill",
                // columns
                "[fill]",
                // rows
                "[80%,fill]" +
                "[]" +
                "[grow,fill]"));
            panel1.add(p, "cell 0 0");

            //---- rbAgregarProveedor ----
            rbAgregarProveedor.setText("Nuevo Proveedor");
            rbAgregarProveedor.setSelected(true);
            rbAgregarProveedor.setHorizontalAlignment(SwingConstants.CENTER);
            rbAgregarProveedor.addActionListener(e -> rbAgregarProveedor(e));
            panel1.add(rbAgregarProveedor, "cell 0 1, grow");

            //---- rbAgregarNegocio ----
            rbAgregarNegocio.setText("Nuevo Negocio");
            rbAgregarNegocio.setHorizontalAlignment(SwingConstants.CENTER);
            rbAgregarNegocio.addActionListener(e -> rbAgregarNegocio(e));
            panel1.add(rbAgregarNegocio, "cell 0 1, grow");
            panel1.add(fProveedor, "cell 0 2");
        }
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rbAgregarProveedor);
        buttonGroup1.add(rbAgregarNegocio);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private pnlCRUD p;
    private JRadioButton rbAgregarProveedor;
    private JRadioButton rbAgregarNegocio;
    private frmProveedor fProveedor;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
