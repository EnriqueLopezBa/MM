package independientes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {

     Connection con;
    private static Conexion instancia;
    
    private Conexion() {
    }

    public static Conexion getInstancia(){ // Patron Singleton
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    

    public  Connection getConexion() {
        try {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=MM;user=sa; password=toor;";
            con = DriverManager.getConnection(connectionUrl);
        } catch (SQLException ex) {
            System.err.println("Error, " + ex.getMessage());
        }
        return con;
    }

    public ResultSet Consulta(String consulta) {
        try {
           return con.prepareStatement(consulta).executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error, " + ex.getMessage());
        }
        return null;
    }
}
