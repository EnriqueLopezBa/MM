
package vista.paneles.edit;

import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorArea;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Ciudad;
import modelo.Proveedor;
import modelo.ProveedorArea;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogProveedor extends JDialog {

    public DialogProveedor(Principal owner) {
        super(owner);
        initComponents();
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setSize(new Dimension(new Double(screensize.getWidth() / 2).intValue(), super.getPreferredSize().height));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idProveedor", "idTipoProveedor", "Nombre", "Nombre Empresa", "Telefono", "Telefono 2", "Precio Aprox"}, 2, true);
//        p.tblBuscar.removeColumn(p.tblBuscar.getColumnModel().getColumn(0));
//        p.tblBuscar.removeColumn(p.tblBuscar.getColumnModel().getColumn(0));
        fProveedorArea.init(new ProveedorArea());
        fProveedor.init();
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
                int x = p.tblBuscar.getSelectedRow();
                if (x == -1) {
                    return;
                }
                TipoProveedor tipoProv = ControladorTipoProveedor.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 1));
                fProveedor.txtNombre.setText(valorTabla(x, 2));
                fProveedor.txtNombreEmpresa.setText(valorTabla(x, 3));
                fProveedor.txtTelefono.setText(valorTabla(x, 4));
                fProveedor.txtTelefono2.setText(valorTabla(x, 5));
                fProveedor.txtPrecioAprox.setText(valorTabla(x, 6));
                fProveedor.cbDisponible.setSelected(ControladorProveedor.getInstancia().obtenerByID(Integer.parseInt(valorTabla(x, 0))).isDisponible());
                llenarAreaDeProveedor(Integer.parseInt(valorTabla(x, 0)));
                 fProveedor.cmbTipoProveedor.setSelectedItem(tipoProv.getTipoProveedor());
            }

        });
        // ------------------------- AGREGAR -----------------------------//
        p.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proveedor proveedor  = new Proveedor();
                proveedor.setIdtipoProveedor(ControladorTipoProveedor.getInstancia().obtenerTipoProveedorByNombre(fProveedor.cmbTipoProveedor.getSelectedItem().toString()).getIdTipoProveedor());
                proveedor.setNombre(fProveedor.txtNombre.getText());
                proveedor.setNombreEmpresa(fProveedor.txtNombreEmpresa.getText());
                proveedor.setTelefono(fProveedor.txtTelefono.getText());
                proveedor.setTelefono2(fProveedor.txtTelefono2.getText());
                proveedor.setPrecioAprox(Integer.parseInt(fProveedor.txtPrecioAprox.getText()));
                proveedor.setDisponible(fProveedor.cbDisponible.isSelected());
                Mensaje m = ControladorProveedor.getInstancia().registrar(proveedor);
                if (m.getTipoMensaje() == Tipo.OK) { //Suponiendo que se agregó el proveedor correctamente
                    llenarTabla();
                    Proveedor provtemp = ControladorProveedor.getInstancia().obtenerByLast();
                    ArrayList<ProveedorArea> lote = new ArrayList<>();
                    for(Object objeto : fProveedorArea.listModel.toArray()){
                        Ciudad ciudad = ControladorCiudad.getInstancia().obtenerByNombre((String) objeto);
                        if (ciudad != null) {
                            lote.add(new ProveedorArea(provtemp.getIdProveedor(), ciudad.getIdCiudad()));
                        }
                    }
                    Mensaje mm = ControladorProveedorArea.getInstancia().registrarLote(lote);
//                    Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
                    
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());// Proveedor Agregado
                
                
            }
        });
        // ------------------------- MODIFICAR -----------------------------//
        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                 Proveedor proveedor  = new Proveedor();
                 proveedor.setIdProveedor(Integer.parseInt(valorTabla(x, 0)));
                proveedor.setIdtipoProveedor(ControladorTipoProveedor.getInstancia().obtenerTipoProveedorByNombre(fProveedor.cmbTipoProveedor.getSelectedItem().toString()).getIdTipoProveedor());
                proveedor.setNombre(fProveedor.txtNombre.getText());
                proveedor.setNombreEmpresa(fProveedor.txtNombreEmpresa.getText());
                proveedor.setTelefono(fProveedor.txtTelefono.getText());
                proveedor.setTelefono2(fProveedor.txtTelefono2.getText());
                proveedor.setPrecioAprox(Integer.parseInt(fProveedor.txtPrecioAprox.getText()));
                proveedor.setDisponible(fProveedor.cbDisponible.isSelected());
                Mensaje m = ControladorProveedor.getInstancia().actualizar(proveedor);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                    
                    ArrayList<ProveedorArea> lote = new ArrayList<>();
                       Ciudad ciudad = null;
                    for(Object objeto : fProveedorArea.listModel.toArray()){
                         ciudad = ControladorCiudad.getInstancia().obtenerByNombre((String) objeto);
                        if (ciudad != null) {
                            lote.add(new ProveedorArea(proveedor.getIdProveedor(), ciudad.getIdCiudad()));
                        }
                    }
                    
                    Mensaje mm = ControladorProveedorArea.getInstancia().actualizarLote(lote, proveedor.getIdProveedor());
//                    Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
        // ------------------------- ELIMINAR -----------------------------//
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar " + p.tblModel.getValueAt(x, 3)) != 0) {
                    return;
                }
                Proveedor pro = new Proveedor();
                pro.setIdProveedor(Integer.parseInt(valorTabla(x, 0)));
                Mensaje m = ControladorProveedor.getInstancia().eliminar(pro);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
        
        // BOTON para acceder a la galeria de proveedores
        fProveedor.btnGaleria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID((int)p.tblModel.getValueAt(x, 0));
                DialogProveedorImagenes dia = new DialogProveedorImagenes(Principal.getInstancia(), proveedor);
                dia.setVisible(true);
            }
        });
    }

    private String valorTabla(int fila, int columna) {
        try {
            return p.tblModel.getValueAt(fila, columna).toString();
        } catch (NullPointerException e) {
        }
        return "";
    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
       
        for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{pro.getIdProveedor(), pro.getIdtipoProveedor(), pro.getNombre(), pro.getNombreEmpresa(),
                pro.getTelefono(), pro.getTelefono2(), pro.getPrecioAprox()});
        }
    }

    private void llenarAreaDeProveedor(int idProveedor) {
        fProveedorArea.listModel.clear();
     
        for (ProveedorArea prov : ControladorProveedorArea.getInstancia().obtenerListaByIdProveedor(idProveedor)) {
            fProveedorArea.listModel.addElement(ControladorCiudad.getInstancia().obtenerById(prov.getIdCiudad()).getCiudad());
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        fProveedor = new frmProveedor();
        fProveedorArea = new frmEtiquetas();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[fill]" +
            "[grow, fill]" +
            "[grow, fill]"));
        contentPane.add(p, "cell 0 0");
        contentPane.add(fProveedor, "cell 0 1");
        contentPane.add(fProveedorArea, "cell 0 2");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private frmProveedor fProveedor;
    private frmEtiquetas fProveedorArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
