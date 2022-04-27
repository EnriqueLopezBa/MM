package dao;

import Componentes.Sweet_Alert.Message;
import idao.IProveedorAreaDAO;
import idao.IProveedorDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import modelo.Proveedor;
import modelo.ProveedorArea;

/**
 *
 * @author Enrique
 */
public class ProveedorAreaDAOImp implements IProveedorAreaDAO {

    private static ProveedorAreaDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    public ProveedorAreaDAOImp() {
    }

    public static ProveedorAreaDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new ProveedorAreaDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<ProveedorArea> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ProveedorArea> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProveedorArea obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(ProveedorArea t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDORAREA VALUES(?,?)")) {
            ps.setInt(1, t.getIdProveedor());
            ps.setInt(2, t.getIdCiudad());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar ProveedorArea, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(ProveedorArea t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE PROVEEDORAREA SET  IDCIUDAD = ? WHERE IDPROVEEDOR = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar ProveedorArea, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(ProveedorArea t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM PROVEEDORAREA WHERE IDCIUDAD = ? AND IDPROVEEDOR = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdProveedor());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar ProveedorArea, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDCIUDAD =" + idCiudad)) {
            ArrayList<ProveedorArea> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorArea(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdCiudad ProveedorArea, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ProveedorArea> obtenerListaByIdProveedor(int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDPROVEEDOR =" + idProveedor)) {
            ArrayList<ProveedorArea> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorArea(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdProveedor ProveedorArea, " + e.getMessage());
        }
        return null;
    }

    @Override
    public String yaExiste(ProveedorArea t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDPROVEEDOR = " + t.getIdProveedor()
                + " AND IDCIUDAD = " + t.getIdCiudad())) {
            if (rs.next()) {
                return t.getIdProveedor() + "";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarEtiqeuetas, " + e.getMessage());
        }
        return "";
    }

    @Override
    public Mensaje registrarLote(ArrayList<ProveedorArea> lote) {
        for (ProveedorArea et : lote) {
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDORAREA VALUES(?,?)")) {
                ps.setInt(1, et.getIdProveedor());
                ps.setInt(2, et.getIdCiudad());
                ps.execute();
            } catch (SQLException e) {
                System.err.println("Error registroLote LugarEtiquetas, " + e.getMessage());
                return null;
            }
        }
        return new Mensaje(Message.Tipo.OK, "Registrado correctamente");
    }

    @Override
    public Mensaje actualizarLote(ArrayList<ProveedorArea> lote, int idProveedor) {
        String ar = "";
        for (ProveedorArea et : lote) {
            ProveedorArea temp = obtenerByIdCiudadAndIdProveedor(et.getIdCiudad(), et.getIdProveedor());
            ar += et.getIdCiudad() + ",";
            if (temp == null) {
                System.out.println("registrar");
                registrar(et);
            }
        }
        if (!ar.isEmpty()) {
            ar = ar.substring(0, ar.length() - 1);
        } else {
            ar = "0";
        }
        try (PreparedStatement ps = cn.prepareStatement(" DELETE FROM proveedorArea WHERE idProveedor = " + idProveedor
                + "AND idCiudad NOT IN(" + ar + ")")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Mensaje(Message.Tipo.OK, "Actualizado correctamente");
    }

    @Override
    public ProveedorArea obtenerByIdCiudadAndIdProveedor(int idCiudad, int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDPROVEEDOR = " + idProveedor + " and "
                + "idCiudad = " + idCiudad)) {
            if (rs.next()) {
                return new ProveedorArea(idProveedor, idCiudad);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByIdCiudadAndIdProveedor ProveedoorArea, " + e.getMessage());
        }
        return null;
    }

}
