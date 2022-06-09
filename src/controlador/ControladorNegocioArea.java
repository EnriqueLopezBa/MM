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

    public static ControladorNegocioArea getInstancia() {
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

    public Mensaje actualizarLote(ArrayList<NegocioArea> t) {
        return NegocioAreaDAOImp.getInstancia().actualizarLote(t);
    }

    public ArrayList<NegocioArea> obtenerListaByIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        return NegocioAreaDAOImp.getInstancia().obtenerListaByIdCiudadAndTipoProveedor(idCiudad, idTipoProveedor);
    }

    public ArrayList<NegocioArea> obtenerListaByIdNegocio(int idNegocio) {
        return NegocioAreaDAOImp.getInstancia().obtenerListaByIdNegocio(idNegocio);
    }

    public NegocioArea obtenerByIdCiudadAndIdNegocioArea(int idCiudad, int idProveedor) {
        return NegocioAreaDAOImp.getInstancia().obtenerByIdCiudadAndIdNegocio(idCiudad, idProveedor);
    }

    public NegocioArea obtenerNegocioByNombre(String nombre) {
        return NegocioAreaDAOImp.getInstancia().obtenerNegocioByNombre(nombre);
    }

    public NegocioArea obtenerNegocioByLast() {
        return NegocioAreaDAOImp.getInstancia().obtenerNegocioByLast();
    }
}
