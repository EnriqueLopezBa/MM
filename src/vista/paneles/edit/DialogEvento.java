package vista.paneles.edit;

import java.awt.event.*;
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

    private pnlEventos pun;

    public DialogEvento(Principal owner) {
        super(owner);
        initComponents();
        pun = pnlEventos.getInstancia();
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        double asd = screensize.getWidth() / 1.9;
        int x = (int) asd;
        super.setSize(new Dimension(x, super.getPreferredSize().height));
        super.setLocationRelativeTo(pun.i);
        super.getContentPane().setBackground(Color.white);

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
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }

                try {
                    int x = p.tblBuscar.getSelectedRow();
                    Cliente cliente = ControladorCliente.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 1));

                    Constante.setClienteTemporal(cliente);
                    lblCliente.setText("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
                    Principal.getInstancia().recargarPanelActivo();

                    pun.cargarEventos();
                    pun.cmbEventos.setSelectedItem(p.tblModel.getValueAt(x, 6).toString());
//                    pun.txtNombreEvento.setText(p.tblModel.getValueAt(x, 6).toString());
                    pun.txtCantInvitados.setText(p.tblModel.getValueAt(x, 7).toString());
                    pun.txtPresupuesto.setText(p.tblModel.getValueAt(x, 8).toString());
                    String fecg = p.tblModel.getValueAt(x, 4).toString().replaceAll("\\(", "").replaceAll("\\)", "");
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd k:mm").parse(fecg);
                    pun.fechaInicio.setSelectedDate(date1);
                    pun.timePickerInicio.setSelectedTime(date1);
                    fecg = p.tblModel.getValueAt(x, 5).toString().replaceAll("\\(", "").replaceAll("\\)", "");
                    date1 = new SimpleDateFormat("yyyy-MM-dd k:mm").parse(fecg);
                    pun.fechaFinal.setSelectedDate(date1);
                    pun.timePickerFinal.setSelectedTime(date1);
                    if ((int) p.tblModel.getValueAt(x, 2) == 0) {
                        lblCliente.setText(lblCliente.getText() + " -- Evento no terminado de registrar");
                        return;
                    }

                    pun.cmbTipoEvento.setSelectedItem(ControladorTipoEvento.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 2)).getTematica());
                    Lugar lug = ControladorLugar.getInstancia().obtenerByID((int) p.tblModel.getValueAt(x, 3));
                    Ciudad ciudad = ControladorCiudad.getInstancia().obtenerById(lug.getIdCiudad());
                    pun.cmbEstado.setSelectedItem(ControladorEstado.getInstancia().obtenerByID(ciudad.getIdEstado()).getEstado());
                    pun.cmbCiudad.setSelectedItem(ciudad.getCiudad());
                    pun.cmbLugar.setSelectedItem(lug.getNombreLocal());
                    pun.txtEstilo.setText(p.tblModel.getValueAt(x, 7).toString());

                } catch (ParseException ex) {
                    Logger.getLogger(DialogEvento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        p.btnModificar.setVisible(false);
        p.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constante.filaSeleccionada(p.tblBuscar)) {
                    return;
                }
                int x = p.tblBuscar.getSelectedRow();
                if (JOptionPane.showConfirmDialog(getParent(), "Seguro que desea eliminar " + p.tblModel.getValueAt(x, 6).toString() + "?") != 0) {
                    return;
                }
                Evento ev = new Evento();
                ev.setIdEvento((int) p.tblModel.getValueAt(x, 0));
                Mensaje m = ControladorEvento.getInstancia().eliminar(ev);
                if (m.getTipoMensaje() == Tipo.OK) {
                    llenarTabla();
                }
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
    }

    private SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd (HH:mm)");

    public void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Evento e : ControladorEvento.getInstancia().obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdEvento(), e.getIdCliente(), e.getIdTipoEvento(),
                e.getIdLugar(), localDateFormat.format(e.getFechaInicio()), localDateFormat.format(e.getFechaFinal()), e.getNombreEvento(), e.getNoInvitados(), e.getPresupuesto(), e.getEstilo()});
        }
    }

    private void thisWindowClosed(WindowEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        lblCliente = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edicion de Eventos");
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
            "[]"));
        contentPane.add(p, "cell 0 0");

        //---- lblCliente ----
        lblCliente.setText("Cliente:");
        contentPane.add(lblCliente, "cell 0 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private pnlCRUD p;
    public JLabel lblCliente;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
