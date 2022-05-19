/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Componentes.Sweet_Alert.Message;
import idao.IUsuarioDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Usuario;

/**
 *
 * @author Enrique
 */
public class UsuarioDAOImp implements IUsuarioDAO {

    private Connection cn = Conexion.getInstancia().getConexion();

    private static UsuarioDAOImp instancia;

    private UsuarioDAOImp() {
    }

    public static UsuarioDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<Usuario> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String sql(String cad) {
        return "%" + cad + "%";
    }

    @Override
    public ArrayList<Usuario> obtenerListaByCadena(String cadena) {
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM usuario WHERE nombre LIKE ? AND apellido LIKE ? AND email LIKE ?")) {
            ps.setString(1, sql(cadena));
            ps.setString(2, sql(cadena));
            ps.setString(3, sql(cadena));
            ResultSet rs = ps.executeQuery();
            ArrayList<Usuario> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new Usuario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByCadena Usuario, " + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Usuario t) {
        if (!yaExiste(t).isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, "Ya existe");
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO USUARIO VALUES(?,?,?,?,?)")) {
            ps.setInt(1, t.getIdTipoUsuario());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellido());
            ps.setString(4, t.getCorreo());
            ps.setString(5, t.getClave());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");
        } catch (SQLException e) {
            System.err.println("Error registrar Usuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(Usuario t) {
        if (!yaExiste(t).isEmpty()) {
            return new Mensaje(Message.Tipo.ERROR, "Ya existe");
        }
        try (PreparedStatement ps = cn.prepareStatement("UPDATE USUARIO SET IDTIPOUSUARIO = ?, NOMBRE = ?, APELLIDO = ?, EMAIL = ?, CLAVE = ? WHERE IDUSUARIO = ?")) {
            ps.setInt(1, t.getIdTipoUsuario());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellido());
            ps.setString(4, t.getCorreo());
            ps.setString(5, t.getClave());
            ps.setInt(6, t.getIdSuario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar Usuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje eliminar(Usuario t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM USUARIO WHERE IDUSUARIO = ?")) {
            ps.setInt(1, t.getIdSuario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar Usuario, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Usuario inicioSesion(String correo, String clave) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM USUARIO WHERE EMAIL = '" + correo + "' AND CLAVE = '" + clave + "'")) {
            if (rs.next()) {
                Usuario usu = new Usuario();
                usu.setIdSuario(rs.getInt(1));
                usu.setIdTipoUsuario(rs.getInt(2));
                usu.setNombre(rs.getString(3));
                usu.setApellido(rs.getString(4));
                return usu;
            }
        } catch (SQLException e) {
            System.err.println("Error InicioSesion, " + e.getMessage());
        }
        return null;
    }

    @Override
    public String yaExiste(Usuario t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM USUARIO WHERE IDUSUARIO != "+t.getIdSuario()+" AND  EMAIL = '" + t.getCorreo() + "'")) {
            if (rs.next()) {
                return "Ya existe";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste Usuario, " + e.getMessage());
        }
        return "";
    }

}
