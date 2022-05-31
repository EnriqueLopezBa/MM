package dao;

import Componentes.Sweet_Alert.Message;
import idao.ICotizacionDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Cotizacion;

/**
 *
 * @author Enrique
 */
public class CotizacionDAOImp implements ICotizacionDAO {

    private Connection cn = Conexion.getInstancia().getConexion();

    private static CotizacionDAOImp instancia;

    private CotizacionDAOImp() {
    }

    public static CotizacionDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new CotizacionDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Cotizacion> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByCadena(String cadena) {
        return null;
    }

    @Override
    public Cotizacion obtenerByID(int id) {

        return null;
    }

    @Override
    public Mensaje registrar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO COTIZACION VALUES(?,?,?)")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdProveedor());
            ps.setInt(3, t.getCotizacion());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Cotizacion, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE COTIZACION SET COTIZACION = ? WHERE IDEVENTO = ? AND IDPROVEEDOR = ?")) {
            ps.setInt(1, t.getCotizacion());
            ps.setInt(2, t.getIdEvento());
            ps.setInt(3, t.getIdProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("error actualizar Cotizacion, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM COTIZACION WHERE IDEVENTO = ? AND IDPROVEEDOR = ?")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");

    }

    @Override
    public String yaExiste(Cotizacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM COTIZACION WHERE IDEVENTO = " + idEvento)) {
            ArrayList<Cotizacion> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Cotizacion, " + e.getMessage());
        }
        return null;
    }

}
