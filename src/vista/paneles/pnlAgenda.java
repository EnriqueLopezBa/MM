/*
 * Created by JFormDesigner on Mon May 02 22:56:02 MDT 2022
 */
package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Calendario.*;
import controlador.ControladorEvento;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static javax.swing.JOptionPane.showMessageDialog;
import modelo.Evento;
import net.miginfocom.swing.*;

/**
 * @author das
 */
public class pnlAgenda extends JPanel {

    private Calendar calen;
    private MigLayout mig;


    public pnlAgenda() {
        initComponents();
        
        mig = (MigLayout) mi.getLayout();
        layered.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                  mi.setBounds(0, 0, layered.getSize().width, layered.getSize().height);
                  mi.revalidate();
                  
            }
       
        });
       
  
        calen = Calendar.getInstance();
        lblYear.setText(calen.get(Calendar.YEAR) + "");
        updateMonths();
    

//        for (Component com : panelDateEnero.getComponents()) {
//            Cell cell = (Cell) com;
//            if (!cell.isTitle()) {
//                cell.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseEntered(MouseEvent e) {
//                        super.mouseEntered(e); //To change body of generated methods, choose Tools | Templates.
//                        JPanel p = new JPanel() {
//                            @Override
//                            protected void paintComponent(Graphics g) {
//                                super.paintComponent(g);
//                                Dimension arcs = new Dimension(15, 15);
//                                int width = getWidth();
//                                int height = getHeight();
//                                Graphics2D graphics = (Graphics2D) g;
//                                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//                                graphics.setColor(getBackground());
//                                graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint background
//                                graphics.setColor(getForeground());
//                                graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint border
//                            }
//                        };
//
//                        p.setBackground(Color.white);
//                        p.setOpaque(false);
//
//                        add(p, "h 30%!, w 30%!, pos " + cell.getLocation().x + " " + cell.getLocationOnScreen().y);
////                        add(a, "pos (asd.x2/2) (asd.y2/2)");
//                        revalidate();
//                        repaint();
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent e) {
//                        super.mouseExited(e); //To change body of generated methods, choose Tools | Templates.
//                        for (Component com : getComponents()) {
//                            if (com instanceof JPanel) {
//                                remove(com);
//                                revalidate();
//                                repaint();
//                            }
//                        }
//                    }
//
//                });
//
//            }
//        }
//        panelDate1.init(calen.get(Calendar.MONTH+1), calen.get(Calendar.YEAR));
    }

    private void updateMonths() {
        panelDateEnero.init(1, calen.get(Calendar.YEAR));
        panelDateFebrero.init(2, calen.get(Calendar.YEAR));
        panelDateMarzo.init(3, calen.get(Calendar.YEAR));
        panelDateAbril.init(4, calen.get(Calendar.YEAR));
        panelDateMayo.init(5, calen.get(Calendar.YEAR));
        panelDateJunio.init(6, calen.get(Calendar.YEAR));
        panelDateJulio.init(7, calen.get(Calendar.YEAR));
        panelDateAgosto.init(8, calen.get(Calendar.YEAR));
        panelDateSeptiembre.init(9, calen.get(Calendar.YEAR));
        panelDateOctubre.init(10, calen.get(Calendar.YEAR));
        panelDateNoviembre.init(11, calen.get(Calendar.YEAR));
        panelDateDiciembre.init(12, calen.get(Calendar.YEAR));
    }

    private void btnAtras(ActionEvent e) {
        calen.set(Calendar.YEAR, calen.get(Calendar.YEAR) - 1);
        updateMonths();
        lblYear.setText(calen.get(Calendar.YEAR) + "");
    }

    private void btnSiguiente(ActionEvent e) {
        calen.set(Calendar.YEAR, calen.get(Calendar.YEAR) + 1);
        updateMonths();
        lblYear.setText(calen.get(Calendar.YEAR) + "");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        layered = new JLayeredPane();
        mi = new JPanel();
        btnAtras = new JButton();
        lblYear = new JLabel();
        btnSiguiente = new JButton();
        Enero = new JLabel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        panelDateEnero = new PanelDate();
        panelDateFebrero = new PanelDate();
        panelDateMarzo = new PanelDate();
        panelDateAbril = new PanelDate();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        panelDateMayo = new PanelDate();
        panelDateJunio = new PanelDate();
        panelDateJulio = new PanelDate();
        panelDateAgosto = new PanelDate();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        panelDateSeptiembre = new PanelDate();
        panelDateOctubre = new PanelDate();
        panelDateNoviembre = new PanelDate();
        panelDateDiciembre = new PanelDate();

        //======== this ========
        setBackground(Color.white);
        setName("prin");
        setLayout(new BorderLayout());

        //======== layered ========
        {
            layered.setBackground(Color.white);
            layered.setName("layered");

            //======== mi ========
            {
                mi.setBackground(Color.white);
                mi.setName("miglayout");
                mi.setLayout(new MigLayout(
                    "fill",
                    // columns
                    "para[fill]para" +
                    "[fill]para" +
                    "[fill]para" +
                    "[fill]para",
                    // rows
                    "[grow 10]" +
                    "[]" +
                    "[grow 30]" +
                    "[]" +
                    "[grow 30]" +
                    "[]" +
                    "[grow 30]"));

                //---- btnAtras ----
                btnAtras.setContentAreaFilled(false);
                btnAtras.setBorderPainted(false);
                btnAtras.setIcon(new ImageIcon(getClass().getResource("/img/back.png")));
                btnAtras.setText("Anterior");
                btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnAtras.setFont(new Font("Source Sans Pro", btnAtras.getFont().getStyle() | Font.BOLD, btnAtras.getFont().getSize() + 5));
                btnAtras.addActionListener(e -> btnAtras(e));
                mi.add(btnAtras, "cell 0 0, grow 0 0, align left");

                //---- lblYear ----
                lblYear.setText("text");
                lblYear.setHorizontalAlignment(SwingConstants.CENTER);
                lblYear.setFont(new Font("Source Sans Pro", lblYear.getFont().getStyle() | Font.BOLD, lblYear.getFont().getSize() + 6));
                mi.add(lblYear, "cell 1 0, spanx 2");

                //---- btnSiguiente ----
                btnSiguiente.setText("Siguiente");
                btnSiguiente.setBorderPainted(false);
                btnSiguiente.setContentAreaFilled(false);
                btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/img/next.png")));
                btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnSiguiente.setFont(new Font("Source Sans Pro", btnSiguiente.getFont().getStyle() | Font.BOLD, btnSiguiente.getFont().getSize() + 5));
                btnSiguiente.addActionListener(e -> btnSiguiente(e));
                mi.add(btnSiguiente, "cell 3 0, grow 0 0, align right");

                //---- Enero ----
                Enero.setText("Enero");
                Enero.setHorizontalAlignment(SwingConstants.CENTER);
                Enero.setFont(new Font("Segoe UI", Font.BOLD, 15));
                mi.add(Enero, "cell 0 1");

                //---- label1 ----
                label1.setText("Febrero");
                label1.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label1, "cell 1 1");

                //---- label2 ----
                label2.setText("Marzo");
                label2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label2, "cell 2 1");

                //---- label3 ----
                label3.setText("Abril");
                label3.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label3.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label3, "cell 3 1");
                mi.add(panelDateEnero, "cell 0 2,dock center");
                mi.add(panelDateFebrero, "cell 1 2,dock center");
                mi.add(panelDateMarzo, "cell 2 2,dock center");
                mi.add(panelDateAbril, "cell 3 2,dock center");

                //---- label4 ----
                label4.setText("Mayo");
                label4.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label4, "cell 0 3");

                //---- label5 ----
                label5.setText("Junio");
                label5.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label5.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label5, "cell 1 3");

                //---- label6 ----
                label6.setText("Julio");
                label6.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label6.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label6, "cell 2 3");

                //---- label7 ----
                label7.setText("Agosto");
                label7.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label7.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label7, "cell 3 3");
                mi.add(panelDateMayo, "cell 0 4,dock center");
                mi.add(panelDateJunio, "cell 1 4,dock center");
                mi.add(panelDateJulio, "cell 2 4,dock center");
                mi.add(panelDateAgosto, "cell 3 4,dock center");

                //---- label8 ----
                label8.setText("Septiembre");
                label8.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label8.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label8, "cell 0 5");

                //---- label9 ----
                label9.setText("Octubre");
                label9.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label9.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label9, "cell 1 5");

                //---- label10 ----
                label10.setText("Noviembre");
                label10.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label10.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label10, "cell 2 5");

                //---- label11 ----
                label11.setText("Diciembre");
                label11.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label11.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(label11, "cell 3 5");
                mi.add(panelDateSeptiembre, "cell 0 6,dock center");
                mi.add(panelDateOctubre, "cell 1 6,dock center");
                mi.add(panelDateNoviembre, "cell 2 6,dock center");
                mi.add(panelDateDiciembre, "cell 3 6,dock center");
            }
            layered.add(mi, JLayeredPane.DEFAULT_LAYER);
            mi.setBounds(0, 0, mi.getPreferredSize().width, 605);
        }
        add(layered, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLayeredPane layered;
    private JPanel mi;
    private JButton btnAtras;
    private JLabel lblYear;
    private JButton btnSiguiente;
    private JLabel Enero;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private PanelDate panelDateEnero;
    private PanelDate panelDateFebrero;
    private PanelDate panelDateMarzo;
    private PanelDate panelDateAbril;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private PanelDate panelDateMayo;
    private PanelDate panelDateJunio;
    private PanelDate panelDateJulio;
    private PanelDate panelDateAgosto;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private PanelDate panelDateSeptiembre;
    private PanelDate panelDateOctubre;
    private PanelDate panelDateNoviembre;
    private PanelDate panelDateDiciembre;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
