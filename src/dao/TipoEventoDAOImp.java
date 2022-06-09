package dao;

import Componentes.Sweet_Alert.Message;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.TipoEvento;
import idao.ITipoEventoDAO;

/**
 *
 * @author Enrique
 */
public class TipoEventoDAOImp implements ITipoEventoDAO {

    private static TipoEventoDAOImp instancia;
    Connection cn = Conexion.getInstancia().getConexion();

    private TipoEventoDAOImp() {
    }

    public static TipoEventoDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new TipoEventoDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<TipoEvento> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoEvento> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.TIPO WHERE tipoevento lIKE '%" + cadena + "%'")) {
            ArrayList<TipoEvento> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new TipoEvento(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerlista tipo evento, " + e.getMessage());
        }
        return null;
    }

    @Override
    public TipoEvento obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.TIPO WHERE IDTIPOEVENTO = "+id)) {
            if (rs.next()) {
                return new TipoEvento(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID TipoEvento , " + e.getMessage());
        }      
        return null;
    }

    @Override
    public Mensaje registrar(TipoEvento t) {
         String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO EVENTO.TIPO VALUES (?)")) {
            ps.setString(1, t.getTematica());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar TipoEvento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(TipoEvento t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE EVENTO.TIPO SET tipoEvento = ? WHERE idTipoEvento = ?")) {
            ps.setString(1, t.getTematica());
            ps.setInt(2, t.getIdTipoEvento());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error Actualizar Tipoevento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(TipoEvento t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM EVENTO.TIPO WHERE IDTIPOEVENTO = ?")) {
            ps.setInt(1, t.getIdTipoEvento());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error Eliminar TipoEvento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(TipoEvento t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.TIPO WHERE idTipoEvento != "+t.getIdTipoEvento()
                +"  AND tipoEvento = '"+t.getTematica()+"'")) {
            if (rs.next()) {
                return t.getTematica();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste TipoEvento, " + e.getMessage());
        }        
        return "";
    }

    @Override
    public TipoEvento obtenerTipoEventoByNombre(String nombre) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.TIPO WHERE TIPOEVENTO ='"+nombre+"'")) {
            if (rs.next()) {
                return new TipoEvento(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerTipoEventoByNombre TipoEventoo, " + e.getMessage());
        }   
        return null;
    }

}
