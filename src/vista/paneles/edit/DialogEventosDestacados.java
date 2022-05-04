package vista.paneles.edit;

import Componentes.Sweet_Alert.Message;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Componentes.TextField;
import controlador.ControladorEvento;
import controlador.ControladorEventosDestacados;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Evento;
import modelo.EventosDestacados;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

public class DialogEventosDestacados extends JDialog {

    private byte[] imagen = null;
    private File abre = null;
    private Evento eventoActual = null;

    
    public DialogEventosDestacados(Principal owner) {
        super(owner);
        initComponents();
        getContentPane().setBackground(Color.white);
        p.init(new String[]{"idEvento", "idLugar", "Nombre Evento"}, 2, true);
        cargarTabla();
        cargarcmbEventos();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                cargarTabla();
            }

        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                EventosDestacados ev = ControladorEventosDestacados.getInstancia().obtenerByIdEvento((int) p.tblModel.getValueAt(x, 0));
                if (ev != null) {
                    lblIMG.setIcon(new ImageIcon(new ImageIcon(ev.getImagen()).getImage().getScaledInstance(lblIMG.getWidth() - 10, lblIMG.getHeight() - 10, Image.SCALE_DEFAULT)));
                }
            }

        });
        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validaDatos();
                    EventosDestacados ev = new EventosDestacados();
                    ev.setIdEvento(eventoActual.getIdEvento());
                    ev.setImagen(imagen);
                    Mensaje m = ControladorEventosDestacados.getInstancia().registrar(ev);
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                } catch (MMException ee) {
                    Constante.mensaje(ee.getMessage(), Message.Tipo.ERROR);
                }
            }
        });
    }

    private void validaDatos() throws MMException {
        if (cmbEventos.getSelectedIndex() == -1) {
            throw new MMException("Sin evento Seleccionado");
        }
        if (imagen == null) {
            throw new MMException("Sin imagen");
        }

    }

    private void cargarTabla() {
        p.tblModel.setRowCount(0);
        for (Evento ev : ControladorEvento.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{ev.getIdEvento(), ev.getIdLugar(), ev.getNombreEvento()});
        }
    }

    private void cargarcmbEventos() {
        cmbEventos.removeAllItems();
        for (Evento ev : ControladorEvento.getInstancia().obtenerListaByCadena("")) {
            cmbEventos.addItem(ev.getNombreEvento());
        }
    }

    private void lblIMGMouseClicked(MouseEvent e) {
        JFileChooser file = new JFileChooser();
        if (abre != null) {
            file.setCurrentDirectory(abre);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, JPEG & PNG", "jpg", "jpeg", "png");
        file.setFileFilter(filter);
        file.showOpenDialog(this);
        abre = file.getSelectedFile();
        if (abre != null) {
            lblIMG.setIcon(new ImageIcon(new ImageIcon(abre.getPath()).getImage().getScaledInstance(lblIMG.getWidth() - 10, lblIMG.getHeight() - 10, Image.SCALE_DEFAULT)));
            try {
                imagen = Files.readAllBytes(abre.toPath());
            } catch (IOException ex) {
                Logger.getLogger(DialogProveedorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void cmbEventosItemStateChanged(ItemEvent e) {
        if (cmbEventos.getSelectedIndex() == -1) {
            return;
        }
        for (Evento ev : ControladorEvento.getInstancia().obtenerListaByCadena("")) {
            if (ev.getNombreEvento().equals(cmbEventos.getSelectedItem().toString())) {
                eventoActual = ev;
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        lblIMG = new JLabel();
        panel1 = new JPanel();
        label1 = new JLabel();
        cmbEventos = new JComboBox();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[grow, fill]" +
            "[grow, fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));
        contentPane.add(p, "cell 0 0, spanx");

        //---- lblIMG ----
        lblIMG.setBorder(new TitledBorder(null, "Imagen", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        lblIMG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblIMG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblIMGMouseClicked(e);
            }
        });
        contentPane.add(lblIMG, "cell 0 1,push,width 10:10,height 30%!");

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- label1 ----
            label1.setText("Evento");
            label1.setHorizontalAlignment(SwingConstants.RIGHT);
            panel1.add(label1, "cell 0 0");

            //---- cmbEventos ----
            cmbEventos.addItemListener(e -> cmbEventosItemStateChanged(e));
            panel1.add(cmbEventos, "cell 1 0");
        }
        contentPane.add(panel1, "cell 1 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private JLabel lblIMG;
    private JPanel panel1;
    private JLabel label1;
    private JComboBox cmbEventos;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
