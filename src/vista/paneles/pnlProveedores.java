package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.tableC.*;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorArea;
import controlador.ControladorProveedorEvento;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import independientes.image_slider.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import modelo.Cliente;
import modelo.Evento;
import modelo.Proveedor;
import modelo.ProveedorArea;
import modelo.ProveedorEvento;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogProveedor;
import vista.paneles.edit.DialogTipoProveedor;
import vista.principales.Principal;

public class pnlProveedores extends JPanel {

    private Cliente clienteTemp = null;

    private TipoProveedor tipoProveedorActual;
    private Proveedor proveedorActual;
    private Evento eventoActual;

    private DefaultTableModel m;

    public pnlProveedores() {
        initComponents();
            m = (DefaultTableModel) tblProveedor.getModel();
      
        cargarNombreEvento();
        cargarTipoProveedor();
        cargarProveedores();
    
         tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
         tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(0));
    }

    private void cargarTipoProveedor() {
        cmbTipoProveedor.removeAllItems();
        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            cmbTipoProveedor.addItem(tipo.getTipoProveedor());
        }
    }

    private void cargarProveedores() {
        cmbProveedor.removeAllItems();
        //Cargar proveedores dependiendo del area donde trabajan los proveedores
        int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
        for (ProveedorArea prov : ControladorProveedorArea.getInstancia().obtenerListaByIdCiudad(idCiudad)) {
            for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
                if (prov.getIdProveedor() == pro.getIdProveedor()) {
                    cmbProveedor.addItem(pro.getNombreEmpresa());
                }
            }
        }

    }
  
    private void cargarNombreEvento() {
        if (Constante.clienteTemporal == null) {
            cmbNombreEvento.removeAllItems();
      
            for (Evento e : ControladorEvento.getInstancia().obtenerEventoByIDCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente())) {
                cmbNombreEvento.addItem(e.getNombreEvento());
            }
            return;
        }
        if (Constante.clienteTemporal == clienteTemp) {
            return;
        }
        clienteTemp = Constante.clienteTemporal;
        cmbNombreEvento.removeAllItems();
        for (Evento e : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
            cmbNombreEvento.addItem(e.getNombreEvento());
        }

    }

    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
        if (cmbTipoProveedor.getSelectedIndex() == -1) {
            return;
        }
        for (TipoProveedor tipo : ControladorTipoProveedor.getInstancia().obtenerListaByCadena("")) {
            if (tipo.getTipoProveedor().equals(cmbTipoProveedor.getSelectedItem().toString())) {
                tipoProveedorActual = tipo;
                cargarProveedores();
               int idCiudad = ControladorLugar.getInstancia().obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
                i.proveedorImagenesByCiudadAndTipoProveedor(idCiudad, tipo.getIdTipoProveedor());
            }
        }
    }

    private void cmbNombreEventoItemStateChanged(ItemEvent e) {
        if (cmbNombreEvento.getSelectedIndex() == -1) {
            return;
        }
        if (Constante.clienteTemporal == null) {
            for (Evento eve : ControladorEvento.getInstancia().obtenerEventoByIDCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente())) {
                if (eve.getNombreEvento().equals(cmbNombreEvento.getSelectedItem().toString())) {
                    eventoActual = eve;
                }
            }
        } else {
            for (Evento eve : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
                if (eve.getNombreEvento().equals(cmbNombreEvento.getSelectedItem().toString())) {
                    eventoActual = eve;
                }
            }
        }
        cargarProveedorEvento();

    }
    private void cargarProveedorEvento(){
        if (eventoActual == null || ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento()) == null) {
            return;
        }
        m.setRowCount(0);
        for(ProveedorEvento pro : ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento())){
          Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(pro.getIdProveedor());
          TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID(proveedor.getIdtipoProveedor());
            m.addRow(new Object[]{tipo.getIdTipoProveedor(), proveedor.getIdProveedor(), tipo.getTipoProveedor(), proveedor.getNombreEmpresa()});
        }
    }

    private void cmbProveedorItemStateChanged(ItemEvent e) {
        if (cmbProveedor.getSelectedIndex() == -1 || tipoProveedorActual == null) {
            return;
        }
        for (Proveedor pro : ControladorProveedor.getInstancia().obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
            if (pro.getNombreEmpresa().equals(cmbProveedor.getSelectedItem().toString())) {
                proveedorActual = pro;
            }
        }
    }

    private void lblEditTipoProveedorMouseClicked(MouseEvent e) {
        DialogTipoProveedor temp = new DialogTipoProveedor(Principal.getInstancia());
        temp.setVisible(true);
    }

    private void lblEditProveedorMouseClicked(MouseEvent e) {
        DialogProveedor temp = new DialogProveedor(Principal.getInstancia());
        temp.setVisible(true);
    }

    private void btnAgregarProveedor(ActionEvent e) {

        if (cbOtro.isSelected()) {
            for (int j = 0; j < m.getRowCount(); j++) {
                if (m.getValueAt(j, 3).toString().equals(cmbProveedor.getEditor().getItem().toString())) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                    return;
                }
            }
            tblProveedor.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), null,
                tipoProveedorActual.getTipoProveedor(), cmbProveedor.getEditor().getItem().toString()});
        } else {
            for (int j = 0; j < m.getRowCount(); j++) {
             
                if ((int)m.getValueAt(j, 1) == proveedorActual.getIdProveedor()) {
                    Constante.mensaje("Este ya existe", Message.Tipo.ERROR);
                  
                    return;
                }
            }
                tblProveedor.addRow(new Object[]{tipoProveedorActual.getIdTipoProveedor(), proveedorActual.getIdProveedor(),
                    tipoProveedorActual.getTipoProveedor(), proveedorActual.getNombreEmpresa()});
           

        }
    }
    

    private void btnEliminar(ActionEvent e) {
        int x = tblProveedor.getSelectedRow();
        if (x == -1) {
            return;
        }
        m.removeRow(x);
    }

    private void cbOtro(ActionEvent e) {
        if (cbOtro.isSelected()) {
            cmbProveedor.setEditable(true);
        } else {
            cmbProveedor.setEditable(false);
        }

    }

    private void btnFinalizarProveedor(ActionEvent e) {
        if (eventoActual == null || m.getRowCount() == 0) {
            return;
        }
        ArrayList<ProveedorEvento> proE = new ArrayList<>();
        for (int j = 0; j < m.getRowCount(); j++) { // comprobrar que existen proveedores nuevo a registrar
            if (m.getValueAt(j, 1) == null) {
                Proveedor pro = new Proveedor();
                pro.setNombreEmpresa(m.getValueAt(j, 3).toString());
                ControladorProveedor.getInstancia().registrar(pro);
                m.setValueAt(ControladorProveedor.getInstancia().obtenerByLast().getIdProveedor(), j, 1);
            }
            proE.add(new ProveedorEvento(eventoActual.getIdEvento(), (int)m.getValueAt(j, 1), new Date(), new Date()));
        }
        Mensaje m = ControladorProveedorEvento.getInstancia().registrarLote(proE);
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblTitutlo = new JLabel();
        lblNombreEvento = new JLabel();
        cmbNombreEvento = new JComboBox();
        scrollPane1 = new JScrollPane();
        tblProveedor = new Table();
        i = new ImageSlider();
        lblTipoProveedor = new JLabel();
        cmbTipoProveedor = new JComboBox();
        lblEditTipoProveedor = new JLabel();
        lblProveedor = new JLabel();
        cmbProveedor = new JComboBox();
        cbOtro = new JCheckBox();
        lblEditProveedor = new JLabel();
        btnAgregarProveedor = new Button();
        btnFinalizarProveedor = new Button();
        popupMenu1 = new JPopupMenu();
        btnEliminar = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]" +
            "[grow,fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[grow, fill]" +
            "[grow, fill]" +
            "[grow,fill]" +
            "[grow, fill]" +
            "[grow, fill]" +
            "[grow, fill]unrel"));

        //---- lblTitutlo ----
        lblTitutlo.setText("Agregar Proveedores");
        lblTitutlo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitutlo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(lblTitutlo, "cell 0 0,spanx, al center");

        //---- lblNombreEvento ----
        lblNombreEvento.setText("Nombre Evento");
        add(lblNombreEvento, "cell 0 1");

        //---- cmbNombreEvento ----
        cmbNombreEvento.addItemListener(e -> cmbNombreEventoItemStateChanged(e));
        add(cmbNombreEvento, "cell 1 1, hmax 6%");

        //======== scrollPane1 ========
        {

            //---- tblProveedor ----
            tblProveedor.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "idTipoProveedor", "idProveedor", "Tipo Proveedor", "Proveedor"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tblProveedor.setComponentPopupMenu(popupMenu1);
            tblProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(tblProveedor);
        }
        add(scrollPane1, "cell 2 1 1 5,grow");

        //---- i ----
        i.setBackground(Color.white);
        add(i, "cell 3 1 1 5,grow");

        //---- lblTipoProveedor ----
        lblTipoProveedor.setText("Tipo Proveeedor");
        add(lblTipoProveedor, "cell 0 2");

        //---- cmbTipoProveedor ----
        cmbTipoProveedor.addItemListener(e -> cmbTipoProveedorItemStateChanged(e));
        add(cmbTipoProveedor, "cell 1 2, hmax 6%, split 2");

        //---- lblEditTipoProveedor ----
        lblEditTipoProveedor.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditTipoProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditTipoProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditTipoProveedorMouseClicked(e);
            }
        });
        add(lblEditTipoProveedor, "cell 1 2, grow 0 0");

        //---- lblProveedor ----
        lblProveedor.setText("Proveedor");
        add(lblProveedor, "cell 0 3");

        //---- cmbProveedor ----
        cmbProveedor.addItemListener(e -> cmbProveedorItemStateChanged(e));
        add(cmbProveedor, "cell 1 3, hmax 6%, split 2");

        //---- cbOtro ----
        cbOtro.setText("Otro");
        cbOtro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cbOtro.addActionListener(e -> cbOtro(e));
        add(cbOtro, "cell 1 3, split 2, grow 0 0");

        //---- lblEditProveedor ----
        lblEditProveedor.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        lblEditProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEditProveedorMouseClicked(e);
            }
        });
        add(lblEditProveedor, "cell 1 3, grow 0 0");

        //---- btnAgregarProveedor ----
        btnAgregarProveedor.setText("Agregar Proveedor");
        btnAgregarProveedor.addActionListener(e -> btnAgregarProveedor(e));
        add(btnAgregarProveedor, "cell 1 4, hmax 6%");

        //---- btnFinalizarProveedor ----
        btnFinalizarProveedor.setText("Finalizar");
        btnFinalizarProveedor.setColorBackground(new Color(0, 204, 0));
        btnFinalizarProveedor.setColorBackground2(new Color(51, 255, 102));
        btnFinalizarProveedor.addActionListener(e -> btnFinalizarProveedor(e));
        add(btnFinalizarProveedor, "cell 1 5,hmax 6%");

        //======== popupMenu1 ========
        {

            //---- btnEliminar ----
            btnEliminar.setText("Remover");
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminar.addActionListener(e -> btnEliminar(e));
            popupMenu1.add(btnEliminar);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblTitutlo;
    private JLabel lblNombreEvento;
    private JComboBox cmbNombreEvento;
    private JScrollPane scrollPane1;
    private Table tblProveedor;
    private ImageSlider i;
    private JLabel lblTipoProveedor;
    private JComboBox cmbTipoProveedor;
    private JLabel lblEditTipoProveedor;
    private JLabel lblProveedor;
    private JComboBox cmbProveedor;
    private JCheckBox cbOtro;
    private JLabel lblEditProveedor;
    private Button btnAgregarProveedor;
    private Button btnFinalizarProveedor;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
