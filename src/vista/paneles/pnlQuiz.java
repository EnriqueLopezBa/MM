package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.Sweet_Alert.Button;
import Componentes.tableC.*;
import java.text.ParseException;
import com.raven.datechooser.*;
import net.miginfocom.swing.*;

/**
 * @author das
 */
public class pnlQuiz extends JPanel {


    
    public pnlQuiz() {
        initComponents();
   
    }

    private void txtAbonoKeyReleased(KeyEvent e) {
     
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane2 = new JScrollPane();
        txtPregunta = new JTextPane();
        scrollPane1 = new JScrollPane();
        txtRespuesta = new JTextArea();
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

        //======== scrollPane2 ========
        {

            //---- txtPregunta ----
            txtPregunta.setText("<html><p style=\"text-align:center\">Center this text!</p></html>");
            txtPregunta.setEditable(false);
            txtPregunta.setBorder(null);
            txtPregunta.setDisabledTextColor(Color.black);
            txtPregunta.setContentType("text/html");
            txtPregunta.setFont(new Font("Segoe UI", Font.BOLD, 20));
            scrollPane2.setViewportView(txtPregunta);
        }
        add(scrollPane2, "cell 0 0, spanx, grow");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(txtRespuesta);
        }
        add(scrollPane1, "cell 0 1, spanx, grow");

        //---- btnSiguiente ----
        btnSiguiente.setText("Siguiente");
        add(btnSiguiente, "cell 1 2,aligny top, alignx right");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane2;
    private JTextPane txtPregunta;
    private JScrollPane scrollPane1;
    private JTextArea txtRespuesta;
    private Button btnSiguiente;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
