/*
 * Created by JFormDesigner on Thu Apr 21 12:17:17 MDT 2022
 */
package vista.paneles.edit;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorEtiqueta;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import modelo.Etiqueta;
import net.miginfocom.swing.*;
import vista.paneles.*;

/**
 * @author das
 */
public class DialogEtiqueta extends JDialog {

    ControladorEtiqueta controlador = new ControladorEtiqueta();

    public DialogEtiqueta(java.awt.Frame parent) {
        super(parent);
        initComponents();
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.5).intValue()));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        p.init(new String[]{"idEtiqueta", "Etiqueta"}, 1, true);
        f.init();
        f.listEtiqueta.setEnabled(false);
        f.btnClean.setEnabled(false);
        f.btnAgregarEtiqueta.setEnabled(false);
        llenarTabla();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                llenarTabla();
            }

        });

        //Busqueda
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = p.tblBuscar.getSelectedRow();
                if (x != -1) {
                    f.txtEtiqueta.setText(p.tblModel.getValueAt(x, 1).toString());
                }
            }
        });

        //AGREGAR
        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (f.txtEtiqueta.getText().isEmpty()) {
                    Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
                    f.txtEtiqueta.requestFocus();
                    return;
                }
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setEtiqueta(f.txtEtiqueta.getText());
                Mensaje m = controlador.registrar(etiqueta);
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                if (m.getTipoMensaje() == Message.Tipo.OK) {
                    llenarTabla();
                }

            }
        });
        //Modificar
        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setIdEtiqueta((int) p.tblModel.getValueAt(x, 0));
                etiqueta.setEtiqueta(p.tblModel.getValueAt(x, 1).toString());
                Mensaje m = controlador.actualizar(etiqueta);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
        //Eliminar
        p.btnEliminar.addActionListener((ActionEvent e) -> {
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            if (JOptionPane.showConfirmDialog(this, " Seguro que desea eliminar?") != 0) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setIdEtiqueta((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = controlador.eliminar(etiqueta);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Etiqueta et : controlador.obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{et.getIdEtiqueta(), et.getEtiqueta()});
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        f = new frmEtiquetas();

        //======== this ========
        setModal(true);
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("                 Edicion de Etiquetas");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[156]" +
            "[]"));
        contentPane.add(p, "cell 0 0");
        contentPane.add(f, "cell 0 1, grow");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private frmEtiquetas f;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
