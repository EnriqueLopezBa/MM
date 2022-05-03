/*
 * Created by JFormDesigner on Thu Apr 21 14:43:17 MDT 2022
 */
package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorTipoEvento;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.TipoEvento;
import net.miginfocom.swing.*;
import vista.paneles.*;

/**
 * @author das
 */
public class DialogTipoEvento extends JDialog {

    public DialogTipoEvento(JFrame owner) {
        super(owner);
        initComponents();
        getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.5).intValue()));
        setLocationRelativeTo(null);
        p.init(new String[]{"idTipoEvento", "Tipo Evento"}, 1, true);
        init();
    }

    public void init() {
        llenarTabla();
        //AGREGAR
        p.btnAgregar.addActionListener((ActionEvent e) -> {
            if (txtTipoEvento.getText().isEmpty()) {
                Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                txtTipoEvento.requestFocus();
                return;
            }
            TipoEvento tipo = new TipoEvento();
            tipo.setTematica(txtTipoEvento.getText());
            Mensaje m = ControladorTipoEvento.getInstancia().registrar(tipo);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //ACTUALIZAR
        p.btnModificar.addActionListener((ActionEvent e) -> {
           if (txtTipoEvento.getText().isEmpty()) {
                Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                txtTipoEvento.requestFocus();
                return;
            }
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            TipoEvento tipo = new TipoEvento();
            tipo.setIdTipoEvento((int) p.tblModel.getValueAt(x, 0));
            tipo.setTematica(txtTipoEvento.getText());
            Mensaje m = ControladorTipoEvento.getInstancia().actualizar(tipo);
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
            int x = p.tblBuscar.getSelectedRow();
            TipoEvento tipo = new TipoEvento();
            tipo.setIdTipoEvento((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = ControladorTipoEvento.getInstancia().eliinar(tipo);
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
                txtTipoEvento.setText(p.tblModel.getValueAt(x, 1).toString());
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
        for (TipoEvento e : ControladorTipoEvento.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdTipoEvento(), e.getTematica()});
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        txtTipoEvento = new TextField();

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

        //---- txtTipoEvento ----
        txtTipoEvento.setLabelText("Tipo Evento");
        contentPane.add(txtTipoEvento, "cell 0 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private TextField txtTipoEvento;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
