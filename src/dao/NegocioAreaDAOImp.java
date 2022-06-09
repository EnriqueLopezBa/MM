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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO NEGOCIO.AREA VALUES(?,?)")) {
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
        try (PreparedStatement ps = cn.prepareStatement("UPDATE NEGOCIO.AREA SET  IDCIUDAD = ? WHERE idNegocio = ?")) {
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
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM NEGOCIO.AREA WHERE IDCIUDAD = ? AND idNegocio = ?")) {
            ps.setInt(1, t.getIdCiudad());
            ps.setInt(2, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar NEGOCIOAREA, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public ArrayList<NegocioArea> obtenerListaByIdCiudadAndTipoProveedor(int idCiudad, int idTipoProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO.AREA N\n"
                + "  JOIN negocio.negocio NE ON\n"
                + "  N.idNegocio = NE.idNegocio\n"
                + "  WHERE IDCIUDAD = "+idCiudad+" AND  NE.idTipoProveedor = "+idTipoProveedor)) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO.AREA WHERE idNegocio =" + idNegocio)) {
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO.AREA WHERE idNegocio = " + t.getIdNegocio()
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
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO NEGOCIO.AREA VALUES(?,?)")) {
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
    public Mensaje actualizarLote(ArrayList<NegocioArea> lote) {
        String ar = "";
        int idNegocio = 0;
        for (NegocioArea et : lote) {
            NegocioArea temp = obtenerByIdCiudadAndIdNegocio(et.getIdCiudad(), et.getIdNegocio());
            ar += et.getIdCiudad() + ",";
            if (temp == null) {
                System.out.println("registrar");
                registrar(et);
            }
            idNegocio = et.getIdNegocio();
        }
        if (!ar.isEmpty()) {
            ar = ar.substring(0, ar.length() - 1);
        } else {
            ar = "0";
        }
        try (PreparedStatement ps = cn.prepareStatement(" DELETE FROM NEGOCIO.AREA WHERE idNegocio = " + idNegocio
                + "AND idCiudad NOT IN(" + ar + ")")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Mensaje(Message.Tipo.OK, "Actualizado correctamente");
    }

    @Override
    public NegocioArea obtenerByIdCiudadAndIdNegocio(int idCiudad, int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM NEGOCIO.AREA WHERE idNegocio = " + idNegocio + " and "
                + "idCiudad = " + idCiudad)) {
            if (rs.next()) {
                return new NegocioArea(idNegocio, idCiudad);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByIdCiudadAndIdProveedor ProveedoorArea, " + e.getMessage());
        }
        return null;
    }

    @Override
    public NegocioArea obtenerNegocioByNombre(String nombre) {
        try (ResultSet rs = Conexion.getInstancia().Consulta(" select n.* from NEGOCIO.AREA n\n"
                + "  join ciudad c on\n"
                + "  n.idCiudad = c.idCiudad\n"
                + "  where ciudad = '" + nombre + "'")) {
            if (rs.next()) {
                return new NegocioArea(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNegocioByNombre NegocioArea, " + e.getMessage());
        }
        return null;
    }

    @Override
    public NegocioArea obtenerNegocioByLast() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT TOP 1* FROM NEGOCIO.AREA ORDER BY IDNEGOCIO DESC")) {
            if (rs.next()) {
                return new NegocioArea(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNegocioByLast NegocioArea," + e.getMessage());
        }
        return null;
    }

}
