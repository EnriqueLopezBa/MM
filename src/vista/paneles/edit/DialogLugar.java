package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorEstado;
import controlador.ControladorEtiqueta;
import controlador.ControladorLugar;
import controlador.ControladorLugarEtiquetas;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;
import modelo.Ciudad;
import modelo.Estado;
import modelo.Etiqueta;
import modelo.Lugar;
import modelo.LugarEtiquetas;

import net.miginfocom.swing.*;
import vista.paneles.*;

/**
 * @author das
 */
public class DialogLugar extends JDialog {

    private ControladorCiudad controladorCiudad = new ControladorCiudad();
    private ControladorLugar controladorLugar = new ControladorLugar();
    private ControladorEstado controladorEstado = new ControladorEstado();
    private ControladorLugarEtiquetas controladorLugarEtiquetas = new ControladorLugarEtiquetas();
    private ControladorEtiqueta controladorEtiqueta = new ControladorEtiqueta();

    private Estado estadoActual = null;
    private Ciudad ciudadActual = null;

    public DialogLugar(JFrame owner) {
        super(owner);
        initComponents();
        getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.5).intValue()));
        setLocationRelativeTo(null);
        p.init(new String[]{"idLugar", "idCiudad", "Nombre Local", "Domicilio", "Capacidad", "Precio"}, 2, true);
        init();
    }

    private void cargarCiduades() {
        if (estadoActual == null) {
            return;
        }
        f.cmbCiudad.removeAllItems();
        for (Ciudad ciudad : controladorCiudad.obtenerListaByIDEstado(estadoActual.getIdEstado())) {
            f.cmbCiudad.addItem(ciudad.getCiudad());
        }
    }

    private boolean validaDatos() {
        if (f.txtLugar.getText().isEmpty()) {
            Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
            f.txtLugar.requestFocus();
            return false;
        }
        if (estadoActual == null) {
            Constante.mensaje("Seleeciona un estado", Tipo.ADVERTENCIA);
            f.cmbEstado.requestFocus();
            return false;
        }
        if (ciudadActual == null) {
            Constante.mensaje("Seleeciona una ciudad", Tipo.ADVERTENCIA);
            f.cmbCiudad.requestFocus();
            return false;
        }
        return true;
    }

    public void init() {

        for (Estado e : controladorEstado.obtenerListaByCadena("")) {
            f.cmbEstado.addItem(e.getEstado());
        }
        cargarCiduades();
        llenarTabla();
        f.init(); // Iniciar Etiquetas
        //AGREGAR
        p.btnAgregar.addActionListener((ActionEvent e) -> {
            if (!validaDatos()) {
                return;
            }

            //Lugar
            Lugar lugar = new Lugar();
            lugar.setIdCiudad(ciudadActual.getIdCiudad());
            lugar.setNombreLocal(f.txtLugar.getText());
            lugar.setDomicilio(f.txtDomicilio.getText());
            lugar.setCapacidad(Integer.parseInt(f.txtCapacidad.getText()));
            lugar.setPrecio(Integer.parseInt(f.txtPrecioAprox.getText()));
            Mensaje m = controladorLugar.registrar(lugar);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();

                //Registro de las etiquetas
                Lugar lug = controladorLugar.obtenerLugarByLast();
                ArrayList<LugarEtiquetas> loteEtiquetas = new ArrayList<>();
                for (Object etique : f.frmEtiquetas1.listModel.toArray()) {
                    Etiqueta temp = controladorEtiqueta.obtenerByEtiquetaNombre((String) etique);
                    if (temp != null) {
                        loteEtiquetas.add(new LugarEtiquetas(temp.getIdEtiqueta(), lug.getIdLugar()));
                    }
                }
                Mensaje mm = controladorLugarEtiquetas.registrarLote(loteEtiquetas);
                Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
                //Fin registro de etiquetas
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
            Lugar lugar = new Lugar();
            lugar.setIdLugar((int) p.tblModel.getValueAt(x, 0));
            lugar.setIdCiudad(ciudadActual.getIdCiudad());
            lugar.setNombreLocal(f.txtLugar.getText());
            lugar.setDomicilio(f.txtDomicilio.getText());
            lugar.setCapacidad(Integer.parseInt(f.txtCapacidad.getText()));
            lugar.setPrecio(Integer.parseInt(f.txtPrecioAprox.getText()));
            Mensaje m = controladorLugar.actualizar(lugar);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
                //Actualizar etiquetas

                ArrayList<LugarEtiquetas> loteEtiquetas = new ArrayList<>();
                for (Object etique : f.frmEtiquetas1.listModel.toArray()) {
                    Etiqueta temp = controladorEtiqueta.obtenerByEtiquetaNombre((String) etique);
                    if (temp != null) {
                        loteEtiquetas.add(new LugarEtiquetas(temp.getIdEtiqueta(), lugar.getIdLugar()));
                    }
                } //SE BUSCA CADA UNO DE LOS REGISTROS PARA INGRESARLOS AL ARRAY
               controladorLugarEtiquetas.actualizarLote(loteEtiquetas); //SE ACTUALIZAN LAS ETIQUETAS 
               
                //Fin actualizar etiquetas

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
            Lugar lugar = new Lugar();
            lugar.setIdLugar((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = controladorLugar.eliminar(lugar);
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
                f.txtLugar.setText(p.tblModel.getValueAt(x, 2).toString());
                f.txtDomicilio.setText(p.tblModel.getValueAt(x, 3).toString());
                f.txtCapacidad.setText(p.tblModel.getValueAt(x, 4).toString());
                f.txtPrecioAprox.setText(p.tblModel.getValueAt(x, 5).toString());
                f.cmbEstado.setSelectedItem(controladorEstado.obtenerByID(controladorCiudad.obtenerById((int) p.tblModel.getValueAt(x, 1)).getIdEstado()).getEstado());
                f.cmbCiudad.setSelectedItem(controladorCiudad.obtenerListaByIDEstado(estadoActual.getIdEstado()));
                ArrayList<LugarEtiquetas> temp = controladorLugarEtiquetas.obtenerEtiquetasByIDLugar((int) p.tblModel.getValueAt(x, 0));
                f.frmEtiquetas1.listModel.clear();
                for (LugarEtiquetas as : temp) {
                    f.frmEtiquetas1.listModel.addElement(controladorEtiqueta.obtenerByID(as.getIdEtiqueta()).getEtiqueta());
                }
            }
        });

        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                llenarTabla();
            }
        });

        f.cmbCiudad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (f.cmbCiudad.getSelectedIndex() != -1 && estadoActual != null) {
                    for (Ciudad ciudad : controladorCiudad.obtenerListaByIDEstado(estadoActual.getIdEstado())) {
                        if (ciudad.getCiudad().equals(f.cmbCiudad.getSelectedItem().toString())) {
                            ciudadActual = ciudad;
                            break;
                        }
                    }
                }
            }
        });

        f.cmbEstado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (f.cmbEstado.getSelectedIndex() != -1) {
                    for (Estado estado : controladorEstado.obtenerListaByCadena("")) {
                        if (estado.getEstado().equals(f.cmbEstado.getSelectedItem().toString())) {
                            estadoActual = estado;
                            cargarCiduades();
                            break;
                        }
                    }
                }
            }
        });

    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Lugar e : controladorLugar.obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdLugar(), e.getIdCiudad(), e.getNombreLocal(), e.getDomicilio(), e.getCapacidad(), e.getPrecio()});
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        f = new frmLugar();

        //======== this ========
        setModal(true);
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("                   Edicion de Lugares");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[198]" +
            "[]"));
        contentPane.add(p, "cell 0 0, spanx");
        contentPane.add(f, "cell 0 1, span, grow");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private frmLugar f;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
