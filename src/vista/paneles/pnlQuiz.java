package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import ProgressCircle.*;
import controlador.ControladorCliente;
import controlador.ControladorEvento;
import controlador.ControladorPregunta;
import controlador.ControladorQuiz;
import independientes.Constante;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Cliente;
import modelo.Evento;
import modelo.Pregunta;
import modelo.Quiz;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogQuiz;
import vista.principales.Principal;

public class pnlQuiz extends JPanel {

    private ArrayList<Pregunta> lista;
    private Evento eventoActual = null;

    private int contador = -1;
    private int constante = -1;
    private MigLayout mig;
    private JPanel panelOpciones = null;

    private static pnlQuiz instancia;

    public static pnlQuiz getInstancia() {
        if (instancia == null) {
            instancia = new pnlQuiz();
        }
        return instancia;
    }

    public void checkAdmin() {
        lblEliminar.setVisible(Constante.getAdmin());
        lblModificar.setVisible(Constante.getAdmin());
        cargarEventos();
    }

    private void init() {
        mig = (MigLayout) this.getLayout();
        lista = ControladorPregunta.getInstancia().obtenerListaByCadenaAndIsEncuesta("", false);
        constante = 100 / lista.size();
        contador++;
        checkOpciones();
        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
        cargarEventos();
    }

    private pnlQuiz() {
        initComponents();
        init();
    }

    public void cargarEventos() {
        ArrayList<Evento> eventos = null;
        if (Constante.getClienteActivo() == null) {
            btnAtras.setEnabled(false);
            btnSiguiente.setEnabled(false);
            cmbEvento.removeAllItems();
            eventoActual = null;
            return;
        }
        btnAtras.setEnabled(true);
        btnSiguiente.setEnabled(true);
        cmbEvento.removeAllItems();
        eventoActual = null;
        eventos = ControladorEvento.getInstancia().obtenerEventoByIDCliente(Constante.getClienteActivo().getIdCliente());

        for (Evento e : eventos) {
            cmbEvento.addItem(e);
        }
        cmbEvento.setRenderer(new MyObjectListCellRenderer());

    }

    private void checkOpciones() {
        String opciones[] = null;
        try {
            if (lista.get(contador).getOpciones() != null) {

                opciones = lista.get(contador).getOpciones().split(",");
                mig.setRowConstraints("[][][][][]");
                mig.setComponentConstraints(scrollPane1, "cell 0 3, spanx, grow");
                mig.setComponentConstraints(btnSiguiente, "cell 1 4,align right top");
                mig.setComponentConstraints(btnAtras, "cell 0 4,align left top");
                mig.setComponentConstraints(lblModificar, "cell 0 5, spanx");
                panelOpciones = new JPanel();
                panelOpciones.setBackground(Color.white);
                panelOpciones.setLayout(new MigLayout("fill"));
                add(panelOpciones, "cell 0 2, spanx, grow");
                ButtonGroup grupo = new ButtonGroup();
                for (String texto : opciones) {
                    JToggleButton boton = new JToggleButton(texto);
                    boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    boton.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            if (boton.isSelected()) {
                                boton.setBackground(new Color(204, 255, 204));
                            } else {
                                boton.setBackground(Color.white);
                            }
                        }
                    });
                    panelOpciones.add(boton, " align center");
                    grupo.add(boton);
                }

            } else {
                if (panelOpciones != null) {
                    remove(panelOpciones);
                    mig.setRowConstraints("[][][][]");
                    mig.setComponentConstraints(scrollPane1, "cell 0 2, spanx, grow");
                    mig.setComponentConstraints(btnSiguiente, "cell 1 3,align right top");
                    mig.setComponentConstraints(btnAtras, "cell 0 3,align left top");
                    mig.setComponentConstraints(lblModificar, "cell 0 4, spanx");
                    panelOpciones = null;
                }

            }
            revalidate();
            repaint();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("das");
        }

    }

    private void btnAtras(ActionEvent e) {
        if (eventoActual == null) {
            cmbEvento.requestFocus();
            Constante.mensaje("Selecciona un evento", Tipo.ERROR);
            return;
        }
        String respuesta = txtRespuesta.getText();
        String opciones = "";
        if (panelOpciones != null) {
            for (Component com : panelOpciones.getComponents()) {
                if (com instanceof JToggleButton) {
                    if (((JToggleButton) com).isSelected()) {
                        opciones = ((JToggleButton) com).getText();
                        break;
                    }
                }
            }
        }
        Quiz q = new Quiz();
        q.setIdPregunta(lista.get(contador).getIdPregunta());
        q.setIdCliente(Constante.getClienteActivo().getIdCliente());
        q.setIdEvento(eventoActual.getIdEvento());
        q.setRespuesta(respuesta);
        q.setOpciones(opciones);
        if (respuesta.equals("Escribenos tu respuesta")) {
            respuesta = "";
        }
        if (!respuesta.isEmpty() || !opciones.isEmpty()) {
            Mensaje m = ControladorQuiz.getInstancia().registrar(q);

        }

        if (contador == 0) {
            return;
        }
        contador--;
         if (constante > 0) {
            circleProgressBar1.setValor(constante * -1);
        }
        
        checkOpciones();
        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
        Quiz quiz = ControladorQuiz.getInstancia().obtenerByIdPreguntaAndIdEvento(lista.get(contador).getIdPregunta(), eventoActual.getIdEvento());
        if (quiz != null) {
            txtRespuesta.setText(quiz.getRespuesta());
            if (quiz.getOpciones() != null) {
                for (Component com : panelOpciones.getComponents()) {
                    if (com instanceof JToggleButton) {
                        if (((JToggleButton) com).getText().equals(quiz.getOpciones())) {
                            ((JToggleButton) com).setSelected(true);
                        }
                    }
                }
            }
        }
    }

    private void btnSiguiente(ActionEvent e) {
        if (eventoActual == null) {
            cmbEvento.requestFocus();
            Constante.mensaje("Selecciona un evento", Tipo.ADVERTENCIA);
            return;
        }

        String respuesta = txtRespuesta.getText();
        String opciones = "";
        if (panelOpciones != null) {
            for (Component com : panelOpciones.getComponents()) {
                if (com instanceof JToggleButton) {
                    if (((JToggleButton) com).isSelected()) {
                        opciones = ((JToggleButton) com).getText();
                    }
                }
            }
        }
        Quiz q = new Quiz();
        q.setIdPregunta(lista.get(contador).getIdPregunta());
        q.setIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente());
        q.setIdEvento(eventoActual.getIdEvento());

        q.setRespuesta(respuesta);
        q.setOpciones(opciones);
        if (respuesta.equals("Escribenos tu respuesta")) {
            respuesta = "";
        }
        if (!respuesta.isEmpty() || !opciones.isEmpty()) {
            Mensaje m = ControladorQuiz.getInstancia().registrar(q);
        }
        if (contador == lista.size() - 1) {
            circleProgressBar1.setValor(100);
            Constante.mensaje("Hemos terminado con las preguntas", Tipo.OK);
            return;
        }
        txtRespuesta.setText("Escribenos tu respuesta");
        contador++;
        if (constante < 100) {
            circleProgressBar1.setValor(constante);
        }

        checkOpciones();

        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
        checarRespuestaGuardada();
    }

    private void checarRespuestaGuardada() {
        Quiz quiz = ControladorQuiz.getInstancia().obtenerByIdPreguntaAndIdEvento(lista.get(contador).getIdPregunta(), eventoActual.getIdEvento());
        if (quiz != null) {
            txtRespuesta.setText(quiz.getRespuesta());
            if (quiz.getOpciones() != null) {
                for (Component com : panelOpciones.getComponents()) {
                    if (com instanceof JToggleButton) {
                        if (((JToggleButton) com).getText().equals(quiz.getOpciones())) {
                            ((JToggleButton) com).setSelected(true);
                        }
                    }
                }
            }
        } else {
            txtRespuesta.setText("Escribenos tu respuesta");
        }
    }

    private void lblModificarMouseClicked(MouseEvent e) {
        DialogQuiz temp = new DialogQuiz(Principal.getInstancia(), this);
        temp.setVisible(true);
    }

    private void txtRespuestaMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void txtRespuestaFocusGained(FocusEvent e) {
        if (txtRespuesta.getText().equals("Escribenos tu respuesta")) {
            txtRespuesta.setText("");
        }

    }

    private void txtRespuestaFocusLost(FocusEvent e) {
        if (txtRespuesta.getText().isEmpty()) {
            txtRespuesta.setText("Escribenos tu respuesta");
        }

    }

    private void lblEliminarMouseClicked(MouseEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Seguro desea borrar esta pregunta?") != 0) {
            return;
        }
        Pregunta t = new Pregunta();
        t.setIdPregunta(lista.get(contador).getIdPregunta());
        Mensaje m = ControladorPregunta.getInstancia().eliminar(t);
        if (m.getTipoMensaje() == Tipo.OK) {
            lista = ControladorPregunta.getInstancia().obtenerListaByCadenaAndIsEncuesta("", false);
            constante = 100 / lista.size();
            contador = 0;
            circleProgressBar1.setValor(0);
            checkOpciones();
            lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
            checarRespuestaGuardada();
        }
        Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
    }

    private void cmbEventoItemStateChanged(ItemEvent e) {
        if (cmbEvento.getSelectedIndex() == -1) {
            return;
        }
        if ((Evento) cmbEvento.getSelectedItem() != eventoActual) {
            lista = ControladorPregunta.getInstancia().obtenerListaByCadenaAndIsEncuesta("", false);
            constante = 100 / lista.size();
            contador = 0;
            checkOpciones();
            lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");

        }
        eventoActual = (Evento) cmbEvento.getSelectedItem();
        checarRespuestaGuardada();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbEvento = new JComboBox();
        lblEliminar = new JLabel();
        circleProgressBar1 = new CircleProgressBar();
        lblPregunta = new JLabel();
        scrollPane1 = new JScrollPane();
        txtRespuesta = new JTextArea();
        btnAtras = new Button();
        btnSiguiente = new Button();
        lblModificar = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setName("asd");
        setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]para" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- cmbEvento ----
        cmbEvento.addItemListener(e -> cmbEventoItemStateChanged(e));
        add(cmbEvento, "pos 0.01al 0.03al");

        //---- lblEliminar ----
        lblEliminar.setText("Eliminar Pregunta");
        lblEliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
        lblEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEliminarMouseClicked(e);
            }
        });
        add(lblEliminar, "pos 0.9al 0.03al");

        //---- circleProgressBar1 ----
        circleProgressBar1.setBackground(Color.white);
        circleProgressBar1.setForeground(new Color(51, 153, 0));
        circleProgressBar1.setEnabled(false);
        add(circleProgressBar1, "cell 0 0 2 1");

        //---- lblPregunta ----
        lblPregunta.setText("text");
        lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
        lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblPregunta, "cell 0 1 2 1,aligny top");

        //======== scrollPane1 ========
        {

            //---- txtRespuesta ----
            txtRespuesta.setFont(new Font("Segoe UI", Font.BOLD, 18));
            txtRespuesta.setLineWrap(true);
            txtRespuesta.setWrapStyleWord(true);
            txtRespuesta.setText("Escribenos tu respuesta");
            txtRespuesta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    txtRespuestaMouseClicked(e);
                }
            });
            txtRespuesta.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtRespuestaFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    txtRespuestaFocusLost(e);
                }
            });
            scrollPane1.setViewportView(txtRespuesta);
        }
        add(scrollPane1, "cell 0 2 2 1,grow");

        //---- btnAtras ----
        btnAtras.setText("Atras");
        btnAtras.setColorBackground(new Color(255, 102, 102));
        btnAtras.setColorBackground2(new Color(255, 153, 102));
        btnAtras.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnAtras.addActionListener(e -> btnAtras(e));
        add(btnAtras, "cell 0 3,align left top,height 5%!, w 30%!");

        //---- btnSiguiente ----
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnSiguiente.addActionListener(e -> btnSiguiente(e));
        add(btnSiguiente, "cell 1 3,align right top,height 5%!, w 30%!");

        //---- lblModificar ----
        lblModificar.setText("Edicion");
        lblModificar.setHorizontalAlignment(SwingConstants.CENTER);
        lblModificar.setForeground(new Color(0, 102, 153));
        lblModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblModificar.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblModificar.setIcon(new ImageIcon(getClass().getResource("/img/admin.png")));
        lblModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblModificarMouseClicked(e);
            }
        });
        add(lblModificar, "cell 0 4, spanx");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JComboBox cmbEvento;
    private JLabel lblEliminar;
    private CircleProgressBar circleProgressBar1;
    private JLabel lblPregunta;
    private JScrollPane scrollPane1;
    private JTextArea txtRespuesta;
    private Button btnAtras;
    private Button btnSiguiente;
    private JLabel lblModificar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
