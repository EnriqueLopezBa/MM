package independientes;

import controlador.ControladorNegocio;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import modelo.Abono;
import modelo.AbonoProveedores;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Cotizacion;
import modelo.Estado;
import modelo.Etiqueta;
import modelo.Evento;
import modelo.EventosDestacados;
import modelo.LugarInformacion;
import modelo.LugarEtiquetas;
import modelo.LugarImagenes;
import modelo.Negocio;
import modelo.Pregunta;
import modelo.Proveedor;
import modelo.ProveedorEvento;
import modelo.Quiz;
import modelo.TipoEvento;
import modelo.TipoProveedor;
import modelo.TipoUsuario;
import modelo.Usuario;

/**
 *
 * @author Enrique
 */
public class MyObjectListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof Negocio) {
            value = ((Negocio) value).getNombreNegocio();
        } else if (value instanceof Evento) {
            value = ((Evento) value).getNombreEvento();
        } else if (value instanceof Ciudad) {
            value = ((Ciudad) value).getCiudad();
        } else if (value instanceof Estado) {
            value = ((Estado) value).getEstado();
        } else if (value instanceof TipoUsuario) {
            value = ((TipoUsuario) value).getTipoUsuario();
        } else if (value instanceof TipoProveedor) {
            value = ((TipoProveedor) value).getTipoProveedor();
        } 
        else if (value instanceof LugarInformacion) {
            Negocio neg = ControladorNegocio.getInstancia().obtenerByID(((LugarInformacion) value).getIdNegocio());
            value = neg.getNombreNegocio();
        } 
        else if (value instanceof TipoEvento) {
            value = ((TipoEvento) value).getTematica();
        } else if (value instanceof Proveedor) {
            value = ((Proveedor) value).getNombre();
        }
//        else if (value instanceof LugarEtiquetas) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof LugarImagenes) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof Negocio) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof Pregunta) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof Proveedor) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof ProveedorEvento) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof Quiz) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof TipoEvento) {
//             value = ((Negocio)value).getNombreNegocio();
//        }else if (value instanceof TipoProveedor) {
//             value = ((Negocio)value).getNombreNegocio();
//        }
//       else if (value instanceof Usuario) {
//             value = ((Negocio)value).getNombreNegocio();
//        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }

}
