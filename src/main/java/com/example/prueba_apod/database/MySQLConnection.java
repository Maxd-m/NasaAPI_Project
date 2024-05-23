package com.example.prueba_apod.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author niluxer
 */
public class MySQLConnection {
    private static Connection conn = null;
    private static String hostname   = "localhost";
    private static String dbname = "proyectonasa";
    private static String dbport = "3306";
    private static String dbuser = "topicos";
    private static String dbpass = "TopicosProgra#";
    

    public static void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://"+ hostname +":"+ dbport +"/" + dbname + "?serverTimezone=UTC", dbuser, dbpass);
                System.out.println("Successful database connection.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection()
    {
        if(conn == null) Connect();
        return conn;
    }
    
    public static void Disconnect() {
        try {
            conn.close();
            System.out.println("Database connection has been terminated.");
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean Insertar(String nombre, String password, String mail, String valorRaddio, Integer edad, String usuario) {
        conn = getConnection();
        String query = "insert into users " + " (name, password, mail, cveGen, age, user)" + " values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, password);
            ps.setString(3, mail);
            ps.setString(4, valorRaddio);
            ps.setInt(5, edad);
            ps.setString(6, usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
            return false;
        }
    }

    
}
