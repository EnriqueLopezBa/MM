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
import modelo.ProveedorAdeudo;

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
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM proveedor.PROVEEDOR WHERE nombre LIKE ? OR telefono LIKE ? OR telefono2 LIKE ? ORDER BY NOMBRE;")) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM proveedor.PROVEEDOR WHERE IDPROVEEDOR = " + id)) {
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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO proveedor.PROVEEDOR VALUES (?,?,?,?)")) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getTelefono());
            if (t.getTelefono2().isEmpty()) {
                ps.setNull(3, Types.NULL);
            } else {
                ps.setString(3, t.getTelefono2());
            }
            ps.setBoolean(4, t.isDisponible());
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
        try (PreparedStatement ps = cn.prepareStatement("UPDATE proveedor.PROVEEDOR SET nombre = ?, telefono = ?, telefono2 = ?, disponible = ? WHERE idProveedor = ?")) {
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
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM proveedor.PROVEEDOR WHERE IDPROVEEDOR = " + t.getIdProveedor())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");

        } catch (SQLException e) {
            System.err.println("Error eliminar Proveedor, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Proveedor t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM proveedor.PROVEEDOR WHERE IDPROVEEDOR != " + t.getIdProveedor() + " and TELEFONO = '" + t.getTelefono() + "'")) {
            if (rs.next()) {
                return t.getTelefono();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Proveedor, " + e.getMessage());
        }
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM proveedor.PROVEEDOR WHERE IDPROVEEDOR != " + t.getIdProveedor() + " and TELEFONO2 = '" + t.getTelefono2() + "'")) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1 * FROM proveedor.PROVEEDOR ORDER BY idProveedor DESC")) {
            if (rs.next()) {
                return new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByLast Proveedor, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ProveedorAdeudo> obtenerProveedoresConEventos() {
        try (ResultSet rs = Conexion.getInstancia().Consulta(";WITH CTE AS \n"
                + "(\n"
                + "SELECT  \n"
                + "      idEvento\n"
                + "      ,idNegocio\n"
                + "      ,idProveedor,cotizacion, ADEUDO"
                + "     , ROW_NUMBER() OVER(PARTITION BY IDPROVEEDOR ORDER BY IDPROVEEDOR DESC) AS \"RowNumber\"\n"
                + "  FROM VW_ADEUDOPROVEEDORES\n"
                + "  )\n"
                + "\n"
                + "  SELECT \n"
                + "       idEvento\n"
                + "      ,idNegocio\n"
                + "      ,idProveedor,cotizacion, ADEUDO"
                + "  FROM CTE WHERE RowNumber=1\n"
                + "  ORDER BY idProveedor DESC")) {
            ArrayList<ProveedorAdeudo> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorAdeudo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerProveedoresConEventos Proveedor," + e.getMessage());
        }
        return null;
    }

    @Override
    public Object obtenerTotalCotizacionByIDEventoAndIDNegocio(int idEvent, int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT cotizacion FROM EVENTO.COTIZACION WHERE cotFinal = 1 AND idEvento = "+idEvent+" AND IDNEGOCIO = "+ idNegocio)) {
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }     
        return null;
    }

    
}
