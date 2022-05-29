package Calendario;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import Componentes.*;
import Componentes.DropShadow.*;
import net.miginfocom.swing.*;

/**
 *
 * @author Enrique
 */
public class PanelPopUp extends javax.swing.JPanel {

    public PanelPopUp() {
        initComponents();
       
//        int tam = new Double(Constante.getScreenSize().getHeight()*0.10).intValue();
                //        FlatSVGIcon ico = new FlatSVGIcon("img/info.svg", tam, tam);
                //        label1.setIcon(ico);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panelShadow1 = new PanelShadow();
        svgIcon1 = new SvgIcon();
        label2 = new JLabel();
        lblNombreEvento = new JLabel();
        label6 = new JLabel();
        lblCliente = new JLabel();
        label3 = new JLabel();
        lblLugar = new JLabel();
        label4 = new JLabel();
        lblDireccion = new JLabel();
        label7 = new JLabel();
        lblAdeudo = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setOpaque(false);
        setLayout(new BorderLayout());

        //======== panelShadow1 ========
        {
            panelShadow1.setBackground(Color.white);
            panelShadow1.setShadowSize(5);
            panelShadow1.setShadowType(ShadowType.BOT);
            panelShadow1.setShadowOpacity(6.0F);
            panelShadow1.setShadowColor(new Color(204, 204, 204));
            panelShadow1.setLayout(new MigLayout(
                "fill",
                // columns
                "para[grow,fill]para",
                // rows
                "[fill]" +
                "[grow,fill]" +
                "[grow,fill]" +
                "[grow,fill]" +
                "[grow,fill]" +
                "[grow,fill]unrel"));

            //---- svgIcon1 ----
            svgIcon1.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\info.svg"));
            svgIcon1.setHorizontalAlignment(SwingConstants.CENTER);
            svgIcon1.setPorcentaje(8);
            panelShadow1.add(svgIcon1, "cell 0 0");

            //---- label2 ----
            label2.setText("Nombre evento:");
            panelShadow1.add(label2, "cell 0 1, w 25%!, sg 1");

            //---- lblNombreEvento ----
            lblNombreEvento.setText("text");
            lblNombreEvento.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
            panelShadow1.add(lblNombreEvento, "cell 0 1,push");

            //---- label6 ----
            label6.setText("Cliente:");
            panelShadow1.add(label6, "cell 0 2,sizegroup 1");

            //---- lblCliente ----
            lblCliente.setText("text");
            lblCliente.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
            panelShadow1.add(lblCliente, "cell 0 2,growx 80");

            //---- label3 ----
            label3.setText("Nombre Local:");
            panelShadow1.add(label3, "cell 0 3,sizegroup 1");

            //---- lblLugar ----
            lblLugar.setText("text");
            lblLugar.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
            panelShadow1.add(lblLugar, "cell 0 3,growx 80");

            //---- label4 ----
            label4.setText("Localidad:");
            panelShadow1.add(label4, "cell 0 4,sizegroup 1");

            //---- lblDireccion ----
            lblDireccion.setText("text");
            lblDireccion.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
            panelShadow1.add(lblDireccion, "cell 0 4,growx 80");

            //---- label7 ----
            label7.setText("Adeudo:");
            panelShadow1.add(label7, "cell 0 5,sizegroup 1");

            //---- lblAdeudo ----
            lblAdeudo.setText("text");
            lblAdeudo.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
            panelShadow1.add(lblAdeudo, "cell 0 5,growx 80");
        }
        add(panelShadow1, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private PanelShadow panelShadow1;
    private SvgIcon svgIcon1;
    private JLabel label2;
    public JLabel lblNombreEvento;
    private JLabel label6;
    public JLabel lblCliente;
    private JLabel label3;
    public JLabel lblLugar;
    private JLabel label4;
    public JLabel lblDireccion;
    private JLabel label7;
    public JLabel lblAdeudo;
    // End of variables declaration//GEN-END:variables
}
