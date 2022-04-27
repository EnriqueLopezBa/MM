package dao;

import Componentes.Sweet_Alert.Message;
import idao.ILugarEtiquetasDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.LugarEtiquetas;

/**
 *
 * @author Enrique
 */
public class LugarEtiquetasDAOImp implements ILugarEtiquetasDAO {

    private static LugarEtiquetasDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private LugarEtiquetasDAOImp() {
    }

    public static LugarEtiquetasDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new LugarEtiquetasDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<LugarEtiquetas> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<LugarEtiquetas> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LugarEtiquetas obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARETIQUETAS FROM IDETIQUETA = " + id)) {
            if (rs.next()) {
                return new LugarEtiquetas(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerLista LugarEtiquetas, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(LugarEtiquetas t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO LUGARETIQUETAS VALUES(?,?)")) {
            ps.setInt(1, t.getIdEtiqueta());
            ps.setInt(2, t.getIdLugar());
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error registrar LugarEtiqueta, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.OK, "Registrado");
    }

    @Override
    public Mensaje actualizar(LugarEtiquetas t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje eliminar(LugarEtiquetas t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM LUGARETIQUETAS WHERE IDETIQUETA = ? AND IDLUGAR = ?")) {
            ps.setInt(1, t.getIdEtiqueta());
            ps.setInt(2, t.getIdLugar());
            ps.execute();
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar LugarEtiquetas, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(LugarEtiquetas t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARETIQUETAS WHERE IDETIQUETA = " + t.getIdEtiqueta()
                + " AND idLUGAR = " + t.getIdLugar())) {
            if (rs.next()) {
                return t.getIdEtiqueta() + "";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarEtiqeuetas, " + e.getMessage());
        }
        return "";
    }

    @Override
    public Mensaje registrarLote(ArrayList<LugarEtiquetas> lote) {
        for (LugarEtiquetas et : lote) {
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO LUGARETIQUETAS VALUES(?,?)")) {
                ps.setInt(1, et.getIdEtiqueta());
                ps.setInt(2, et.getIdLugar());
                ps.execute();
            } catch (SQLException e) {
                System.err.println("Error registroLote LugarEtiquetas, " + e.getMessage());
                return null;
            }
        }
        return new Mensaje(Message.Tipo.OK, "Registrado correctamente");
    }

    @Override
    public Mensaje actualizarLote(ArrayList<LugarEtiquetas> lote, int idLugar) {

        ArrayList<LugarEtiquetas> temp = obtenerEtiquetasByIDLugar(idLugar);
        for (LugarEtiquetas l : temp) {
            if (!lote.contains(l)) {
                eliminar(l);
            }
        }
        for (LugarEtiquetas et : lote) {
            if (!yaExiste(et).isEmpty()) {
                continue;
            }
            registrar(et);
        }

        return new Mensaje(Message.Tipo.OK, "Registrado correctamente");
    }

    @Override
    public ArrayList<LugarEtiquetas> obtenerEtiquetasByIDLugar(int idLugar) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM LUGARETIQUETAS WHERE IDLUGAR = " + idLugar)) {
            ArrayList<LugarEtiquetas> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new LugarEtiquetas(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerLista LugarEtiqeuetas, " + e.getMessage());
        }
        return null;
    }

}
