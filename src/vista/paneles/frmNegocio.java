package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import Componentes.TextField;
import controlador.ControladorProveedor;
import controlador.ControladorTipoProveedor;
import independientes.MyObjectListCellRenderer;
import modelo.Etiqueta;
import modelo.Negocio;
import modelo.NegocioArea;
import modelo.Proveedor;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;

public class frmNegocio extends JPanel {

    public TipoProveedor tipoProveedorActual = null;
    public Negocio negocioActual = null;
    public Proveedor proveedorActual = null;
    public frmEtiquetas frm;
    public frmNegocio() {
        initComponents();

    }

    public void init() {
        cargarProveedores();
        cargarTipoProveedor();
        cargarEtiquetas();
    }
    
   public void cargarEtiquetas() {
        pnlListEtiquetas.init(new NegocioArea());
    }

    private void cargarProveedores(){
        cmbProveedor.removeAllItems();
        for(Proveedor prov : ControladorProveedor.getInstancia().obtenerListaByCadena("")){
         
            cmbProveedor.addItem(prov);
        }
        cmbProveedor.setRenderer(new MyObjectListCellRenderer());
    }

    private void cargarTipoProveedor() {
        cmbTipoProveedor.removeAllItems();
        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            if (tipo.getTipoProveedor().equals("Local")) {
                continue;
            }
            cmbTipoProveedor.addItem(tipo);
        }
        cmbTipoProveedor.setRenderer(new MyObjectListCellRenderer());
    }


 

    private void txtPrecioAproxKeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || txtPrecioAprox.getText().length() > 8) {
            e.consume();
        }
    }

    private void btnGaleria(ActionEvent e) {

    }

    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
        if (cmbTipoProveedor.getSelectedIndex() == -1) {
            return;
        }
        tipoProveedorActual = (TipoProveedor) cmbTipoProveedor.getSelectedItem();
        cargarProveedores();
    }


    private void txtDescripcionFocusGained(FocusEvent e) {
        if (txtDescripcion.getText().equals("Puedes añadir una descripcion del negocio")) {
            txtDescripcion.setText("");
        }
    }

    private void txtDescripcionFocusLost(FocusEvent e) {
        if (txtDescripcion.getText().isEmpty()) {
            txtDescripcion.setText("Puedes añadir una descripcion del negocio");
        }
    }

    private void cmbProveedorItemStateChanged(ItemEvent e) {
        if (cmbProveedor.getSelectedIndex() == -1) {
            return;
        }
        proveedorActual = (Proveedor) cmbProveedor.getSelectedItem();
        lblProveedor.setText("Numero Proveedor: " + proveedorActual.getTelefono());
    }

  

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbProveedor = new JComboBox();
        cmbTipoProveedor = new JComboBox();
        txtNombreNegocio = new TextField();
        txtPrecioAprox = new TextField();
        cbDisponible = new JCheckBox();
        scrollPane1 = new JScrollPane();
        txtDescripcion = new JTextPane();
        btnGaleria = new Button();
        lblProveedor = new JLabel();
        pnlListEtiquetas = new frmEtiquetas();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "rel[grow 40,shrink 0,sizegroup 1,fill]unrel" +
            "[grow 30,shrink 0,sizegroup 1,fill]unrel" +
            "[grow 30,shrink 0,sizegroup 1,fill]10",
            // rows
            "[fill]para" +
            "[]" +
            "[grow,fill]" +
            "[]" +
            "[fill]" +
            "[grow,fill]"));

        //---- cmbProveedor ----
        cmbProveedor.addItemListener(e -> cmbProveedorItemStateChanged(e));
        add(cmbProveedor, "cell 0 0");

        //---- cmbTipoProveedor ----
        cmbTipoProveedor.addItemListener(e -> cmbTipoProveedorItemStateChanged(e));
        add(cmbTipoProveedor, "cell 1 0");

        //---- txtNombreNegocio ----
        txtNombreNegocio.setLabelText("Nombre Negocio");
        add(txtNombreNegocio, "cell 0 1");

        //---- txtPrecioAprox ----
        txtPrecioAprox.setLabelText("Precio Aprox (Hora)");
        txtPrecioAprox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtPrecioAproxKeyTyped(e);
            }
        });
        add(txtPrecioAprox, "cell 1 1");

        //---- cbDisponible ----
        cbDisponible.setText("Disponible");
        add(cbDisponible, "cell 2 1");

        //======== scrollPane1 ========
        {

            //---- txtDescripcion ----
            txtDescripcion.setFont(txtDescripcion.getFont().deriveFont(txtDescripcion.getFont().getStyle() & ~Font.BOLD, txtDescripcion.getFont().getSize() + 2f));
            txtDescripcion.setText("Puedes a\u00f1adir una descripcion del negocio");
            txtDescripcion.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtDescripcionFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    txtDescripcionFocusLost(e);
                }
            });
            scrollPane1.setViewportView(txtDescripcion);
        }
        add(scrollPane1, "cell 0 2, spanx");

        //---- btnGaleria ----
        btnGaleria.setText("Galeria");
        btnGaleria.addActionListener(e -> btnGaleria(e));
        add(btnGaleria, "cell 0 3 3 1,aligny top,grow");

        //---- lblProveedor ----
        lblProveedor.setText("Telefono Proveedor:");
        add(lblProveedor, "cell 0 4");
        add(pnlListEtiquetas, "cell 0 5, spanx, grow");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JComboBox cmbProveedor;
    public JComboBox cmbTipoProveedor;
    public TextField txtNombreNegocio;
    public TextField txtPrecioAprox;
    public JCheckBox cbDisponible;
    private JScrollPane scrollPane1;
    public JTextPane txtDescripcion;
    public Button btnGaleria;
    private JLabel lblProveedor;
    public frmEtiquetas pnlListEtiquetas;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
