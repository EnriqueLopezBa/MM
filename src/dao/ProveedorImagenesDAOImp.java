package dao;

import Componentes.Sweet_Alert.Message;
import idao.IProveedorImagenesDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import modelo.ProveedorImagenes;

/**
 *
 * @author Enrique
 */
public class ProveedorImagenesDAOImp implements IProveedorImagenesDAO {
    
    private static ProveedorImagenesDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();
    
    private ProveedorImagenesDAOImp() {
    }
    
    public static ProveedorImagenesDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new ProveedorImagenesDAOImp();
        }
        return instancia;
    }
    
    @Override
    public Mensaje registrar(ProveedorImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO LUGARIMAGENES VALUES(?,?,?,?,?)")) {
            if (t.getImagen() != null) {
                String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
                t.setId2(id2);
                String x = yaExiste(t);
                if (!x.isEmpty()) {
                    return new Mensaje(Message.Tipo.ERROR, x);
                }
                ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
                ps.setInt(1, t.getIdProveedor());
                ps.setString(2, id2);
                ps.setBinaryStream(3, forindex, t.getImagen().length);
                ps.setString(4, t.getDescripcion());
                ps.setBoolean(5, t.isPredeterminada());
                return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
            }
        } catch (SQLException e) {
            System.err.println("Error registrar proveedorImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public Mensaje actualizar(ProveedorImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE LUGARIMAGENES SET IMAGEN = ?, DESCRIPCION = ?, PREDETERMINADA = ? WHERE ID2 = ?")) {
            String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
            t.setId2(id2);
            //A traves de un dispaarador se quitan la predeterminada anterior     
            ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
            ps.setBinaryStream(1, forindex, t.getImagen().length);
            ps.setString(2, t.getDescripcion());
            ps.setInt(3, (t.isPredeterminada()) ? 1 : 0);
            ps.setString(4, id2);
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar proveedorImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public Mensaje eliminar(ProveedorImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM proveedorIMAGENES WHERE ID2 = ?")) {
            ps.setString(1, t.getId2());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }
    
    @Override
    public String yaExiste(ProveedorImagenes t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORIMAGENES WHERE"
                + " idProveedor = " + t.getIdProveedor() + "  and ID2 = '" + t.getId2() + "'")) {
            if (rs.next()) {
                return "Esta imagen ya existe";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarImaagenes, " + e.getMessage());
        }
        return "";
    }
    
    @Override
    public ArrayList<ProveedorImagenes> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<ProveedorImagenes> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ProveedorImagenes obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<ProveedorImagenes> obtenerListabyIdProveedor(int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDORIMAGENES WHERE IDPROVEEDOR = " + idProveedor)) {
            ArrayList<ProveedorImagenes> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListabyIdProveedor ProveedorImagenes, " + e.getMessage());
        }        
        return null;
    }
    
    @Override
    public ArrayList<ProveedorImagenes> obtenerListabyIdCiudad(int idCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT\n"
                + "	proveedorImagenes.*\n"
                + "FROM\n"
                + "	dbo.proveedorImagenes\n"
                + "	INNER JOIN\n"
                + "	dbo.proveedorArea\n"
                + "	ON \n"
                + "		proveedorImagenes.idProveedor = proveedorArea.idProveedor\n"
                + "WHERE\n"
                + "	proveedorArea.idCiudad = "+ idCiudad)) {
            ArrayList<ProveedorImagenes> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new ProveedorImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListabyIdCiudad proveedorImagenes, " + e.getMessage());
        }      
        return null;
    }
    
}
