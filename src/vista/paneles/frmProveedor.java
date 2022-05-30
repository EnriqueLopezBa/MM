package vista.paneles;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import Componentes.TextField;
import controlador.ControladorTipoProveedor;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;

public class frmProveedor extends JPanel{
    
    public frmProveedor() {
        initComponents();
    }
    
    public void init(){
        for(TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")){
            cmbTipoProveedor.addItem(tipo.getTipoProveedor());
        }
    }

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

    private void txtTelefono2KeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) || txtTelefono2.getText().length() >= 10) {
            e.consume();
        }
    }

    private void txtPrecioAproxKeyTyped(KeyEvent e) {
       if (!Character.isDigit(e.getKeyChar())) {
            e.consume();
        }
    }

    private void btnGaleria(ActionEvent e) {
   
      
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbTipoProveedor = new JComboBox();
        txtNombre = new TextField();
        txtNombreEmpresa = new TextField();
        cbDisponible = new JCheckBox();
        txtPrecioAprox = new TextField();
        txtTelefono = new TextField();
        txtTelefono2 = new TextField();
        btnGaleria = new Button();

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
            "[]"));
        add(cmbTipoProveedor, "cell 0 0");

        //---- txtNombre ----
        txtNombre.setLabelText("Nombre");
        txtNombre.setMargin(new Insets(6, 6, 8, 6));
        add(txtNombre, "cell 1 0");

        //---- txtNombreEmpresa ----
        txtNombreEmpresa.setLabelText("Nombre Empresa");
        add(txtNombreEmpresa, "cell 2 0");

        //---- cbDisponible ----
        cbDisponible.setText("Disponible");
        add(cbDisponible, "cell 0 1");

        //---- txtPrecioAprox ----
        txtPrecioAprox.setLabelText("Precio Aprox (Hora)");
        txtPrecioAprox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtPrecioAproxKeyTyped(e);
            }
        });
        add(txtPrecioAprox, "cell 0 1");

        //---- txtTelefono ----
        txtTelefono.setLabelText("Telefono");
        txtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtTelefonoKeyTyped(e);
            }
        });
        add(txtTelefono, "cell 1 1,aligny top");

        //---- txtTelefono2 ----
        txtTelefono2.setLabelText("Telefono 2");
        txtTelefono2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtTelefono2KeyTyped(e);
            }
        });
        add(txtTelefono2, "cell 2 1,aligny top,growx");

        //---- btnGaleria ----
        btnGaleria.setText("Galeria");
        btnGaleria.addActionListener(e -> btnGaleria(e));
        add(btnGaleria, "cell 0 2, spanx, aligny top, grow");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JComboBox cmbTipoProveedor;
    public TextField txtNombre;
    public TextField txtNombreEmpresa;
    public JCheckBox cbDisponible;
    public TextField txtPrecioAprox;
    public TextField txtTelefono;
    public TextField txtTelefono2;
    public Button btnGaleria;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    
   
}
