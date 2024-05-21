package com.example.prueba_apod.database.dao;

import com.example.prueba_apod.models.APOD;
import com.example.prueba_apod.models.APODkey;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

//import static com.example.prueba_apod.database.MySQLConnection.conn;
import static com.example.prueba_apod.database.MySQLConnection.getConnection;

public class DAOapod implements Dao<APOD,Integer> {
    Connection conn = getConnection();
    @Override
    public Optional<APOD> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<APOD> findAll() {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(APOD record) {
        return false;
    }

    @Override
    public boolean save(Object record){
        return false;
    }
    @Override
    public boolean save(Object record, int id) {

        String updateQuery =
                "UPDATE archivos SET url = ?, fecha=?, explic=?, titulo=?, version=?, cveArch=?, copyright=? WHERE url = ?";
        try {

            PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
            APOD apod = (APOD) record;
           // ps.setString(1, apod.getUrl());

            //ps.setString(8,apod.getUrl());
            //ps.execute();
            psUpdate.setString(1,apod.getUrl());
            psUpdate.setString(2,apod.getDate());
            psUpdate.setString(3,apod.getExplanation());
            psUpdate.setString(4,apod.getTitle());
            psUpdate.setString(5,apod.getService_version());
            psUpdate.setString(6,apod.getMedia_type());
            psUpdate.setString(7,apod.getCopyright());
            psUpdate.setString(8, apod.getUrl());
            //psUpdate.setString(2, apod.getUrl());
            psUpdate.execute();

            // Execute insert
            //int rowsInserted = ps.executeUpdate();

            // If no rows were inserted, execute update
           // if (rowsInserted == 0) {
             //   psUpdate.executeUpdate();
            //}
            String insertGuardadosQuery = "INSERT INTO guardados (id, url) VALUES (?, ?);";
            PreparedStatement psGuardados = conn.prepareStatement(insertGuardadosQuery);
            psGuardados.setInt(1, id);
            psGuardados.setString(2, apod.getUrl());
            psGuardados.execute();
            //ps.setInt(8,id);
            //ps.setString(9,apod.getUrl());
           // return true;


            //ps.setString(11, apod.getCopyright());
            /*
            * // ps.setInt(1, auto.getIdAuto());
            ps.setString(1, auto.getNombreMarca());
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
            //ps.setFloat(13,auto.getPrecio());

            * */

            return true;
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println(e.toString());
            System.out.println(e.getErrorCode());
            if (e.getErrorCode()==1452){
                System.out.println("entra if");
                String query =
                        "INSERT INTO archivos (url, fecha, explic, titulo, version, cveArch, copyright)" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?);";
                try {
                    PreparedStatement ps = conn.prepareStatement(query);
                    APOD apod = (APOD) record;

                    ps.setString(1,apod.getUrl());
                    ps.setString(2,apod.getDate());
                    ps.setString(3,apod.getExplanation());
                    ps.setString(4,apod.getTitle());
                    ps.setString(5,apod.getService_version());
                    ps.setString(6,apod.getMedia_type());
                    ps.setString(7,apod.getCopyright());
                    ps.execute();

                    String insertGuardadosQuery = "INSERT INTO guardados (id, url) VALUES (?, ?);";
                    PreparedStatement psGuardados = conn.prepareStatement(insertGuardadosQuery);
                    psGuardados.setInt(1, id);
                    psGuardados.setString(2, apod.getUrl());
                    psGuardados.execute();
                    return true;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }else{
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean saveKey(String key){
        String query = "insert into APODkeys " +
                " (apodKey)" +
                " values (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            // ps.setInt(1, auto.getIdAuto());
            ps.setString(1,key);
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
            //ps.setFloat(13,auto.getPrecio());
            * */

            ps.execute();
            return true;
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    public List<String> findAllKeys(){
        List<String> keysList = FXCollections.observableArrayList();
        String query = "select * from APODkeys";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                APODkey dkey = new APODkey();
                dkey.setId(rs.getInt("id"));
                dkey.setKey(rs.getString("apodKey"));

                keysList.add(dkey.getKey());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return keysList;
    }
}
