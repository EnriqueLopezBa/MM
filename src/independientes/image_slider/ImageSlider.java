package independientes.image_slider;


import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import vista.principales.Principal;

public class ImageSlider extends javax.swing.JPanel {

    private final MigLayout imageLayout = null;

//    Principal p;
//    ArrayList<LugarImagenes> lugarImagenes;
//    ArrayList<ProveedorImagen> proveedorImagenes;
//    private ImageItem.opciones opcion;
//
//    public ImageSlider(Principal p, ImageItem.opciones opcion) {
//        initComponents();
//        this.p = p;
//        this.opcion = opcion;
//        imageLayout = new MigLayout("al center, filly", "10[]10");
//        panelItem.setLayout(imageLayout);
//        ScrollBar sb = new ScrollBar();
//        sb.setOrientation(ScrollBar.HORIZONTAL);
//        sp.setHorizontalScrollBar(sb);
//        panelItem.setBackground(p.constante.getColor());
//    }
//
//    public void listaLugares(ArrayList<Lugar> lugar) {
//        panelItem.removeAll();
//        ArrayList<LugarImagenes> temp = new ArrayList<>();
//        int y = 0;
//        for (Lugar lu : lugar) {
//            lugarImagenes = p.getDatos().getLugarImagenes(lu.getIdLugar());
//            if (lugarImagenes.isEmpty()) {
//                continue;
//            }
//            y++;
//            for (LugarImagenes l : lugarImagenes) {
//                if (l.getImagen() != null && !yaexiste(l.getLugar().getIdLugar(), temp)) {
//                    temp.add(l);
//                    panelItem.add(getItem(new ImageIcon(l.getImagen()), lu), "w 320, h 260");
//                }
//            }
//        }
//        if (y == 0) {
//             JLabel laabel = new JLabel("SIN IMAGENES");
//                laabel.setFont(new Font("Arial", Font.BOLD, 25));
//                panelItem.revalidate();
//                panelItem.repaint();
//                panelItem.add(laabel);
//        }
//        revalidate();
//        repaint();
//    }
//
//    private boolean yaexiste(int idLugar, ArrayList<LugarImagenes> lista) {
//        for (LugarImagenes lu : lista) {
//            if (lu.getLugar().getIdLugar() == idLugar) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void listaLugares(Lugar lugar) {
//        panelItem.removeAll();
//        ArrayList<LugarImagenes> l = p.getDatos().getLugarImagenes(lugar.getIdLugar());
//        for (LugarImagenes lu : l) {
//            if (lu.getImagen() != null) {
//                panelItem.add(getItem(new ImageIcon(lu.getImagen()), lu), "w 320, h 260");
//            }
//        }
//        revalidate();
//        repaint();
//    }
    
//     public void listaProveedores(Proveedor proveedor) {
//        panelItem.removeAll();
//        ArrayList<ProveedorImagen> l = p.getDatos().getProveedorImagenes(proveedor.getIdProveedor());
//        for (ProveedorImagen lu : l) {
//            if (lu.getImagen() != null) {
//                panelItem.add(getItem(new ImageIcon(lu.getImagen()), lu), "w 320, h 260");
//            }
//        }
//        revalidate();
//        repaint();
//    }

    //pnlProveedores
    
//    public void listaProveedores(ArrayList<Proveedor> proveedor) {
//        panelItem.removeAll();
//        ArrayList<ProveedorImagen> temp = new ArrayList<>();
//        int y = 0;
//        for (Proveedor lu : proveedor) {
//            proveedorImagenes = p.getDatos().getProveedorImagenes(lu.getIdProveedor());
//            if (proveedorImagenes.isEmpty()) {
//                continue;
//            }
//            y++;
//            for (ProveedorImagen l : proveedorImagenes) {
//                if (l.getImagen() != null && !yaexistee(l.getProveedor().getIdProveedor(), temp)) {
//                    temp.add(l);
//                    panelItem.add(getItem(new ImageIcon(l.getImagen()), lu), "w 320, h 260");
//                }
//            }
//        }
//        if (y == 0) {
//             JLabel laabel = new JLabel("SIN IMAGENES");
//                laabel.setFont(new Font("Arial", Font.BOLD, 25));
//                panelItem.revalidate();
//                panelItem.repaint();
//                panelItem.add(laabel);
//        }
//        revalidate();
//        repaint();
//    }

//    private boolean yaexistee(int idProveedor, ArrayList<ProveedorImagen> lista) {
//        for (ProveedorImagen lu : lista) {
//            if (lu.getProveedor().getIdProveedor()== idProveedor) {
//                return true;
//            }
//        }
//        return false;
//    }

//    private ImageItem getItem(Icon icon, Lugar lugar) {
//        return new ImageItem(icon, imageLayout, lugar, p, opcion);
//    }
//
//    private ImageItem getItem(Icon icon, LugarImagenes lugarimg) {
//        return new ImageItem(icon, imageLayout, lugarimg, p, opcion);
//    }
//
//    private ImageItem getItem(Icon icon, Proveedor proveedor) {
//        return new ImageItem(icon, imageLayout, proveedor, p, opcion);
//    }
//    private ImageItem getItem(Icon icon, ProveedorImagen proveedorimg) {
//        return new ImageItem(icon, imageLayout, proveedorimg, p, opcion);
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panelItem = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelItem.setBackground(new java.awt.Color(255, 255, 255));

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
            .addComponent(sp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
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
