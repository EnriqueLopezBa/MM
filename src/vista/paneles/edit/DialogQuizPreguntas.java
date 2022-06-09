package vista.paneles.edit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import controlador.ControladorPregunta;
import controlador.ControladorQuiz;
import independientes.Constante;
import independientes.MMException;
import independientes.Mensaje;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.event.*;
import javax.swing.table.*;
import modelo.Cliente;
import modelo.Evento;
import modelo.Pregunta;
import modelo.Quiz;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

public class DialogQuizPreguntas extends JDialog {

    private DefaultListModel<String> mLista = new DefaultListModel<>();
    private DefaultTableModel m;
    private Evento eventoActual;

    public DialogQuizPreguntas(Principal owner, pnlQuiz quiz) {
        super(owner);
        initComponents();
        super.getContentPane().setBackground(Color.white);
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        super.setSize(new Dimension(new Double(screensize.getWidth() / 1.5).intValue(), new Double(screensize.getHeight() / 1.2).intValue()));
        super.setLocationRelativeTo(null);
        p.init(new String[]{"idPregunta", "Pregunta", "Opciones"}, 1, false);
        cargarPreguntas();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                cargarPreguntas();
            }

        });
        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                try {
                    validaDatos(txtPregunta);
                    Pregunta pregunta = new Pregunta();
                    pregunta.setIdPregunta((int) p.tblModel.getValueAt(x, 0));
                    pregunta.setPregunta(txtPregunta.getText());
                    if (!mLista.isEmpty()) {
                        String concat = "";
                        for (int i = 0; i < mLista.getSize(); i++) {
                            concat += mLista.elementAt(i) + ",";
                        }
                        concat = concat.substring(0, concat.length() - 1);
                        pregunta.setOpciones(concat);
                    }
                    Mensaje m = ControladorPregunta.getInstancia().actualizar(pregunta);
                    if (m.getTipoMensaje() == Tipo.OK) {
                        cargarPreguntas();
                    }
                    Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

                } catch (MMException ex) {
                    Constante.mensaje(ex.getMessage(), Tipo.ADVERTENCIA);
                }

            }
        });
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar esta pregunta?") != 0) {
                    return;
                }
                Pregunta pregunta = new Pregunta();
                pregunta.setIdPregunta((int) p.tblModel.getValueAt(x, 0));
                Mensaje m = ControladorPregunta.getInstancia().eliminar(pregunta);
                if (m.getTipoMensaje() == Tipo.OK) {
                    cargarPreguntas();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
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

                Pregunta pregunta = ControladorPregunta.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 0));
                txtPregunta.setText(pregunta.getPregunta());
                if (pregunta.getOpciones() == null) {
                    return;
                }
                mLista.clear();
                String opciones[] = pregunta.getOpciones().split(",");
                for (int i = 0; i < opciones.length; i++) {
                    mLista.addElement(opciones[i]);
                }
                list.setModel(mLista);

            }

        });

    }

    private void cargarPreguntas() {
        p.tblModel.setRowCount(0);
        for (Pregunta pr : ControladorPregunta.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{pr.getIdPregunta(), pr.getPregunta(), pr.getOpciones()});
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
            Mensaje mm = ControladorPregunta.getInstancia().registrar(pregunta);
            if (mm.getTipoMensaje() == Tipo.OK) {
                cargarPreguntas();
            }
            Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
        } catch (MMException ee) {
            Constante.mensaje(ee.getMessage(), Message.Tipo.ERROR);
        }
    }

    private void btnEliminarRespuesta(ActionEvent e) {
//        int x = tblPregunta.getSelectedRow();
//        if (x != -1) {
//            int idPregunta = (int) m.getValueAt(x, 0);
//            int idCliente = (int) m.getValueAt(x, 1);
//            Mensaje m = ControladorQuiz.getInstancia().eliminarRespuesta(idPregunta, eventoActual.getIdEvento(), idCliente);
//            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
//            if (m.getTipoMensaje() == Tipo.OK) {
//                cargarTable();
//            }
//        }
//        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlPregunta = new JPanel();
        lblPregunta = new JLabel();
        scrollPane3 = new JScrollPane();
        txtPregunta = new JTextPane();
        btnAgregarOpcion = new Button();
        scrollPane2 = new JScrollPane();
        list = new JList();
        cb = new JCheckBox();
        btnAgregar = new Button();
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
            "[30%,fill]"));

        //======== pnlPregunta ========
        {
            pnlPregunta.setBackground(Color.white);
            pnlPregunta.setLayout(new MigLayout(
                "fill",
                // columns
                "[grow 25, fill]" +
                "[grow 75, fill]",
                // rows
                "[grow,fill]" +
                "[grow,fill]" +
                "[]" +
                "[grow,fill]"));

            //---- lblPregunta ----
            lblPregunta.setText("Pregunta");
            lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 18));
            pnlPregunta.add(lblPregunta, "cell 0 0");

            //======== scrollPane3 ========
            {

                //---- txtPregunta ----
                txtPregunta.setFont(new Font("Segoe UI", Font.BOLD, 16));
                scrollPane3.setViewportView(txtPregunta);
            }
            pnlPregunta.add(scrollPane3, "cell 1 0, h 10%!");

            //---- btnAgregarOpcion ----
            btnAgregarOpcion.setText("Agregar Opcion");
            btnAgregarOpcion.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btnAgregarOpcion.addActionListener(e -> btnAgregarOpcion(e));
            pnlPregunta.add(btnAgregarOpcion, "cell 0 1,aligny center,height 10%!");

            //======== scrollPane2 ========
            {

                //---- list ----
                list.setVisibleRowCount(4);
                list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.setComponentPopupMenu(popupMenu1);
                scrollPane2.setViewportView(list);
            }
            pnlPregunta.add(scrollPane2, "cell 1 1");

            //---- cb ----
            cb.setText("Encuesta de satisfaccion");
            cb.setFont(new Font("Segoe UI", Font.BOLD, 15));
            cb.setVisible(false);
            pnlPregunta.add(cb, "cell 0 2");

            //---- btnAgregar ----
            btnAgregar.setText("Agregar");
            btnAgregar.setColorBackground(new Color(0, 255, 51));
            btnAgregar.setColorBackground2(new Color(0, 204, 51));
            btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btnAgregar.addActionListener(e -> btnAgregar(e));
            pnlPregunta.add(btnAgregar, "cell 0 3 2 1,height 10%!");
        }
        contentPane.add(pnlPregunta, "cell 0 0 2 1");
        contentPane.add(p, "cell 0 1 2 1");
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
            btnEliminarRespuesta.addActionListener(e -> btnEliminarRespuesta(e));
            popupMenu2.add(btnEliminarRespuesta);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlPregunta;
    private JLabel lblPregunta;
    private JScrollPane scrollPane3;
    private JTextPane txtPregunta;
    private Button btnAgregarOpcion;
    private JScrollPane scrollPane2;
    private JList list;
    private JCheckBox cb;
    private Button btnAgregar;
    private pnlCRUD p;
    private JPopupMenu popupMenu1;
    private JMenuItem btnEliminar;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminarRespuesta;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
