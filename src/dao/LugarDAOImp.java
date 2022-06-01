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
        try (PreparedStatement ps = cn.prepareStatement(" SELECT L.* FROM lugar L \n"
                + "  JOIN ciudad c on \n"
                + "  c.idCiudad = L.idCiudad\n"
                + "  JOIN proveedor P ON\n"
                + "  P.idProveedor = L.idProveedor\n"
                + " WHERE C.ciudad LIKE ? OR L.nombreLocal LIKE ? OR domicilio LIKE ? OR precioAprox LIKE ? OR P.nombre LIKE ? ORDER BY L.NOMBRELOCAL")) {
            ps.setString(1, "%"+cadena+"%");
            ps.setString(2, "%"+cadena+"%");
            ps.setString(3, "%"+cadena+"%");
            ps.setString(4, "%"+cadena+"%");
            ps.setString(5, "%"+cadena+"%");
            ResultSet rs = ps.executeQuery();
            ArrayList<Lugar> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Lugar(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerlista Lugar, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Lugar obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE IDLUGAR = " + id)) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO lugar VALUES (?,?,?,?,?,?)")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdProveedor());
            ps.setString(3, t.getNombreLocal());
            ps.setString(4, t.getDomicilio());
            ps.setInt(5, t.getCapacidad());
            ps.setInt(6, t.getPrecio());
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
        try (PreparedStatement ps = cn.prepareStatement("UPDATE lugar SET idCiudad = ?, idProveedor = ?, nombreLocal = ?, domicilio = ?, capacidad = ?, precioAprox = ? WHERE idLugar = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdProveedor());
            ps.setString(3, t.getNombreLocal());
            ps.setString(4, t.getDomicilio());
            ps.setInt(5, t.getCapacidad());
            ps.setInt(6, t.getPrecio());
            ps.setInt(7, t.getIdLugar());
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM lugar WHERE idLugar != " + t.getIdLugar() + " and idCiudad = '" + t.getIdCiudad()
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
            while (rs.next()) {
                Lugar lugar = new Lugar();
                lugar.setIdLugar(rs.getInt("idLugar"));
                lugar.setIdCiudad(idCiudad);
                lugar.setIdProveedor(rs.getInt("idProveedor"));
                lugar.setNombreLocal(rs.getString("nombreLocal"));
                lugar.setDomicilio(rs.getString("domicilio"));
                lugar.setCapacidad(rs.getInt("capacidad"));
                lugar.setPrecio(rs.getInt("precioAprox"));
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1 * FROM lugar ORDER BY idLugar DESC")) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            }
        } catch (SQLException e) {
            System.err.println("Error ObtenerLugarByLast Lugar, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Lugar obtenerLugarByCadena(Lugar lug) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR WHERE idCiudad = " + lug.getIdCiudad() + " and NOMBRELOCAL = '" + lug.getNombreLocal() + "'")) {
            if (rs.next()) {
                return new Lugar(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerLugarByCadena Lugar," + e.getMessage());
        }
        return null;
    }

}
