package dao;

import Componentes.Sweet_Alert.Message;
import idao.IAbonoDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Abono;

/**
 *
 * @author Enrique
 */
public class AbonoDAOImp implements IAbonoDAO {

    private static AbonoDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private AbonoDAOImp() {
    }

    public static AbonoDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new AbonoDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Abono> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Abono> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Abono obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Abono t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO ABONOS VALUES(?,?,?,?)")) {
            ps.setInt(1, t.getIdCliente());
            ps.setInt(2, t.getIdEvento());
            ps.setInt(3, t.getImporte());
            ps.setDate(4, new Date(t.getFecha().getTime()));
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registar Abono, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Abono t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE ")) {
            
        } catch (SQLException e) {
            System.err.println("Error actualizar Abono, " + e.getMessage());
        }    
         return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Abono t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM ABONOS WHERE IDABONO = " + t.getIdAbono())) {
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public String yaExiste(Abono t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Abono> obtenerListaByIdEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM ABONOS WHERE IDEVENTO = " +idEvento + " ORDER BY FECHA")) {
            ArrayList<Abono> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new Abono(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }   
        return null;
    }

    @Override
    public int obtenerCantidadADeber(int idCliente, int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT (e.precioEvento - SUM(a.Importe)) FROM abonos a JOIN evento e ON a.idEvento = e.idEvento"
                + " WHERE a.idCliente = "+idCliente+" AND a.idEvento = "+idEvento+" GROUP BY e.precioEvento")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerCantidadADeber AbonoCliente," + e.getMessage());
        }       
        return -1;
    }

}
