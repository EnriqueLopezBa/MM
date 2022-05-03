/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.AbonoProveedoresDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.AbonoProveedores;

/**
 *
 * @author Enrique
 */
public class ControladorAbonoProveedores {

    private static ControladorAbonoProveedores instancia;
    private ControladorAbonoProveedores() {
    }
    public static ControladorAbonoProveedores getInstancia(){
        if (instancia == null) {
            instancia = new ControladorAbonoProveedores();
        }
        return instancia;
    }

    
    public ArrayList<AbonoProveedores> obtenerListaByIdEvento(int idEvento) {
        return AbonoProveedoresDAOImp.getInstancia().obtenerListaByIdEvento(idEvento);
    }

    public Mensaje eliminar(AbonoProveedores t) {
        return AbonoProveedoresDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje registrar(AbonoProveedores t) {
        return AbonoProveedoresDAOImp.getInstancia().registrar(t);
    }
}
