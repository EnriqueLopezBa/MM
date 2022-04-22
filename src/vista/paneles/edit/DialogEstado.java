package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorEstado;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Estado;
import net.miginfocom.swing.*;
import vista.paneles.*;

/**
 * @author das
 */
public class DialogEstado extends JDialog {

    ControladorEstado controlador = new ControladorEstado();

    public DialogEstado(JFrame owner) {
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
            if (txtEstado.getText().isEmpty()) {
                Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                txtEstado.requestFocus();
                return;
            }
            Estado tipo = new Estado();
            tipo.setEstado(txtEstado.getText());
            Mensaje m = controlador.registrar(tipo);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //ACTUALIZAR
        p.btnModificar.addActionListener((ActionEvent e) -> {
            if (txtEstado.getText().isEmpty()) {
                Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                txtEstado.requestFocus();
                return;
            }
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            Estado tipo = new Estado();
            tipo.setIdEstado((int) p.tblModel.getValueAt(x, 0));
            tipo.setEstado(txtEstado.getText());
            Mensaje m = controlador.actualizar(tipo);
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
            Estado tipo = new Estado();
            tipo.setIdEstado((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = controlador.eliminar(tipo);
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
                txtEstado.setText(p.tblModel.getValueAt(x, 1).toString());
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
        for (Estado e : controlador.obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdEstado(), e.getEstado()});
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        txtEstado = new TextField();

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

        //---- txtEstado ----
        txtEstado.setLabelText("Estado");
        contentPane.add(txtEstado, "cell 0 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private TextField txtEstado;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
