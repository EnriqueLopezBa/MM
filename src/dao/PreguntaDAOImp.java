package dao;

import idao.IPreguntaDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Pregunta;

/**
 *
 * @author Enrique
 */
public class PreguntaDAOImp implements IPreguntaDAO{

    private static PreguntaDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();
    private PreguntaDAOImp() {
    }
    
    public static PreguntaDAOImp getInstancia(){
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje actualizar(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje eliminar(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String yaExiste(Pregunta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pregunta> obtenerListaByCadenaAndIsEncuesta(String cadena, boolean encuesta) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM pregunta p WHERE "
                + "p.escuestaSatisfaccion = "+((encuesta)?1:0)+" "
                + "AND p.pregunta LIKE '%"+cadena+"%'")) {
            ArrayList<Pregunta> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new Pregunta(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getString(4)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadenaAndIsEncuesta pregunta, " + e.getMessage());
        }      
        return null;
    }
    
}
