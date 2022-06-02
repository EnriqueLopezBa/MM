package dao;

import Componentes.Sweet_Alert.Message;
import independientes.Conexion;
import independientes.Mensaje;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import modelo.NegocioImagenes;
import idao.INegocioImagenesDAO;

/**
 *
 * @author Enrique
 */
public class NegocioImagenesDAOImp implements INegocioImagenesDAO {

    private static NegocioImagenesDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private NegocioImagenesDAOImp() {
    }

    public static NegocioImagenesDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new NegocioImagenesDAOImp();
        }
        return instancia;
    }

    @Override
    public Mensaje registrar(NegocioImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO negocioIMAGENES VALUES(?,?,?,?,?)")) {
            if (t.getImagen() != null) {
                String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
                t.setId2(id2);
                String x = yaExiste(t);
                if (!x.isEmpty()) {
                    return new Mensaje(Message.Tipo.ERROR, x);
                }
                ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
                ps.setInt(1, t.getIdNegocio());
                ps.setString(2, id2);
                ps.setBinaryStream(3, forindex, t.getImagen().length);
                ps.setString(4, t.getDescripcion());
                ps.setBoolean(5, t.isPredeterminada());
                return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
            }
        } catch (SQLException e) {
            System.err.println("Error registrar NegocioImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(NegocioImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE negocioIMAGENES SET IMAGEN = ?, DESCRIPCION = ?, PREDETERMINADA = ?, ID2 = ? WHERE ID2 = ?")) {
            String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
//            t.setId2(id2);
            //A traves de un dispaarador se quitan la predeterminada anterior     
            ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
            ps.setBinaryStream(1, forindex, t.getImagen().length);
            ps.setString(2, t.getDescripcion());
            ps.setBoolean(3, t.isPredeterminada());
            ps.setString(4, id2);
            ps.setString(5, t.getId2());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar proveedorImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(NegocioImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM negocioIMAGENES WHERE ID2 = ?")) {
            ps.setString(1, t.getId2());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar ProveedorImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(NegocioImagenes t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM negocioIMAGENES WHERE"
                + " idNegocio = " + t.getIdNegocio() + "  and ID2 = '" + t.getId2() + "'")) {
            if (rs.next()) {
                return "Esta imagen ya existe";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste ProveedorImagenes, " + e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<NegocioImagenes> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<NegocioImagenes> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NegocioImagenes obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<NegocioImagenes> obtenerListabyIdNegocio(int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM negocioIMAGENES WHERE IDNegocio = " + idNegocio)) {
            ArrayList<NegocioImagenes> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new NegocioImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListabyIdProveedor ProveedorImagenes, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<NegocioImagenes> obtenerListabyIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        try (PreparedStatement ps = cn.prepareStatement("SELECT N.* FROM negocioImagenes N\n"
                + "JOIN negocio NE ON\n"
                + "N.idNegocio = NE.idNegocio\n"
                + "JOIN negocioArea P ON\n"
                + "P.idNegocio = NE.idNegocio\n"
                + "WHERE P.idCiudad = ? AND  NE.idTipoProveedor = ? AND PREDETERMINADA = 1;")) {
            ps.setInt(1, idCiudad);
            ps.setInt(2, idTipoProveedor);
            ResultSet rs = ps.executeQuery();
            ArrayList<NegocioImagenes> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new NegocioImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListabyIdCiudad proveedorImagenes, " + e.getMessage());
        }
        return null;
    }

    @Override
    public NegocioImagenes obtenerByID2(String id2) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM negocioIMAGENES WHERE ID2 = '" + id2 + "'")) {
            if (rs.next()) {
                return new NegocioImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID ProveedorImagenes, " + e.getMessage());
        }
        return null;
    }

}
