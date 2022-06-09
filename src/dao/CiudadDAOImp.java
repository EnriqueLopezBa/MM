package dao;

import Componentes.Sweet_Alert.Message;
import idao.ICiudadDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Ciudad;

/**
 *
 * @author Enrique
 */
public class CiudadDAOImp implements ICiudadDAO {

    private static CiudadDAOImp instancia;
    Connection cn = Conexion.getInstancia().getConexion();

    private CiudadDAOImp() {
    }

    public static CiudadDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new CiudadDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Ciudad> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Ciudad> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ciudad WHERE ciudad LIKE '%" + cadena + "%'")) {
            ArrayList<Ciudad> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Ciudad(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error ObtenerListCiudad, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Ciudad obtenerByID(int id) {
         try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CIUDAD WHERE IDCIUDAD = "+ id)) {
            if (rs.next()) {
                return new Ciudad(rs.getInt(1),rs.getInt(2), rs.getString(3));
            }
            
        } catch (SQLException e) {
            System.err.println("Error otenerbyID Ciudad, " + e.getMessage());
        }     
        return null;
    }

    @Override
    public Mensaje registrar(Ciudad t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO ciudad(idEstado,Ciudad) VALUES(?,?)")) {
            ps.setInt(1, t.getIdEstado());
            ps.setString(2, t.getCiudad());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error regidtrar Ciudad, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Ciudad t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE CIUDAD SET IDESTADO = ?, CIUDAD = ? WHERE IDCIUDAD = ?")) {
            ps.setInt(1, t.getIdEstado());
            ps.setString(2, t.getCiudad());
            ps.setInt(3, t.getIdCiudad());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Ciudad, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Ciudad t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM CIUDAD WHERE IDCIUDAD = ?")) {
            ps.setInt(1, t.getIdCiudad());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar Ciudad, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");

    }

    @Override
    public String yaExiste(Ciudad t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CIUDAD WHERE IDCIUDAD != "+ t.getIdCiudad()
                +" and ciudad = '"+t.getCiudad()+"'")) {
            if (rs.next()) {
                return t.getCiudad();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }   
        return "";
    }

    @Override
    public ArrayList<Ciudad> obtenerByIDEstado(int idEstado) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CIUDAD WHERE IDESTADO = " + idEstado)) {
            ArrayList<Ciudad> temp = new ArrayList<>();
            while(rs.next()){
               
                temp.add(new Ciudad(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerBYIDESTADO Ciudad, " + e.getMessage());
        }    
        return null;
    }

    @Override
    public Ciudad obtenerByNombre(String nombreCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CIUDAD WHERE CIUDAD = '"+nombreCiudad+"'")) {
            if (rs.next()) {
                return new Ciudad(rs.getInt(1), rs.getInt(2), rs.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByNombre Ciudad, " + e.getMessage());
        } 
        return null;
    }

}
