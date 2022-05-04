package dao;

import Componentes.Sweet_Alert.Message;
import idao.IEventoDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Evento;

/**
 *
 * @author Enrique
 */
public class EventoDAOImp implements IEventoDAO {

    private static EventoDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private EventoDAOImp() {
    }

    public static EventoDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new EventoDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Evento> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Evento> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM F_EVENTO('" + cadena + "')")) {
            ArrayList<Evento> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Evento(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getInt(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10)));

            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena Evento, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Evento obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Evento t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existe");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO EVENTO(idCliente, idTipoEvento, idLugar, fechaProgramada, noInvitados, presupuesto, estilo, nombreEvento, precioEvento) VALUES(?,?,?,?,?,?,?,?,?)")) {
            ps.setInt(1, t.getIdCliente());
            ps.setInt(2, t.getIdTipoEvento());
            ps.setInt(3, t.getIdLugar());
            ps.setDate(4, new java.sql.Date(t.getFecha().getTime()));
            ps.setInt(5, t.getNoInvitados());
            ps.setInt(6, t.getPresupuesto());
            ps.setString(7, t.getEstilo());
            ps.setString(8, t.getNombreEvento());
            ps.setInt(9, t.getPrecioFinal());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Evento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Evento t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE evento SET idCliente = ?, idTipoEvento = ?,"
                + " idLugar = ?, fechaProgramada = ?, noInvitados = ?, presupuesto = ?,"
                + " estilo = ?, nombreEvento = ?, precioEvento = ? WHERE idEvento = ?")) {
            ps.setInt(1, t.getIdCliente());
            ps.setInt(2, t.getIdTipoEvento());
            ps.setInt(3, t.getIdLugar());
            ps.setDate(4, new java.sql.Date(t.getFecha().getTime()));
            ps.setInt(5, t.getNoInvitados());
            ps.setInt(6, t.getPresupuesto());
            ps.setString(7, t.getEstilo());
            ps.setString(8, t.getNombreEvento());
            ps.setInt(9, t.getPrecioFinal());
            ps.setInt(10, t.getIdEvento());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Evento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Evento t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM EVENTO WHERE IDEVENTO = " + t.getIdEvento())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar Evento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Evento t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO WHERE IDEVENTO != " + t.getIdEvento()
                + " and idCliente = " + t.getIdCliente() + " and nombreEvento = '" + t.getNombreEvento() + "'")) {
            if (rs.next()) {
                return t.getNombreEvento();
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Evento, " + e.getMessage());
        }
        return "";
    }

    @Override
    public ArrayList<Evento> obtenerEventoByIDCliente(int idCliente) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTO WHERE IDCLIENTE = " + idCliente)) {
            ArrayList<Evento> temp = new ArrayList<>();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setIdEvento(rs.getInt(1));
                evento.setIdCliente(rs.getInt(2));
                evento.setIdTipoEvento(rs.getInt(3));
                evento.setIdLugar(rs.getInt(4));
                evento.setFecha(rs.getDate(5));
                evento.setNoInvitados(rs.getInt(6));
                evento.setPresupuesto(rs.getInt(7));
                evento.setEstilo(rs.getString(8));
                evento.setNombreEvento(rs.getString(9));
                evento.setPrecioFinal(rs.getInt(10));
                temp.add(evento);
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerEventoByIDCliente Evento, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Evento> obtenerEventoByAnio(int anio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM evento e WHERE e.fechaProgramada LIKE '%" + anio + "%'")) {
            ArrayList<Evento> temp = new ArrayList<>();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setIdEvento(rs.getInt(1));
                evento.setIdCliente(rs.getInt(2));
                evento.setIdTipoEvento(rs.getInt(3));
                evento.setIdLugar(rs.getInt(4));
                evento.setFecha(rs.getDate(5));
                evento.setNoInvitados(rs.getInt(6));
                evento.setPresupuesto(rs.getInt(7));
                evento.setEstilo(rs.getString(8));
                evento.setNombreEvento(rs.getString(9));
                evento.setPrecioFinal(rs.getInt(10));
                temp.add(evento);
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerEventoByAnio Evento," + e.getMessage());
        }
        return null;
    }

}
