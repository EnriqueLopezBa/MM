package dao;

import Componentes.Sweet_Alert.Message;
import idao.ITipoProveedorDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.TipoProveedor;

/**
 *
 * @author Enrique
 */
public class TipoProveedorDAOImp implements ITipoProveedorDAO {

    private static TipoProveedorDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private TipoProveedorDAOImp() {
    }

    public static TipoProveedorDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new TipoProveedorDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<TipoProveedor> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoProveedor> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOPROVEEDOR WHERE TIPOPROVEEDOR LIKE '%"+cadena+"%'")) {
            ArrayList<TipoProveedor> temp = new ArrayList();
            while(rs.next()){
                temp.add(new TipoProveedor(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena TipoProveedor, " + e.getMessage());
        }        
        return null;
    }

    @Override
    public TipoProveedor obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOPROVEEDOR WHERE IDTIPOPROVEEDOR = "+ id)) {
            if (rs.next()) {
                return new TipoProveedor(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID TipoProveedor, " + e.getMessage());
        }        
        return null;
    }

    @Override
    public Mensaje registrar(TipoProveedor t) {
         String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO TIPOPROVEEDOR VALUES(?)")) {
            ps.setString(1, t.getTipoProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar TipoProveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(TipoProveedor t) {
         String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE TIPOPROVEEDOR SET TIPOPROVEEDOR = ? WHERE IDTIPOPROVEEDOR = ?")) {
            ps.setString(1, t.getTipoProveedor());
            ps.setInt(2, t.getIdTipoProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar TipoProveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(TipoProveedor t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM TIPOPROVEEDOR WHERE IDTIPOPROVEEDOR = ?")) {
            ps.setInt(1, t.getIdTipoProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar TipoProveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(TipoProveedor t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOPROVEEDOR WHERE TIPOPROVEEDOR = '"+t.getTipoProveedor()+"'")) {
            if (rs.next()) {
                return t.getTipoProveedor();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste TipoProveedor, " + e.getMessage());
        }        
        return "";
    }

    @Override
    public TipoProveedor obtenerTipoProveedorByNombre(String nombre) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOPROVEEDOR WHERE TIPOPROVEEDOR = '"+nombre+"'")) {
            if (rs.next()) {
                return new TipoProveedor(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("error obtenerTipoProveedorByNombre TipoProveedor, "  +e.getMessage());
        }        
        return null;
    }

}
