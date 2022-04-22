/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import idao.IUsuarioDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
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
//    private Conexion con = Conexion.getInstancia();

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

    @Override
    public ArrayList<Usuario> obtenerListaByCadena(String cadena) {
     
        return null;
    }

    @Override
    public Usuario obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(Usuario t) {
       
        return null;
    }

    @Override
    public Mensaje actualizar(Usuario t) {
       
        return null;
    }

    @Override
    public Mensaje eliminar(Usuario t) {
       
        return null;
    }

    @Override
    public Usuario inicioSesion(String correo, String clave) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM USUARIO WHERE EMAIL = '"+correo+"' AND CLAVE = '"+clave+"'")) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
