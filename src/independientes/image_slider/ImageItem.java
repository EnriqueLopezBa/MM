package independientes.image_slider;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;

public class ImageItem extends JComponent {

    private Icon image;
    private int shadowSize = 58;
    private Timer timer;
    private boolean show;
    Lugar lugar;
    Principal p;
    Proveedor proveedor;
    LugarImagenes lugarimg;
    ProveedorImagen proIMG;

    public enum opciones {
        GALERIA, POPUP
    }

    public ImageItem(Icon image, MigLayout mig, Object objeto, Principal p, opciones opcion) {
        this.p = p;
        this.image = image;
        if (objeto instanceof Lugar) {
            lugar = (Lugar) objeto;
        } else if (objeto instanceof LugarImagenes) {
            lugarimg = (LugarImagenes) objeto;
        } else if (objeto instanceof Proveedor){
            proveedor = (Proveedor) objeto;
        } else if (objeto instanceof ProveedorImagen) {
            proIMG = (ProveedorImagen) objeto;
        }
        setBackground(Color.BLACK);
//        JPopupMenu menu = new JPopupMenu();
//        JMenuItem cambiar = new JMenuItem("Cambiar");
//        JMenuItem descripcion = new JMenuItem("Descripcion");
//        JMenuItem eliminar = new JMenuItem("Eliminar");
//        descripcion.setIcon(new ImageIcon(getClass().getResource("/img/description.png")));
//        cambiar.setIcon(new ImageIcon(getClass().getResource("/img/change.png")));
//        eliminar.setIcon(new ImageIcon(getClass().getResource("/img/delete.png")));
//        menu.add(descripcion);
//        menu.add(cambiar);
//        menu.add(eliminar);
      
          setToolTipText("Click derecho para ver Galeria");
        if (opcion == opciones.POPUP) {
            setToolTipText(null);
        }
    
         
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                if (opcion == opciones.GALERIA) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        p.galeria = new Galeria(p, true);
                        p.galeria.init(lugar);
                        p.galeria.setVisible(true);
//                        new Galeria(p, true, lugar).setVisible(true);
                    }
                }
                if (lugar != null) {
                    p.pnlEvento.cmbLugar.setSelectedItem(lugar.getNombreLocal());
                } else if (proveedor != null) {
                    p.pnlproveedores.cmbProveedor.setSelectedItem(proveedor.getNombreEmpresa());
                }
            }

        });
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (show) {
                    int width = getWidth();
                    int height = getHeight();
                    if (height < 320) {
                        mig.setComponentConstraints(ImageItem.this, "w " + (width + 1) + ", h " + (height + 1));
                        getParent().revalidate();
                    } else {
                        timer.stop();
                    }
                } else {
                    int width = getWidth();
                    int height = getHeight();
                    if (height > 260) {
                        mig.setComponentConstraints(ImageItem.this, "w " + (width - 1) + ", h " + (height - 1));
                        getParent().revalidate();
                    } else {
                        timer.stop();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                show = true;
                timer.start();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                show = false;
                timer.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (image != null) {
              Graphics2D g2 = (Graphics2D) grphcs;
             Rectangle size = getAutoSize(image);
            int width = getWidth();
            int height = getHeight();
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, width, height - shadowSize, 25, 25);
            g.setComposite(AlphaComposite.SrcIn);
            g.drawImage(toImage(image), size.x, size.y, size.width, size.height, null);
            g.dispose();
            g2.drawImage(img, 0, 0, null);
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (lugar != null) {
                g2.drawString("Nombre: " + lugar.getNombreLocal(), 0, height - shadowSize + 15);
                g2.drawString("Capacidad: " + lugar.getCapacidad(), 0, height - shadowSize + 35);
                if (lugar.getPrecio() == 0) {
                    g2.drawString("$ Aprox: ---", 0, height - shadowSize + 55);
                } else {
                    g2.drawString("$ Aprox: " + lugar.getPrecio(), 0, height - shadowSize + 55);
                }
            } else if (lugarimg != null) {

                g2.drawString((lugarimg.getDescripcion() != null) ? lugarimg.getDescripcion() : "", 0, height - shadowSize + 15);
            } else if(proveedor != null) {
                
                g2.drawString("Nombre: " + proveedor.getNombreEmpresa(), 0, height - shadowSize + 15);
            } else if (proIMG != null) {
                 g2.drawString((proIMG.getDescripcion() != null) ? proIMG.getDescripcion() : "", 0, height - shadowSize + 15);
            }

//            g2.drawImage(createShadow5Image(img), 0, height - shadowSize + 5, null);
            g2.dispose();
        }
        super.paintComponent(grphcs);
    }

    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight() - shadowSize;
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}
