package com.example.prueba_apod;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller_test {
    @FXML
    private Button btnBack;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void onBackButtonClick(ActionEvent actionEvent) {

         try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("apod-view.fxml"));


            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerAPOD controlador = loader.getController();

            VBox currentRoot = (VBox) this.btnBack.getScene().getRoot();

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
}
