/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Componentes.Sweet_Alert.Message;
import idao.IAbonoProveedoresDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.AbonoProveedores;

/**
 *
 * @author lalo_
 */
public class AbonoProveedoresDAOImp implements IAbonoProveedoresDAO{
    private static AbonoProveedoresDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private AbonoProveedoresDAOImp() {
    }

    public static AbonoProveedoresDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new AbonoProveedoresDAOImp();
        }
        return instancia;
    }

    public ArrayList<AbonoProveedores> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<AbonoProveedores> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AbonoProveedores obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mensaje registrar(AbonoProveedores t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDOR.ABONOS VALUES(?,?,?,?)")) {
            ps.setInt(1, t.getIdProveedor());
            ps.setInt(2, t.getIdEvento());
            ps.setInt(3, t.getImporte());
            ps.setDate(4, new Date(t.getFecha().getTime()));
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registar Abono, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    public Mensaje actualizar(AbonoProveedores t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE ")) {
            
        } catch (SQLException e) {
            System.err.println("Error actualizar Abono, " + e.getMessage());
        }    
         return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    public Mensaje eliminar(AbonoProveedores t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM PROVEEDOR.ABONOS WHERE IDABONO = " + t.getIdAbono())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    public String yaExiste(AbonoProveedores t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<AbonoProveedores> obtenerListaByIdEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOR.ABONOS WHERE IDEVENTO = " +idEvento)) {
            ArrayList<AbonoProveedores> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new AbonoProveedores(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }   
        return null;
    }
}
