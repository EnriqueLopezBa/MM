package dao;

import Componentes.Sweet_Alert.Message;
import idao.ILugarImagenesDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import modelo.LugarImagenes;

/**
 *
 * @author Enrique
 */
public class LugarImagenesDAOImp implements ILugarImagenesDAO {

    private static LugarImagenesDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private LugarImagenesDAOImp() {
    }

    public static LugarImagenesDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new LugarImagenesDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<LugarImagenes> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<LugarImagenes> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LugarImagenes obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(LugarImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO LUGAR.IMAGENES VALUES(?,?,?,?,?)")) {
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
            System.err.println("Error registrar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(LugarImagenes t) {

        try (PreparedStatement ps = cn.prepareStatement("UPDATE LUGAR.IMAGENES SET IMAGEN = ?, DESCRIPCION = ?, PREDETERMINADA = ?, ID2 = ? WHERE ID2 = ?")) {
            String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
//            t.setId2(id2);

            ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
            ps.setBinaryStream(1, forindex, t.getImagen().length);
            ps.setString(2, t.getDescripcion());
            ps.setBoolean(3, t.isPredeterminada());
            ps.setString(4, id2);
            ps.setString(5, t.getId2());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(LugarImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM LUGAR.IMAGENES WHERE ID2 = ? AND IDNegocio = ?")) {
            ps.setString(1, t.getId2());
            ps.setInt(2, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(LugarImagenes t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR.IMAGENES WHERE idNegocio = " + t.getIdNegocio() + "  and ID2 = '" + t.getId2() + "'")) {
            if (rs.next()) {
                return "Esta imagen ya existe";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarImaagenes, " + e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<LugarImagenes> obtenerListaByIDLugar(int idLugar) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR.IMAGENES WHERE IDNegocio = " + idLugar)) {
            ArrayList<LugarImagenes> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new LugarImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIDLugar, " + e.getMessage());
        }
        return null;
    }

    @Override
    public LugarImagenes obtenerById2(String id2) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGAR.IMAGENES WHERE ID2 = '" + id2 + "'")) {
            if (rs.next()) {
                return new LugarImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getBoolean(5));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<LugarImagenes> obtenerListaByIDCiudad(int idCiudad, String etiquetas) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT LUI.*,(SELECT COUNT(*)  FROM LUGAR.etiquetas LUE  WHERE LUE.idNegocio = LUI.idNegocio AND LUE.idEtiqueta IN ("+ etiquetas +")) AS CDDV FROM LUGAR.IMAGENES LUI\n" +
"                JOIN lugar.imagenes L ON L.idNegocio = LUI.idNegocio\n" +
"				join NEGOCIO.area a on L.idNegocio = a.idNegocio\n" +
"                WHERE a.idCiudad = " + idCiudad + " AND LUI.predeterminada = 1 \n" +
"                 ORDER BY CDDV DESC")) {
            ArrayList<LugarImagenes> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new LugarImagenes(rs.getInt(1), rs.getString(2), rs.getBytes(3), rs.getString(4), true));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIDCiudad LugarImagenes, " + e.getMessage());
        }
        return null;
    }

}
