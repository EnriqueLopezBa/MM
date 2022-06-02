package dao;

import Componentes.Sweet_Alert.Message;
import idao.INegocioDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Negocio;

/**
 *
 * @author Enrique
 */
public class NegocioDAOImp implements INegocioDAO {

    private Connection cn = Conexion.getInstancia().getConexion();
    private static NegocioDAOImp instancia;

    private NegocioDAOImp() {
    }

    public static NegocioDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new NegocioDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Negocio> obtenerLista() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO")) {
            ArrayList<Negocio> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerLista Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Negocio> obtenerListaByCadena(String cadena) {
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM negocio N\n"
                + "  JOIN proveedor P ON\n"
                + "  N.idProveedor = P.idProveedor\n"
                + "  JOIN tipoProveedor T ON\n"
                + "  N.idTipoProveedor = T.idTipoProveedor\n"
                + "  WHERE P.nombre LIKE ? OR N.nombreNegocio LIKE ? OR T.tipoProveedor LIKE ?\n"
                + "  ORDER BY P.nombre")) {
            ps.setString(1, "%" + cadena + "%");
            ps.setString(2, "%" + cadena + "%");
            ps.setString(3, "%" + cadena + "%");
            ResultSet rs = ps.executeQuery();
            ArrayList<Negocio> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Negocio obtenerByID(int id
    ) {
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM NEGOCIO WHERE IDNEGOCIO = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(Negocio t
    ) {
        if (!yaExiste(t).isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, "Este negocio ya se encuentra registrado en este proveedor");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO NEGOCIO VALUES(?,?,?,?,?,?)")) {
            if (t.getIdProveedor() == 0) { // Un nuevo negocio agregado por el cliente
                ps.setNull(1, Types.NULL);
                ps.setInt(2, t.getIdTipoProveedor());
                ps.setString(3, t.getNombreNegocio());
                ps.setNull(4, Types.NULL);
                ps.setBoolean(5, true);
                ps.setNull(6, Types.NULL);
                return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
            }
            ps.setInt(1, t.getIdProveedor());
            ps.setInt(2, t.getIdTipoProveedor());
            ps.setString(3, t.getNombreNegocio());
            ps.setInt(4, t.getPrecioAprox());
            ps.setBoolean(5, t.isDisponible());
            if (t.getDescripcion().isEmpty() || t.getDescripcion().equals("Puedes añadir una descripcion del negocio")) {
                ps.setNull(6, Types.NULL);
            } else {
                ps.setString(6, t.getDescripcion());
            }

            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Negocio, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "ERROR, intentelo mas tarde.");
    }

    @Override
    public Mensaje actualizar(Negocio t
    ) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO WHERE IDProveedor = " + t.getIdProveedor() + " AND IDNEGOCIO <>" + t.getIdNegocio() + " AND NOMBRENegocio = '" + t.getNombreNegocio() + "'")) {
            if (rs.next()) {
                return new Mensaje(Message.Tipo.ERROR, "Este negocio ya existe en otro proveedor");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE NEGOCIO SET IDPROVEEDOR = ?, IDTIPOPROVEEDOR = ?, NOMBRENEGOCIO = ?, PRECIOAPROX = ?, DISPONIBLE = ?, DESCRIPCION = ? WHERE IDNEGOCIO = ?")) {
            ps.setInt(1, t.getIdProveedor());
            ps.setInt(2, t.getIdTipoProveedor());
            ps.setString(3, t.getNombreNegocio());
            ps.setInt(4, t.getPrecioAprox());
            ps.setBoolean(5, t.isDisponible());
            if (t.getDescripcion().isEmpty() || t.getDescripcion().equals("Puedes añadir una descripcion del negocio")) {
                ps.setNull(6, Types.NULL);
            } else {
                ps.setString(6, t.getDescripcion());
            }
            ps.setInt(7, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Negocio, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "ERROR, intentelo mas tarde.");
    }

    @Override
    public Mensaje eliminar(Negocio t
    ) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM NEGOCIO WHERE IDNEGOCIO = ?")) {
            ps.setInt(1, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "ERROR, intentelo mas tarde.");
    }

    @Override
    public String yaExiste(Negocio t
    ) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO WHERE IDProveedor = " + t.getIdProveedor() + " AND NOMBRENegocio = '" + t.getNombreNegocio() + "'")) {
            if (rs.next()) {
                return rs.getString(3);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Negocio obtenerNegocioByIdProvAndNombreNeg(int idProveedor, String nombreNegocio
    ) {
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM NEGOCIO WHERE IDPROVEEDOR = ?, AND NOMBRENEGOCIO = ?")) {
            ps.setInt(1, idProveedor);
            ps.setString(2, nombreNegocio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
            }

        } catch (SQLException e) {
            System.err.println("Error obtenerNegocio Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Negocio> obtenerListaByIdProveedor(int idProveedor
    ) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO WHERE IDPROVEEDOR = " + idProveedor)) {
            ArrayList<Negocio> temp = new ArrayList<Negocio>();
            while (rs.next()) {
                temp.add(new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdProveedor Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Negocio> obtenerListaByIdTipoProveedor(int idTipoProveedor
    ) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO WHERE IDTIPOPROVEEDOR = " + idTipoProveedor)) {
            ArrayList<Negocio> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdProveedor Negocio, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Negocio obtenerNegocioByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1* FROM NEGOCIO ORDER BY IDNEGOCIO DESC")) {
            if (rs.next()) {
                return new Negocio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNegocioByLast Negocio, " + e.getMessage());
        }
        return null;
    }

}
