package dao;

import Componentes.Sweet_Alert.Message;
import idao.INegocioAreaDAO;
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
import modelo.NegocioArea;

/**
 *
 * @author Enrique
 */
public class NegocioAreaDAOImp implements INegocioAreaDAO {

    private static NegocioAreaDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    public NegocioAreaDAOImp() {
    }

    public static NegocioAreaDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new NegocioAreaDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<NegocioArea> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<NegocioArea> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NegocioArea obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(NegocioArea t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO NEGOCIOAREA VALUES(?,?)")) {
            ps.setInt(1, t.getIdNegocio());
            ps.setInt(2, t.getIdCiudad());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar NEGOCIOAREA, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(NegocioArea t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE NEGOCIOAREA SET  IDCIUDAD = ? WHERE idNegocio = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar NEGOCIOAREA, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(NegocioArea t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM NEGOCIOAREA WHERE IDCIUDAD = ? AND idNegocio = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar NEGOCIOAREA, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public ArrayList<NegocioArea> obtenerListaByIdCiudad(int idCiudad) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIOAREA WHERE IDCIUDAD =" + idCiudad)) {
            ArrayList<NegocioArea> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new NegocioArea(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdCiudad NEGOCIOAREA, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<NegocioArea> obtenerListaByIdNegocio(int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIOAREA WHERE idNegocio =" + idNegocio)) {
            ArrayList<NegocioArea> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new NegocioArea(rs.getInt(1), rs.getInt(2)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdProveedor NEGOCIOAREA, " + e.getMessage());
        }
        return null;
    }

    @Override
    public String yaExiste(NegocioArea t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIOAREA WHERE idNegocio = " + t.getIdNegocio()
                + " AND IDCIUDAD = " + t.getIdCiudad())) {
            if (rs.next()) {
                return t.getIdNegocio() + "";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarEtiqeuetas, " + e.getMessage());
        }
        return "";
    }

    @Override
    public Mensaje registrarLote(ArrayList<NegocioArea> lote) {
        for (NegocioArea et : lote) {
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO NEGOCIOAREA VALUES(?,?)")) {
                ps.setInt(1, et.getIdNegocio());
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
    public Mensaje actualizarLote(ArrayList<NegocioArea> lote, int idProveedor) {
        String ar = "";
        for (NegocioArea et : lote) {
            NegocioArea temp = obtenerByIdCiudadAndIdNegocio(et.getIdCiudad(), et.getIdNegocio());
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
        try (PreparedStatement ps = cn.prepareStatement(" DELETE FROM NEGOCIOAREA WHERE idProveedor = " + idProveedor
                + "AND idCiudad NOT IN(" + ar + ")")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Mensaje(Message.Tipo.OK, "Actualizado correctamente");
    }

    @Override
    public NegocioArea obtenerByIdCiudadAndIdNegocio(int idCiudad, int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIOAREA WHERE idNegocio = " + idNegocio + " and "
                + "idCiudad = " + idCiudad)) {
            if (rs.next()) {
                return new NegocioArea(idNegocio, idCiudad);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByIdCiudadAndIdProveedor ProveedoorArea, " + e.getMessage());
        }
        return null;
    }

}
