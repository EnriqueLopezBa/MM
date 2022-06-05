package vista.paneles;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.event.*;
import Componentes.*;
import Componentes.Sweet_Alert.Message;
import javax.swing.*;
import javax.swing.table.*;
import Componentes.tableC.*;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import controlador.ControladorCotizacion;
import controlador.ControladorEvento;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorEvento;
import controlador.ControladorTipoProveedor;
import independientes.Constante;
import independientes.Mensaje;
import independientes.MyObjectListCellRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static javax.swing.JOptionPane.showMessageDialog;
import modelo.Cliente;
import modelo.Cotizacion;
import modelo.Evento;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.ProveedorEvento;
import modelo.TipoProveedor;
import net.miginfocom.swing.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import vista.principales.Principal;

/**
 * @author das
 */
public class pnlCotizacion extends JPanel {

    private static pnlCotizacion instancia;
    private DefaultTableModel m;
    private Evento eventoActual = null;
    private SimpleDateFormat todoFechaAMPM = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
    private SimpleDateFormat todoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat soloFecha = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat soloHora = new SimpleDateFormat("hh:mm aa");

    public static pnlCotizacion getInstancia() {
        if (instancia == null) {
            instancia = new pnlCotizacion();
        }
        return instancia;
    }

    private pnlCotizacion() {
        initComponents();
        m = (DefaultTableModel) tblCotizacion.getModel();
        super.setBackground(Color.white);
//        tblCotizacion.removeColumn(tblCotizacion.getColumnModel().getColumn(0));
//        tblCotizacion.removeColumn(tblCotizacion.getColumnModel().getColumn(0));

        tblCotizacion.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int suma = 0;
                for (int i = 0; i < m.getRowCount(); i++) {
                    if (m.getValueAt(i, 7) == null) {
                        continue;
                    }
                    suma += (int) m.getValueAt(i, 7);
                }
                lblTotal.setText("Total: " + suma);
            }
        });

        txtComentarioAdmin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e); //To change body of generated methods, choose Tools | Templates.
                int x = tblCotizacion.getSelectedRow();
                if (x == -1) {
                    return;
                }
                if (!txtComentarioAdmin.getText().isEmpty() && !txtComentarioAdmin.getText().equals("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione")) {
                    m.setValueAt(txtComentarioAdmin.getText(), x, 8);
                }

            }

        });

    }

    private org.w3c.dom.Document createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        System.out.println("HTML parsing done...");
        return new W3CDom().fromJsoup(document);
    }

    private void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {

        OutputStream os = new FileOutputStream(outputPdf);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdf);
        builder.toStream(os);
        // add external font
//        builder.useFont(new File(getClass().getClassLoader().getResource("fonts/PRISTINA.ttf").getFile()), "PRISTINA");
        builder.withW3cDocument(doc, "archivo/");
        builder.run();
        showMessageDialog(null, "PDF Generado");
//        System.out.println("PDF creation completed");
        os.close();
    }

    public void cargarEventos() {
        Cliente cliente = Constante.getClienteActivo();
        if (cliente == null) {
            Constante.mensaje("Selecciona un cliente", Message.Tipo.ADVERTENCIA);
            Principal.getInstancia().btnCliente.doClick();
            return;
        }
        cmbEvento.removeAllItems();
        for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(cliente.getIdCliente())) {
            cmbEvento.addItem(ev);
        }
        cmbEvento.setRenderer(new MyObjectListCellRenderer());
    }

    private void cmbEventoItemStateChanged(ItemEvent e) {
        if (cmbEvento.getSelectedIndex() == -1) {
            return;
        }
        eventoActual = (Evento) cmbEvento.getSelectedItem();

        llenarTabla();
    }

    private void llenarTabla() {
        m.setRowCount(0);
        if (!cbCotizaciones.isSelected()) { // cargar propuestas inicial del cliente
            ArrayList<ProveedorEvento> provE = ControladorProveedorEvento.getInstancia().obtenerListaByIdEvento(eventoActual.getIdEvento());
            for (ProveedorEvento pro : provE) {
                Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(pro.getIdNegocio());
                TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID(negocio.getIdNegocio());
                m.addRow(new Object[]{pro.getIdEvento(), pro.getIdNegocio(), tipo.getTipoProveedor(),
                    negocio.getNombreNegocio(), todoFechaAMPM.format(pro.getFechaInicio()), todoFechaAMPM.format(pro.getFechaFinal()), "0"});
            }
        } else {//Cargar cotizaciones
            ArrayList<Cotizacion> cotizacion = ControladorCotizacion.getInstancia().obtenerListaByNumCotizacionAndIdClienteAndIdEvento((int) spinner1.getValue(), Constante.getClienteActivo().getIdCliente(), eventoActual.getIdEvento());
            for (Cotizacion cot : cotizacion) {
                Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(cot.getIdNegocio());
                TipoProveedor tipo = ControladorTipoProveedor.getInstancia().obtenerByID(negocio.getIdTipoProveedor());
                ProveedorEvento provE = ControladorProveedorEvento.getInstancia().obtenerByIdEventoAndIdNegocio(cot.getIdEvento(), negocio.getIdNegocio());
                m.addRow(new Object[]{cot.getIdEvento(), cot.getIdNegocio(), tipo.getTipoProveedor(),
                    negocio.getNombreNegocio(), todoFecha.format(provE.getFechaInicio()), todoFecha.format(provE.getFechaFinal()), "0", cot.getCotizacion(), (cot.getComentario() != null) ? cot.getComentario() : ""});
                cbFinal.setSelected(cot.isCotFinal());
            }
            
        }
    }

//    private void sumarCotizacion(){
//     
//    }
    private void spinner1StateChanged(ChangeEvent e) {
        llenarTabla();
    }

    private void cbCotizaciones(ActionEvent e) {
        spinner1.setEnabled(cbCotizaciones.isSelected());
        cbFinal.setVisible(cbCotizaciones.isSelected());
        if (!cbCotizaciones.isSelected()) {
            
        }
        llenarTabla();
    }
    int lastX = -1;


    private void tblCotizacionMouseClicked(MouseEvent e) {
        int x = tblCotizacion.getSelectedRow();
        if (x == -1) {
            return;
        }

        if (lastX != x) {
            if (!txtComentarioAdmin.getText().isEmpty() && !txtComentarioAdmin.getText().equals("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione")) {
                m.setValueAt(txtComentarioAdmin.getText(), lastX, 8);
            }
            if (m.getValueAt(x, 8) != null && !m.getValueAt(x, 8).toString().isEmpty()) {
                txtComentarioAdmin.setText(m.getValueAt(x, 8).toString());
            } else {
                txtComentarioAdmin.setText("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione");
            }
//            m.fireTableDataChanged();
        }
        lastX = x;
//           ((AbstractTableModel) tblCotizacion.getModel()).fireTableCellUpdated(lastX, 7); 
//              ((AbstractTableModel) tblCotizacion.getModel()).fireTableCellUpdated(x, 7); 

        Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(Integer.parseInt(m.getValueAt(x, 1).toString()));
        ProveedorEvento provE = ControladorProveedorEvento.getInstancia().obtenerByIdEventoAndIdNegocio(eventoActual.getIdEvento(), negocio.getIdNegocio());
        
        Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(provE.getIdProveedor());
        String s = Character.toString((char)0x2022);
        if (proveedor == null) {
            lblInfo.setText("Proveedor: ⸰ Sin datos registrados...");
        } else {
            lblInfo.setText("Proveedor: "+ proveedor.getNombre()+ "  " + s + "  Telefono: " + proveedor.getTelefono());
        }
        if ( provE.getComentario() != null) {
            txtComentarioCliente.setText(provE.getComentario());
        } else {
            txtComentarioCliente.setText("Aqui apareceran los comentarios del cliente");
        }
    }

    private void txtComentarioAdminFocusGained(FocusEvent e) {
        if (txtComentarioAdmin.getText().equals("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione")) {
            txtComentarioAdmin.setText("");
        }
    }

    private void txtComentarioAdminFocusLost(FocusEvent e) {
        if (txtComentarioAdmin.getText().isEmpty()) {
            txtComentarioAdmin.setText("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione");
        }
    }

    private int valorSpinner() {
        return (int) spinner1.getValue();
    }

    private void aumentarSpinner() {
        spinner1.setValue(valorSpinner() + 1);
    }

    private void decrementarSpinner() {
        spinner1.setValue(valorSpinner() - 1);
    }

    private void lblGuardarMouseClicked(MouseEvent e) {
        m.fireTableDataChanged();
//
        int valor = ControladorCotizacion.getInstancia().obtenerNumNuevaCotizacion(eventoActual.getIdEvento());

        ArrayList<Cotizacion> resumen = new ArrayList<>();

        for (int i = 0; i < m.getRowCount(); i++) {
            Cotizacion cotiz = new Cotizacion();
            cotiz.setIdEvento(Integer.parseInt(m.getValueAt(i, 0).toString()));
            cotiz.setIdNegocio(Integer.parseInt(m.getValueAt(i, 1).toString()));
            cotiz.setCotizacion(Integer.parseInt(m.getValueAt(i, 7).toString()));
            cotiz.setComentario((m.getValueAt(i, 8) != null) ? m.getValueAt(i, 8).toString() : "");
            cotiz.setNumCotizacion(valor);
            cotiz.setFechaCotizacion(new Date());
            resumen.add(cotiz);
        }

        Mensaje mm = ControladorCotizacion.getInstancia().registrarLote(resumen);
        Constante.mensaje(mm.getMensaje(), mm.getTipoMensaje());
    }

    private void btnAgregar(ActionEvent e) {
//        int x = tblCotizacion.getSelectedRow();
//        if (x == -1) {
//            return;
//        }
//        if (txtCotizacion.getText().isEmpty()) {
//            Constante.mensaje("Ponga un precio", Message.Tipo.ADVERTENCIA);
//            txtCotizacion.requestFocus();
//            return;
//        }
//        String arr[] = { m.getValueAt(x, 0).toString(), m.getValueAt(x, 1).toString(),m.getValueAt(x, 2).toString(),
//            m.getValueAt(x, 3).toString(),m.getValueAt(x, 4).toString(),m.getValueAt(x, 5).toString(),
//            m.getValueAt(x, 6).toString()};
//        m.removeRow(x);
//     
//       
//        if (!txtComentarioAdmin.getText().isEmpty() && !txtComentarioAdmin.getText().equals("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione")) {
//            
//            m.setValueAt(txtComentarioAdmin.getText(), x, 8);
//        }
//        if (!txtCotizacion.getText().isEmpty()) {
//            m.setValueAt(txtCotizacion.getText(), x, 7);
//        }
//           m.addRow(new Object[]{arr[0],arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], txtCotizacion.getText(), txtComentarioAdmin.getText()});
    }

    private void lblGenerarMouseClicked(MouseEvent e) {
        try {

            generarCotizacion(Constante.getClienteActivo());
            File inputHTML = new File("src/modelo/index.html");
            // Converted PDF file - Output
            String outputPdf = "cotizacion.pdf";

            //create well formed HTML
            org.w3c.dom.Document doc = createWellFormedHtml(inputHTML);
            System.out.println("Starting conversion to PDF...");
            xhtmlToPdf(doc, outputPdf);
        } catch (IOException ex) {
            System.out.println("Error while converting HTML to PDF " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void lblContratoMouseClicked(MouseEvent e) {
        try {

            File inputHTML = new File("src/modelo/Cotizacion/1.html");
            // Converted PDF file - Output
            String outputPdf = "contrato.pdf";

            //create well formed HTML
            org.w3c.dom.Document doc = createWellFormedHtml(inputHTML);
            System.out.println("Starting conversion to PDF...");
            xhtmlToPdf(doc, outputPdf);
        } catch (IOException ex) {
            System.out.println("Error while converting HTML to PDF " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void cbFinal(ActionEvent e) {
        if (cbFinal.isSelected()) {
            ControladorCotizacion.getInstancia().setCotizacionFinal(eventoActual.getIdEvento(), (int) spinner1.getValue());
        }
    }

    private void svgIcon1KeyPressed(KeyEvent e) {
      
    }

//    private void txtCotizacionKeyTyped(KeyEvent e) {
//        if (!Character.isDigit(e.getKeyChar())) {
//            e.consume();
//        }
//    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        lblEnviar = new SvgIcon();
        lblGenerar = new SvgIcon();
        lblContrato = new SvgIcon();
        lblGuardar = new SvgIcon();
        cmbEvento = new JComboBox();
        cbCotizaciones = new JCheckBox();
        svgIcon1 = new SvgIcon();
        cbFinal = new JCheckBox();
        spinner1 = new JSpinner();
        scrollPane1 = new JScrollPane();
        tblCotizacion = new Table();
        lblInfo = new JLabel();
        lblTotal = new JLabel();
        scrollPane2 = new JScrollPane();
        txtComentarioCliente = new JTextArea();
        scrollPane3 = new JScrollPane();
        txtComentarioAdmin = new JTextArea();

        //======== this ========
        setBackground(Color.white);
        setLayout(new MigLayout(
            "fill",
            // columns
            "[33%,fill]" +
            "[3%,fill]" +
            "[33%,fill]",
            // rows
            "[]" +
            "[]" +
            "[fill]" +
            "[fill]" +
            "[fill]"));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new MigLayout(
                null,
                // columns
                "push[fill]push" +
                "[fill]push",
                // rows
                "[grow,fill]"));

            //---- lblEnviar ----
            lblEnviar.setText("Enviar");
            lblEnviar.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\pdfmail.svg"));
            lblEnviar.setHorizontalAlignment(SwingConstants.CENTER);
            lblEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblEnviar.setVerticalAlignment(SwingConstants.TOP);
            lblEnviar.setHorizontalTextPosition(SwingConstants.LEADING);
            lblEnviar.setToolTipText("Enviar Cotizacion por correo al cliente");
            lblEnviar.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel1.add(lblEnviar, "cell 0 0,alignx center,grow 0 0");

            //---- lblGenerar ----
            lblGenerar.setText("Generar PDF");
            lblGenerar.setHorizontalAlignment(SwingConstants.RIGHT);
            lblGenerar.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\pdf.svg"));
            lblGenerar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblGenerar.setVerticalAlignment(SwingConstants.TOP);
            lblGenerar.setHorizontalTextPosition(SwingConstants.LEADING);
            lblGenerar.setToolTipText("Guardar Cotizacion en PDF");
            lblGenerar.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblGenerar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lblGenerarMouseClicked(e);
                }
            });
            panel1.add(lblGenerar, "east");

            //---- lblContrato ----
            lblContrato.setText("Generar Contrato");
            lblContrato.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\contrato.svg"));
            lblContrato.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblContrato.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblContrato.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lblContratoMouseClicked(e);
                }
            });
            panel1.add(lblContrato, "cell 1 0,alignx center,grow 0 0");

            //---- lblGuardar ----
            lblGuardar.setText("Guardar");
            lblGuardar.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\save.svg"));
            lblGuardar.setVerticalAlignment(SwingConstants.TOP);
            lblGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblGuardar.setHorizontalTextPosition(SwingConstants.LEADING);
            lblGuardar.setToolTipText("Guardar Cotizacion en el sistema");
            lblGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblGuardar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lblGuardarMouseClicked(e);
                }
            });
            panel1.add(lblGuardar, "west");
        }
        add(panel1, "cell 0 0 4 1,hmax 5%");

        //---- cmbEvento ----
        cmbEvento.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cmbEvento.addItemListener(e -> cmbEventoItemStateChanged(e));
        add(cmbEvento, "cell 0 1,alignx left,growx 0,wmax 20%,hmin 5%");

        //---- cbCotizaciones ----
        cbCotizaciones.setText("Cotizaciones");
        cbCotizaciones.setHorizontalAlignment(SwingConstants.CENTER);
        cbCotizaciones.addActionListener(e -> cbCotizaciones(e));
        add(cbCotizaciones, "cell 1 1,hmin 5%, split 2, gap 0 0, growx 0");

        //---- svgIcon1 ----
        svgIcon1.setIcon(new ImageIcon("C:\\Users\\Enrique\\Documents\\NetBeansProjects\\MM\\src\\img\\help.svg"));
        svgIcon1.setHorizontalTextPosition(SwingConstants.LEFT);
        svgIcon1.setHorizontalAlignment(SwingConstants.LEFT);
        svgIcon1.setPorcentaje(2);
        svgIcon1.setToolTipText("Si este no se encuentra tildado se muestra la cotizacion inicial(hecha por el cliente)");
        svgIcon1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                svgIcon1KeyPressed(e);
            }
        });
        add(svgIcon1, "cell 1 1");

        //---- cbFinal ----
        cbFinal.setText("Final");
        cbFinal.addActionListener(e -> cbFinal(e));
        add(cbFinal, "cell 1 1");

        //---- spinner1 ----
        spinner1.setModel(new SpinnerNumberModel(0, 0, null, 1));
        spinner1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        spinner1.setEnabled(false);
        spinner1.addChangeListener(e -> spinner1StateChanged(e));
        add(spinner1, "cell 2 1,alignx right,growx 0,wmax 20%,hmin 5%");

        //======== scrollPane1 ========
        {

            //---- tblCotizacion ----
            tblCotizacion.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "idEvento", "idNegocio", "Tipo", "Negocio", "Inicio", "Fin", "Cotizacion del sistema", "Cotizacion Final", "Comentario"
                }
            ) {
                Class<?>[] columnTypes = new Class<?>[] {
                    Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Integer.class, Object.class
                };
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false, false, true, false
                };
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tblCotizacion.setFont(new Font("Segoe UI", Font.BOLD, 16));
            tblCotizacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblCotizacion.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tblCotizacionMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(tblCotizacion);
        }
        add(scrollPane1, "cell 0 2, sx");

        //---- lblInfo ----
        lblInfo.setText("Informacion Adicional:");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblInfo, "cell 0 3");

        //---- lblTotal ----
        lblTotal.setText("Total Cotizacion:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTotal, "cell 2 3");

        //======== scrollPane2 ========
        {

            //---- txtComentarioCliente ----
            txtComentarioCliente.setWrapStyleWord(true);
            txtComentarioCliente.setLineWrap(true);
            txtComentarioCliente.setFont(new Font("Segoe UI", Font.BOLD, 16));
            txtComentarioCliente.setDisabledTextColor(Color.black);
            txtComentarioCliente.setEditable(false);
            txtComentarioCliente.setText("Aqui apareceran los comentarios del cliente");
            scrollPane2.setViewportView(txtComentarioCliente);
        }
        add(scrollPane2, "cell 0 4");

        //======== scrollPane3 ========
        {

            //---- txtComentarioAdmin ----
            txtComentarioAdmin.setLineWrap(true);
            txtComentarioAdmin.setDisabledTextColor(Color.black);
            txtComentarioAdmin.setText("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione");
            txtComentarioAdmin.setFont(new Font("Segoe UI", Font.BOLD, 16));
            txtComentarioAdmin.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtComentarioAdminFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    txtComentarioAdminFocusLost(e);
                }
            });
            scrollPane3.setViewportView(txtComentarioAdmin);
        }
        add(scrollPane3, "cell 2 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private String generarRenglones(ArrayList<Cotizacion> cotizacion) {
        String texto = "";
        for (int i = 0; i < cotizacion.size(); i++) {
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(cotizacion.get(i).getIdNegocio());
            TipoProveedor tipoP = ControladorTipoProveedor.getInstancia().obtenerByID(negocio.getIdTipoProveedor());
            texto += "<tr>\n";
            texto += "<th scope=\"row\">" + (i + 1) + "</th>\n";
            texto += "<td>" + ((tipoP != null) ? tipoP.getTipoProveedor() : "NO") + "</td>\n";
            texto += "<td>" + negocio.getNombreNegocio() + "</td>\n";
            texto += " <td>" + cotizacion.get(i).getCotizacion() + "</td>\n";
            texto += "</tr>\n";
        }

        return texto;
    }

    private void generarCotizacion(Cliente cliente) {

        ArrayList<Cotizacion> cot = ControladorCotizacion.getInstancia().obtenerListaByIDEventoAndNumCotizacion(eventoActual.getIdEvento(), valorSpinner());
        String renglones = generarRenglones(cot);
        int totalCotizacion = new Double(ControladorCotizacion.getInstancia().obtenerTotalCotizacionByIDEventoAndNumCotizacion(eventoActual.getIdEvento(), valorSpinner())).intValue();
        int totalIva = new Double((double) totalCotizacion * 0.16 + totalCotizacion).intValue();
        String pagina = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Cotización</title>\n"
                + "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css\">\n"
                + "    <!--FONTS-->\n"
                + "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n"
                + "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n"
                + "    <link\n"
                + "        href=\"https://fonts.googleapis.com/css2?family=Arimo:wght@700&family=Lato:wght@400;700&family=Oswald&display=swap\"\n"
                + "        rel=\"stylesheet\">\n"
                + "\n"
                + "    <!--ICONS-->\n"
                + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css\">\n"
                + "    <style>\n"
                + "        .txt-title {\n"
                + "            font-size: 44px;\n"
                + "        }\n"
                + "\n"
                + "        .logo {\n"
                + "            font-size: 20px;\n"
                + "        }\n"
                + "\n"
                + "        hr {\n"
                + "            background-color: #fff;\n"
                + "            padding: 0;\n"
                + "        }\n"
                + "\n"
                + "        hr.hr-2 {\n"
                + "            border: 0;\n"
                + "            border-bottom: 2px dashed #eee;\n"
                + "            background: #999;\n"
                + "        }\n"
                + "\n"
                + "        hr.hr-3 {\n"
                + "            border: 0;\n"
                + "            height: 0;\n"
                + "            border-top: 1px solid #8c8c8c;\n"
                + "        }\n"
                + "\n"
                + "        body {\n"
                + "            font-family: 'Oswald', sans-serif !important;\n"
                + "            color: #1d1d1d !important;\n"
                + "        }\n"
                + "\n"
                + "        .small-txt {\n"
                + "            font-size: 10px !important;\n"
                + "            text-align: justify !important;\n"
                + "\n"
                + "        }\n"
                + "        tr {\n"
                + "            height: 50px;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "\n"
                + "<body class=\"bg-secondary\">\n"
                + "    <div class=\"container bg-white my-5 pb-3\">\n"
                + "        <div class=\"row pt-5 align-items-center\">\n"
                + "            <div class=\"col-12 font-arimo row\">\n"
                + "                <div class=\"col-6 logo ps-5\">\n"
                + "                    <img src=\"https://i.ibb.co/L62279n/logo.png\" width=\"100\" alt=\"\">\n"
                + "                    <p><span class=\"ms-3\">Event Quiz</span></p>\n"
                + "                </div>\n"
                + "\n"
                + "                <div class=\"col-6 pe-5\">\n"
                + "                    <div class=\"text-end mt-3\">\n"
                + "                        <span class=\"font-arimo txt-title align-middle\">COTIZACIÓN</span><br>\n"
                + "                        <span>DE EVENTO</span>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <hr class=\"hr-2\">\n"
                + "\n"
                + "        <div class=\"row justify-content-end\">\n"
                + "            <div class=\"col-8 col-md-7 col-sm-6 ps-5\"> CLIENTE: <b>" + cliente.getNombre() + " " + cliente.getApellido() + "</b></div>\n"
                + "            <div class=\"col-4 col-md-5 col-sm-6 text-end pe-5\">\n"
                + "                <span class=\"mx-3\"><b>FECHA</b></span> " + new Date() + "\n"
                + "            </div>\n"
                + "        </div>\n"
                + "\n"
                + "        <hr class=\"hr-2\">\n"
                + "\n"
                + "        <div class=\"row my-3 justify-content-center\">\n"
                + "            <div class=\"col-11\">\n"
                + "                <table class=\"table table-striped\">\n"
                + "                    <thead>\n"
                + "                        <tr>\n"
                + "                            <th scope=\"col\">#</th>\n"
                + "                            <th scope=\"col\">Tipo Proveedor</th>\n"
                + "                            <th scope=\"col\">Proveedor</th>\n"
                + "                            <th scope=\"col\">Importe</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + renglones + ""
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "\n"
                + "        <div class=\"row my-3 justify-content-end\">\n"
                + "            <div class=\"col-6 col-sm-12 col-md-6 col-lg-6 text-center\">\n"
                + "                <h5>¡Gracias por tu preferencia!</h5>\n"
                + "            </div>\n"
                + "            <div class=\"col-6 col-sm-6 col-md-4 col-lg-3 me-5\">\n"
                + "                <div class=\"\">\n"
                + "                    <div class=\"card-body\">\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col-6 text-start\"><b>SUBTOTAL: </b></div>\n"
                + "                            <div class=\"col-6 text-start\">$" + totalCotizacion + "</div>\n"
                + "                            <div class=\"col-6 text-start\"><b>IVA:</b></div>\n"
                + "                            <div class=\"col-6 text-start\">16 %</div>\n"
                + "                            <hr class=\"hr-3 w-75 my-4 mx-auto\">\n"
                + "                            <div class=\"col-6 text-start\"><b>TOTAL: </b></div>\n"
                + "                            <div class=\"col-6 text-start\">$" + totalIva + "</div>\n"
                + "\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "\n"
                + "        <div class=\"row my-5\">\n"
                + "            <div class=\"col-12 text-center\">\n"
                + "                <span class=\"mx-2\">\n"
                + "                    <i class=\"fa-brands fa-facebook\"></i> Marina Meza Event Quiz\n"
                + "                </span>\n"
                + "                <span class=\"mx-2\">\n"
                + "                    <i class=\"fa-solid fa-at\"></i> marina_meza@gmail.com\n"
                + "                </span>\n"
                + "                <span class=\"mx-2\">\n"
                + "                    <i class=\"fa-solid fa-phone\"></i> +52 1 695 103 6942\n"
                + "                </span>\n"
                + "            </div>\n"
                + "\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        // crear archivo
        try {
            File myObj = new File("src/modelo/index.html");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            //Escribir
            FileWriter myWriter = new FileWriter("src/modelo/index.html");
            myWriter.write(pagina);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private SvgIcon lblEnviar;
    private SvgIcon lblGenerar;
    private SvgIcon lblContrato;
    private SvgIcon lblGuardar;
    private JComboBox cmbEvento;
    private JCheckBox cbCotizaciones;
    private SvgIcon svgIcon1;
    private JCheckBox cbFinal;
    private JSpinner spinner1;
    private JScrollPane scrollPane1;
    private Table tblCotizacion;
    private JLabel lblInfo;
    private JLabel lblTotal;
    private JScrollPane scrollPane2;
    private JTextArea txtComentarioCliente;
    private JScrollPane scrollPane3;
    private JTextArea txtComentarioAdmin;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
