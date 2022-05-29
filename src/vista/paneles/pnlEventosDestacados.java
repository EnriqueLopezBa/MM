/*
 * Created by JFormDesigner on Wed May 04 08:23:39 MDT 2022
 */
package vista.paneles;

import controlador.ControladorEvento;
import java.awt.event.*;
import javax.swing.border.*;
import controlador.ControladorEventosDestacados;
import independientes.Constante;
import java.awt.*;
import javax.swing.*;
import independientes.image_slider.*;
import modelo.Evento;
import modelo.EventosDestacados;
import net.miginfocom.swing.*;
import vista.paneles.edit.DialogEventosDestacados;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlEventosDestacados extends JPanel {

    private static pnlEventosDestacados instancia;
    public static pnlEventosDestacados getInstancia(){
        if (instancia == null) {
            instancia = new pnlEventosDestacados();
        }
        return instancia;
    }
    
    public void checkAdmin(){
        lblEdicion.setVisible(Constante.getAdmin());
    }
    private pnlEventosDestacados() {
        initComponents();

        for (EventosDestacados e : ControladorEventosDestacados.getInstancia().obtenerLista()) {
            JLabel img = new JLabel();
            img.setBounds(0, 0, 300, 300);
            img.setIcon(new ImageIcon(new ImageIcon(e.getImagen()).getImage().getScaledInstance(img.getWidth() - 10, img.getHeight() - 10, Image.SCALE_DEFAULT)));
            i.init(ScrollBar.VERTICAL);
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
        i = new ImageSlider();

        //======== this ========
        setLayout(new MigLayout(
            "insets 0,hidemode 3,gap 0 0",
            // columns
            "para[grow,fill]para",
            // rows
            "[fill]" +
            "[grow,fill]"));

        //---- lblEdicion ----
        lblEdicion.setText("Edicion");
        lblEdicion.setIcon(new ImageIcon(getClass().getResource("/img/configuration.png")));
        lblEdicion.setHorizontalAlignment(SwingConstants.CENTER);
        lblEdicion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEdicion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblEdicionMouseClicked(e);
            }
        });
        add(lblEdicion, "cell 0 0");
        add(i, "cell 0 1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblEdicion;
    private ImageSlider i;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
