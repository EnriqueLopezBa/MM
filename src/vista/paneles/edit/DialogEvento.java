package vista.paneles.edit;

import java.awt.event.*;
import Componentes.TextField;
import Componentes.Sweet_Alert.Message.Tipo;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorTipoEvento;
import independientes.Constante;
import independientes.Mensaje;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Evento;
import modelo.Lugar;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogEvento extends JDialog {

    pnlEventos pun;

    public DialogEvento(Principal owner, pnlEventos puntero) {
        super(owner);
        initComponents();
        pun = puntero;
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        double asd = screensize.getWidth() / 1.9;
        int x = (int) asd;
        setSize(new Dimension(x, getPreferredSize().height));
        setLocationRelativeTo(pun.i);
        getContentPane().setBackground(Color.white);
        p.init(new String[]{"idEvento", "idCliente", "idTipoEvento", "idLugar", "Fecha Inicio", "Fecha Final", "Nombre de Evento", "Num. Invitados", "Presupuesto", "Estilo", "Precio Total"}, 4, false);
        llenarTabla();
        p.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                llenarTabla();
            }

        });
        p.tblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                pun.txtNombreEvento.setText(p.tblModel.getValueAt(x, 6).toString());
                pun.txtCantInvitados.setText(p.tblModel.getValueAt(x, 7).toString());
                pun.txtPresupuesto.setText(p.tblModel.getValueAt(x, 8).toString());
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(p.tblModel.getValueAt(x, 4).toString());
                    pun.fechaInicio.setSelectedDate(date1);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(p.tblModel.getValueAt(x, 5).toString());
                    pun.fechaFinal.setSelectedDate(date2);
                    pun.cmbTipoEvento.setSelectedItem(ControladorTipoEvento.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 2)).getTematica());
                    Lugar lug = ControladorLugar.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 3));
                    Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(lug.getIdCiudad());
                    pun.cmbEstado.setSelectedItem(ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado()).getEstado());
                    pun.cmbCiudad.setSelectedItem(ciudad.getCiudad());
                    pun.cmbLugar.setSelectedItem(lug.getNombreLocal());
                    pun.txtEstilo.setText(p.tblModel.getValueAt(x, 7).toString());
                    Cliente cliente = ControladorCliente.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 1));
                    Principal.getInstancia().lblCliente.setText("Cliente activo (SOLO ADMIN): "
                            + cliente.getCorreo() + " - " + cliente.getNombre() + " " + cliente.getApellido());
                    lblCliente.setText("Cliente: " + cliente.getCorreo() + " - "
                            + cliente.getNombre() + " " + cliente.getApellido());
                    Constante.setClienteTemporal(cliente);
                } catch (ParseException ex) {
                    Logger.getLogger(DialogEvento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        p.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                Evento evento = new Evento();
                evento.setIdEvento((int) p.tblModel.getValueAt(x, 0));
                evento.setIdCliente(ControladorCliente.getInstancia().obtenerClienteActivo().getIdCliente());
                evento.setIdTipoEvento(pun.tipoEventoActual.getIdTipoEvento());
                if (pun.lugarActual == null) {
                    Lugar lugar = new Lugar();
                    lugar.setIdCiudad(pun.ciudadActual.getIdCiudad());
                    lugar.setNombreLocal(pun.cmbLugar.getEditor().getItem().toString());
                    ControladorLugar.getInstancia().registrar(lugar);
                    evento.setIdLugar(ControladorLugar.getInstancia().obtenerLugarByLast().getIdLugar());
                } else {
                    evento.setIdLugar(pun.lugarActual.getIdLugar());
                }
                evento.setFechaInicio(pun.obtenerFecha(pun.txtFechaInicio, pun.txtHorarioInicio));
                evento.setFechaFinal(pun.obtenerFecha(pun.txtFechaFinal, pun.txtHorarioFinal));
                evento.setNoInvitados(Integer.parseInt(pun.txtCantInvitados.getText()));
                evento.setPresupuesto(Integer.parseInt(pun.txtPresupuesto.getText()));
                evento.setEstilo(pun.txtEstilo.getText());
                evento.setNombreEvento(pun.txtNombreEvento.getText());
                if (!txtPrecioTotal.getText().isEmpty()) {
                    evento.setPrecioFinal(Integer.parseInt(txtPrecioTotal.getText()));
                }
                Mensaje m = ControladorEvento.getInstancia().actualizar(evento);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());

            }
        });

        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(getParent(), "Seguro que desea eliminar " + p.tblModel.getValueAt(x, 0).toString()) != 0) {
                    return;
                }
                Evento ev = new Evento();
                ev.setIdEvento((int) p.tblModel.getValueAt(x, 0));
                Mensaje m = ControladorEvento.getInstancia().eliminar(ev);
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
    }

    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Evento e : ControladorEvento.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdEvento(), e.getIdCliente(), e.getIdTipoEvento(),
                e.getIdLugar(), e.getFechaInicio(), e.getNoInvitados(), e.getPresupuesto(), e.getEstilo(), e.getNombreEvento(), e.getPrecioFinal()});
        }
    }

    private void thisWindowClosed(WindowEvent e) {
        Constante.removeClienteTemporal();
    }

    private void txtPrecioTotalKeyReleased(KeyEvent e) {

    }

    private void txtPrecioTotalKeyPressed(KeyEvent e) {

    }

    private void txtPrecioTotalKeyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar())) {

            e.consume();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        lblCliente = new JLabel();
        txtPrecioTotal = new TextField();

        //======== this ========
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));
        contentPane.add(p, "cell 0 0");

        //---- lblCliente ----
        lblCliente.setText("Cliente:");
        contentPane.add(lblCliente, "cell 0 1");

        //---- txtPrecioTotal ----
        txtPrecioTotal.setLabelText("Precio Total");
        txtPrecioTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtPrecioTotal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                txtPrecioTotalKeyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                txtPrecioTotalKeyReleased(e);
            }
            @Override
            public void keyTyped(KeyEvent e) {
                txtPrecioTotalKeyTyped(e);
            }
        });
        contentPane.add(txtPrecioTotal, "cell 0 2");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    public JLabel lblCliente;
    private TextField txtPrecioTotal;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
