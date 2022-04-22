package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import Componentes.TextField;
import net.miginfocom.swing.*;

public class frmLugar extends JPanel {

    public frmLugar() {
        initComponents();
    }
    
    public void init(){
         frmEtiquetas1.init();
    }

    private void btnEliminarIMG(ActionEvent e) {

    }

    
     


    private void btnGaleria(ActionEvent e) {
//        if (p.pnlEvento.crud.lugar == null || p.pnlEvento.crud.filaSeleccionada == -1) {
//            new Message(p, true, "Selecciona un lugar", Message.Tipo.ERROR).showAlert();
//            return;
//        }
//   
//        p.galeria = new Galeria(p, true);
//        p.galeria.init(p.pnlEvento.crud.lugar);
//        p.galeria.setVisible(true);
        
    }

    private void txtDescripcionKeyPressed(KeyEvent e) {
        // TODO add your code here
    }

  

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbEstado = new JComboBox();
        cmbCiudad = new JComboBox();
        frmEtiquetas1 = new frmEtiquetas();
        txtLugar = new TextField();
        txtDomicilio = new TextField();
        txtCapacidad = new TextField();
        txtPrecioAprox = new TextField();
        btnGaleria = new Button();
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
            "[]"));
        add(cmbEstado, "cell 0 0,grow, hmax 15%");
        add(cmbCiudad, "cell 1 0,,grow, hmax 15%");
        add(frmEtiquetas1, "cell 2 0 1 4,grow, hmax 50%");

        //---- txtLugar ----
        txtLugar.setLabelText("Nombre del Lugar");
        add(txtLugar, "cell 0 1 2 1, growx");

        //---- txtDomicilio ----
        txtDomicilio.setLabelText("Domicilio");
        add(txtDomicilio, "cell 0 1 2 1,growx");

        //---- txtCapacidad ----
        txtCapacidad.setLabelText("Capacidad");
        add(txtCapacidad, "cell 0 2,growx");

        //---- txtPrecioAprox ----
        txtPrecioAprox.setLabelText("Precio Aprox");
        add(txtPrecioAprox, "cell 1 2,growx");

        //---- btnGaleria ----
        btnGaleria.setText("Galeria");
        btnGaleria.addActionListener(e -> btnGaleria(e));
        add(btnGaleria, "cell 0 3 2 1,grow,hmax 20%");

        //======== popupMenu2 ========
        {

            //---- btnEliminarIMG ----
            btnEliminarIMG.setText("Eliminar");
            btnEliminarIMG.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminarIMG.addActionListener(e -> btnEliminarIMG(e));
            popupMenu2.add(btnEliminarIMG);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public JComboBox cmbEstado;
    public JComboBox cmbCiudad;
    public frmEtiquetas frmEtiquetas1;
    public TextField txtLugar;
    public TextField txtDomicilio;
    public TextField txtCapacidad;
    public TextField txtPrecioAprox;
    public Button btnGaleria;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminarIMG;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
