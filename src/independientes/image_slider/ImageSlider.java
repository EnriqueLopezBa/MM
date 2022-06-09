package independientes.image_slider;

import controlador.ControladorEtiqueta;
import controlador.ControladorLugarInformacion;
import controlador.ControladorLugarImagenes;
import controlador.ControladorNegocio;
import controlador.ControladorProveedor;
import controlador.ControladorNegocioImagenes;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import modelo.Etiqueta;
import modelo.LugarInformacion;
import modelo.LugarImagenes;
import modelo.Negocio;
import modelo.Proveedor;
import modelo.NegocioImagenes;
import net.miginfocom.swing.MigLayout;

public class ImageSlider extends javax.swing.JPanel {

    private MigLayout imageLayout;
    ScrollBar sb = new ScrollBar();
    private LugarInformacion lugar;

    public ImageSlider() {
        initComponents();
        imageLayout = new MigLayout("al center, filly", "10[]10");
        panelItem.setLayout(imageLayout);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                if (lugar == null) {
                    return;
                }
                panelItem.removeAll();
                panelItem.revalidate();
                panelItem.repaint();
                int x = new Double(panelItem.getWidth() * 0.30).intValue();
                int y = new Double(panelItem.getHeight() * 0.80).intValue();
                for (LugarImagenes lu : ControladorLugarImagenes.getInstancia().obtenerListaByIDLugar(lugar.getIdNegocio())) {
                    if (sb.getOrientation() == ScrollBar.VERTICAL) {
                        panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 60%, h 30%");
                    } else {
//                        panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 30%, h 80%");
                        panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w " + x + ", h " + y);
                    }

                }
            }

        });
    }

    public void init(int orientacion) {
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

    public void lugarImagenes(LugarInformacion lugar) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        this.lugar = lugar;
        int x = new Double(panelItem.getWidth() * 0.30).intValue();
        int y = new Double(panelItem.getHeight() * 0.80).intValue();
        for (LugarImagenes lu : ControladorLugarImagenes.getInstancia().obtenerListaByIDLugar(lugar.getIdNegocio())) {
            if (sb.getOrientation() == ScrollBar.VERTICAL) {
                panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 60%, h 30%");
            } else {

//                panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 30%, h 80%");
                panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w " + x + ", h " + y);
            }

        }

    }

    public void negocioImagenes(Negocio negocio) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        for (NegocioImagenes lu : ControladorNegocioImagenes.getInstancia().obtenerListabyIdNegocio(negocio.getIdNegocio())) {
            panelItem.add(getItem(new ImageIcon(lu.getImagen()), null, lu), "w 30%, h 80%");
        }
    }

    public void negocioImagenesByCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        panelItem.removeAll();
        panelItem.revalidate();
        panelItem.repaint();
        for (NegocioImagenes lu : ControladorNegocioImagenes.getInstancia().obtenerListabyIdCiudadAndTipoProveedor(idCiudad, idTipoProveedor)) {
            Negocio negocio = ControladorNegocio.getInstancia().obtenerByID(lu.getIdNegocio());
            Proveedor proveedor = ControladorProveedor.getInstancia().obtenerByID(negocio.getIdProveedor());
            if (!proveedor.isDisponible()) {
                continue;
            }
            if (!negocio.isDisponible()) {
                continue;
            }
            if (sb.getOrientation() == ScrollBar.VERTICAL) {
                panelItem.add(getItem(new ImageIcon(lu.getImagen()), negocio, lu), "w 60%, h 30%");
            } else {
                panelItem.add(getItem(new ImageIcon(lu.getImagen()), negocio, lu), "w 30%, h 80%");
            }

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
            LugarInformacion lugar = ControladorLugarInformacion.getInstancia().obtenerByID(lugarimg.getIdNegocio());
            if (sb.getOrientation() == ScrollBar.VERTICAL) {
                panelItem.add(getItem(new ImageIcon(lugarimg.getImagen()), lugar, lugarimg), "w 60%, h 30%");
            } else {

//                panelItem.add(getItem(new ImageIcon(lugarimg.getImagen()), lugar, lugarimg), "w 250, h 300");
                panelItem.add(getItem(new ImageIcon(lugarimg.getImagen()), lugar, lugarimg), "w 30%, h 80%");
            }

        }
    }

    private ImageItem getItem(Icon icon, Object clase, Object claseIMG) {
        return new ImageItem(icon, imageLayout, clase, claseIMG, this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panelItem = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("asd"); // NOI18N

        sp.setBorder(null);
        sp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        sp.setName("nnnnn"); // NOI18N

        panelItem.setBackground(new java.awt.Color(255, 255, 255));
        panelItem.setName("bvc"); // NOI18N

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
