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
                a.setGender(rs.getString("gender"));
                a.setAge(rs.getInt("age"));
                a.setUser(rs.getString("user"));
                usersList.add(a);
                a = null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    @Override
    public boolean update(Object record) {
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
                usersList.add(a);
                a = null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
}
