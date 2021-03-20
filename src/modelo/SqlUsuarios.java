package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUsuarios extends Conexion {

    public boolean registrar(Usuarios usr) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO login (usuario, contraseña, nombre, apellido, correo, id_tipo) VALUES (?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPass());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getApellido());
            ps.setString(5, usr.getCorreo());
            ps.setInt(6, usr.getId_tipo());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean login(Usuarios usr) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id_usuario, usuario, contraseña, nombre, apellido, id_tipo FROM login WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {

                if (usr.getPass().equals(rs.getString(3))) {

                    String sqlUpdate = "UPDATE login SET last_log = ? WHERE id_usuario =?";

                    ps = con.prepareStatement(sqlUpdate);
                    ps.setString(1, usr.getLast_log());
                    ps.setInt(2, rs.getInt(1));
                    ps.execute();

                    usr.setId(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    usr.setApellido(rs.getString(5));
                    usr.setId_tipo(6);
                    return true;
                } else {
                    return false;
                }

            }

            return false;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public int validarUsuarioExistente(String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id_usuario) FROM login WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }

    }

    public boolean validarEmail(String correo) {

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo);

        return mather.find();

    }

}
