package dao;

import Componentes.Sweet_Alert.Message;
import idao.IProveedorEventoDAO;
import independientes.Conexion;
import independientes.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDORESEVENTO VALUES(?,?,?,?,?,?)")) {
            ps.setInt(1, t.getIdEvento());
            ps.setInt(2, t.getIdProveedor());
            ps.setInt(3, t.getIdNegocio());
            ps.setTimestamp(4, new java.sql.Timestamp(t.getFechaInicio().getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(t.getFechaFinal().getTime()));
            ps.setString(6, t.getComentario());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Registrado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al registrar");

        } catch (SQLException e) {
            System.err.println("Error registrar ProveedorEvento, " + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "Error");
    }

    @Override
    public Mensaje actualizar(ProveedorEvento t) {
        try (PreparedStatement ps = cn.prepareStatement("UPDATE PROVEEDOREVENTO SET fechaINICIO = ?, fechaFINAL = ?, COMENTARIO = ? "
                + "WHERE IDEVENTO = ? AND IDPROVEEDOR = ? AND IDNEGOCIO = ?")) {
            ps.setTimestamp(1, new Timestamp(t.getFechaInicio().getTime()));
            ps.setTimestamp(2, new Timestamp(t.getFechaFinal().getTime()));
            ps.setString(3, t.getComentario());
            ps.setInt(4, t.getIdEvento());
            ps.setInt(5, t.getIdProveedor());
            ps.setInt(6, t.getIdNegocio());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Actualizado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al actualizar");
        } catch (SQLException e) {
            System.err.println("Error actualizar ProveedorEventoo," + e.getMessage());
        }
        return new Mensaje(Message.Tipo.ERROR, "ERROR, intentelo mas tarde.");
    }

    @Override
    public Mensaje eliminar(ProveedorEvento t) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM PROVEEDOREVENTO WHERE IDPROVEEDOR = ? AND IDEVENTO = ?")) {
            ps.setInt(1, t.getIdProveedor());
            ps.setInt(2, t.getIdEvento());
            return (ps.executeUpdate() >= 1) ? new Mensaje(Message.Tipo.OK, "Eliminado correctamente") : new Mensaje(Message.Tipo.ADVERTENCIA, "Problema al eliminar");
        } catch (SQLException e) {
            System.err.println("Error eliminar ProveedorEvento, " + e.getMessage());
        }

        return new Mensaje(Message.Tipo.ERROR, "Error");

    }

    @Override
    public String yaExiste(ProveedorEvento t) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO =" + t.getIdEvento()
                + " and idNegocio = " + t.getIdNegocio())) {
            if (rs.next()) {
                return "Negocio repetido";
            }
        } catch (SQLException e) {
            System.err.println("Error yaExiste ProveederEvento, " + e.getMessage());
        }
        return "";
    }

    @Override
    public Mensaje registrarLote(ArrayList<ProveedorEvento> loteProveedores) {
        for (ProveedorEvento pro : loteProveedores) {
            if (!yaExiste(pro).isEmpty()) {
                continue;
            }
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO PROVEEDOREVENTO VALUES(?,?,?,?,?,?)")) {
                ps.setInt(1, pro.getIdEvento());
                if (pro.getIdProveedor() == 0) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, pro.getIdProveedor());
                }
                ps.setInt(3, pro.getIdNegocio());
                ps.setTimestamp(4, new java.sql.Timestamp(pro.getFechaInicio().getTime()));
                ps.setTimestamp(5, new java.sql.Timestamp(pro.getFechaFinal().getTime()));
                if (pro.getComentario().isEmpty() || pro.getComentario().equals("Si deseas algo en especifico de este proveedoor, puedes escribirlo aqui")) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, pro.getComentario());
                }
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
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO = " + idEvento)) {
            ArrayList<ProveedorEvento> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdEvento ProveedorEvento," + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ProveedorEvento> obtenerListaByIdProveedor(int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE idProveedor = " + idProveedor)) {
            ArrayList<ProveedorEvento> temp = new ArrayList<>();
            while (rs.next()) {
                temp.add(new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6)));
            }
            return temp;
        } catch (SQLException e) {
            System.err.println("Error obtenerListaByIdEvento ProveedorEvento," + e.getMessage());
        }
        return null;
    }

    @Override
    public ProveedorEvento obtenerByIdEventoAndIdProveedor(int idEvento, int idProveedor) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO = " + idEvento
                + " AND IDPROVEEDOR = " + idProveedor)) {
            if (rs.next()) {
                return new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ProveedorEvento obtenerByIdEventoAndIdNegocio(int idEvento, int idNegocio) {
        try (ResultSet rs = Conexion.getInstancia().Consulta("SELECT * FROM PROVEEDOREVENTO WHERE IDEVENTO = " + idEvento
                + " AND idNegocio = " + idNegocio)) {
            if (rs.next()) {
                return new ProveedorEvento(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


}
