package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import Componentes.TextField;
import modelo.Etiqueta;
import net.miginfocom.swing.*;

public class frmLugar extends JPanel {

    public frmLugar() {
        initComponents();
    }
    
    public void init(){
         frmEtiquetas1.init(new Etiqueta());
    }

    private void btnGaleria(ActionEvent e) {
        // TODO add your code here
    }

    private void txtCapacidadKeyTyped(KeyEvent e) {
       if (!Character.isDigit(e.getKeyChar()) || txtCapacidad.getText().length() >= 10) {
            e.consume();
        }
    }

    private void txtPrecioAproxKeyTyped(KeyEvent e) {
      if (!Character.isDigit(e.getKeyChar()) || txtPrecioAprox.getText().length() >= 6) {
            e.consume();
        }
    }


    
     




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbEstado = new JComboBox();
        cmbCiudad = new JComboBox();
        cmbProveedor = new JComboBox();
        txtLugar = new TextField();
        txtDomicilio = new TextField();
        frmEtiquetas1 = new frmEtiquetas();
        txtCapacidad = new TextField();
        txtPrecioAprox = new TextField();
        btnGaleria = new Button();
        lblProveedor = new JLabel();
        popupMenu2 = new JPopupMenu();
        btnEliminarIMG = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        setName("kk");
        setLayout(new MigLayout(
            "fill",
            // columns
            "[]" +
            "[]" +
            "[]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[fill]"));
        add(cmbEstado, "cell 0 0,grow, hmax 15%");
        add(cmbCiudad, "cell 1 0,grow, hmax 15%");
        add(cmbProveedor, "cell 2 0,grow, hmax 15%");

        //---- txtLugar ----
        txtLugar.setLabelText("Nombre del Lugar");
        add(txtLugar, "cell 0 1 2 1, growx");

        //---- txtDomicilio ----
        txtDomicilio.setLabelText("Domicilio");
        add(txtDomicilio, "cell 0 1 2 1,growx");
        add(frmEtiquetas1, "cell 2 1,spany 2,grow,hmax 50%");

        //---- txtCapacidad ----
        txtCapacidad.setLabelText("Capacidad");
        txtCapacidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtCapacidadKeyTyped(e);
            }
        });
        add(txtCapacidad, "cell 0 2,growx");

        //---- txtPrecioAprox ----
        txtPrecioAprox.setLabelText("Precio Aprox (Hora)");
        txtPrecioAprox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtPrecioAproxKeyTyped(e);
            }
        });
        add(txtPrecioAprox, "cell 1 2,growx");

        //---- btnGaleria ----
        btnGaleria.setText("Galeria");
        btnGaleria.addActionListener(e -> btnGaleria(e));
        add(btnGaleria, "cell 0 3 2 1,grow,hmax 20%");

        //---- lblProveedor ----
        lblProveedor.setText("Proveedor:");
        add(lblProveedor, "cell 0 4");

        //======== popupMenu2 ========
        {

            //---- btnEliminarIMG ----
            btnEliminarIMG.setText("Eliminar");
            btnEliminarIMG.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            popupMenu2.add(btnEliminarIMG);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JComboBox cmbEstado;
    public JComboBox cmbCiudad;
    public JComboBox cmbProveedor;
    public TextField txtLugar;
    public TextField txtDomicilio;
    public frmEtiquetas frmEtiquetas1;
    public TextField txtCapacidad;
    public TextField txtPrecioAprox;
    public Button btnGaleria;
    public JLabel lblProveedor;
    private JPopupMenu popupMenu2;
    public JMenuItem btnEliminarIMG;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
