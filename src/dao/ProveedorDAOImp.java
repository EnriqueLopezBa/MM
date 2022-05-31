package dao;

import Componentes.Sweet_Alert.Message;
import idao.IProveedorDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Proveedor;

/**
 *
 * @author Enrique
 */
public class ProveedorDAOImp implements IProveedorDAO {

    private static ProveedorDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private ProveedorDAOImp() {
    }

    public static ProveedorDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new ProveedorDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Proveedor> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Proveedor> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT P.* FROM proveedor P\n"
                + "JOIN tipoProveedor T ON\n"
                + "P.idTipoProveedor = T.idTipoProveedor\n"
                + "WHERE T.tipoProveedor LIKE '%" + cadena + "%' OR nombre LIKE '%" + cadena + "%'"
                + " OR nombreEmpresa LIKE '%" + cadena + "%' OR telefono LIKE '%" + cadena + "%' "
                + "OR telefono2 LIKE '%" + cadena + "%' OR precioAprox LIKE '%" + cadena + "%'")) {
            ArrayList<Proveedor> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Proveedor(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getString(9)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena Proveedor, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Proveedor obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOR WHERE IDPROVEEDOR = " + id)) {
            if (rs.next()) {
                return new Proveedor(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getString(9));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerBYID Proveedor, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(Proveedor t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDOR VALUES (?,?,?,?,?,?,?,?)")) {
            ps.setInt(1, t.getIdtipoProveedor());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getNombreEmpresa());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getTelefono2());
            ps.setInt(6, t.getPrecioAprox());
            ps.setBoolean(7, t.isDisponible());
            if (t.getDescripcion().isEmpty()) {
                ps.setNull(8, Types.NULL);
            }else{
                 ps.setString(8, t.getDescripcion());
            }
          
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Proveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Proveedor t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE proveedor SET idTipoProveedor = ?, nombre = ?, nombreEmpresa = ?,"
                + " telefono = ?, telefono2 = ?, precioAprox = ?, disponible = ?, descripcion = ? WHERE idProveedor = ?")) {
            ps.setInt(1, t.getIdtipoProveedor());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getNombreEmpresa());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getTelefono2());
            ps.setInt(6, t.getPrecioAprox());
            ps.setBoolean(7, t.isDisponible());
             if (t.getDescripcion().isEmpty()) {
                ps.setNull(8, Types.NULL);
            }else{
                 ps.setString(8, t.getDescripcion());
            }
            ps.setInt(9, t.getIdProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Proveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Proveedor t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM PROVEEDOR WHERE IDPROVEEDOR = " + t.getIdProveedor())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");

        } catch (SQLException e) {
            System.err.println("Error eliminar Proveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Proveedor t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOR WHERE IDPROVEEDOR != " + t.getIdProveedor() + " and TELEFONO = '" + t.getTelefono() + "'")) {
            if (rs.next()) {
                return t.getTelefono();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Proveedor, " + e.getMessage());
        }
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOR WHERE IDPROVEEDOR != " + t.getIdProveedor() + " and TELEFONO2 = '" + t.getTelefono2() + "'")) {
            if (rs.next()) {
                return t.getTelefono2();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM proveedor where idproveedor != " + t.getIdProveedor() + " and nombre = '" + t.getNombre() + "'")) {
            if (rs.next()) {
                return t.getNombre();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<Proveedor> obtenerListaByIdTipoProveedor(int idTipoProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOR WHERE IDTIPOPROVEEDOR = " + idTipoProveedor)) {
            ArrayList<Proveedor> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Proveedor(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getString(9)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdTipoProveedor Proveedor, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Proveedor obtenerByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1 * FROM proveedor ORDER BY idProveedor DESC")) {
            if (rs.next()) {
                return new Proveedor(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8), rs.getString(9));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByLast Proveedor, " + e.getMessage());
        }
        return null;
    }

}
