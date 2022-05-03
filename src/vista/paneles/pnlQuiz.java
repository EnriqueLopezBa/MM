package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Componentes.Sweet_Alert.Button;
import ProgressCircle.*;
import controlador.ControladorPregunta;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Pregunta;
import net.miginfocom.swing.*;

public class pnlQuiz extends JPanel {

    ArrayList<Pregunta> lista;
    private int contador = -1;
    private int constante = -1;
    private MigLayout mig;
    JPanel panelOpciones = null;

    public pnlQuiz() {
        initComponents();
        mig = (MigLayout) this.getLayout();
        lista = ControladorPregunta.getInstancia().obtenerListaByCadenaAndIsEncuesta("", false);
        constante = 100 / lista.size();

        contador++;
        checkOpciones();
        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");

    }

    private void txtAbonoKeyReleased(KeyEvent e) {

    }

    private void checkOpciones() {
        String opciones[] = null;

        if (lista.get(contador).getOpciones() != null) {
            opciones = lista.get(contador).getOpciones().split(",");
            mig.setRowConstraints("[][][][][]");
            mig.setComponentConstraints(scrollPane1, "cell 0 3, spanx, grow");
            mig.setComponentConstraints(btnSiguiente, "cell 1 4,align right top");
            mig.setComponentConstraints(btnAtras, "cell 0 4,align left top");
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
                panelOpciones = null;
            }

        }
        revalidate();
        repaint();
    }

    private void btnAtras(ActionEvent e) {
        if (contador == 0) {
            return;
        }
        contador--;
        circleProgressBar1.setValor(constante* -1);
        checkOpciones();
        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
    }

    private void btnSiguiente(ActionEvent e) {
        if (contador == lista.size()) {
            return;
        }
        contador++; 
        circleProgressBar1.setValor(constante);
        checkOpciones();
        lblPregunta.setText("<html><p style=\"text-align:center\">" + lista.get(contador).getPregunta() + "</p></html>");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        cmbEvento = new JComboBox();
        circleProgressBar1 = new CircleProgressBar();
        lblPregunta = new JLabel();
        scrollPane1 = new JScrollPane();
        txtRespuesta = new JTextArea();
        btnAtras = new Button();
        btnSiguiente = new Button();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[grow,fill]" +
            "[grow,fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));
        add(cmbEvento, "pos 0.01al 0.03al");

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
            scrollPane1.setViewportView(txtRespuesta);
        }
        add(scrollPane1, "cell 0 2 2 1,grow");

        //---- btnAtras ----
        btnAtras.setText("Atras");
        btnAtras.setColorBackground(new Color(255, 102, 102));
        btnAtras.setColorBackground2(new Color(255, 153, 102));
        btnAtras.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnAtras.addActionListener(e -> btnAtras(e));
        add(btnAtras, "cell 0 3,align left top,height 10%!");

        //---- btnSiguiente ----
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnSiguiente.addActionListener(e -> btnSiguiente(e));
        add(btnSiguiente, "cell 1 3,align right top,height 10%!");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JComboBox cmbEvento;
    private CircleProgressBar circleProgressBar1;
    private JLabel lblPregunta;
    private JScrollPane scrollPane1;
    private JTextArea txtRespuesta;
    private Button btnAtras;
    private Button btnSiguiente;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
