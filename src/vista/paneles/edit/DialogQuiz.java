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

public class DialogQuiz extends JDialog {
    
    private DefaultListModel<String> mLista = new DefaultListModel<>();
    private DefaultTableModel m;
    private Evento eventoActual;
    
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
        p.init(new String[]{"idCliente", "Nombre", "Apellido"}, 1, false);
        p.btnEliminar.setVisible(false);
        p.btnModificar.setVisible(false);
        cargarClientes();

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
    
    private void cargarQuiz() {
        m.setRowCount(0);
        Cliente cliente = ControladorCliente.getInstancia().obtenerClienteActivo();
        ArrayList<Evento> temp = ControladorEvento.getInstancia().obtenerEventoByIDCliente(cliente.getIdCliente());
        if (temp.size() > 1) {
            cbEventos.removeAllItems();
            cbEventos.setVisible(true);
            for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(cliente.getIdCliente())) {
                cbEventos.addItem(ev.getNombreEvento());
            }
        } else {
            
            cbEventos.removeAllItems();
            cbEventos.setVisible(false);
            if (!temp.isEmpty()) {
                eventoActual = temp.get(0);
                cargarTable();
            }
        }
        
    }
    
    private void cargarTable() {
        m.setRowCount(0);
        for (Quiz z : ControladorQuiz.getInstancia().obtenerListaByIdClienteAndIdEvento(Constante.getClienteActivo().getIdCliente(), eventoActual.getIdEvento())) {
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
    
 
    private void cbEventosItemStateChanged(ItemEvent e) {
        if (cbEventos.getSelectedIndex() == -1 && eventoActual == null) {
            return;
        }
        
        for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente())) {
            if (ev.getNombreEvento().equals(cbEventos.getSelectedItem())) {
                eventoActual = ev;
                cargarTable();
                break;
            }
        }
        
    }
    
    private void btnEliminarRespuesta(ActionEvent e) {
        int x = tblPregunta.getSelectedRow();
        if (x != -1) {
            int idPregunta = (int) m.getValueAt(x, 0);
            int idCliente = (int) m.getValueAt(x, 1);
            Mensaje m = ControladorQuiz.getInstancia().eliminarRespuesta(idPregunta, eventoActual.getIdEvento(), idCliente);
            Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            if (m.getTipoMensaje() == Tipo.OK) {
                cargarTable();
            }
        }
        
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        cbEventos = new JComboBox();
        scrollPane1 = new JScrollPane();
        tblPregunta = new JTable();
        p = new pnlCRUD();
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
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- cbEventos ----
            cbEventos.setVisible(false);
            cbEventos.addItemListener(e -> cbEventosItemStateChanged(e));
            panel1.add(cbEventos, "cell 0 1");

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
            panel1.add(scrollPane1, "cell 0 2");
        }
        contentPane.add(panel1, "cell 0 1, spanx");
        contentPane.add(p, "cell 0 2, spanx");
        pack();
        setLocationRelativeTo(getOwner());

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
    private JPanel panel1;
    private JComboBox cbEventos;
    private JScrollPane scrollPane1;
    private JTable tblPregunta;
    private pnlCRUD p;
    private JPopupMenu popupMenu2;
    private JMenuItem btnEliminarRespuesta;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
