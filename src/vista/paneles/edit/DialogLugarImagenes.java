package vista.paneles.edit;

import Componentes.Sweet_Alert.Message.Tipo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Componentes.TextField;
import controlador.ControladorLugarInformacion;
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
import modelo.LugarInformacion;
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
    private LugarInformacion lugar;

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

    public DialogLugarImagenes(Principal owner, LugarInformacion lugar) {
        super(owner);
        initComponents();
        this.lugar = lugar;
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        if (!Constante.getAdmin()) {

            remove(p);
            remove(panel1);
            int y = screensize.getSize().height / 2;
            int x = screensize.getSize().width / 2;
            super.setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 2).intValue()));
            i.setMinimumSize(new Dimension(x, y));

        } else {
//            System.out.println(i.getPreferredSize());
            super.setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.2).intValue()));
        }

        super.setLocationRelativeTo(null);
        p.init(new String[]{"idLugar", "id2", "Descripcion"}, 2, true);
        llenarTabla();

        i.init(ScrollBar.HORIZONTAL);
        i.lugarImagenes(lugar);

        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validaDatos();
                    LugarImagenes temp = new LugarImagenes();
                    temp.setIdNegocio(lugar.getIdNegocio());
                    temp.setImagen(getImagen());
                    temp.setDescripcion(txtDescripcion.getText());
                    temp.setPredeterminada(cbPredeterminada.isSelected());
                    Mensaje m = ControladorLugarImagenes.getInstancia().registrar(temp);
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                    llenarTabla();
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
                        temp.setIdNegocio((int) p.tblModel.getValueAt(x, 0));
                        temp.setId2(p.tblModel.getValueAt(x, 1).toString());
                        temp.setImagen(getImagen());
                        temp.setDescripcion(txtDescripcion.getText());
                        temp.setPredeterminada(cbPredeterminada.isSelected());
                        Mensaje m = ControladorLugarImagenes.getInstancia().actualizar(temp);
                        if (m.getTipoMensaje() == Tipo.OK) {
                            llenarTabla();
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
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.tblBuscar.getSelectedRow() == -1) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(null, "Seguro que sea eliminar " + p.tblModel.getValueAt(x, 2) + "?") != 0) {
                    return;
                }
                LugarImagenes lu = new LugarImagenes();
                lu.setIdNegocio((int) p.tblModel.getValueAt(x, 0));
                lu.setId2(p.tblModel.getValueAt(x, 1).toString());
                Mensaje m = ControladorLugarImagenes.getInstancia().eliminar(lu);
                if (m.getTipoMensaje() == Tipo.OK) {
                      i.lugarImagenes(lugar);
                      llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });

        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = p.tblBuscar.getSelectedRow();
                if (x != -1) {
                    LugarImagenes temp = ControladorLugarImagenes.getInstancia().obtenerById2(p.tblModel.getValueAt(x, 1).toString());
                    lblIMG.setIcon(new ImageIcon(new ImageIcon(temp.getImagen()).getImage().getScaledInstance(lblIMG.getWidth() - 10, lblIMG.getHeight() - 10, Image.SCALE_DEFAULT)));
                    imagen = temp.getImagen();
                    cbPredeterminada.setSelected(temp.isPredeterminada());
                    txtDescripcion.setText(p.tblModel.getValueAt(x, 2).toString());
                }

            }
        });
    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (LugarImagenes lu : ControladorLugarImagenes.getInstancia().obtenerListaByIDLugar(lugar.getIdNegocio())) {
            p.tblModel.addRow(new Object[]{lu.getIdNegocio(), lu.getId2(), lu.getDescripcion()});
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
        panel1 = new JPanel();
        lblIMG = new JLabel();
        cbPredeterminada = new JCheckBox();
        txtDescripcion = new TextField();

        //======== this ========
        setModal(true);
        setBackground(Color.white);
        setTitle("Imagenes del Lugar");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]push" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]" +
            "[grow, fill]"));

        //---- i ----
        i.setBackground(Color.white);
        contentPane.add(i, "cell 0 0,height 40%!, spanx");
        contentPane.add(p, "cell 0 1,height 25%!, spanx");

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
                "[]" +
                "[]"));

            //---- lblIMG ----
            lblIMG.setBorder(new TitledBorder(null, "Imagen", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
            lblIMG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblIMG.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lblIMGMouseClicked(e);
                }
            });
            panel1.add(lblIMG, "cell 0 0,spany, growy, w 10:10, h 10: 10");

            //---- cbPredeterminada ----
            cbPredeterminada.setText("Imagen predeterminada");
            panel1.add(cbPredeterminada, "cell 1 1");

            //---- txtDescripcion ----
            txtDescripcion.setLabelText("Descripcion");
            panel1.add(txtDescripcion, "cell 1 2");
        }
        contentPane.add(panel1, "cell 0 2,growx, spanx");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private ImageSlider i;
    private pnlCRUD p;
    private JPanel panel1;
    private JLabel lblIMG;
    private JCheckBox cbPredeterminada;
    private TextField txtDescripcion;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
