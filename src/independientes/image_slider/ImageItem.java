package independientes.image_slider;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import modelo.Lugar;
import modelo.LugarImagenes;
import modelo.Proveedor;
import modelo.ProveedorImagenes;
import net.miginfocom.swing.MigLayout;
import vista.paneles.edit.DialogLugarImagenes;
import vista.principales.Principal;

public class ImageItem extends JComponent {

    private Icon image;
    private final int shadowSize = 60;
    private Timer timer;
    private boolean show;

    private Lugar lugar;
    private LugarImagenes lugarIMG;

    private Proveedor proveedor;
    private ProveedorImagenes proveedorIMG;

    public ImageItem(Icon image, MigLayout mig, Object clase, Object claseIMG) {
        this.image = image;
        if (clase instanceof Lugar) {
            this.lugar = (Lugar) clase;
        } else if (clase instanceof Proveedor) {
            this.proveedor = (Proveedor) clase;
        }
        if (claseIMG instanceof LugarImagenes) {
            this.lugarIMG = (LugarImagenes) claseIMG;
        } else if (claseIMG instanceof ProveedorImagenes) {
            this.proveedorIMG = (ProveedorImagenes) claseIMG;
        }

        setBackground(Color.BLACK);
        setToolTipText("Click derecho para ver galeria");

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (show) {
                    int width = getWidth();
                    int height = getHeight();
                    if (height < 300) {
                        mig.setComponentConstraints(ImageItem.this, "w " + (width + 1) + ", h " + (height + 1));
                        getParent().revalidate();
                    } else {
                        timer.stop();
                    }
                } else {
                    int width = getWidth();
                    int height = getHeight();
                    if (height > 200) {
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

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (lugar != null) {
                        DialogLugarImagenes te = new DialogLugarImagenes(Principal.getInstancia(), lugar);
                        te.setVisible(true);
                    }

                }
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
            g.fillRoundRect(0, 0, width, height - shadowSize, 25, 25);
            g.setComposite(AlphaComposite.SrcIn);
            g.drawImage(toImage(image), size.x, size.y, size.width, size.height, null);
            g.dispose();
            g2.drawImage(img, 0, 0, null);
            if (lugar != null && lugarIMG != null) {
                g2.setFont(new Font("Times Roman", Font.PLAIN, 14));
                g2.drawString("Nombre: " + lugar.getNombreLocal(), 15, height - shadowSize + 15);
                g2.drawString("Capacidad Aprox.: " + lugar.getCapacidad() + "", 15, height - shadowSize + 30);
                g2.drawString("Precio Aprox.: " + lugar.getPrecio() + "", 15, height - shadowSize + 45);
            } else if (lugarIMG != null) {
                g2.drawString(lugarIMG.getDescripcion(), 15, height - shadowSize + 15);
            }
            if (proveedor != null && proveedorIMG != null) {
                g2.setFont(new Font("Times Roman", Font.PLAIN, 14));
                g2.drawString("Nombre: " + proveedor.getNombreEmpresa(), 15, height - shadowSize + 15);
                g2.drawString("Precio Aprox.: " + proveedor.getPrecioAprox()+ "", 15, height - shadowSize + 35);
            } else if (proveedorIMG != null) {
                g2.drawString(proveedorIMG.getDescripcion(), 15, height - shadowSize + 15);
            }
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
