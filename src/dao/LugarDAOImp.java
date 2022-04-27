package dao;

import Componentes.Sweet_Alert.Message;
import idao.ILugarDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Lugar;

/**
 *
 * @author Enrique
 */
public class LugarDAOImp implements ILugarDAO {

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
    public ArrayList<Lugar> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Lugar> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT L.idLugar, L.idCiudad, L.nombreLocal, L.domicilio, L.capacidad, L.precio FROM lugar L\n"
                + "JOIN ciudad c on\n"
                + "    c.idCiudad = L.idCiudad\n"
                + "WHERE C.ciudad LIKE '%"+cadena+"%' OR L.nombreLocal LIKE '%"+cadena+"%' OR domicilio LIKE '%"+cadena+"%' OR precio LIKE '%"+cadena+"%'")) {

            ArrayList<Lugar> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new Lugar(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerlista Lugar, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Lugar obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE IDLUGAR = "+id)) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Lugar, " + e.getMessage());
        }   
        return null;
    }

    @Override
    public Mensaje registrar(Lugar t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO lugar VALUES (?,?,?,?,?)")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setString(2, t.getNombreLocal());
            ps.setString(3, t.getDomicilio());
            ps.setInt(4, t.getCapacidad());
            ps.setInt(5, t.getPrecio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error Registrar Lugar, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Lugar t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE lugar SET idCiudad = ? , nombreLocal = ?, domicilio = ?, capacidad = ?, precio = ? WHERE idLugar = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setString(2, t.getNombreLocal());
            ps.setString(3, t.getDomicilio());
            ps.setInt(4, t.getCapacidad());
            ps.setInt(5, t.getPrecio());
            ps.setInt(6, t.getIdLugar());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Lugar, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Lugar t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM LUGAR WHERE IDLUGAR = " + t.getIdLugar())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error al elimnar Lugar, " + e.getMessage());
        }

        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Lugar t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM lugar WHERE idLugar != "+t.getIdLugar()+" and idCiudad = '" + t.getIdCiudad()
                + "' AND nombreLocal = '" + t.getNombreLocal() + "'")) {
            if (rs.next()) {
                return t.getNombreLocal();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Lugar, " + e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<Lugar> obtenerListaByIDCiudad(int idCiudad) {
       try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE IDCIUDAD = " + idCiudad)) {
            ArrayList<Lugar> temp = new ArrayList<>();
            while(rs.next()){
                Lugar lugar = new Lugar();
                lugar.setIdLugar(rs.getInt(1));
                lugar.setIdCiudad(idCiudad);
                lugar.setNombreLocal(rs.getString(3));
                lugar.setDomicilio(rs.getString(4));
                lugar.setCapacidad(rs.getInt(5));
                lugar.setPrecio(rs.getInt(6));
               temp.add(lugar);
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByIDCiudad Lugar, " + e.getMessage());
        }    
        return null;
    }

    @Override
    public Lugar obtenerLugarByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM lugar ORDER BY idLugar DESC")) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (SQLException e) {
            System.err.println("Error ObtenerLugarByLast Lugar, "  + e.getMessage());
        }      
        return null;
    }

    @Override
    public Lugar obtenerLugarByCadena(Lugar lug) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE idCiudad = "+lug.getIdCiudad()+" and NOMBRELOCAL = '"+lug.getNombreLocal()+"'")) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerLugarByCadena Lugar," + e.getMessage());
        }        
        return null;
    }

}
