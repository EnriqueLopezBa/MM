package dao;

import Componentes.Sweet_Alert.Message;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.LugarInformacion;
import idao.ILugarInformacionDAO;

/**
 *
 * @author Enrique
 */
public class LugarDAOImp implements ILugarInformacionDAO {
    
    private static LugarDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();
    
    private LugarDAOImp() {
    }
    
    public static LugarDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new LugarDAOImp();
        }
        return instancia;
    }
    
    @Override
    public ArrayList<LugarInformacion> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<LugarInformacion> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM F_LUGARINFO('" + cadena + "')")) {
            ArrayList<LugarInformacion> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new LugarInformacion(rs.getInt(1), rs.getString(5), rs.getInt(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerlista Lugar, " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public LugarInformacion obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM lugar.Informacion WHERE idNegocio = " + id)) {
            if (rs.next()) {
                return new LugarInformacion(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Lugar, " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Mensaje registrar(LugarInformacion t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO lugar.Informacion VALUES (?,?,?)")) {
            ps.setInt(1, t.getIdNegocio());
            ps.setString(2, t.getDomicilio());
            ps.setInt(3, t.getCapacidad());
            
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error Registrar Lugar, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public Mensaje actualizar(LugarInformacion t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE lugar.informacion SET domicilio = ?, capacidad = ? WHERE idNegocio = ?")) {
            ps.setString(1, t.getDomicilio());
            ps.setInt(2, t.getCapacidad());
            ps.setInt(3, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Lugar, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public Mensaje eliminar(LugarInformacion t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM LUGAR.informacion WHERE idNegocio = " + t.getIdNegocio())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error al elimnar Lugar, " + e.getMessage());
        }
        
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public String yaExiste(LugarInformacion t) {
        
        return "";
    }
    
    @Override
    public ArrayList<LugarInformacion> obtenerListaByIDCiudad(int idCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM [F_LUGARBYCITY]('"+idCiudad+"')")) {
            ArrayList<LugarInformacion> temp = new ArrayList<>();
            while (rs.next()) {
              temp.add(new LugarInformacion(rs.getInt(1), rs.getString(5), rs.getInt(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByIDCiudad Lugar, " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public LugarInformacion obtenerLugarByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1 * FROM lugar.informacion ORDER BY idNegocio DESC")) {
            if (rs.next()) {
                return new LugarInformacion(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            System.err.println("Error ObtenerLugarByLast Lugar, " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public LugarInformacion obtenerLugarByCadena(LugarInformacion lug) {
//        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE idCiudad = " + lug.getIdCiudad() + " and NOMBRELOCAL = '" + lug.getNombreLocal() + "'")) {
//            if (rs.next()) {
//                return new LugarInformacion(rs.getInt(1), rs.getString(5), rs.getInt(6));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error obtenerLugarByCadena Lugar," + e.getMessage());
//        }
        return null;
    }
    
}
