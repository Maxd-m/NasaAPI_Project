package com.example.prueba_apod.controllers;

import com.example.prueba_apod.database.dao.DAOapod;
import com.example.prueba_apod.models.User;
import com.example.prueba_apod.reports.ReportUsers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    @FXML
    private Button btnAdmin;
    @FXML
    private Button btnUsers;



    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;
    private boolean isAdmin;
    private User currentUser;
    private String tipo;
    private String key="DEMO_KEY";
    private Button report;
    private Button usersSettings;


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
            controlador.setKey(getKey());
            controlador.setAdmin(isAdmin);

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
        btnAdmin.setVisible(false);
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

    public void recibirTipo(String user) {
        if(user != null){
            tipo = "1";
            if(user.equals("1")){
                btnAdmin.setVisible(true);
                setAdmin(true);
            }
            //una vez validado puedes agregar el boton aqui para las acciones del administrador
            System.out.println(tipo+" si es *1* es admin");
        }else{
            setAdmin(false);
            System.out.println("no es admin");
        }
    }

    public void onAdminButtonCLick(ActionEvent actionEvent) {
        Stage modalStage = new Stage();
        Stage currentStage = (Stage) btnAdmin.getScene().getWindow();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(currentStage);
        modalStage.setTitle("Admin Options");

        VBox mainVbox=new VBox(15);

        HBox content = new HBox(20);

        VBox lftVbox=new VBox(5);
        ComboBox<String> keys = new ComboBox<>(FXCollections.observableArrayList(new DAOapod().findAllKeys()));
        keys.getSelectionModel().selectFirst();
        //keys.prefWidth(35);
        Label labelCB = new Label("Set Key");
        lftVbox.getChildren().addAll(labelCB,keys);

        VBox rgtVbox=new VBox();
        rgtVbox.setPrefWidth(280);
        Label labelSV = new Label("Save a new key");
        TextField newKey = new TextField();
        //newKey.prefWidth(60);
        //newKey.setPrefWidth(160);
        Button btnSave = new Button("Save");
        VBox btn1=new VBox(btnSave);
        btn1.setAlignment(Pos.CENTER);
        btnSave.setAlignment(Pos.CENTER);
        btnSave.setOnAction(event->{
            saveKey(newKey.getText());
            keys.setItems(FXCollections.observableArrayList(new DAOapod().findAllKeys()));
        });

        rgtVbox.getChildren().addAll(labelSV,newKey,btn1);


        //report = new Button("Generate Report");
        //report.setOnAction(event->onReportButtonClick());
        //report.setTooltip(new Tooltip("Generate report of registered users"));

       // usersSettings=new Button("Manage users");
        //usersSettings.setTooltip(new Tooltip("Update or Delete users"));
       // usersSettings.setOnAction(actionEvent1 -> onUsersSettingsButtonClick(modalStage));

       // HBox btn2=new HBox(report);
       // btn2.setAlignment(Pos.CENTER);

        content.getChildren().addAll(lftVbox,rgtVbox);
        content.setAlignment(Pos.CENTER);


        mainVbox.getChildren().addAll(content);

        modalStage.setOnCloseRequest(event -> onAdminCLosed(modalStage,keys.getSelectionModel().getSelectedItem()));

        StackPane modalRoot = new StackPane();
        modalRoot.getChildren().addAll(mainVbox);
        modalStage.setScene(new Scene(modalRoot, 600, 150));
        modalStage.showAndWait();

    }

    private void onReportButtonClick(){
        String dest="reports/Report_Users.pdf";
        new Thread(()->{
            try {
                Platform.runLater(()->{report.setDisable(true);});
                new ReportUsers().createPdf(dest);
                Platform.runLater(()->{report.setDisable(false);});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @FXML
    private void onUsersSettingsButtonClick(){
        //st.close();
        try {
            // Cargo la vista
            //st.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/usersSettings-view.fxml"));

            System.out.println("user: "+getCurrentUser().getName());
            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerUsersSettings controlador = loader.getController();
            //controlador.setUser(isUser);

            controlador.setCurrentUser(getCurrentUser());
            //controlador.setKey(getKey());
            //controlador.setAdmin(isAdmin);

            //System.out.println(isUser);

            VBox currentRoot = (VBox) this.btnAPOD.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean saveKey(String key){
        if(new DAOapod().saveKey(key)){
            return true;
        }else{return false;}
    }

    private void onAdminCLosed(Stage st,String selKey){
        setKey(selKey);
        st.close();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
        btnAdmin.setVisible(admin);
        btnUsers.setVisible(admin);
    }
}
