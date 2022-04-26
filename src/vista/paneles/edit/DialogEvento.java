package vista.paneles.edit;

import java.awt.event.*;
import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import com.raven.datechooser.SelectedDate;
import controlador.ControladorCiudad;
import controlador.ControladorCliente;
import controlador.ControladorEstado;
import controlador.ControladorEtiqueta;
import controlador.ControladorEvento;
import controlador.ControladorLugar;
import controlador.ControladorLugarEtiquetas;
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
import modelo.LugarEtiquetas;
import net.miginfocom.swing.*;
import vista.paneles.*;
import vista.principales.Principal;

/**
 * @author das
 */
public class DialogEvento extends JDialog {
    
    private ControladorEtiqueta controladorEtiqueta = new ControladorEtiqueta();
    private ControladorLugarEtiquetas controladorLugEti = new ControladorLugarEtiquetas();
    private ControladorEvento controladorEvento = new ControladorEvento();
    private ControladorTipoEvento controladortipoEvento = new ControladorTipoEvento();
    private ControladorLugar controladorLugar = new ControladorLugar();
    private ControladorEstado controladorEstado = new ControladorEstado();
    private ControladorCiudad controladorCiudad = new ControladorCiudad();
    private ControladorCliente controladorCliente = new ControladorCliente();
    
    pnlEventos pun;
    
    public DialogEvento(Principal owner, pnlEventos puntero) {
        super(owner);
        initComponents();
        pun = puntero;
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(screensize.height / 2, getPreferredSize().height));
        setLocationRelativeTo(pun.i);
        getContentPane().setBackground(Color.white);
        p.init(new String[]{"idEvento", "idCliente", "idTipoEvento", "idLugar", "Fecha", "Num. Invitados", "Presupuesto", "Estilo", "Nombre de Evento"}, 4, false);
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
                pun.txtNombreEvento.setText(p.tblModel.getValueAt(x, 8).toString());
                pun.txtPresupuesto.setText(p.tblModel.getValueAt(x, 6).toString());
                pun.txtCantInvitados.setText(p.tblModel.getValueAt(x, 5).toString());
//                pun.frm.listModel.clear();
//                for (LugarEtiquetas lu : controladorLugEti.obtenerEtiquetasByIDLugar((int) p.tblModel.getValueAt(x, 3))) {
//                    pun.frm.listModel.addElement(controladorEtiqueta.obtenerByID(lu.getIdEtiqueta()).getEtiqueta());
//                }
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(p.tblModel.getValueAt(x, 4).toString());
                    pun.dateChooser.setSelectedDate(date1);
                    pun.cmbTipoEvento.setSelectedItem(controladortipoEvento.obtenerByID((int) p.tblModel.getValueAt(x, 2)).getTematica());
                    Lugar lug = controladorLugar.obtenerByID((int) p.tblModel.getValueAt(x, 3));
                    Ciudad ciudad = controladorCiudad.obtenerById(lug.getIdCiudad());
                    pun.cmbEstado.setSelectedItem(controladorEstado.obtenerByID(ciudad.getIdEstado()).getEstado());
                    pun.cmbCiudad.setSelectedItem(ciudad.getCiudad());
                    pun.cmbLugar.setSelectedItem(lug.getNombreLocal());
                    Cliente cliente = controladorCliente.obtenerByID((int) p.tblModel.getValueAt(x, 1));
                    Principal.getInstancia().lblCliente.setText("Cliente activo (SOLO ADMIN): "
                            + cliente.getCorreo() + " - " + cliente.getNombre() + " " + cliente.getApellido());
                    lblCliente.setText("Cliente: " + cliente.getCorreo() + " - "
                            + cliente.getNombre() + " " + cliente.getApellido());
                    Constante.clienteTemporal = cliente;
                  
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
                if (Constante.clienteTemporal != null) {
                    evento.setIdCliente(Constante.clienteTemporal.getIdCliente());
                } else {
                    evento.setIdCliente(controladorCliente.obtenerClienteActivo().getIdCliente());
                }
                evento.setIdTipoEvento(pun.tipoEventoActual.getIdTipoEvento());
                if (pun.lugarActual == null) {
                    Lugar lugar = new Lugar();
                    lugar.setIdCiudad(pun.ciudadActual.getIdCiudad());
                    lugar.setNombreLocal(pun.cmbLugar.getEditor().getItem().toString());
                    controladorLugar.registrar(lugar);
                    evento.setIdLugar(controladorLugar.obtenerLugarByLast().getIdLugar());
                } else {
                    evento.setIdLugar(pun.lugarActual.getIdLugar());
                }
                evento.setFecha(pun.getFecha());
                evento.setNoInvitados(Integer.parseInt(pun.txtCantInvitados.getText()));
                evento.setPresupuesto(Integer.parseInt(pun.txtPresupuesto.getText()));
                evento.setEstilo(pun.txtEstilo.getText());
                evento.setNombreEvento(pun.txtNombreEvento.getText());
              Mensaje m =  controladorEvento.actualizar(evento);
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
                ev.setIdEvento((int)p.tblModel.getValueAt(x, 0));
                Mensaje m = controladorEvento.eliminar(ev);
                Constante.mensaje(m.getMensaje(), m.getTipoMensaje());
            }
        });
    }
    
    private void llenarTabla() {
        p.tblModel.setRowCount(0);
        for (Evento e : controladorEvento.obtenerListaByCadena(p.txtBusqueda.getText())) {
            p.tblModel.addRow(new Object[]{e.getIdEvento(), e.getIdCliente(), e.getIdTipoEvento(),
                e.getIdLugar(), e.getFecha(), e.getNoInvitados(), e.getPresupuesto(), e.getEstilo(), e.getNombreEvento()});
        }
    }

    private void thisWindowClosed(WindowEvent e) {
      Constante.clienteTemporal = null;
       Principal.getInstancia().getClienteActivo();
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        p = new pnlCRUD();
        lblCliente = new JLabel();

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
