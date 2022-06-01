package dao;

import Componentes.Sweet_Alert.Message;
import idao.IEstadoDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Estado;

/**
 *
 * @author Enrique
 */
public class EstadoDAOImp implements IEstadoDAO {

    private static EstadoDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private EstadoDAOImp() {
    }

    public static EstadoDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new EstadoDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Estado> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Estado> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ESTADO WHERE ESTADO lIKE '%" + cadena + "%' ORDER BY ESTADO")) {
            ArrayList<Estado> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Estado(rs.getInt(1), rs.getString(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerlista ESTADO, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Estado obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ESTADO WHERE IDESTADO = "+ id)) {
            if (rs.next()) {
                return new Estado(rs.getInt(1), rs.getString(2));
            }
            
        } catch (SQLException e) {
            System.err.println("Error otenerbyID Estado, " + e.getMessage());
        }     
        return null;
    }

    @Override
    public Mensaje registrar(Estado t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO ESTADO VALUES (?)")) {
            ps.setString(1, t.getEstado());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Estado, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Estado t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE ESTADO SET ESTADO = ? WHERE IDESTADO = ?")) {
            ps.setString(1, t.getEstado());
            ps.setInt(2, t.getIdEstado());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error Actualizar Estado, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Estado t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM ESTADO WHERE IDESTADO = ?")) {
            ps.setInt(1, t.getIdEstado());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error Eliminar TipoEvento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Estado t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ESTADO WHERE IDESTADO != " + t.getIdEstado()
                + "  AND estado = '" + t.getEstado() + "'")) {
            if (rs.next()) {
                return t.getEstado();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Estado, " + e.getMessage());
        }
        return "";
    }

}
