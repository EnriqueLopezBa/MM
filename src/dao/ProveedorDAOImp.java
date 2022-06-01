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
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM proveedor WHERE nombre LIKE ? OR telefono LIKE ? OR telefono2 LIKE ? ORDER BY NOMBRE;")) {
            ps.setString(1, "%" + cadena + "%");
            ps.setString(2, "%" + cadena + "%");
            ps.setString(3, "%" + cadena + "%");
            ArrayList<Proveedor> temp = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp.add(new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5)));
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
                return new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDOR VALUES (?,?,?,?,?)")) {
            ps.setInt(1, t.getIdProveedor());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getTelefono());
            if (t.getTelefono2().isEmpty()) {
                ps.setNull(4, Types.NULL);
            } else {
                ps.setString(4, t.getTelefono2());
            }
            ps.setBoolean(5, t.isDisponible());
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
        try (PreparedStatement ps = cn.prepareStatement("UPDATE proveedor SET nombre = ?, telefono = ?, telefono2 = ?, disponible = ? WHERE idProveedor = ?")) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getTelefono());
            if (t.getTelefono2().isEmpty()) {
                ps.setNull(3, Types.NULL);
            } else {
                ps.setString(3, t.getTelefono2());
            }
            ps.setBoolean(4, t.isDisponible());
            ps.setInt(5, t.getIdProveedor());
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
        return "";
    }

    @Override
    public Proveedor obtenerByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1 * FROM proveedor ORDER BY idProveedor DESC")) {
            if (rs.next()) {
                return new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByLast Proveedor, " + e.getMessage());
        }
        return null;
    }



}
