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

    private static ControladorProveedorArea instancia;
    private ControladorProveedorArea() {
    }
    public static ControladorProveedorArea getInstancia(){
        if (instancia == null) {
            instancia = new ControladorProveedorArea();
        }
        return instancia;
    }

    
    public Mensaje eliminar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje registrar(ProveedorArea t) {
        return ProveedorAreaDAOImp.getInstancia().registrar(t);
    }

    public Mensaje registrarLote(ArrayList<ProveedorArea> t) {
        return ProveedorAreaDAOImp.getInstancia().registrarLote(t);
    }

    public Mensaje actualizarLote(ArrayList<ProveedorArea> t, int idProveedor) {
        return ProveedorAreaDAOImp.getInstancia().actualizarLote(t, idProveedor);
    }

    public ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad) {
        return ProveedorAreaDAOImp.getInstancia().obtenerListaByIdCiudad(idCiudad);
    }

    public ArrayList<ProveedorArea> obtenerListaByIdProveedor(int idProveedor) {
        return ProveedorAreaDAOImp.getInstancia().obtenerListaByIdProveedor(idProveedor);
    }

    public ProveedorArea obtenerByIdCiudadAndIdProveedor(int idCiudad, int idProveedor) {
        return ProveedorAreaDAOImp.getInstancia().obtenerByIdCiudadAndIdProveedor(idCiudad, idProveedor);
    }
}
