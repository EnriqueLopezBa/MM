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
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
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
    public String yaExiste(ProveedorArea t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDPROVEEDOR =" + t.getIdProveedor() + " "
                + "AND IDCIUDAD = " + t.getIdCiudad())) {
            if (rs.next()) {
                return "Proveedor en esta ciudad";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<ProveedorArea> obtenerListaByIdCiudad(int idCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORAREA WHERE IDCIUDAD ="+idCiudad)) {
            ArrayList<ProveedorArea> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new ProveedorArea(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdCiudad ProveedorArea, "+ e.getMessage());
        }        
        return null;
    }

}
