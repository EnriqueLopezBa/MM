package independientes.image_slider;

import controlador.ControladorEtiqueta;
import controlador.ControladorLugar;
import controlador.ControladorLugarImagenes;
import controlador.ControladorProveedor;
import controlador.ControladorProveedorImagenes;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import modelo.Etiqueta;
import modelo.Lugar;
import modelo.LugarImagenes;
import modelo.Proveedor;
import modelo.ProveedorImagenes;
import net.miginfocom.swing.MigLayout;

public class ImageSlider extends javax.swing.JPanel {

 
 


 

    private MigLayout imageLayout;

    public ImageSlider() {
        initComponents();
        imageLayout = new MigLayout("al center, filly", "10[]10");
        panelItem.setLayout(imageLayout);
    }

    public void init(int orientacion) {
        ScrollBar sb = new ScrollBar();
        if (orientacion == ScrollBar.HORIZONTAL) {
            sb.setOrientation(ScrollBar.HORIZONTAL);
            sp.setHorizontalScrollBar(sb);
        } else {
            imageLayout = new MigLayout("fillx, flowy", "push[]push");
            panelItem.setLayout(imageLayout);
            sb.setOrientation(ScrollBar.VERTICAL);
            sp.setVerticalScrollBar(sb);
        }
    }

    public void lugarImagenes(Lugar lugar) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        for (LugarImagenes lu : ControladorLugarImagenes.getInstancia().obtenerListaByIDLugar(lugar.getIdLugar())) {
            panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 250, h 200");
        }
    }

    public void proveedorImagenes(Proveedor proveedor) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        for (ProveedorImagenes lu : ControladorProveedorImagenes.getInstancia().obtenerListabyIdProveedor(proveedor.getIdProveedor())) {
            panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 250, h 200");
        }
    }

    public void proveedorImagenesByCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        for (ProveedorImagenes lu : ControladorProveedorImagenes.getInstancia().obtenerobtenerListabyIdCiudadAndTipoProveedor(idCiudad, idTipoProveedor)) {
            Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(lu.getIdProveedor());
            panelItem.add(getItem(new ImageIcon(lu.getImagen()), proveedor, lu), "w 250, h 200");
        }
    }

    public void lugarImagenesByIDCiudad(int idCiudad, DefaultListModel modelo) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        //Obtener caracteristicas (etiquetas) por el usuario

        ArrayList<String> arrayList = Collections.list(modelo.elements());
        String arr = "";
        if (!arrayList.isEmpty()) {
            for (String ee : arrayList) {
                Etiqueta etiqueta = ControladorEtiqueta.getInstancia().obtenerByEtiquetaNombre(ee);
                arr += etiqueta.getIdEtiqueta() + ",";
            }
            arr = arr.substring(0, arr.length() - 1);
        } else {
            arr = "0";
        }
        for (LugarImagenes lugarimg : ControladorLugarImagenes.getInstancia().obtenerListaByIDCiudad(idCiudad, arr)) {
            Lugar lugar = ControladorLugar.getInstancia().obtenerByID(lugarimg.getIdLugar());
            panelItem.add(getItem(new ImageIcon(lugarimg.getImagen()), lugar, lugarimg), "w 250, h 200");
        }
    }

    private ImageItem getItem(Icon icon, Object clase, Object claseIMG) {
        return new ImageItem(icon, imageLayout, clase, claseIMG);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panelItem = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout panelItemLayout = new javax.swing.GroupLayout(panelItem);
        panelItem.setLayout(panelItemLayout);
        panelItemLayout.setHorizontalGroup(
            panelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1046, Short.MAX_VALUE)
        );
        panelItemLayout.setVerticalGroup(
            panelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );

        sp.setViewportView(panelItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (panelItem != null) {
            panelItem.setBackground(color);
            sp.getHorizontalScrollBar().setBackground(color);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelItem;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
