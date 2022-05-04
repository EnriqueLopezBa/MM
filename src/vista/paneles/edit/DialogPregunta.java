/*
 * Created by JFormDesigner on Wed May 04 06:00:39 MDT 2022
 */

package vista.paneles.edit;

import java.awt.*;
import javax.swing.*;
import Componentes.TextField;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogPregunta extends JDialog {
    public DialogPregunta(Principal owner) {
        super(owner);
        initComponents();
        
        p.init(new String[]{"idPregunta", }, 1, true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        textField1 = new TextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]"));
        contentPane.add(p, "cell 0 0");

        //---- textField1 ----
        textField1.setLabelText("Pregunta");
        textField1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        contentPane.add(textField1, "cell 0 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    private TextField textField1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
