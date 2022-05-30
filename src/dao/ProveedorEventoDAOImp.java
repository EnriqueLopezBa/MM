package dao;

import Componentes.Sweet_Alert.Message;
import idao.IProveedorEventoDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Proveedor;
import modelo.ProveedorEvento;

/**
 *
 * @author Enrique
 */
public class ProveedorEventoDAOImp implements IProveedorEventoDAO {

    private static ProveedorEventoDAOImp instancia;
    private Connection cn = Conexion.getInstancia().getConexion();

    private ProveedorEventoDAOImp() {
    }

    public static ProveedorEventoDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new ProveedorEventoDAOImp();
        }
        return instancia;
    }

    @Override
    public ArrayList<ProveedorEvento> obtenerLista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ProveedorEvento> obtenerListaByCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProveedorEvento obtenerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje registrar(ProveedorEvento t) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDORESEVENTO VALUES(?,?,?,?)")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdProveedor());
            ps.setDate(3, new java.sql.Date(t.getHoraInicio().getTime()));
            ps.setDate(4, new java.sql.Date(t.getHoraFinal().getTime()));
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");

        } catch (SQLException e) {
            System.err.println("Error registrar ProveedorEvento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(ProveedorEvento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensaje eliminar(ProveedorEvento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String yaExiste(ProveedorEvento t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO ="+t.getIdEvento()
        +" and idProveedor = "+t.getIdProveedor())) {
            if (rs.next()) {
                return "Proveedor repetido";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste ProveederEvennto, " + e.getMessage());
        }        
        return "";
    }

    @Override
    public Mensaje registrarLote(ArrayList<ProveedorEvento> loteProveedores) {
        for (ProveedorEvento pro : loteProveedores) {
            if (!yaExiste(pro).isEmpty()) {
                continue;
            }
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDOREVENTO VALUES(?,?,?,?,?)")) {
                ps.setInt(1, pro.getIdEvento());
                ps.setInt(2, pro.getIdProveedor());      
                ps.setTimestamp(3, new java.sql.Timestamp(pro.getHoraInicio().getTime()));
                ps.setTimestamp(4, new java.sql.Timestamp(pro.getHoraFinal().getTime()));
                ps.setInt(5, 0);
                ps.execute();
            } catch (SQLException e) {
                System.err.println("Error registrarLote ProveedorEvento, " + e.getMessage());
            }
        }

        return new Mensaje(Message.Tipo.OK, "Registrado correctamete");
    }

    @Override
    public Mensaje actualizarLote(ArrayList<ProveedorEvento> loteProveedores) {
//       String ar = "";
//        for (ProveedorEvento et : loteProveedores) {
//            ProveedorEvento temp = obtenerByIdCiudadAndIdProveedor(et.getIdCiudad(), et.getIdProveedor());
//            ar += et.getIdCiudad() + ",";
//            if (temp == null) {
//                registrar(et);
//            }
//        }
//        if (!ar.isEmpty()) {
//            ar = ar.substring(0, ar.length() - 1);
//        } else {
//            ar = "0";
//        }
//        try (PreparedStatement ps = cn.prepareStatement(" DELETE FROM proveedorArea WHERE idProveedor = " + idProveedor
//                + "AND idCiudad NOT IN(" + ar + ")")) {
//            ps.execute();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }

        return new Mensaje(Message.Tipo.OK, "ProvEvento actualizado correctamente");
    }


    @Override
    public ArrayList<ProveedorEvento> obtenerListaByIdEvento(int idEvento) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO = "+idEvento)) {
            ArrayList<ProveedorEvento> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdEvento ProveedorEvento," + e.getMessage());
        }        
        return null;
    }

    @Override
    public ArrayList<ProveedorEvento> obtenerListaByIdProveedor(int idProveedor) {
           try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE idProveedor = "+idProveedor)) {
            ArrayList<ProveedorEvento> temp = new ArrayList<>();
            while(rs.next()){
                temp.add(new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdEvento ProveedorEvento," + e.getMessage());
        }        
        return null;
    }

    @Override
    public ProveedorEvento obtenerByIdEventoAndIdProveedor(int idEvento, int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO = "+ idEvento+
                " AND IDPROVEEDOR = " + idProveedor)) {
            if (rs.next()) {
                return new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3), rs.getTimestamp(4));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }        
        return null;
    }

}
