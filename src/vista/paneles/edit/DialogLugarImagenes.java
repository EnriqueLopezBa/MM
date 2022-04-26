package vista.paneles.edit;

import Componentes.Sweet_Alert.Message.Tipo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Componentes.TextField;
import controlador.ControladorLugar;
import controlador.ControladorLugarImagenes;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import independientes.image_slider.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Lugar;
import modelo.LugarImagenes;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogLugarImagenes extends JDialog {

    private byte[] imagen = null;
    private File abre = null;

    private ControladorLugarImagenes controladorLugarImagenes = new ControladorLugarImagenes();
    private ControladorLugar controladorLugar = new ControladorLugar();

    public byte[] getImagen() {
        return imagen;
    }

    private void validaDatos() throws MMException {
        if (getImagen() == null) {
            lblIMG.requestFocus();
            throw new MMException("Selecciona una imagen");
        }
        if (txtDescripcion.getText().isEmpty()) {
            txtDescripcion.requestFocus();
            throw new MMException("Campo vacio");
        }
    }

    public DialogLugarImagenes(Principal owner, Lugar lugar) {
        super(owner);
        initComponents();
        getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.2).intValue()));
        setLocationRelativeTo(null);
        p.init(new String[]{"idLugar", "id2", "Descripcion"}, 2, true);
        llenarTabla(lugar.getIdLugar());
        Lugar temp = controladorLugar.obtenerByID(lugar.getIdLugar());
        i.init(ScrollBar.HORIZONTAL);
        i.lugarImagenes(temp);

        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validaDatos();
                    LugarImagenes temp = new LugarImagenes();
                    temp.setIdLugar(lugar.getIdLugar());
                    temp.setImagen(getImagen());
                    temp.setDescripcion(txtDescripcion.getText());
                    temp.setPredeterminada(cbPredeterminada.isSelected());
                    Mensaje m = controladorLugarImagenes.registrar(temp);
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    llenarTabla(lugar.getIdLugar());
                    i.lugarImagenes(lugar);
                } catch (MMException ee) {
                    Constante.mensaje(ee.getMessage(), Tipo.ADVERTENCIA);
                }
            }
        });

        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = p.tblBuscar.getSelectedRow();
                    if (x != -1) {
                        validaDatos();
                        LugarImagenes temp = new LugarImagenes();
                        temp.setIdLugar((int)p.tblModel.getValueAt(x, 0));
                        temp.setId2(p.tblModel.getValueAt(x, 1).toString());
                        temp.setImagen(getImagen());
                        temp.setDescripcion(txtDescripcion.getText());
                        temp.setPredeterminada(cbPredeterminada.isSelected());
                        Mensaje m = controladorLugarImagenes.actualizar(temp);
                        if (m.getTipoMensaje() == Tipo.OK) {
                            llenarTabla(lugar.getIdLugar());
                            i.lugarImagenes(lugar);
                        }
                        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    } else {
                        Constante.mensaje("Seleeciona una fila", Tipo.ERROR);
                    }

                } catch (MMException ee) {
                    Constante.mensaje(ee.getMessage(), Tipo.ERROR);
                }
            }
        });

        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = p.tblBuscar.getSelectedRow();
                if (x != -1) {
                    LugarImagenes temp = controladorLugarImagenes.obtenerById2(p.tblModel.getValueAt(x, 1).toString());
                    lblIMG.setIcon(new ImageIcon(new ImageIcon(temp.getImagen()).getImage().getScaledInstance(lblIMG.getWidth() - 10, lblIMG.getHeight() - 10, Image.SCALE_DEFAULT)));
                    imagen = temp.getImagen();
                    cbPredeterminada.setSelected(temp.isPredeterminada());
                    txtDescripcion.setText(p.tblModel.getValueAt(x, 2).toString());
                }

            }
        });
    }

    private void llenarTabla(int id) {
        p.tblModel.setRowCount(0);
        for (LugarImagenes lu : controladorLugarImagenes.obtenerListaByIDLugar(id)) {
            p.tblModel.addRow(new Object[]{lu.getIdLugar(), lu.getId2(), lu.getDescripcion()});
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
                Logger.getLogger(DialogLugarImagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        i = new ImageSlider();
        p = new pnlCRUD();
        lblIMG = new JLabel();
        panel1 = new JPanel();
        cbPredeterminada = new JCheckBox();
        txtDescripcion = new TextField();

        //======== this ========
        setModal(true);
        setBackground(Color.white);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]push" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]0" +
            "[]"));

        //---- i ----
        i.setBackground(Color.white);
        contentPane.add(i, "cell 0 0,height 30%!, spanx");
        contentPane.add(p, "cell 0 1,height 35%!, spanx");

        //---- lblIMG ----
        lblIMG.setBorder(new TitledBorder(null, "Imagen", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        lblIMG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblIMG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblIMGMouseClicked(e);
            }
        });
        contentPane.add(lblIMG, "cell 0 2,push,width 10:10, h 30%!");

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill",
                // columns
                "[fill]",
                // rows
                "[]" +
                "[]"));

            //---- cbPredeterminada ----
            cbPredeterminada.setText("Imagen predeterminada");
            panel1.add(cbPredeterminada, "cell 0 0");

            //---- txtDescripcion ----
            txtDescripcion.setLabelText("Descripcion");
            panel1.add(txtDescripcion, "cell 0 1");
        }
        contentPane.add(panel1, "cell 1 2, growx");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private ImageSlider i;
    private pnlCRUD p;
    private JLabel lblIMG;
    private JPanel panel1;
    private JCheckBox cbPredeterminada;
    private TextField txtDescripcion;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
