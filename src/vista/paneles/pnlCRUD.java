package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Button;
import Componentes.tableC.*;
import net.miginfocom.swing.*;

public class pnlCRUD extends JPanel {

    public DefaultTableModel tblModel;

    public pnlCRUD() {
        initComponents();
    }

    public void init(String columnas[], int hideColumnsCount, boolean btnAgregar) {
        if (!btnAgregar) {
            MigLayout mig = (MigLayout) pnlBotones.getLayout();
            pnlBotones.remove(this.btnAgregar);
            mig.setRowConstraints("[grow][grow]");
            mig.setComponentConstraints(btnModificar, "cell 0 0,height 50, growx");
            mig.setComponentConstraints(btnEliminar, "cell 0 1,height 50, growx");
            
        }
//        this.btnAgregar.setVisible(btnAgregar);
        tblBuscar.setModel(new DefaultTableModel(
                new Object[][]{},
                columnas
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        }
        );
        for (int i = 0; i < hideColumnsCount; i++) {
            tblBuscar.removeColumn(tblBuscar.getColumnModel().getColumn(0));
        }
        tblModel = (DefaultTableModel) tblBuscar.getModel();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlBusqueda = new JPanel();
        txtBusqueda = new TextField();
        pnlTabla = new JPanel();
        jScrollPane1 = new JScrollPane();
        tblBuscar = new Table();
        pnlBotones = new JPanel();
        btnModificar = new Button();
        btnAgregar = new Button();
        btnEliminar = new Button();
        jPopupMenu1 = new JPopupMenu();
        btnSeleccionar = new JMenuItem();
        btnEventos = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        setName("cc");
        setLayout(new MigLayout(
            null,
            // columns
            "[grow 90]rel" +
            "[grow 10]rel",
            // rows
            "[]" +
            "[grow,fill]push"));

        //======== pnlBusqueda ========
        {
            pnlBusqueda.setBackground(Color.white);
            pnlBusqueda.setPreferredSize(new Dimension(1165, 51));
            pnlBusqueda.setLayout(new MigLayout(
                null,
                // columns
                "[grow]",
                // rows
                "[grow, fill]"));

            //---- txtBusqueda ----
            txtBusqueda.setLabelText("Buscar");
            txtBusqueda.setMargin(new Insets(2, 6, 5, 6));
            pnlBusqueda.add(txtBusqueda, "cell 0 0, grow");
        }
        add(pnlBusqueda, "cell 0 0, spanx, grow");

        //======== pnlTabla ========
        {
            pnlTabla.setLayout(new BorderLayout());

            //======== jScrollPane1 ========
            {
                jScrollPane1.setBackground(new Color(204, 204, 0));

                //---- tblBuscar ----
                tblBuscar.setModel(new DefaultTableModel(
                    new Object[][] {
                    },
                    new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
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
                jScrollPane1.setViewportView(tblBuscar);
            }
            pnlTabla.add(jScrollPane1, BorderLayout.CENTER);
        }
        add(pnlTabla, "cell 0 1, grow");

        //======== pnlBotones ========
        {
            pnlBotones.setBackground(Color.white);
            pnlBotones.setPreferredSize(new Dimension(300, 338));
            pnlBotones.setLayout(new MigLayout(
                "flowy",
                // columns
                "[grow]",
                // rows
                "[grow]" +
                "[grow]" +
                "[grow]"));

            //---- btnModificar ----
            btnModificar.setText("Modificar");
            btnModificar.setColorBackground2(new Color(102, 153, 255));
            btnModificar.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
            pnlBotones.add(btnModificar, "cell 0 1,height 50, growx");

            //---- btnAgregar ----
            btnAgregar.setText("Agregar");
            btnAgregar.setColorBackground(new Color(102, 255, 102));
            btnAgregar.setColorBackground2(new Color(0, 204, 51));
            btnAgregar.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
            pnlBotones.add(btnAgregar, "cell 0 0,height 50, growx");

            //---- btnEliminar ----
            btnEliminar.setText("Eliminar");
            btnEliminar.setColorBackground(new Color(255, 102, 102));
            btnEliminar.setColorBackground2(new Color(255, 51, 51));
            btnEliminar.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
            pnlBotones.add(btnEliminar, "cell 0 2,height 50, growx");
        }
        add(pnlBotones, "cell 1 1, grow");

        //======== jPopupMenu1 ========
        {

            //---- btnSeleccionar ----
            btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/img/person.png")));
            btnSeleccionar.setText("Seleccionar Cliente");
            jPopupMenu1.add(btnSeleccionar);

            //---- btnEventos ----
            btnEventos.setText("Eventos");
            btnEventos.setIcon(new ImageIcon(getClass().getResource("/img/event.png")));
            jPopupMenu1.add(btnEventos);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlBusqueda;
    public TextField txtBusqueda;
    private JPanel pnlTabla;
    private JScrollPane jScrollPane1;
    public Table tblBuscar;
    private JPanel pnlBotones;
    public Button btnModificar;
    public Button btnAgregar;
    public Button btnEliminar;
    public JPopupMenu jPopupMenu1;
    public JMenuItem btnSeleccionar;
    public JMenuItem btnEventos;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
