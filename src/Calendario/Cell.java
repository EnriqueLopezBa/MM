package Calendario;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorAbono;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugarInformacion;
import controlador.ControladorNegocio;
import controlador.ControladorNegocioArea;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Estado;
import modelo.Evento;
import modelo.LugarInformacion;
import modelo.Negocio;
import modelo.NegocioArea;
import vista.paneles.pnlAgenda;
import vista.principales.Principal;

public class Cell extends JButton implements MouseListener {

    private Date date;
    private boolean title;
    private boolean isToDay;
    private Evento evento;

    public Cell() {
        super.setContentAreaFilled(false);
        super.setBorder(null);
        super.setHorizontalAlignment(JLabel.CENTER);
        super.addMouseListener(Cell.this);
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

    public void setAsToDay(boolean is) {
        isToDay = is;
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (title) {
            grphcs.setColor(new Color(213, 213, 213));
            grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
        if (isToDay && evento == null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(102, 204, 0));
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
        if (SwingUtilities.isRightMouseButton(e)) {
            if (evento == null) {
                return;
            }
            int x = JOptionPane.showConfirmDialog(this, "Seguro desea borrar este "+ evento.getNombreEvento()+"?");
            if (x == 0) {
                Mensaje m = ControladorEvento.getInstancia().eliminar(evento);
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
                if (m.getTipoMensaje() == Tipo.OK) {
                    pnlAgenda.getInstancia().calen.set(Calendar.YEAR, pnlAgenda.getInstancia().calen.get(Calendar.YEAR));
                    pnlAgenda.getInstancia().updateMonths();
                }
            }
        }
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
        setToolTipText("Click derecho para eliminar");
        int celdaX = getLocationOnScreen().x - Principal.getInstancia().pnlOpciones.getWidth() + getWidth();
        int celdaY = getLocationOnScreen().y - Principal.getInstancia().pnlMenu.getHeight() + getHeight();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        PanelPopUp p = new PanelPopUp();
        p.setName("temp");
        p.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                if (p.getLocationOnScreen().x + p.getWidth() > screensize.width && p.getLocationOnScreen().y + p.getHeight() > screensize.height) {
                    p.setLocation(celdaX - p.getWidth() - getWidth(), celdaY - p.getHeight() - getHeight());
                } else if (p.getLocationOnScreen().x + p.getWidth() > screensize.width) { // se sale por la derecha
                    p.setLocation(celdaX - p.getWidth() - getWidth(), celdaY);
                } else if (p.getLocationOnScreen().y + p.getHeight() > screensize.height) { //se sale por abajo
                    p.setLocation(celdaX, celdaY - p.getHeight() - getHeight());
                }
            }

        });

        double as = screensize.getWidth() * 0.2;
        double as2 = screensize.getHeight() * 0.3;

        p.setBounds(celdaX, celdaY, (int) as, (int) as2);
        p.lblNombreEvento.setText(evento.getNombreEvento());
        JLayeredPane layered = (JLayeredPane) getParent().getParent().getParent();
        LugarInformacion lugar = ControladorLugarInformacion.getInstancia().obtenerByID(evento.getIdNegocio());
        Cliente cliente = ControladorCliente.getInstancia().obtenerByID(evento.getIdCliente());
        if (lugar == null) {
            p.lblLugar.setText("<<Registro no completo>>");
            p.lblDireccion.setText("<<Registro no completo>>");
            p.lblAdeudo.setText("Sin registro");
            p.lblCliente.setText(cliente.getNombre() + " " + cliente.getApellido());

        } else {
            NegocioArea area = ControladorNegocioArea.getInstancia().obtenerListaByIdNegocio(lugar.getIdNegocio()).get(0);
            Negocio neg = ControladorNegocio.getInstancia().obtenerByID(lugar.getIdNegocio());
            Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(area.getIdCiudad());
            Estado estado = ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado());
            int canti = ControladorAbono.getInstancia().obtenerCantidadADeber(cliente.getIdCliente(), evento.getIdEvento());
            p.lblLugar.setText(neg.getNombreNegocio());
            p.lblDireccion.setText(estado.getEstado() + ", " + ciudad.getCiudad());
            p.lblCliente.setText(cliente.getNombre() + " " + cliente.getApellido());
            if (canti <= 0) {
                p.lblAdeudo.setText("Sin registro");
            } else {
                p.lblAdeudo.setText(canti + "");
            }
        }

        layered.add(p, JLayeredPane.POPUP_LAYER);
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
