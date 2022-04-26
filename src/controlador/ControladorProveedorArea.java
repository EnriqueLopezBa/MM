/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProveedorAreaDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.ProveedorArea;

/**
 *
 * @author Enrique
 */
public class ControladorProveedorArea {

    public Mensaje eliminar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje registrar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().registrar(t);
    }

    public ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad) {
        return ProveedorAreaDAOImp.getInstancia().obtenerListaByIdCiudad(idCiudad);
    }
}
