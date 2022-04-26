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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO LUGARIMAGENES VALUES(?,?,?,?,?)")) {
            if (t.getImagen() != null) {
                String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
                t.setId2(id2);
                String x = yaExiste(t);
                if (!x.isEmpty()) {
                    return new Mensaje(Message.Tipo.ERROR, x);
                }
                ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
                ps.setInt(1, t.getIdLugar());
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

        try (PreparedStatement ps = cn.prepareStatement("UPDATE LUGARIMAGENES SET IMAGEN = ?, DESCRIPCION = ?, PREDETERMINADA = ? WHERE ID2 = ?")) {
            String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
            t.setId2(id2);
            //Quitar la predeterminada anteror
            if (t.isPredeterminada()) {
                try (PreparedStatement pss = cn.prepareStatement("update lugarImagenes set predeterminada = 0 where idLugar = ?")) {
                    pss.setInt(1, t.getIdLugar());
                    pss.execute();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
            ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
            ps.setBinaryStream(1, forindex, t.getImagen().length);
            ps.setString(2, t.getDescripcion());
            ps.setInt(3, (t.isPredeterminada()) ? 1 : 0);
            ps.setString(4, id2);
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(LugarImagenes t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM LUGARIMAGENES WHERE ID2 = ?")) {
            ps.setString(1, t.getId2());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar LugarImagenes, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(LugarImagenes t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARIMAGENES WHERE idLugar = " + t.getIdLugar() + "  and ID2 = '" + t.getId2() + "'")) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARIMAGENES WHERE IDLUGAR = " + idLugar)) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARIMAGENES WHERE ID2 = '" + id2 + "'")) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT LUI.*, \n"
                + "  (\n"
                + "  SELECT COUNT(*) \n"
                + "  FROM LugarEtiquetas LUE\n"
                + "  WHERE LUE.idLugar = LUI.idLugar AND LUE.idEtiqueta IN ("+etiquetas+")\n"
                + "  ) AS CDDV\n"
                + "  FROM lugarImagenes LUI\n"
                + "  JOIN lugar L ON\n"
                + "  L.idLugar = LUI.idLugar\n"
                + "  WHERE L.idCiudad = "+idCiudad+" AND LUI.predeterminada = 1 \n"
                + "  ORDER BY CDDV DESC")) {
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
