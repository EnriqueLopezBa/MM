package vista.paneles;

import Clases.Objetos.Etiqueta;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Componentes.TextField;
import independientes.AutoComplete;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.*;
import vista.principales.Principal;

public class frmProveedorImagenes extends JPanel  {

    private byte[] imagen;
    private File abre;
    private DefaultListModel listModel = new DefaultListModel();
    
    public frmProveedorImagenes(Principal p) {
        initComponents();
        ArrayList<String> lista = new ArrayList<>();
        for(Etiqueta as : p.getDatos().getEtiquetas()){
            lista.add(as.getEtiqueta());
        }
            
        
        AutoComplete autoComplete = new AutoComplete(txtDescripcion,lista);
        txtDescripcion.getDocument().addDocumentListener(autoComplete);
        txtDescripcion.setFocusTraversalKeysEnabled(false); // false para que TAB funcione como commit
        txtDescripcion.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "commit");
        txtDescripcion.getActionMap().put("commit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               addElementListModel();
            }
        });
        

    }

    
     private void addElementListModel() {
        if (txtDescripcion.getText().isEmpty()) {
            return;
        }
        if (etiquetaEnLista()) {
            return;
        }
        listModel.addElement(txtDescripcion.getText());
        listEtiqueta.setModel(listModel);
        txtDescripcion.setText("");
    }
     
     private boolean etiquetaEnLista() {
        for (Object ob : listModel.toArray()) {
            if (((String) ob).equals(txtDescripcion.getText())) {
                return true;
            }
        }
        return false;
    }
     
    public byte[] getImagen() {
        return imagen;
    }

    
    private void btnEliminarIMG(ActionEvent e) {
        lblIMG.setIcon(null);
        imagen = null;
    }

    private void lblIMGMouseClicked(MouseEvent e) {
        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, JPEG & PNG", "jpg", "jpeg", "png");
        file.setFileFilter(filter);
        file.showOpenDialog(this);
        abre = file.getSelectedFile();
        if (abre != null) {
            lblIMG.setIcon(new ImageIcon(new ImageIcon(abre.getPath()).getImage().getScaledInstance(lblIMG.getWidth()- 30, lblIMG.getHeight() - 20, Image.SCALE_DEFAULT)));
            try {
                imagen = Files.readAllBytes(abre.toPath());
            } catch (IOException ex) {
                Logger.getLogger(frmProveedorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void txtDescripcionKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            addElementListModel();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblIMG = new JLabel();
        panel1 = new JPanel();
        txtDescripcion = new TextField();
        scrollPane1 = new JScrollPane();
        listEtiqueta = new JList();
        popupMenu2 = new JPopupMenu();
        btnEliminarIMG = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "insets 0 0 0 10,hidemode 3,gap 0 0",
            // columns
            "rel[grow 40,shrink 0,sizegroup 1,fill]unrel" +
            "[grow 30,shrink 0,sizegroup 1,fill]unrel",
            // rows
            "[fill]" +
            "[]" +
            "[grow,center]"));

        //---- lblIMG ----
        lblIMG.setBorder(new TitledBorder("Imagen"));
        lblIMG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblIMG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblIMGMouseClicked(e);
            }
        });
        add(lblIMG, "cell 0 0 1 3,grow, wmax 50%");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                "insets 0,hidemode 3,gap 0 0",
                // columns
                "[grow,fill]",
                // rows
                "[fill]" +
                "[grow,fill]"));

            //---- txtDescripcion ----
            txtDescripcion.setLabelText("Descripcion / Caracteristica");
            txtDescripcion.setMargin(new Insets(6, 6, 8, 6));
            txtDescripcion.setMinimumSize(new Dimension(80, 30));
            txtDescripcion.setPreferredSize(new Dimension(80, 30));
            txtDescripcion.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    txtDescripcionKeyPressed(e);
                }
            });
            panel1.add(txtDescripcion, "cell 0 0, h 20%!");

            //======== scrollPane1 ========
            {

                //---- listEtiqueta ----
                listEtiqueta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listEtiqueta.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                scrollPane1.setViewportView(listEtiqueta);
            }
            panel1.add(scrollPane1, "cell 0 1");
        }
        add(panel1, "cell 1 0 1 3");

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
    public JLabel lblIMG;
    private JPanel panel1;
    public TextField txtDescripcion;
    private JScrollPane scrollPane1;
    private JList listEtiqueta;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminarIMG;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
