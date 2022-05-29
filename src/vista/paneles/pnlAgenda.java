/*
 * Created by JFormDesigner on Mon May 02 22:56:02 MDT 2022
 */
package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Calendario.*;
import java.util.Calendar;
import Componentes.*;
import net.miginfocom.swing.*;

/**
 * @author das
 */
public class pnlAgenda extends JPanel {

    public Calendar calen;

    private static pnlAgenda instancia;
    public static pnlAgenda getInstancia(){
        if (instancia == null) {
            instancia = new pnlAgenda();
        }
        return instancia;
    }
    
    private pnlAgenda() {
        initComponents();

 
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

    }

    public void updateMonths() {
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


    private void lblAnteriorMouseClicked(MouseEvent e) {
        calen.set(Calendar.YEAR, calen.get(Calendar.YEAR) - 1);
        updateMonths();
        lblYear.setText(calen.get(Calendar.YEAR) + "");
    }

    private void lblProximoMouseClicked(MouseEvent e) {
        calen.set(Calendar.YEAR, calen.get(Calendar.YEAR) + 1);
        updateMonths();
        lblYear.setText(calen.get(Calendar.YEAR) + "");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        layered = new JLayeredPane();
        mi = new JPanel();
        lblAnterior = new SvgIcon();
        lblYear = new JLabel();
        lblProximo = new SvgIcon();
        Enero = new JLabel();
        lblFebrero = new JLabel();
        lblMarzo = new JLabel();
        lblAbril = new JLabel();
        panelDateEnero = new PanelDate();
        panelDateFebrero = new PanelDate();
        panelDateMarzo = new PanelDate();
        panelDateAbril = new PanelDate();
        lblMayo = new JLabel();
        lblJunio = new JLabel();
        lblJulio = new JLabel();
        lblAgosto = new JLabel();
        panelDateMayo = new PanelDate();
        panelDateJunio = new PanelDate();
        panelDateJulio = new PanelDate();
        panelDateAgosto = new PanelDate();
        lblSeptiembre = new JLabel();
        lblOctubre = new JLabel();
        lblNoviembre = new JLabel();
        lblDiciembre = new JLabel();
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

                //---- lblAnterior ----
                lblAnterior.setText("Anterior");
                lblAnterior.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\back.svg"));
                lblAnterior.setPorcentaje(3);
                lblAnterior.setFont(lblAnterior.getFont().deriveFont(lblAnterior.getFont().getStyle() | Font.BOLD, lblAnterior.getFont().getSize() + 3f));
                lblAnterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblAnterior.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lblAnteriorMouseClicked(e);
                    }
                });
                mi.add(lblAnterior, "cell 0 0, grow 0 0, align left");

                //---- lblYear ----
                lblYear.setText("text");
                lblYear.setHorizontalAlignment(SwingConstants.CENTER);
                lblYear.setFont(new Font("Source Sans Pro", lblYear.getFont().getStyle() | Font.BOLD, lblYear.getFont().getSize() + 6));
                mi.add(lblYear, "cell 1 0, spanx 2");

                //---- lblProximo ----
                lblProximo.setText("Posterior");
                lblProximo.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\next.svg"));
                lblProximo.setFont(lblProximo.getFont().deriveFont(lblProximo.getFont().getStyle() | Font.BOLD, lblProximo.getFont().getSize() + 3f));
                lblProximo.setHorizontalTextPosition(SwingConstants.LEFT);
                lblProximo.setPorcentaje(3);
                lblProximo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblProximo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lblProximoMouseClicked(e);
                    }
                });
                mi.add(lblProximo, "cell 3 0, grow 0 0, align right");

                //---- Enero ----
                Enero.setText("Enero");
                Enero.setHorizontalAlignment(SwingConstants.CENTER);
                Enero.setFont(new Font("Segoe UI", Font.BOLD, 15));
                mi.add(Enero, "cell 0 1");

                //---- lblFebrero ----
                lblFebrero.setText("Febrero");
                lblFebrero.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblFebrero.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblFebrero, "cell 1 1");

                //---- lblMarzo ----
                lblMarzo.setText("Marzo");
                lblMarzo.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblMarzo.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblMarzo, "cell 2 1");

                //---- lblAbril ----
                lblAbril.setText("Abril");
                lblAbril.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblAbril.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblAbril, "cell 3 1");
                mi.add(panelDateEnero, "cell 0 2,dock center");
                mi.add(panelDateFebrero, "cell 1 2,dock center");
                mi.add(panelDateMarzo, "cell 2 2,dock center");
                mi.add(panelDateAbril, "cell 3 2,dock center");

                //---- lblMayo ----
                lblMayo.setText("Mayo");
                lblMayo.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblMayo.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblMayo, "cell 0 3");

                //---- lblJunio ----
                lblJunio.setText("Junio");
                lblJunio.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblJunio.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblJunio, "cell 1 3");

                //---- lblJulio ----
                lblJulio.setText("Julio");
                lblJulio.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblJulio.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblJulio, "cell 2 3");

                //---- lblAgosto ----
                lblAgosto.setText("Agosto");
                lblAgosto.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblAgosto.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblAgosto, "cell 3 3");
                mi.add(panelDateMayo, "cell 0 4,dock center");
                mi.add(panelDateJunio, "cell 1 4,dock center");
                mi.add(panelDateJulio, "cell 2 4,dock center");
                mi.add(panelDateAgosto, "cell 3 4,dock center");

                //---- lblSeptiembre ----
                lblSeptiembre.setText("Septiembre");
                lblSeptiembre.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblSeptiembre.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblSeptiembre, "cell 0 5");

                //---- lblOctubre ----
                lblOctubre.setText("Octubre");
                lblOctubre.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblOctubre.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblOctubre, "cell 1 5");

                //---- lblNoviembre ----
                lblNoviembre.setText("Noviembre");
                lblNoviembre.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblNoviembre.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblNoviembre, "cell 2 5");

                //---- lblDiciembre ----
                lblDiciembre.setText("Diciembre");
                lblDiciembre.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblDiciembre.setHorizontalAlignment(SwingConstants.CENTER);
                mi.add(lblDiciembre, "cell 3 5");
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
    private SvgIcon lblAnterior;
    private JLabel lblYear;
    private SvgIcon lblProximo;
    private JLabel Enero;
    private JLabel lblFebrero;
    private JLabel lblMarzo;
    private JLabel lblAbril;
    private PanelDate panelDateEnero;
    private PanelDate panelDateFebrero;
    private PanelDate panelDateMarzo;
    private PanelDate panelDateAbril;
    private JLabel lblMayo;
    private JLabel lblJunio;
    private JLabel lblJulio;
    private JLabel lblAgosto;
    private PanelDate panelDateMayo;
    private PanelDate panelDateJunio;
    private PanelDate panelDateJulio;
    private PanelDate panelDateAgosto;
    private JLabel lblSeptiembre;
    private JLabel lblOctubre;
    private JLabel lblNoviembre;
    private JLabel lblDiciembre;
    private PanelDate panelDateSeptiembre;
    private PanelDate panelDateOctubre;
    private PanelDate panelDateNoviembre;
    private PanelDate panelDateDiciembre;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
