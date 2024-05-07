package com.example.prueba_apod.controllers;

import com.example.prueba_apod.LimitarTF;
import com.example.prueba_apod.database.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRegistration implements Initializable {
    @FXML
    private Label lblmensajeinit,lblusermsj;
    @FXML
    private ToggleGroup generoGoup;
    String valorRaddio = "";
    @FXML
    private TextField txtname,txtedad,txtmail,txtuser;
    @FXML
    private PasswordField txtpassregi;
    @FXML
    private Button btnLimpia,btnRegi;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblmensajeinit.setText("Create an account");
        generoGoup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                valorRaddio = ((RadioButton)newVal).getText();
                if(valorRaddio.equals("Male")){valorRaddio="H";}
                if(valorRaddio.equals("Female")){valorRaddio="M";}
            }
        });
        LimitarTF.addTextLimiter(txtedad,2);
        lblusermsj.setText("Enter your username\n (you will log in with it in the future)");
        /*
        *  btnRegi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nombre = txtname.getText().trim();
                String password = txtpassregi.getText().trim();
                String edad = txtedad.getText().trim();
                String usuario = txtuser.getText().trim().toLowerCase();
                String mail = txtmail.getText().trim();
                if(!(nombre.isEmpty() || password.isEmpty() || mail.isEmpty() || edad.isEmpty() || usuario.isEmpty() || generoGoup.getSelectedToggle()==null)){
                    if(MySQLConnection.Insertar(nombre,password,mail,valorRaddio,Integer.parseInt(edad),usuario)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Registro exitoso!");
                        alert.setContentText("Se ha hecho el registro exitosamente!\nYa puede iniciar sesion");
                        alert.show();
                        Stage currentStage = (Stage) btnRegi.getScene().getWindow();
                        currentStage.close();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Registro fallido!");
                        alert.setContentText("Ha habido un error en el registro!\nPor favor reintente");
                        alert.show();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Llena todos los datos");
                    alert.setContentText("Falta llenar algunos datos!\nPor favor verificalos y reintenta");
                    alert.show();
                }
            }
        });
        btnLimpia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtuser.setText("");
                txtpassregi.setText("");
                txtedad.setText("");
                txtmail.setText("");
                txtname.setText("");
                generoGoup.selectToggle(null);
            }});
        * */

    }

    @FXML
    public void onClearButtonClick(ActionEvent actionEvent) {
        txtuser.setText("");
        txtpassregi.setText("");
        txtedad.setText("");
        txtmail.setText("");
        txtname.setText("");
        generoGoup.selectToggle(null);
    }

    @FXML
    public void onSignInButtonCLick(ActionEvent actionEvent) {
        String nombre = txtname.getText().trim();
        String password = txtpassregi.getText().trim();
        String edad = txtedad.getText().trim();
        String usuario = txtuser.getText().trim();
        String mail = txtmail.getText().trim();
        if(!(nombre.isEmpty() || password.isEmpty() || mail.isEmpty() || edad.isEmpty() || usuario.isEmpty() || generoGoup.getSelectedToggle()==null)){
            if(MySQLConnection.Insertar(nombre,password,mail,valorRaddio,Integer.parseInt(edad),usuario)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful registration");
                alert.setContentText("Se ha hecho el registro exitosamente!\nYa puede iniciar sesion");
                alert.show();
                onBackButtonClick(new ActionEvent());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("registration failed");
                alert.setContentText("Ha habido un error en el registro!\nPor favor reintente");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fill all the fields");
            alert.setContentText("Falta llenar algunos datos!\nPor favor verificalos y reintenta");
            alert.show();
        }
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
           // System.out.println("entra inv");
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/innit-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            InnitController controlador = loader.getController();

            VBox currentRoot = (VBox) btnRegi.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
