package vista.paneles.edit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import controlador.ControladorCliente;
import controlador.ControladorPregunta;
import controlador.ControladorQuiz;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.event.*;
import javax.swing.table.*;
import modelo.Cliente;
import modelo.Pregunta;
import modelo.Quiz;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

public class DialogQuiz extends JDialog {

    DefaultListModel<String> mLista = new DefaultListModel<>();
    DefaultTableModel m;

    public DialogQuiz(Principal owner, pnlQuiz quiz) {
        super(owner);
        initComponents();
        m = (DefaultTableModel) tblPregunta.getModel();
        tblPregunta.removeColumn(tblPregunta.getColumnModel().getColumn(0));
        tblPregunta.removeColumn(tblPregunta.getColumnModel().getColumn(0));
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        super.setSize(new Dimension(new Double(screensize.getWidth() / 1.5).intValue(), super.getPreferredSize().height));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idCliente", "Nombre", "Apellido"}, HIDE_ON_CLOSE, false);
        p.btnEliminar.setVisible(false);
        p.btnModificar.setVisible(false);
        cargarClientes();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e); //To change body of generated methods, choose Tools | Templates.
                Constante.removeClienteTemporal();
                dispose();
            }

        });
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                cargarClientes();
            }

        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Cliente cliente = ControladorCliente.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0));
                Constante.setClienteTemporal(cliente);
                cargarQuiz();
                quiz.cargarEventos();
            }

        });

    }

    private void cargarQuiz(){
        m.setRowCount(0);
        for(Quiz z : ControladorQuiz.getInstancia().obtenerListaByIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente())){
            Pregunta pregunta = ControladorPregunta.getInstancia().obtenerByID(z.getIdPregunta());
            m.addRow(new Object[]{z.getIdPregunta(), z.getIdCliente(), pregunta.getPregunta(), z.getRespuesta()});
        }
    }
    private void cargarClientes() {
        p.tblModel.setRowCount(0);
        for (Cliente c : ControladorCliente.getInstancia().obtenerClientes(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getApellido()});
        }
    }

    private void validaDatos(JTextPane f) throws MMException {
        if (f.getText().isEmpty()) {
            f.requestFocus();
            throw new MMException("Pregunta vacia");
        }
    }

    private void btnAgregarOpcion(ActionEvent e) {
        String dato = showInputDialog(null, "Escriba la opcion");
        if (dato != null) {
            mLista.addElement(dato);
            list.setModel(mLista);
        }

    }

    private void btnEliminar(ActionEvent e) {
        if (list.getSelectedIndex() == -1) {
            return;
        }
        mLista.remove(list.getSelectedIndex());
        list.setModel(mLista);
    }

    private void btnAgregar(ActionEvent e) {
        try {
            validaDatos(txtPregunta);
            String opciones = "";

            if (!mLista.isEmpty()) {
                for (Object a : mLista.toArray()) {
                    opciones += a + ",";
                }
                opciones = opciones.substring(0, opciones.length() - 1);
            }

            Pregunta pregunta = new Pregunta();
            pregunta.setPregunta(txtPregunta.getText());
            pregunta.setEscuestaSatisfaccion(cb.isSelected());
            pregunta.setOpciones(opciones);
            Mensaje m = ControladorPregunta.getInstancia().registrar(pregunta);
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
        } catch (MMException ee) {
            Constante.mensaje(ee.getMessage(), Message.Tipo.ERROR);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        scrollPane3 = new JScrollPane();
        txtPregunta = new JTextPane();
        btnAgregarOpcion = new Button();
        scrollPane2 = new JScrollPane();
        list = new JList();
        cb = new JCheckBox();
        btnAgregar = new Button();
        scrollPane1 = new JScrollPane();
        tblPregunta = new JTable();
        p = new pnlCRUD();
        popupMenu1 = new JPopupMenu();
        btnEliminar = new JMenuItem();
        popupMenu2 = new JPopupMenu();
        btnEliminarRespuesta = new JMenuItem();

        //======== this ========
        setBackground(Color.white);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]" +
            "[grow,fill]",
            // rows
            "[grow, fill]" +
            "[grow,fill]"));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                "fill",
                // columns
                "[grow 25, fill]" +
                "[grow 75, fill]",
                // rows
                "[grow,fill]" +
                "[grow,fill]" +
                "[]" +
                "[grow,fill]"));

            //---- label1 ----
            label1.setText("Pregunta");
            label1.setFont(new Font("Segoe UI", Font.BOLD, 18));
            panel1.add(label1, "cell 0 0");

            //======== scrollPane3 ========
            {

                //---- txtPregunta ----
                txtPregunta.setFont(new Font("Segoe UI", Font.BOLD, 16));
                scrollPane3.setViewportView(txtPregunta);
            }
            panel1.add(scrollPane3, "cell 1 0, h 10%!");

            //---- btnAgregarOpcion ----
            btnAgregarOpcion.setText("Agregar Opcion");
            btnAgregarOpcion.addActionListener(e -> btnAgregarOpcion(e));
            panel1.add(btnAgregarOpcion, "cell 0 1,aligny center,height 10%!");

            //======== scrollPane2 ========
            {

                //---- list ----
                list.setVisibleRowCount(4);
                list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.setComponentPopupMenu(popupMenu1);
                scrollPane2.setViewportView(list);
            }
            panel1.add(scrollPane2, "cell 1 1");

            //---- cb ----
            cb.setText("Encuesta de satisfaccion");
            cb.setFont(new Font("Segoe UI", Font.BOLD, 15));
            panel1.add(cb, "cell 0 2");

            //---- btnAgregar ----
            btnAgregar.setText("Agregar");
            btnAgregar.setColorBackground(new Color(0, 255, 51));
            btnAgregar.setColorBackground2(new Color(0, 204, 51));
            btnAgregar.addActionListener(e -> btnAgregar(e));
            panel1.add(btnAgregar, "cell 0 3 2 1,height 10%!");
        }
        contentPane.add(panel1, "cell 0 1");

        //======== scrollPane1 ========
        {

            //---- tblPregunta ----
            tblPregunta.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "idPregunta", "idCliente", "Pregunta", "Respuesta"
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
            tblPregunta.setComponentPopupMenu(popupMenu2);
            scrollPane1.setViewportView(tblPregunta);
        }
        contentPane.add(scrollPane1, "cell 1 1");
        contentPane.add(p, "cell 0 2, spanx");
        pack();
        setLocationRelativeTo(getOwner());

        //======== popupMenu1 ========
        {

            //---- btnEliminar ----
            btnEliminar.setText("Eliminar");
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
            btnEliminar.addActionListener(e -> btnEliminar(e));
            popupMenu1.add(btnEliminar);
        }

        //======== popupMenu2 ========
        {

            //---- btnEliminarRespuesta ----
            btnEliminarRespuesta.setText("Eliminar Respuesta");
            popupMenu2.add(btnEliminarRespuesta);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JScrollPane scrollPane3;
    private JTextPane txtPregunta;
    private Button btnAgregarOpcion;
    private JScrollPane scrollPane2;
    private JList list;
    private JCheckBox cb;
    private Button btnAgregar;
    private JScrollPane scrollPane1;
    private JTable tblPregunta;
    private pnlCRUD p;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminarRespuesta;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
