package com.example.prueba_apod;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;


import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("views/innit-view.fxml"));
        Scene scene = new Scene(root);
        //stage.getIcons().add(new Image("/images/nasa.png"));
        scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("NASA API");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        /*
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/innit-view.fxml"));
        try{stage.getIcons().add(new Image("images/nasa.png"));}catch(Exception e){System.out.println(e.getMessage());}
        Scene scene = new Scene(fxmlLoader.load(), 290, 350);
        stage.setTitle("Inicio de sesión");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

         */


    }

    public static void main(String[] args) {
        launch();
    }
}