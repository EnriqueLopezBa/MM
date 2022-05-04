/*
 * Created by JFormDesigner on Wed May 04 08:23:39 MDT 2022
 */
package vista.paneles;

import controlador.ControladorEvento;
import java.awt.event.*;
import javax.swing.border.*;
import controlador.ControladorEventosDestacados;
import java.awt.*;
import javax.swing.*;
import modelo.Evento;
import modelo.EventosDestacados;
import vista.paneles.edit.DialogEventosDestacados;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlEventosDestacados extends JPanel {

    public pnlEventosDestacados() {
        initComponents();

        for (EventosDestacados e : ControladorEventosDestacados.getInstancia().obtenerLista()) {
            JLabel img = new JLabel();
            img.setBounds(0, 0, 300, 300);
            img.setIcon(new ImageIcon(new ImageIcon(e.getImagen()).getImage().getScaledInstance(img.getWidth() - 10, img.getHeight() - 10, Image.SCALE_DEFAULT)));
            add(img);
            Evento ev = ControladorEvento.getInstancia().obtenerByID(e.getIdEvento());
            add(new JLabel(ev.getNombreEvento()));
        }
    }

    private void lblIMGMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void lblEdicionMouseClicked(MouseEvent e) {
        DialogEventosDestacados temp = new DialogEventosDestacados(Principal.getInstancia());
        temp.setVisible(true);
        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblEdicion = new JLabel();

        //======== this ========
        setLayout(new FlowLayout());

        //---- lblEdicion ----
        lblEdicion.setText("Edicion");
        lblEdicion.setIcon(new ImageIcon(getClass().getResource("/img/configuration.png")));
        lblEdicion.setHorizontalAlignment(SwingConstants.CENTER);
        lblEdicion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEdicionMouseClicked(e);
            }
        });
        add(lblEdicion);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblEdicion;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
