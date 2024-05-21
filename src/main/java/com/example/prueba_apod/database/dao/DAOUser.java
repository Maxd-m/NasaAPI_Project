package com.example.prueba_apod.database.dao;

//import com.vgsg.login.MySQLConnection;
//import com.vgsg.login.models.User;
import com.example.prueba_apod.models.User;
import javafx.collections.FXCollections;
import com.example.prueba_apod.database.MySQLConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DAOUser extends MySQLConnection implements Dao<User,Integer> {
    Connection conn = getConnection();
    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> optUser = Optional.empty();
        String query = "select * from users where id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if ( rs.next() )
            {
                User user = new User();
                user.setUser(rs.getString("user"));
                user.setAge(rs.getInt("age"));
                user.setName(rs.getString("name"));
                user.setPass(rs.getString("password"));
                user.setMail(rs.getString("mail"));
                user.setGender(rs.getString("cveGen"));
                user.setCveAdmin(rs.getString("cveAdmin"));

                optUser = Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optUser;
    }

    @Override
    public List<User> findAll() {

        List<User> usersList = FXCollections.observableArrayList();
        String query = "select * from users;";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setPass(rs.getString("password"));
                a.setMail(rs.getString("mail"));
                a.setGender(rs.getString("cveGen"));
                a.setAge(rs.getInt("age"));
                a.setUser(rs.getString("user"));
                a.setCveAdmin(rs.getString("cveAdmin"));
                //a.setCveAdmin(rs.getString("cveAdmin"));
                usersList.add(a);
                a = null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from users where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User usr) {
        String query = "update users set age=?, name=?, user=?, password=?, mail=?, cveGen=?, " +
                "cveAdmin=?  where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,usr.getAge());
            ps.setString(2,usr.getName());
            ps.setString(3,usr.getUser());
            ps.setString(4,usr.getPass());
            ps.setString(5,usr.getMail());
            ps.setString(6,usr.getGender());
            ps.setString(7,usr.getCveAdmin());

            /*
            * ps.setString(1, auto.getNombreMarca());
            ps.setString(2, auto.getModelo());
            ps.setString(3, auto.getColor());
            ps.setInt(4, auto.getPeso());
            ps.setString(5, auto.getTipoTransmision());
            ps.setInt(6,auto.getNumPuertas());
            ps.setString(7,auto.getNumPasajeros());
            ps.setString(8,auto.getMotor());
            ps.setString(9,auto.getTipoFrenos());
            ps.setInt(10,auto.getVelocidades());
            ps.setString(11,auto.getOtrosAccesorios());
            ps.setString(12,auto.getFotografia());
            ps.setDouble(13,auto.getPrecio());
            ps.setInt(14,auto.getIdAuto());
            System.out.println("ID to update: " + auto.getIdAuto());
            * */

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(Object record) {
        return false;
    }

    public boolean save(Object record, int id){return false;}

    public List<User> findByName(String usuario) {

        List<User> usersList = FXCollections.observableArrayList();
        String query = "select * from users where user = '"+usuario+"';";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setPass(rs.getString("password"));
                a.setMail(rs.getString("mail"));
                a.setGender(rs.getString("cveGen"));
                a.setAge(rs.getInt("age"));
                a.setUser(rs.getString("user"));
                a.setCveAdmin(rs.getString("cveAdmin"));
                usersList.add(a);
                a = null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
}
