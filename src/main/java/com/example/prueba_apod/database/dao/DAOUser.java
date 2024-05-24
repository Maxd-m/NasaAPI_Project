package com.example.prueba_apod.database.dao;

//import com.vgsg.login.MySQLConnection;
//import com.vgsg.login.models.User;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.example.prueba_apod.models.User;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import com.example.prueba_apod.database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOUser extends MySQLConnection implements Dao<User,Integer>
{
    ArrayList<String>genders= new ArrayList<>(){{
        add("H");
        add("M");
    }};
    private final ListProperty<String> allGenders =
            new SimpleListProperty<>(FXCollections.observableArrayList(genders));
    private final ObjectProperty<String> i= new SimpleObjectProperty<String>(allGenders.get(0));
    IntegerProperty age = new SimpleIntegerProperty(0);
    StringProperty name = new SimpleStringProperty("");
    StringProperty pass = new SimpleStringProperty("");
    StringProperty mail = new SimpleStringProperty("");
    StringProperty user = new SimpleStringProperty("");
    BooleanProperty bool = new SimpleBooleanProperty(false);
    String cveAdmin = null;

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
            ps.setInt(8, usr.getId());
            System.out.println(usr.getId()+"id");
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

    public Form createForm()
    {
        return Form.of(
                Group.of(
                        Field.ofStringType(this.user).label("User name").required("Obligatory field"),
                        Field.ofStringType(this.pass).label("Password").required("Obligatory field"),
                        Field.ofStringType(this.name).label("Name").required("Obligatory field"),
                        Field.ofIntegerType(this.age).label("Age").required("Obligatory field"),
                        Field.ofStringType(this.mail).label("Email").required("Obligatory field"),
                        Field.ofBooleanType(this.bool).label("Administrator"),
                        Field.ofSingleSelectionType(allgProperty(), gProperty()).label("Genre").required("Obligatory field")
                )
        );
    }
    public User generateUser(int id)
    {
        User user1= new User();
        user1.setId(id);
        user1.setUser(this.user.getValue());
        user1.setPass(this.pass.getValue());
        user1.setName(this.name.getValue());
        user1.setAge(this.age.getValue());
        user1.setMail(this.mail.getValue());
        if (cveAdmin==null)
        {
            user1.setCveAdmin(null);
        }else
        {
            user1.setCveAdmin(cveAdmin);
        }
        user1.setGender(gProperty().getValue());
        return user1;
    }

    public void setFields(User us)
    {
        age.setValue(us.getAge());
        mail.setValue(us.getMail());
        if(us.getGender().equals("M"))
        {
            gProperty().setValue("M");
        }
        else
        {
            gProperty().setValue("H");
        }
        user.setValue(us.getUser());
        if (us.getCveAdmin()==null)
        {
            bool.setValue(false);
        };
        name.setValue(us.getName());
        pass.setValue(us.getPass());
    }
    public ObjectProperty<String> gProperty() {
        return i;
    }

    public ListProperty<String> allgProperty() {
        return allGenders;
    }
}
