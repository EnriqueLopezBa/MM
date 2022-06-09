package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorEstado;
import controlador.ControladorEtiqueta;
import controlador.ControladorLugarInformacion;
import controlador.ControladorLugarEtiquetas;
import controlador.ControladorNegocio;
import controlador.ControladorNegocioArea;
import controlador.ControladorProveedor;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import modelo.Ciudad;
import modelo.Estado;
import modelo.Etiqueta;
import modelo.LugarInformacion;
import modelo.LugarEtiquetas;
import modelo.Negocio;
import modelo.NegocioArea;
import modelo.Proveedor;

import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogLugarInformacion extends JDialog {

    private Estado estadoActual = null;
    private Ciudad ciudadActual = null;
    private Proveedor proveedorActual = null;

    public DialogLugarInformacion(JFrame owner) {
        super(owner);
        initComponents();
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setMinimumSize(new Dimension(screensize.getSize().width / 2, new Double(screensize.getSize().height / 1.5).intValue()));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idNegocio", "idCiudad", "idProveedor", "Nombre Negocio", "Domicilio", "Capacidad", "Precio"}, 3, true);
        llenarTabla();
        f.init(); // Iniciar Etiquetas
        //AGREGAR
        p.btnAgregar.addActionListener((ActionEvent e) -> {
            if (!validaDatos()) {
                return;
            }
            //Registrar nuevo negocio
            Negocio negocio = new Negocio();
            negocio.setIdProveedor(proveedorActual.getIdProveedor());
            negocio.setIdTipoProveedor(ControladorTipoProveedor.getInstancia().obtenerTipoProveedorByNombre("Local").getIdTipoProveedor());
            negocio.setNombreNegocio(f.txtNegocio.getText());
            negocio.setPrecioAprox(Integer.parseInt(f.txtPrecioAprox.getText()));
            ControladorNegocio.getInstancia().registrar(negocio);
            int idNegocio = ControladorNegocio.getInstancia().obtenerNegocioByLast().getIdNegocio();
            //NegocioLugar
            NegocioArea negocioarea = new NegocioArea();
            negocioarea.setIdNegocio(idNegocio);
            negocioarea.setIdCiudad(ciudadActual.getIdCiudad());
            ControladorNegocioArea.getInstancia().registrar(negocioarea);
            //Lugar
            LugarInformacion lugar = new LugarInformacion();
            lugar.setDomicilio(f.txtDomicilio.getText());
            lugar.setCapacidad(Integer.parseInt(f.txtCapacidad.getText()));
            lugar.setIdNegocio(idNegocio);
            Mensaje m = ControladorLugarInformacion.getInstancia().registrar(lugar);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
                //Registro de las etiquetas
                LugarInformacion lug = ControladorLugarInformacion.getInstancia().obtenerLugarByLast();
                ArrayList<LugarEtiquetas> loteEtiquetas = new ArrayList<>();
                for (Object etique : f.frmEtiquetas1.listModel.toArray()) {
                    Etiqueta temp = ControladorEtiqueta.getInstancia().obtenerByEtiquetaNombre((String) etique);
                    if (temp != null) {
                        loteEtiquetas.add(new LugarEtiquetas(temp.getIdEtiqueta(), lug.getIdNegocio()));
                    }
                }
                Mensaje mm = ControladorLugarEtiquetas.getInstancia().registrarLote(loteEtiquetas);
//                Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
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
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0));
            negocio.setIdProveedor(proveedorActual.getIdProveedor());
            negocio.setIdTipoProveedor(ControladorTipoProveedor.getInstancia().obtenerTipoProveedorByNombre("Local").getIdTipoProveedor());
            negocio.setNombreNegocio(f.txtNegocio.getText());
            negocio.setPrecioAprox(Integer.parseInt(f.txtPrecioAprox.getText()));
            ControladorNegocio.getInstancia().actualizar(negocio);
            //NegocioLugar
            NegocioArea negocioarea = new NegocioArea();
            negocioarea.setIdNegocio(negocio.getIdNegocio());
            negocioarea.setIdCiudad(ciudadActual.getIdCiudad());
            ControladorNegocioArea.getInstancia().actualizar(negocioarea);
            //Lugar
            LugarInformacion lugar = new LugarInformacion();
            lugar.setDomicilio(f.txtDomicilio.getText());
            lugar.setCapacidad(Integer.parseInt(f.txtCapacidad.getText()));
            lugar.setIdNegocio(negocio.getIdNegocio());
            Mensaje m = ControladorLugarInformacion.getInstancia().actualizar(lugar);
            if (m.getTipoMensaje() == Tipo.OK) {
                llenarTabla();
                //Actualizar etiquetas
                ArrayList<LugarEtiquetas> loteEtiquetas = new ArrayList<>();
                for (Object etique : f.frmEtiquetas1.listModel.toArray()) {
                    Etiqueta temp = ControladorEtiqueta.getInstancia().obtenerByEtiquetaNombre((String) etique);
                    if (temp != null) {
                        loteEtiquetas.add(new LugarEtiquetas(temp.getIdEtiqueta(), lugar.getIdNegocio()));
                    }
                } //SE BUSCA CADA UNO DE LOS REGISTROS PARA INGRESARLOS AL ARRAY
                ControladorLugarEtiquetas.getInstancia().actualizarLote(loteEtiquetas, lugar.getIdNegocio()); //SE ACTUALIZAN LAS ETIQUETAS 

                //Fin actualizar etiquetas
            }
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        });

        //ELIMINAR
        p.btnEliminar.addActionListener((ActionEvent e) -> {
            if (!Constante.filaSeleccionada(p.tblBuscar)) {
                return;
            }
            int x = p.tblBuscar.getSelectedRow();
            if (JOptionPane.showConfirmDialog(null, "Estas seguro que desea eliminar " + p.tblModel.getValueAt(x, 3) + "?") != 0) {
                return;
            }
            Negocio negocio = new Negocio();
            negocio.setIdNegocio((int) p.tblModel.getValueAt(x, 0));
            Mensaje m = ControladorNegocio.getInstancia().eliminar(negocio);
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
                f.txtNegocio.setText(p.tblModel.getValueAt(x, 3).toString());
                f.txtDomicilio.setText(p.tblModel.getValueAt(x, 4).toString());
                f.txtCapacidad.setText(p.tblModel.getValueAt(x, 5).toString());
                f.txtPrecioAprox.setText(p.tblModel.getValueAt(x, 6).toString());
                Ciudad city = ControladorCiudad.getInstancia().obtenerById((int) p.tblModel.getValueAt(x, 1));
                f.cmbEstado.getModel().setSelectedItem(ControladorEstado.getInstancia().obtenerByID(city.getIdEstado()).getEstado());
                f.cmbCiudad.getModel().setSelectedItem(city);
                f.cmbProveedor.getModel().setSelectedItem(ControladorProveedor.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 2)));
                ArrayList<LugarEtiquetas> temp = ControladorLugarEtiquetas.getInstancia().obtenerEtiquetasByIDLugar((int) p.tblModel.getValueAt(x, 0));
                f.frmEtiquetas1.listModel.clear();
                for (LugarEtiquetas as : temp) {
                    f.frmEtiquetas1.listModel.addElement(ControladorEtiqueta.getInstancia().obtenerByID(as.getIdEtiqueta()).getEtiqueta());
                }
            }
        });
        //boton GALERIA
        f.btnGaleria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                DialogLugarImagenes temp = new DialogLugarImagenes(Principal.getInstancia(), ControladorLugarInformacion.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0)));
                temp.setVisible(true);

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
                if (f.cmbCiudad.getSelectedIndex() == -1 || estadoActual == null) {
                    return;
                }
                ciudadActual = (Ciudad) f.cmbCiudad.getSelectedItem();
            }
        });

        f.cmbEstado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (f.cmbEstado.getSelectedIndex() == -1) {
                    return;
                }
                estadoActual = (Estado) f.cmbEstado.getSelectedItem();
                cargarCiudades();
            }
        });
        f.cmbProveedor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (f.cmbProveedor.getSelectedIndex() == -1) {
                    return;
                }
                proveedorActual = (Proveedor) f.cmbProveedor.getSelectedItem();
                f.lblProveedor.setText("Telefono proveedor: " + proveedorActual.getTelefono());
            }
        });
        cargarEstados();
        cargarProveedores();
    }

    private void cargarProveedores() {
        f.cmbProveedor.removeAllItems();
        for (Proveedor prov : ControladorProveedor.getInstancia().obtenerListaByCadena("")) {
            f.cmbProveedor.addItem(prov);
        }
        f.cmbProveedor.setRenderer(new MyObjectListCellRenderer());
    }

    private void cargarEstados() {
        f.cmbEstado.removeAllItems();
        for (Estado e : ControladorEstado.getInstancia().obtenerListaByCadena("")) {
            f.cmbEstado.addItem(e);
        }
        f.cmbEstado.setRenderer(new MyObjectListCellRenderer());
        cargarCiudades();
    }

    private void cargarCiudades() {
        if (estadoActual == null) {
            return;
        }
        f.cmbCiudad.removeAllItems();
        for (Ciudad ciudad : ControladorCiudad.getInstancia().obtenerListaByIDEstado(estadoActual.getIdEstado())) {
            f.cmbCiudad.addItem(ciudad);
        }
        f.cmbCiudad.setRenderer(new MyObjectListCellRenderer());
    }

    private boolean validaDatos() {
        if (f.txtNegocio.getText().isEmpty()) {
            Constante.mensaje("Campo vacio", Message.Tipo.ADVERTENCIA);
            f.txtNegocio.requestFocus();
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

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (LugarInformacion e : ControladorLugarInformacion.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(e.getIdNegocio());
            NegocioArea area = ControladorNegocioArea.getInstancia().obtenerListaByIdNegocio(negocio.getIdNegocio()).get(0);
            Proveedor prov = ControladorProveedor.getInstancia().obtenerByID(negocio.getIdProveedor());
            p.tblModel.addRow(new Object[]{e.getIdNegocio(), area.getIdCiudad(), prov.getIdProveedor(), negocio.getNombreNegocio(), e.getDomicilio(), e.getCapacidad(), negocio.getPrecioAprox()});
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
