package dao;

import Componentes.Sweet_Alert.Message;
import idao.ITipoUsuarioDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.TipoUsuario;

/**
 *
 * @author Enrique
 */
public class TipoUsuarioDAOImp implements ITipoUsuarioDAO {

    private static TipoUsuarioDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private TipoUsuarioDAOImp() {
    }

    public static TipoUsuarioDAOImp getInstacia() {
        if (instancia == null) {
            instancia = new TipoUsuarioDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<TipoUsuario> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoUsuario> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOUSUARIO WHERE TIPOUSUARIO LIKE '%" + cadena + "%'")) {
            ArrayList<TipoUsuario> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new TipoUsuario(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena TipoUsuario, " + e.getMessage());
        }
        return null;
    }

    @Override
    public TipoUsuario obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOUSUARIO WHERE idTipoUsuario = " + id)) {
            if (rs.next()) {
                return new TipoUsuario(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID TipoUsuario, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(TipoUsuario t) {
        if (!yaExiste(t).isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, "Ya existe");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO TIPOUSUARIO VALUES(?)")) {
            ps.setString(1, t.getTipoUsuario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar TipoUsuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(TipoUsuario t) {
        if (!yaExiste(t).isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, "Ya existe");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE TIPOUSUARIO SET TIPOUSUARIO = ? WHERE IDTIPOUSUARIO = ?")) {
            ps.setString(1, t.getTipoUsuario());
            ps.setInt(2, t.getIdTipoUsuario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar TipoUsuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(TipoUsuario t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM TIPOUSUARIO WHERE IDTIPOUSUARIO = ?")) {
            ps.setInt(1, t.getIdTipoUsuario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar TipoUsuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(TipoUsuario t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM TIPOUSUARIO WHERE TIPOUSUARIO = '" + t.getTipoUsuario() + "'")) {
            if (rs.next()) {
                return "Ya existe";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

}
