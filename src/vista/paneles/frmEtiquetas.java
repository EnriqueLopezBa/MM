/*
 * Created by JFormDesigner on Mon Apr 18 16:37:34 MDT 2022
 */

package vista.paneles;


import controlador.ControladorEtiqueta;
import independientes.AutoComplete;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import modelo.Etiqueta;
import net.miginfocom.swing.*;

public class frmEtiquetas extends JPanel {
    
    public DefaultListModel listModel = new DefaultListModel();
    ArrayList<String> lista;

    
    ControladorEtiqueta controlador = new ControladorEtiqueta();
    public frmEtiquetas() {
        initComponents();      
    }
    
    public void init(){
        
        ArrayList<String> lista = new ArrayList<>();
        for(Etiqueta as : controlador.obtenerLista()){
            lista.add(as.getEtiqueta().toLowerCase());
        }   
        autoCompletar(lista);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                    double xas = listEtiqueta.getSize().getHeight() * 0.035;
                    int a = (int)Math.round(xas);
                listEtiqueta.setVisibleRowCount(a);
            }
        });
    }

    public void autoCompletar(java.util.List<String> lista){
          AutoComplete autoComplete = new AutoComplete(txtEtiqueta,lista);
        txtEtiqueta.getDocument().addDocumentListener(autoComplete);
        txtEtiqueta.setFocusTraversalKeysEnabled(true); // false para que TAB funcione como commit
        txtEtiqueta.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "commit");
        txtEtiqueta.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "commit");
        txtEtiqueta.getActionMap().put("commit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               addElementListModel();
            }
        });
    }
    //Lista de etiqueas
    private boolean etiquetaEnLista() {
      
      for(Object et : listModel.toArray()){
          if (((String) et).equalsIgnoreCase(txtEtiqueta.getText())) {
              listEtiqueta.setSelectedValue(et, true);
              return true;
          }
      }
      return false;
    }

    private void addElementListModel() {
        if (txtEtiqueta.getText().isEmpty()) {
            return;
        }
        if (etiquetaEnLista()) {
            txtEtiqueta.setText("");
            return;
        }
        listModel.addElement(txtEtiqueta.getText().substring(0,1).toUpperCase() + txtEtiqueta.getText().substring(1));
        listEtiqueta.setModel(listModel);
        txtEtiqueta.setText("");
    }
    
    private void listEtiquetasValueChanged(ListSelectionEvent e) {
       
    }

    private void btnAgregarEtiqueta(ActionEvent e) {
        addElementListModel();
    }

    private void btnClean(ActionEvent e) {
      listModel.clear();
      listEtiqueta.setModel(listModel);
    }

    private void btnQuitar(ActionEvent e) {
        if (listEtiqueta.getSelectedValue() == null) {
            return;
        }
        listModel.removeElement(listEtiqueta.getSelectedValue());
        listEtiqueta.setModel(listModel);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        txtEtiqueta = new JTextField();
        btnAgregarEtiqueta = new JButton();
        scrollPane1 = new JScrollPane();
        listEtiqueta = new JList();
        btnClean = new JButton();
        lblEncontrado = new JLabel();
        popupMenu1 = new JPopupMenu();
        btnQuitar = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[grow 50,fill]" +
            "[]" +
            "[grow,fill]push",
            // rows
            "[]" +
            "[]"));
        add(txtEtiqueta, "cell 0 0, spany, growx, h 35%!");

        //---- btnAgregarEtiqueta ----
        btnAgregarEtiqueta.setText("+");
        btnAgregarEtiqueta.addActionListener(e -> btnAgregarEtiqueta(e));
        add(btnAgregarEtiqueta, "cell 1 0, grow");

        //======== scrollPane1 ========
        {

            //---- listEtiqueta ----
            listEtiqueta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listEtiqueta.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            listEtiqueta.setVisibleRowCount(2);
            listEtiqueta.setComponentPopupMenu(popupMenu1);
            listEtiqueta.addListSelectionListener(e -> listEtiquetasValueChanged(e));
            scrollPane1.setViewportView(listEtiqueta);
        }
        add(scrollPane1, "cell 2 0 1 2, grow");

        //---- btnClean ----
        btnClean.setIcon(new ImageIcon(getClass().getResource("/img/clear.png")));
        btnClean.setBorderPainted(false);
        btnClean.setContentAreaFilled(false);
        btnClean.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClean.addActionListener(e -> btnClean(e));
        add(btnClean, "cell 1 1,grow");

        //---- lblEncontrado ----
        lblEncontrado.setForeground(Color.red);
        lblEncontrado.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblEncontrado, "cell 2 1,grow");

        //======== popupMenu1 ========
        {

            //---- btnQuitar ----
            btnQuitar.setText("Quitar");
            btnQuitar.addActionListener(e -> btnQuitar(e));
            popupMenu1.add(btnQuitar);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField txtEtiqueta;
    private JButton btnAgregarEtiqueta;
    private JScrollPane scrollPane1;
    public JList listEtiqueta;
    private JButton btnClean;
    private JLabel lblEncontrado;
    private JPopupMenu popupMenu1;
    private JMenuItem btnQuitar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
