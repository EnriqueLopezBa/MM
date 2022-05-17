/*
 * Created by JFormDesigner on Tue May 17 01:06:51 MDT 2022
 */
package vista.paneles.edit;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import java.awt.*;
import javax.swing.*;
import Componentes.TextField;
import controlador.ControladorTipoUsuario;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.TipoUsuario;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogTipoUsuario extends JDialog {

    public DialogTipoUsuario() {
        super(Principal.getInstancia());
        initComponents();
        getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(new Double(screensize.getSize().width / 4).intValue(), new Double(screensize.getSize().height / 3).intValue()));
        setLocationRelativeTo(null);
        p.init(new String[]{"idTipoUsuario", "Tipo Usuario"}, 1, true);
        llenarTabla();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                llenarTabla();
            }
       
        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                if (p.tblBuscar.getSelectedRow() == -1) {
                     return;
                }
               
                int x = p.tblBuscar.getSelectedRow();
                txtTipoUsuario.setText(p.tblModel.getValueAt(x, 1)+"");
            }
        
        });
        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtTipoUsuario.getText().isEmpty()) {
                    Constante.mensaje("Rellene el campo", Message.Tipo.ERROR);
                    txtTipoUsuario.requestFocus();
                    return;
                }
                TipoUsuario tipo = new TipoUsuario();
                tipo.setTipoUsuario(txtTipoUsuario.getText());
                Mensaje m = ControladorTipoUsuario.getInstancia().registrar(tipo);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         
                if (txtTipoUsuario.getText().isEmpty()) {
                    Constante.mensaje("Rellene el campo", Message.Tipo.ERROR);
                    txtTipoUsuario.requestFocus();
                    return;
                }
                
                if (p.tblBuscar.getSelectedRow() == -1) {
                    Constante.mensaje("Seleccione una fila", Message.Tipo.ERROR);
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                TipoUsuario tipo = new TipoUsuario();
                tipo.setIdTipoUsuario((int) p.tblModel.getValueAt(x, 0));
                tipo.setTipoUsuario(txtTipoUsuario.getText());
                Mensaje m = ControladorTipoUsuario.getInstancia().actualizar(tipo);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.tblBuscar.getSelectedRow() == -1) {
                    Constante.mensaje("Selecciona una fila", Message.Tipo.ADVERTENCIA);
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                TipoUsuario tipo = new TipoUsuario();
                tipo.setIdTipoUsuario((int) p.tblModel.getValueAt(x, 0));
                Mensaje m = ControladorTipoUsuario.getInstancia().eliminar(tipo);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });

    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (TipoUsuario ti : ControladorTipoUsuario.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{ti.getIdTipoUsuario(), ti.getTipoUsuario()});
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        txtTipoUsuario = new TextField();
        p = new pnlCRUD();

        //======== this ========
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EDICION TIPO DE USUARIOS");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]"));

        //---- txtTipoUsuario ----
        txtTipoUsuario.setLabelText("Tipo Usuario");
        contentPane.add(txtTipoUsuario, "cell 0 1");
        contentPane.add(p, "cell 0 0");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private TextField txtTipoUsuario;
    private pnlCRUD p;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
