package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Conexion {

    public static final String BASE = "dbmundo_aventura"; // Nombre base de datos
    public static final String USERNAME = "root"; // Usuario principal en MySQL
    public static final String PASSWORD = ""; // Pass en Usuario Principal MySQL
    public static final String URL = "jdbc:mysql://localhost:3306/" + BASE; //URL Conexion db
    private Connection con = null;

    public Connection getConexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);

        } catch (SQLException e) {

            System.out.println(e);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }

}
