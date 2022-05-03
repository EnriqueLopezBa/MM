/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public static ControladorAbono getInstancia(){
        if (instancia == null) {
            instancia = new ControladorAbono();
        }
        return instancia;
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
}
