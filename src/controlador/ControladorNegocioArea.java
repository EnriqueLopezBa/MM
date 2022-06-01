/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.NegocioAreaDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.NegocioArea;

/**
 *
 * @author Enrique
 */
public class ControladorNegocioArea {

    private static ControladorNegocioArea instancia;
    private ControladorNegocioArea() {
    }
    public static ControladorNegocioArea getInstancia(){
        if (instancia == null) {
            instancia = new ControladorNegocioArea();
        }
        return instancia;
    }

    
    public Mensaje eliminar(NegocioArea t) {
        return NegocioAreaDAOImp.getInstancia().eliminar(t);
    }

    public Mensaje actualizar(NegocioArea t) {
        return NegocioAreaDAOImp.getInstancia().actualizar(t);
    }

    public Mensaje registrar(NegocioArea t) {
        return NegocioAreaDAOImp.getInstancia().registrar(t);
    }

    public Mensaje registrarLote(ArrayList<NegocioArea> t) {
        return NegocioAreaDAOImp.getInstancia().registrarLote(t);
    }

    public Mensaje actualizarLote(ArrayList<NegocioArea> t, int idProveedor) {
        return NegocioAreaDAOImp.getInstancia().actualizarLote(t, idProveedor);
    }

    public ArrayList<NegocioArea> obtenerListaByIdCiudad(int idCiudad) {
        return NegocioAreaDAOImp.getInstancia().obtenerListaByIdCiudad(idCiudad);
    }

    public ArrayList<NegocioArea> obtenerListaByIdNegocio(int idProveedor) {
        return NegocioAreaDAOImp.getInstancia().obtenerListaByIdNegocio(idProveedor);
    }

    public NegocioArea obtenerByIdCiudadAndIdNegocioArea(int idCiudad, int idProveedor) {
        return NegocioAreaDAOImp.getInstancia().obtenerByIdCiudadAndIdNegocio(idCiudad, idProveedor);
    }
}
