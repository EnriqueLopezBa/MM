package dao;

import Componentes.Sweet_Alert.Message;
import controlador.ControladorEvento;
import idao.ICotizacionDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Cotizacion;
import modelo.Evento;

/**
 *
 * @author Enrique
 */
public class CotizacionDAOImp implements ICotizacionDAO {

    private Connection cn = Conexion.getInstancia().getConexion();

    private static CotizacionDAOImp instancia;

    private CotizacionDAOImp() {
    }

    public static CotizacionDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new CotizacionDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Cotizacion> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByCadena(String cadena) {
        return null;
    }

    @Override
    public Cotizacion obtenerByID(int id) {

        return null;
    }

    @Override
    public Mensaje registrar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO EVENTO.EVENTO.COTIZACION VALUES(?,?,?,?,?,?)")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdNegocio());
            ps.setInt(3, t.getCotizacion());
            ps.setString(4, t.getComentario());
            ps.setInt(5, t.getNumCotizacion());
            ps.setDate(6, new java.sql.Date(t.getFechaCotizacion().getTime()));
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Cotizacion, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE EVENTO.COTIZACION SET EVENTO.COTIZACION = ?, comentario = ?, numCotizacion = ?, fechaCotizacion = ? WHERE IDEVENTO = ? AND IDPROVEEDOR = ?")) {
            ps.setInt(1, t.getCotizacion());
            ps.setString(2, t.getComentario());
            ps.setInt(3, t.getNumCotizacion());
            ps.setDate(4, new java.sql.Date(t.getFechaCotizacion().getTime()));
            ps.setInt(5, t.getIdEvento());
            ps.setInt(6, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("error actualizar Cotizacion, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Cotizacion t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM EVENTO.COTIZACION WHERE IDEVENTO = ? AND IDNegocio = ?")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");

    }

    @Override
    public String yaExiste(Cotizacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByIDEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.COTIZACION WHERE IDEVENTO = " + idEvento)) {
            ArrayList<Cotizacion> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getBoolean(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Cotizacion, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrarLote(ArrayList<Cotizacion> lista) {
        for (Cotizacion cotizacion : lista) {
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO EVENTO.COTIZACION VALUES(?,?,?,?,?,?,?)")) {
                ps.setInt(1, cotizacion.getIdEvento());
                ps.setInt(2, cotizacion.getIdNegocio());
                ps.setInt(3, cotizacion.getCotizacion());
                if (cotizacion.getComentario().isEmpty() || cotizacion.getComentario().equals("Aqui podra poner un mensaje/descripcion/comentario a cada proveedor que seleccione")) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, cotizacion.getComentario());
                }
                ps.setInt(5, cotizacion.getNumCotizacion());
                ps.setDate(6, new java.sql.Date(cotizacion.getFechaCotizacion().getTime()));
                ps.setBoolean(7, false);
                ps.execute();
            } catch (SQLException e) {
                System.err.println("Error registrarLote Cotizacion," + e.getMessage());
            }
        }
        return new Mensaje(Message.Tipo.OK, "Registrado correctamete");
    }

    @Override
    public Mensaje actualizarLote(ArrayList<Cotizacion> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByNumCotizacionAndIdClienteAndIdEvento(int numCotizacion, int idCliente, int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT C.* FROM EVENTO.COTIZACION C\n"
                + "   JOIN evento.evento E ON\n"
                + "   E.idEvento = C.idEvento\n"
                + "    WHERE E.idCliente = " + idCliente
                + "	 AND C.NumCotizacion = " + numCotizacion
                + "	 AND E.idEvento = " + idEvento)) {
            ArrayList<Cotizacion> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getBoolean(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Cotizacion, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByIDEventoAndNumCotizacion(int idEvento, int numCotizacion) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.COTIZACION WHERE IDEVENTO = " + idEvento + " AND numCotizacion = " + numCotizacion)) {
            ArrayList<Cotizacion> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getBoolean(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Cotizacion, " + e.getMessage());
        }
        return null;
    }

    @Override
    public int obtenerNumNuevaCotizacion(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta(" SELECT MAX(NumCotizacion)+1 FROM EVENTO.COTIZACION WHERE idEvento = " + idEvento)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNumNuevaCotizacion Cotiazcion, " + e.getMessage());
        }
        return -1;
    }

    @Override
    public String obtenerTotalCotizacionByIDEventoAndNumCotizacion(int idEvento, int numCotizacion) {
        try (ResultSet rs = Conexion.getInstancia().Consulta(" SELECT SUM(cotizacion) FROM EVENTO.COTIZACION WHERE idEvento = " + idEvento + " AND NumCotizacion = " + numCotizacion)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNumNuevaCotizacion Cotiazcion, " + e.getMessage());
        }
        return "0";
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByIDEventoAndLastCotizacion(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.COTIZACION WHERE IDEVENTO = " + idEvento + " AND NUMEVENTO.COTIZACION = (SELECT MAX(NumCotizacion) FROM cotizacion WHERE idEvento = " + idEvento + ")")) {
            ArrayList<Cotizacion> temp = new ArrayList<>();
            while (rs.next()) {

                temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getBoolean(7)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerNumNuevaCotizacion Cotiazcion, " + e.getMessage());
        }
        return null;
    }

    @Override
    public int obtenerLastCotizacion(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT MAX(COTIZACION) FROM EVENTO.COTIZACION WHERE IDEVENTO = " + idEvento)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("obtenerLastCotizacion Cotizacion," + e.getMessage());
        }
        return -1;
    }

    @Override
    public ArrayList<Cotizacion> obtenerListaByIdCliete(int idCliente) {
        ArrayList<Cotizacion> temp = new ArrayList<>();
        for (Evento ev : ControladorEvento.getInstancia().obtenerEventoByIDCliente(idCliente)) {
            try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO.COTIZACION WHERE idEvento = " + ev.getIdEvento())) {
                while (rs.next()) {
                    temp.add(new Cotizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getBoolean(7)));
                }
            } catch (SQLException e) {
                System.err.println("obtenerLastCotizacion Cotizacion," + e.getMessage());
            }
        }
        return temp;

    }

    @Override
    public void setCotizacionFinal(int idEvento, int numCotizacion) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE EVENTO.COTIZACION SET cotFinal = 1\n"
                + "  WHERE idEvento = " + idEvento + " AND NumCotizacion = " + numCotizacion)) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error setCotizacionFinal Cotizacion," + e.getMessage());
        }
    }

    @Override
    public Object obtenerTotalCotizacionByIDEventoAndisCotFinal(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT SUM(cotizacion) FROM EVENTO.COTIZACION WHERE cotFinal = 1 AND idEvento = " + idEvento)) {
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerTotalCotizacionByIDEventoAndisCotFinal Cotizacion, " + e.getMessage());
        }
        return null;
    }

}
