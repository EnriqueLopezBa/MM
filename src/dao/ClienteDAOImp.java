package dao;

import Componentes.Sweet_Alert.Message;
import independientes.Conexion;
import idao.IClienteDAO;
import independientes.Constante;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Cliente;

/**
 *
 * @author Enrique
 */
public class ClienteDAOImp implements IClienteDAO {

    private Connection cn = Conexion.getInstancia().getConexion();
//    private Conexion con = Conexion.getInstancia();

    private static ClienteDAOImp instancia;

    private ClienteDAOImp() {
    }

    public static ClienteDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new ClienteDAOImp();
        }
        return instancia;
    }

    @Override
    public Cliente obtenerByID(int id) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE WHERE IDCLIENTE = " + id)) {
            if (rs.next()) {
                return new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerByID, " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cliente> obtenerListaByCadena(String cadena) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM Cliente WHERE "
                + "nombre + ' ' + apellido LIKE '%" + cadena + "%' OR email LIKE '%" + cadena + "%'"
                + " OR telefono LIKE '%" + cadena + "%' OR telefono2 LIKE '%" + cadena + "%'")) {
            ArrayList<Cliente> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error getClienteByBusqueda, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mensaje registrar(Cliente t) {
        //Validar
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente!");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO CLIENTE (nombre, apellido, email, telefono, telefono2)"
                + " VALUES (?,?,?,?,?)")) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getTelefono());
            if (t.getTelefono2().isEmpty()) {
                ps.setNull(5, java.sql.Types.NULL);
            }else{
                ps.setString(5, t.getTelefono2());
            }
            
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado!") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar!");
        } catch (SQLException e) {
            System.err.println("Error registro cliente, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.OK, "Error!");
    }

    @Override
    public Mensaje actualizar(Cliente t) {
        String x = yaExiste(t);
        if (!x.isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, x + " ya existente!");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE cliente SET nombre = ?, apellido = ?, email = ?, telefono = ?, telefono2 = ? where idCliente = ?")) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getTelefono());
             if (t.getTelefono2().isEmpty()) {
                ps.setNull(5, java.sql.Types.NULL);
            }else{
                ps.setString(5, t.getTelefono2());
            }
            ps.setInt(6, t.getIdCliente());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Cliente actualizado!") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar!");
        } catch (SQLException e) {
            System.err.println("Error actualizar cliente, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error!");
    }

    @Override
    public Mensaje eliminar(Cliente t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM cliente WHERE idCliente = ?")) {
            ps.setInt(1, t.getIdCliente());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Cliente eliminado!") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar!");
        } catch (SQLException e) {
            System.err.println("Error eliminar Cliente, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error!");
    }

    @Override
    public String yaExiste(Cliente t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE WHERE idCliente != " + t.getIdCliente() + " and EMAIL = '" + t.getCorreo() + "'")) {
            if (rs.next()) {
                return "Correo";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try (ResultSet rss = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE WHERE idCliente != " + t.getIdCliente() + " and TELEFONO = '" + t.getTelefono() + "'")) {
            if (rss.next()) {
                return "Telefono";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try (ResultSet rsss = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE WHERE idCliente != " + t.getIdCliente() + " and TELEFONO2 = '" + t.getTelefono2() + "'")) {
            if (rsss.next()) {
                return "Telefono 2";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Cliente obtenerClienteActivo() {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM CLIENTE WHERE ACTIVO = 1")) {
            if (rs.next()) {
                return new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerClienteActivo Cliente, " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean setClienteActivoById(int idCliente) {
        desactivarClienteActivo();
        try (PreparedStatement pss = cn.prepareStatement("UPDATE CLIENTE SET ACTIVO = 1 WHERE idCliente = " + idCliente)) {
            return pss.executeUpdate() >= 1;
        } catch (SQLException e) {
            System.err.println("Error setClienteActivoById Cliente, " + e.getMessage());
        }

        return false;
    }

    public void desactivarClienteActivo() {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE cliente SET ACTIVO = 0 WHERE ACTIVO = 1;")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void desactivarClienteTemporal(){
        Constante.removeClienteTemporal();
    }
}
