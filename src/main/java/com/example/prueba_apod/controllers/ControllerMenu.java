package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMenu implements Initializable {
    @FXML
    private Button btnAPOD;
    @FXML
    private Button btnImageVideoLib;
    @FXML
    private Panel mainPanel;
    @FXML
    private VBox rootVbox;


    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;
    private User currentUser =new User();

    @FXML
    public void onAPODButtonClick(ActionEvent actionEvent) {

         try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/apod-view.fxml"));


            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerAPOD controlador = loader.getController();
            controlador.setUser(isUser);
            controlador.setCurrentUser(getCurrentUser());

             System.out.println(isUser);

            VBox currentRoot = (VBox) this.btnAPOD.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);
            /*
            * // Creo la scene y el stage
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

            // Indico que debe hacer al cerrar
            //stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnBack.getScene().getWindow();
            myStage.close();

            * */

        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }


        /*
        *  try {
            root=FXMLLoader.load(HelloApplication.class.getResource("apod-view.fxml"));
            stage=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene=new Scene(root);
            scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        * */

    }

    @FXML
    public void onImgVidLibButtonCLick(ActionEvent actionEvent)
    {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/IVL.fxml"));

            Parent root = loader.load();

            ControllerIVL controlador = loader.getController();
            controlador.setUser(isUser);

            System.out.println(isUser);

            VBox currentRoot = (VBox) this.btnImageVideoLib.getScene().getRoot();
            currentRoot.setStyle("-fx-background-image: url('fondoe.jpg'); -fx-background-size: cover");
            currentRoot.getChildren().setAll(root);
            currentRoot.setAlignment(Pos.TOP_CENTER);

        } catch (IOException ex) {
            Logger.getLogger(ControllerIVL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onAsteroidsButtonCLick(ActionEvent actionEvent) {
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPanel.getStyleClass().add("panel-default");
       // mainPanel.setPrefHeight(mainPanel.getMaxHeight());
       // VBox.setVgrow(mainPanel, Priority.ALWAYS);
    }

    @FXML
    public void onLogOutButtonClick(ActionEvent actionEvent) {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/innit-view.fxml"));


            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            InnitController controlador = loader.getController();
            //controlador.setUser(isUser);

            //System.out.println(isUser);

            VBox currentRoot = (VBox) this.btnAPOD.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
