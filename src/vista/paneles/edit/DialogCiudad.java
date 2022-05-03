package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorEstado;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Ciudad;
import modelo.Estado;

import net.miginfocom.swing.*;
import vista.paneles.*;

/**
 * @author das
 */
public class DialogCiudad extends JDialog {


   
    private Estado estadoActual = null;
    
    
    private boolean validaDatos(){
         if (txtCiudad.getText().isEmpty()) {
                Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                txtCiudad.requestFocus();
                return false;
            }
            if (estadoActual == null) {
                Constante.mensaje("Seleeciona un estado", Tipo.ADVERTENCIA);
                cmbEstado.requestFocus();
                return false;
            }
            return true;
    }
    public DialogCiudad(JFrame owner) {
        super(owner);
        initComponents();
        getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.5).intValue()));
        setLocationRelativeTo(null);
        p.init(new String[]{"idCiudad","idEstado", "Tipo Evento"}, 2, true);
        init();
    }

    public void init() {
        
        for(Estado e :  ControladorEstado.getInstancia().obtenerListaByCadena("")){
            cmbEstado.addItem(e.getEstado());
        }
        llenarTabla();
        //AGREGAR
        p.btnAgregar.addActionListener((ActionEvent e) -> {
            if (!validaDatos()) {
                return;
            }
            
            Ciudad ciudad = new Ciudad();
            ciudad.setIdEstado(estadoActual.getIdEstado());
            ciudad.setCiudad(txtCiudad.getText());
            Mensaje m = ControladorCiudad.getInstancia().registrar(ciudad);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //ACTUALIZAR
        p.btnModificar.addActionListener((ActionEvent e) -> {
            if (!validaDatos()) {
                return;
            }
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            Ciudad ciudad = new Ciudad();
            ciudad.setIdCiudad((int) p.tblModel.getValueAt(x, 0));
            ciudad.setIdEstado(estadoActual.getIdEstado());
            ciudad.setCiudad(txtCiudad.getText());
            Mensaje m = ControladorCiudad.getInstancia().actualizar(ciudad);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //ELIMINAR
        p.btnEliminar.addActionListener((ActionEvent e) -> {
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            if (JOptionPane.showConfirmDialog(this, " Seguro que desea eliminar?") != 0) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            Ciudad ciudad = new Ciudad();
            ciudad.setIdCiudad((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = ControladorCiudad.getInstancia().eliminar(ciudad);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //TABLA
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                txtCiudad.setText(p.tblModel.getValueAt(x, 2).toString());
            }
        });

        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                llenarTabla();
            }
        });

    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Ciudad e : ControladorCiudad.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdCiudad(),e.getIdEstado(), e.getCiudad()});
        }
    }

    private void cmbEstado(ActionEvent e) {
        if (cmbEstado.getSelectedIndex() != -1) {
            for(Estado estado :  ControladorEstado.getInstancia().obtenerListaByCadena("")){
                if (estado.getEstado().equals(cmbEstado.getSelectedItem().toString())) {
                    estadoActual = estado;
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        cmbEstado = new JComboBox();
        txtCiudad = new TextField();

        //======== this ========
        setModal(true);
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[198]" +
            "[]"));
        contentPane.add(p, "cell 0 0");

        //---- cmbEstado ----
        cmbEstado.addActionListener(e -> cmbEstado(e));
        contentPane.add(cmbEstado, "cell 0 1, grow,hmax 10%");

        //---- txtCiudad ----
        txtCiudad.setLabelText("Estado");
        contentPane.add(txtCiudad, "cell 0 1, split 2, growx");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private JComboBox cmbEstado;
    private TextField txtCiudad;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
