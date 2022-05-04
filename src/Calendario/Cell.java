package Calendario;

import Componentes.DropShadow.PanelShadow;
import Componentes.PanelGradiante;
import controlador.ControladorAbono;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorLugar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import modelo.Abono;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Estado;
import modelo.Evento;
import modelo.Lugar;
import net.miginfocom.swing.MigLayout;

public class Cell extends JButton implements MouseListener {

    private Date date;
    private boolean title;
    private boolean isToDay;
    private Evento evento;
    int x;
    int y;

    public Cell() {
        setContentAreaFilled(false);
        setBorder(null);
        setHorizontalAlignment(JLabel.CENTER);
        super.addMouseListener(this);
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void asTitle() {
        title = true;
    }

    public boolean isTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void currentMonth(boolean act) {
        if (act) {
            setVisible(true);
            setForeground(new Color(68, 68, 68));
        } else {
            setVisible(false);
            setForeground(new Color(169, 169, 169));
        }
    }

    public void setAsToDay() {
        isToDay = true;
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (title) {
            grphcs.setColor(new Color(213, 213, 213));
//            grphcs.setColor(new Color(213, 213, 213));
            grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
        if (isToDay && evento == null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(97, 49, 237));
            int x = getWidth() / 2 - 12;
            int y = getHeight() / 2 - 13;
            g2.fillRoundRect(x, y, 27, 27, 100, 100);
        }
        if (evento != null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 153, 0));
            int x = getWidth() / 2 - 12;
            int y = getHeight() / 2 - 13;
            g2.fillRoundRect(x, y, 27, 27, 100, 100);
        }
        super.paintComponent(grphcs);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (evento == null) {
            return;
        }

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        PanelPopUp p = new PanelPopUp();
        p.setName("temp");
        p.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                x = p.getLocationOnScreen().x;
                x = x + p.getWidth();
                if (x > screensize.width && y > screensize.height) {
                    p.setLocation(getLocationOnScreen().x - p.getWidth() - getWidth(), getLocationOnScreen().y - p.getHeight() - getHeight());
                } else if (x > screensize.width) {
                    p.setLocation(getLocationOnScreen().x - p.getWidth() - getWidth(), getLocationOnScreen().y);
                } else if (y > screensize.height) {
                    p.setLocation(p.getLocationOnScreen().x, getLocationOnScreen().y - p.getHeight() - getHeight());
                }
            }

        });

        double as = screensize.getWidth() * 0.2;
        double as2 = screensize.getHeight() * 0.3;
        p.setBounds((getLocationOnScreen().x - getWidth()), (getLocationOnScreen().y - getHeight()), (int) as, (int) as2);
        p.lblNombreEvento.setText(evento.getNombreEvento());
       
        JLayeredPane layered = (JLayeredPane) getParent().getParent().getParent();
        Lugar lugar = ControladorLugar.getInstancia().obtenerByID(evento.getIdLugar());
        Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(lugar.getIdCiudad());
        Estado estado = ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado());
        Cliente cliente = ControladorCliente.getInstancia().obtenerByID(evento.getIdCliente());
        int canti = ControladorAbono.getInstancia().obtenerCantidadADeber(cliente.getIdCliente(), evento.getIdEvento());
        p.lblLugar.setText(lugar.getNombreLocal());
        p.lblDireccion.setText(estado.getEstado()+", "+ ciudad.getCiudad());
        p.lblCliente.setText(cliente.getNombre() +" " + cliente.getApellido());
        if (canti <= 0) {
              p.lblAdeudo.setText("Sin registro");
        }else{
              p.lblAdeudo.setText(canti+"");
        }
      
        layered.add(p, JLayeredPane.POPUP_LAYER);
        
//        getParent().getParent().add(p, "h 30%!, w 30%!, pos " + (getLocationOnScreen().x - getWidth()) + " " + (getLocationOnScreen().y - getHeight()));
//        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
//
//        MigLayout mi = (MigLayout) getParent().getParent().getLayout();
//
//        String[] spli = mi.getComponentConstraints(p).toString().split(",")[2].split(" ");
//        x = Integer.parseInt(spli[2]);
//        y = Integer.parseInt(spli[3]);
//        p.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
//                x = x + p.getWidth();
//                y = y + p.getHeight();
//                if (x > screensize.width && y > screensize.height) {
//                    mi.setComponentConstraints(p, "h 30%!, w 30%!, pos " + (getLocationOnScreen().x - getWidth() - p.getWidth()) + " " + (getLocationOnScreen().y - p.getHeight() - getHeight()));
//                } else if (x > screensize.width) {
//                    mi.setComponentConstraints(p, "h 30%!, w 30%!, pos " + (getLocationOnScreen().x - getWidth() - p.getWidth()) + " " + getLocationOnScreen().y);
//                } else if (y > screensize.height) {
//                    mi.setComponentConstraints(p, "h 30%!, w 30%!, pos " + getLocationOnScreen().x + " " + (getLocationOnScreen().y - p.getHeight() - getHeight()));
//                }
//                getParent().getParent().revalidate();
//                getParent().getParent().repaint();
//
//            }
//
//        });
//
////                        add(a, "pos (asd.x2/2) (asd.y2/2)");
//        getParent().getParent().revalidate();
//        getParent().getParent().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (evento == null) {
            return;
        }
        for (Component com : getParent().getParent().getParent().getComponents()) {
            if (com instanceof JPanel) {
                if (com.getName() != null && com.getName().equals("temp")) {
                    getParent().getParent().getParent().remove(com);
                    getParent().getParent().getParent().revalidate();
                    getParent().getParent().getParent().repaint();
                }

            }
        }
    }

}
