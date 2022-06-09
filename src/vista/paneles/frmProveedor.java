package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.TextField;
import net.miginfocom.swing.*;

public class frmProveedor extends JPanel {

    
    public frmProveedor() {
        initComponents();

    }
  

    public void init() {
//        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
//            cmbTipoProveedor.addItem(tipo);
//        }
//        cmbTipoProveedor.setRenderer(new MyObjectListCellRenderer());
    }
    
//    private void cargarNegocios(){
//        if (tipoProveedorActual == null) {
//            return;
//        }
//        cmbNegocio.removeAllItems();
//        for(Negocio negocio : ControladorNegocio.getInstancia().obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())){
//            cmbNegocio.addItem(negocio);
//            
//        }
//        cmbNegocio.setRenderer(new MyObjectListCellRenderer());
//        
//    }

    private void btnEliminarIMG(ActionEvent e) {
//        lblIMG.setIcon(null);
//        imagen = null;
    }

    private void lblIMGMouseClicked(MouseEvent e) {
//        JFileChooser file = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, JPEG & PNG", "jpg", "jpeg", "png");
//        file.setFileFilter(filter);
//        file.showOpenDialog(this);
//        abre = file.getSelectedFile();
//        if (abre != null) {
//            lblIMG.setIcon(new ImageIcon(new ImageIcon(abre.getPath()).getImage().getScaledInstance(lblIMG.getWidth(), lblIMG.getHeight() - 20, Image.SCALE_DEFAULT)));
//            try {
//                imagen = Files.readAllBytes(abre.toPath());
//            } catch (IOException ex) {
//                Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    private void txtTelefonoKeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || txtTelefono.getText().length() >= 10) {
            e.consume();
        }
    }

    private void btnGaleria(ActionEvent e) {

    }

    private void txtTelefono2KeyTyped(KeyEvent e) {
          if (!Character.isDigit(e.getKeyChar()) || txtTelefono2.getText().length() >= 10) {
            e.consume();
        }
    }

//    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
//        if (cmbTipoProveedor.getSelectedIndex() == -1) {
//
//            return;
//        }
//        if (tipoProveedorActual != null && cmbTipoProveedor.getSelectedItem() == tipoProveedorActual.getTipoProveedor()) {
//            return;
//        }
//        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
//            if (tipo.getTipoProveedor() == cmbTipoProveedor.getSelectedItem()) {
//                tipoProveedorActual = tipo;
////                cargarNegocios();
//                break;
//            }
//        }
//    }

//    private void cmbNegocioItemStateChanged(ItemEvent e) {
//        if (cmbNegocio.getSelectedIndex() == -1) {
//            return;
//        }
//       
//    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cbDisponible = new JCheckBox();
        txtNombreCompleto = new TextField();
        txtTelefono = new TextField();
        txtTelefono2 = new TextField();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            null,
            // columns
            "[grow]" +
            "[grow]" +
            "[grow]",
            // rows
            "[]para" +
            "[]"));

        //---- cbDisponible ----
        cbDisponible.setText("Disponible");
        add(cbDisponible, "cell 1 0, grow");

        //---- txtNombreCompleto ----
        txtNombreCompleto.setLabelText("Nombre Completo");
        add(txtNombreCompleto, "cell 0 1, growx");

        //---- txtTelefono ----
        txtTelefono.setLabelText("Telefono");
        txtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtTelefonoKeyTyped(e);
            }
        });
        add(txtTelefono, "cell 1 1, growx");

        //---- txtTelefono2 ----
        txtTelefono2.setLabelText("Telefono 2");
        txtTelefono2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtTelefono2KeyTyped(e);
            }
        });
        add(txtTelefono2, "cell 2 1, growx");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JCheckBox cbDisponible;
    public TextField txtNombreCompleto;
    public TextField txtTelefono;
    public TextField txtTelefono2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
