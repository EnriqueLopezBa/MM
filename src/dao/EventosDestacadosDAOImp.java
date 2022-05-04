package dao;

import Componentes.Sweet_Alert.Message;
import idao.IEventosDestacadosDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import modelo.EventosDestacados;

/**
 *
 * @author Enrique
 */
public class EventosDestacadosDAOImp implements IEventosDestacadosDAO {

    private static EventosDestacadosDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private EventosDestacadosDAOImp() {
    }

    public static EventosDestacadosDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new EventosDestacadosDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<EventosDestacados> obtenerLista() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTOSDESTACADOS")) {
            ArrayList<EventosDestacados> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new EventosDestacados(rs.getString(1), rs.getInt(2), rs.getBytes(3)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerLista EventosDestacados, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<EventosDestacados> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventosDestacados obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(EventosDestacados t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO EventosDestacados VALUES(?,?,?)")) {
            if (t.getImagen() != null) {
                String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
                t.setIdEventoDestacado(id2);
                String x = yaExiste(t);
                if (!x.isEmpty()) {
                    return new Mensaje(Message.Tipo.ERROR, x);
                }
                ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
                ps.setString(1, t.getIdEventoDestacado());
                ps.setInt(2, t.getIdEvento());
                ps.setBinaryStream(3, forindex, t.getImagen().length);
                return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
            }
        } catch (SQLException e) {
            System.err.println("Error registrar EventosDestacados, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(EventosDestacados t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE EventosDestacados SET IMAGEN = ?, idEvento = ? WHERE idEventoDestacado = ?")) {
            String id2 = UUID.nameUUIDFromBytes(t.getImagen()).toString().toUpperCase();
            t.setIdEventoDestacado(id2);

            ByteArrayInputStream forindex = new ByteArrayInputStream(t.getImagen());
            ps.setBinaryStream(1, forindex, t.getImagen().length);
            ps.setInt(2, t.getIdEvento());
            ps.setString(3, id2);
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar EventosDestacados, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(EventosDestacados t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM EventosDestacados WHERE ID2 = ?")) {
            ps.setString(1, t.getIdEventoDestacado());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar EventosDestacados, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(EventosDestacados t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EventosDestacados WHERE idEvento = " + t.getIdEvento() + "  and idEventoDestacado = '" + t.getIdEventoDestacado() + "'")) {
            if (rs.next()) {
                return "Esta imagen ya existe";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste LugarImaagenes, " + e.getMessage());
        }
        return "";
    }

    @Override
    public EventosDestacados obtenerByIdEventoDestacado(String idEventoDestacado) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTOSDESTACADOS WHERE IDEVENTODESTACADO = '" + idEventoDestacado + "'")) {
            if (rs.next()) {
                return new EventosDestacados(rs.getString(1), rs.getInt(2), rs.getBytes(3));
            }

        } catch (SQLException e) {
            System.err.println("Error obtenerByIdEventoDestacado EventoosDestacados, " + e.getMessage());
        }
        return null;
    }

    @Override
    public EventosDestacados obtenerByIdEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM EVENTOSDESTACADOS WHERE idEvento = '" + idEvento + "'")) {
            if (rs.next()) {
                return new EventosDestacados(rs.getString(1), rs.getInt(2), rs.getBytes(3));
            }

        } catch (SQLException e) {
            System.err.println("Error obtenerByIdEvento EventoosDestacados, " + e.getMessage());
        }
        return null;
    }

}
