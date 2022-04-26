/*
 * Created by JFormDesigner on Mon Apr 25 15:58:16 MDT 2022
 */
package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.Sweet_Alert.Button;
import Componentes.tableC.*;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorArea;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.image_slider.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import modelo.Cliente;
import modelo.Evento;
import modelo.Proveedor;
import modelo.ProveedorArea;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlProveedores extends JPanel {

    private ControladorCliente controladorCliente = new ControladorCliente();
    private ControladorEvento controladorEvento = new ControladorEvento();
    private ControladorTipoProveedor controladorTipoProveedor = new ControladorTipoProveedor();
    private ControladorProveedor controladorProveedor = new ControladorProveedor();
    private ControladorProveedorArea controladorProveedorArea = new ControladorProveedorArea();
    private ControladorLugar controladorLugar = new ControladorLugar();

    private Cliente clienteTemp = null;

    private TipoProveedor tipoProveedorActual;
    private Proveedor proveedorActual;
    private Evento eventoActual;

    public pnlProveedores() {
        initComponents();
        new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNombreEvento();
            }
        }).start();
        cargarNombreEvento();
        cargarTipoProveedor();
        cargarProveedores();
    }

    private void cargarTipoProveedor() {
        cmbTipoProveedor.removeAllItems();
        for (TipoProveedor tipo : controladorTipoProveedor.obtenerListaByCadena("")) {
            cmbTipoProveedor.addItem(tipo.getTipoProveedor());
        }
    }

    private void cargarProveedores() {
        cmbProveedor.removeAllItems();
        //Cargar proveedores dependiendo del area donde trabajan los proveedores
        int idCiudad = controladorLugar.obtenerByID(eventoActual.getIdLugar()).getIdCiudad();
        for (ProveedorArea prov : controladorProveedorArea.obtenerListaByIdCiudad(idCiudad)) {
            for (Proveedor pro : controladorProveedor.obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
                if (prov.getIdProveedor() == pro.getIdProveedor()) {
                    cmbProveedor.addItem(pro.getNombreEmpresa());
                }
            }
        }

    }

    private void cargarNombreEvento() {
        if (Constante.clienteTemporal == null) {
            cmbNombreEvento.removeAllItems();
            for (Evento e : controladorEvento.obtenerEventoByIDCliente(controladorCliente.obtenerClienteActivo().getIdCliente())) {
                cmbNombreEvento.addItem(e.getNombreEvento());
            }
            return;
        }
        if (Constante.clienteTemporal == clienteTemp) {
            return;
        }
        clienteTemp = Constante.clienteTemporal;
        cmbNombreEvento.removeAllItems();
        for (Evento e : controladorEvento.obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
            cmbNombreEvento.addItem(e.getNombreEvento());
        }

    }

    private void cmbTipoProveedorItemStateChanged(ItemEvent e) {
        if (cmbTipoProveedor.getSelectedIndex() == -1) {
            return;
        }
        for (TipoProveedor tipo : controladorTipoProveedor.obtenerListaByCadena("")) {
            if (tipo.getTipoProveedor().equals(cmbTipoProveedor.getSelectedItem().toString())) {
                tipoProveedorActual = tipo;
                cargarProveedores();
            }
        }
    }

    private void cmbNombreEventoItemStateChanged(ItemEvent e) {
        if (cmbNombreEvento.getSelectedIndex() == -1) {
            return;
        }
        if (Constante.clienteTemporal == null) {
            for (Evento eve : controladorEvento.obtenerEventoByIDCliente(controladorCliente.obtenerClienteActivo().getIdCliente())) {
                if (eve.getNombreEvento().equals(cmbNombreEvento.getSelectedItem().toString())) {
                    eventoActual = eve;
                }
            }
        } else {
            for (Evento eve : controladorEvento.obtenerEventoByIDCliente(Constante.clienteTemporal.getIdCliente())) {
                if (eve.getNombreEvento().equals(cmbNombreEvento.getSelectedItem().toString())) {
                    eventoActual = eve;
                }
            }
        }

    }

    private void cmbProveedorItemStateChanged(ItemEvent e) {
        if (cmbProveedor.getSelectedIndex() == -1 || tipoProveedorActual == null) {
            return;
        }
        for (Proveedor pro : controladorProveedor.obtenerListaByIdTipoProveedor(tipoProveedorActual.getIdTipoProveedor())) {
            if (pro.getNombreEmpresa().equals(cmbProveedor.getSelectedItem().toString())) {
                proveedorActual = pro;
            }
        }
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
        label1 = new JLabel();
        lblProveedor = new JLabel();
        cmbProveedor = new JComboBox();
        label2 = new JLabel();
        btnAgregarProveedor = new Button();
        btnFinalizarProveedor = new Button();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]" +
            "[fill]" +
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

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        add(label1, "cell 1 2, grow 0 0");

        //---- lblProveedor ----
        lblProveedor.setText("Proveedor");
        add(lblProveedor, "cell 0 3");

        //---- cmbProveedor ----
        cmbProveedor.addItemListener(e -> cmbProveedorItemStateChanged(e));
        add(cmbProveedor, "cell 1 3, hmax 6%, split 2");

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/img/edit.png")));
        add(label2, "cell 1 3, grow 0 0");

        //---- btnAgregarProveedor ----
        btnAgregarProveedor.setText("Agregar Proveedor");
        add(btnAgregarProveedor, "cell 1 4, hmax 6%");

        //---- btnFinalizarProveedor ----
        btnFinalizarProveedor.setText("Finalizar");
        btnFinalizarProveedor.setColorBackground(new Color(0, 204, 0));
        btnFinalizarProveedor.setColorBackground2(new Color(51, 255, 102));
        add(btnFinalizarProveedor, "cell 1 5,hmax 6%");
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
    private JLabel label1;
    private JLabel lblProveedor;
    private JComboBox cmbProveedor;
    private JLabel label2;
    private Button btnAgregarProveedor;
    private Button btnFinalizarProveedor;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
