package controlador;

import dao.AbonoDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Abono;

/**
 *
 * @author Enrique
 */
public class ControladorAbono {

    private static ControladorAbono instancia;

    private ControladorAbono() {
    }

    public static ControladorAbono getInstancia() {
        if (instancia == null) {
            instancia = new ControladorAbono();
        }
        return instancia;
    }

    public int obtenerCantidadADeber(int idCliente, int idEvento) {
        return AbonoDAOImp.getInstancia().obtenerCantidadADeber(idCliente, idEvento);
    }

    public ArrayList<Abono> obtenerListaByIdEvento(int idEvento) {
        return AbonoDAOImp.getInstancia().obtenerListaByIdEvento(idEvento);
    }

    public Mensaje eliminar(Abono t) {
        return AbonoDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje registrar(Abono t) {
        return AbonoDAOImp.getInstancia().registrar(t);
    }

    public ArrayList<Integer> obtenerEventosConAdeudo(int idCliente) {
        return AbonoDAOImp.getInstancia().obtenerEventosConAdeudo(idCliente);
    }
}
