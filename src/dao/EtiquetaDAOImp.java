
package dao;

import Componentes.Sweet_Alert.Message;
import idao.IEtiquetaDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.Etiqueta;

/**
 *
 * @author Enrique
 */
public class EtiquetaDAOImp implements IEtiquetaDAO {

    private static EtiquetaDAOImp instancia;
    private EtiquetaDAOImp() {
    }
    
    public static EtiquetaDAOImp getInstancia(){
        if (instancia == null) {
            instancia = new EtiquetaDAOImp();
        }
        return instancia;
    }

    Connection cn = Conexion.getInstancia().getConexion();
    
    
    @Override
    public ArrayList<Etiqueta> obtenerLista() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ETIQUETA")) {
            ArrayList<Etiqueta> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new Etiqueta(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerLista Etiqueta, " + e.getMessage());
        }  
        return null;
    }

    @Override
    public ArrayList<Etiqueta> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ETIQUETA WHERE ETIQUETA LIKE '%" + cadena + "%'")) {
            ArrayList<Etiqueta> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Etiqueta(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerLista Etiqueta, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Etiqueta obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ETIQUETA WHERE IDETIQUETA = " + id)) {
            if (rs.next()) {
                return new Etiqueta(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Etiqueta, " + e.getMessage());
        }        
        return null;
    }

    @Override
    public Mensaje registrar(Etiqueta t) {
         String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps =  cn.prepareStatement("INSERT INTO etiqueta VALUES (?)")) {
            ps.setString(1, t.getEtiqueta());
            return (ps.executeUpdate() >= 1)? new Mensaje(Message.Tipo.OK, "Etique registrada") : new Mensaje(Message.Tipo.ERROR, "Problema al registrar!");
        } catch (SQLException e) {
            System.err.println("Error registro Etiqueta, " + e.getMessage());
        }        
        return new Mensaje(Message.Tipo.ERROR, "Error!");
    }

    @Override
    public Mensaje actualizar(Etiqueta t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE etiqueta SET etiqueta = '?' WHERE idEtiqueta = ?")) {
            ps.setString(1, t.getEtiqueta());
            ps.setInt(1, t.getIdEtiqueta());
            return (ps.executeUpdate() >= 1) ?new Mensaje(Message.Tipo.OK, "Etiqueta actualizada!") :new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar!");
        } catch (SQLException e) {
            System.err.println("Error actualizar Etiqueta, " + e.getMessage());
        }     
        return new Mensaje(Message.Tipo.ERROR, "Error al actualizar!");
    }

    @Override
    public Mensaje eliminar(Etiqueta t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM ETIQUETA WHERE IDETIQUETA = ?")) {
            ps.setInt(1, t.getIdEtiqueta());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Etiqueta eliminada!") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar!");
        } catch (SQLException e) {
            System.err.println("Error eliminar etiqueta, " + e.getMessage());
        }        
        return new Mensaje(Message.Tipo.ERROR, "Error al eliminar!");
    }

    @Override
    public String yaExiste(Etiqueta etiqueta) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ETIQUETA idEtiqueta != "+etiqueta.getIdEtiqueta()+" and WHERE ETIQUETA = '"+etiqueta.getEtiqueta()+"'")) {
            if (rs.next()) {
                return etiqueta.getEtiqueta();
            }
        } catch (SQLException e) {
            System.err.println("Error ya existeEtiqueta, " + e.getMessage());
        }        
        return "";
    }

    @Override
    public Etiqueta obtenerByEtiquetaNombre(String nombre) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ETIQUETA WHERE ETIQUETA = '"+nombre+"'")) {
            if(rs.next()){
                return new Etiqueta(rs.getInt(1), rs.getString(2));
            }
           
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByNombre Etiqueta, " + e.getMessage());
        }  
        return null;      
    }
    
}
