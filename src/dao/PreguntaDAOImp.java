package dao;

import Componentes.Sweet_Alert.Message;
import idao.IPreguntaDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Pregunta;

/**
 *
 * @author Enrique
 */
public class PreguntaDAOImp implements IPreguntaDAO {

    private static PreguntaDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private PreguntaDAOImp() {
    }

    public static PreguntaDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new PreguntaDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Pregunta> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pregunta> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pregunta obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PREGUNTA WHERE IDPREGUNTA = " + id)) {
            if (rs.next()) {
                return new Pregunta(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getString(4));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID Pregunta, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(Pregunta t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PREGUNTA VALUES(?,?,?)")) {
            ps.setString(1, t.getPregunta());
            ps.setBoolean(2, t.isEscuestaSatisfaccion());
            ps.setString(3, t.getOpciones());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Pregunta no guardada");
        } catch (SQLException e) {
            System.err.println("Error registrar Pregunta, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje eliminar(Pregunta t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM PREGUNTA WHERE IDPREGUNTA = " + t.getIdPregunta())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al borrar");
        } catch (SQLException e) {
            System.err.println("Error eliminar Pregunta, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pregunta> obtenerListaByCadenaAndIsEncuesta(String cadena, boolean encuesta) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM pregunta p WHERE "
                + "p.escuestaSatisfaccion = " + ((encuesta) ? "1" : "0") + " "
                + "AND p.pregunta LIKE '%" + cadena + "%'")) {
            ArrayList<Pregunta> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Pregunta(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getString(4)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadenaAndIsEncuesta pregunta, " + e.getMessage());
        }
        return null;
    }

}
