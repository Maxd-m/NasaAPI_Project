package com.example.prueba_apod.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private RadioButton btnUser;
    @FXML
    private Panel mainPanel;
    @FXML
    private VBox rootVbox;


    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;

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
    public void onImgVidLibButtonCLick(ActionEvent actionEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnUser.setVisible(false);
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
