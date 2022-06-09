package dao;

import Componentes.Sweet_Alert.Message;
import idao.IQuizDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Quiz;

/**
 *
 * @author Enrique
 */
public class QuizDAOImp implements IQuizDAO {

    private static QuizDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private QuizDAOImp() {
    }

    public static QuizDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new QuizDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Quiz> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Quiz> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Quiz obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Quiz t) {

        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO CLIENTE.QUIZ VALUES(?,?,?,?,?)")) {
            ps.setInt(1, t.getIdPregunta());
            ps.setInt(2, t.getIdCliente());
            ps.setInt(3, t.getIdEvento());
            if (t.getRespuesta().isEmpty()) {
                ps.setNull(4, Types.NULL);
            } else {
                ps.setString(4, t.getRespuesta());
            }

            if (t.getOpciones().isEmpty()) {
                ps.setNull(5, Types.NULL);
            } else {
                ps.setString(5, t.getOpciones());
            }

            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Surgió un problema");
        } catch (SQLException e) {
            System.err.println("Error registrar QUIZ, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Quiz t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje eliminar(Quiz t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String yaExiste(Quiz t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Quiz> obtenerListaByIdClienteAndIdEvento(int idCliente, int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE.QUIZ WHERE IDCLIENTE = " + idCliente + " AND IDEVENTO = " + idEvento)) {
            ArrayList<Quiz> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Quiz(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdCliente QUIZ, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Quiz obtenerByIdPreguntaAndIdEvento(int idPregunta, int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE.QUIZ WHERE IDPREGUNTA  = " + idPregunta + " AND IDEVENTO = " + idEvento)) {
            if (rs.next()) {
                return new Quiz(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByIdPreguntaAndIdEvento QUIZ, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje eliminarRespuesta(int idPregunta, int idEvento, int idCliente) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM CLIENTE.QUIZ WHERE IDPREGUNTA = " + idPregunta + " AND IDEVENTO = " + idEvento + " AND IDCLIENTE = "+idCliente)) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Respuesta Eliminada") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminarRespuesta Quiz," + e.getMessage());
        }
          return new Mensaje(Message.Tipo.ERROR, "Error");
    }

}
