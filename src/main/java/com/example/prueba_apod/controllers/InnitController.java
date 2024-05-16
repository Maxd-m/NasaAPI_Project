package com.example.prueba_apod.controllers;

import com.example.prueba_apod.database.dao.DAOUser;
import com.example.prueba_apod.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InnitController implements Initializable{
    @FXML
    private Label lblNasa;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnInv,btnInit,btnRegi;
    DAOUser userDao = new DAOUser();
    List<User> userList = new ArrayList<>();

    User user=new User();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUser.setPromptText("User");
        txtPass.setPromptText("Password");
        //Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/prueba_apod/images/nasa.png")),75,64,false,false);
        //lblNasa.setGraphic(new ImageView(image));
        lblNasa.setPrefSize(64,64);
        lblNasa.setMinSize(64,64);
        lblNasa.setMaxSize(64,64);
        lblNasa.requestFocus();
    }

    
    private boolean validaUser(String param) {
        boolean ban = false;
        try {
            userList = userDao.findByName(param);
            if(!userList.isEmpty()){
                User usuario = userList.get(0);
                String userS = usuario.getUser();
                String passS = usuario.getPass();
                String usertxt = txtUser.getText().trim();
                String passtxt = txtPass.getText().trim();
                user=usuario;
                System.out.println(usertxt+"  "+passtxt);
                System.out.println(userS+" "+passS);

                if(userS.equals(usertxt)&&passS.equals(passtxt)){
                    ban = true;
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion error");
            alert.setContentText("There was a communication error with the database\nPlease try again");
            alert.show();
        }
        return ban;
    }
    
    private void limpiar(){
        txtPass.setText("");
        txtUser.setText("");
    }

    @FXML
    public void onInitButtonClick(ActionEvent actionEvent) {
        if(!txtUser.getText().toLowerCase().trim().isEmpty() && !txtPass.getText().trim().isEmpty()){
            try {
                boolean bandera = validaUser(txtUser.getText().toLowerCase().trim());
                System.out.println(txtUser.getText().toLowerCase().trim());
                if(bandera){
                    try {
                        // Cargo la vista
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/menu-view.fxml"));

                        // Cargo el padre
                        Parent root = loader.load();

                        // Obtengo el controlador
                        ControllerMenu controlador = loader.getController();
                        controlador.setUser(true);
                        controlador.setCurrentUser(user);
                        controlador.recibirTipo(userList.get(0).getCveAdmin());

                        VBox currentRoot = (VBox) btnInv.getScene().getRoot();

                        // Reemplazo el contenido del contenedor actual con el nuevo contenido
                        currentRoot.getChildren().setAll(root);

                    } catch (IOException ex) {
                        Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login error");
                    alert.setContentText("User/Password incorrect\nPlease verify and try again");
                    alert.show();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Complete all fields");
            alert.setContentText("There are empty fields!\nPlease verify and try again");
            alert.show();
        }
    }

    @FXML
    public void onInvButtonCLick(ActionEvent actionEvent) {
        try {
            System.out.println("entra inv");
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/menu-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            ControllerMenu controlador = loader.getController();
            controlador.setUser(false);

            VBox currentRoot = (VBox) btnInv.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRegiButtonCLick(ActionEvent actionEvent) {
        try {
            System.out.println("entra inv");
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/registration-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerRegistration controlador = loader.getController();

            VBox currentRoot = (VBox) btnInv.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
