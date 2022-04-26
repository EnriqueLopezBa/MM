/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.TipoProveedorDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.TipoProveedor;

/**
 *
 * @author Enrique
 */
public class ControladorTipoProveedor {

    public ArrayList<TipoProveedor> obtenerListaByCadena(String cadena) {
        return TipoProveedorDAOImp.getInstancia().obtenerListaByCadena(cadena);
    }

    public Mensaje registrar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().registrar(t);
    }

    public Mensaje actualizar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje eliminar(TipoProveedor t) {
        return TipoProveedorDAOImp.getInstancia().eliminar(t);
    }
}
