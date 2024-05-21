package com.example.prueba_apod.controllers;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.example.prueba_apod.database.dao.DAOUser;
import com.example.prueba_apod.models.User;
import com.example.prueba_apod.reports.ReportUsers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerUsersSettings implements Initializable {
    @FXML
    private TableView tblUsers;
    @FXML
    private Button btnDelete,btnUpdate,btnBack,btnReport;
    @FXML
    private ComboBox usersCB;
    @FXML
    private BorderPane bp;
    private List<User> userList;
    private User currentUser=new User();
    private Form updateForm;
    private User newUser;
    //int id;


    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/menu-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerMenu controlador = loader.getController();
            controlador.setUser(true);
            controlador.setAdmin(true);
            controlador.setCurrentUser(getCurrentUser());

            VBox currentRoot = (VBox) this.btnBack.getScene().getRoot();

            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onDeleteButtonCLick(ActionEvent actionEvent) {
        if(new DAOUser().delete((Integer) usersCB.getValue())){
            showMessage("Success","User deleted succesfully", Alert.AlertType.INFORMATION);
            userList=new DAOUser().findAll();
            tblUsers.setItems(FXCollections.observableArrayList(userList));
            innitCB();
        }else{
            showMessage("Error","Something failed", Alert.AlertType.ERROR);
        }

    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        User upd = new User();
        upd.setUser(String.valueOf(updateForm.getFields().get(0)));
        System.out.println("Nombre actualizar: "+newUser.getName());


        //new DAOUser().update(newUser);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //mainPanel.getStyleClass().add("panel-default");
        tblUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn idUserCol =new TableColumn<>("ID");
        idUserCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn userNameCol = new TableColumn<>("User Name");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));

        TableColumn ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn adminCol = new TableColumn<>("Admin type");
        adminCol.setCellValueFactory(new PropertyValueFactory<>("cveAdmin"));

        tblUsers.getColumns().addAll(idUserCol,nameCol,userNameCol,emailCol,ageCol,adminCol);
        userList=new DAOUser().findAll();
        tblUsers.setItems(FXCollections.observableArrayList(userList));




    }

    public void onReportButtonCLick(ActionEvent actionEvent) {
        String dest="reports/Report_Users.pdf";
        new Thread(()->{
            try {
                Platform.runLater(()->{btnReport.setDisable(true);});
                new ReportUsers().createPdf(dest);
                Platform.runLater(()->{btnReport.setDisable(false);});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        //id=this.currentUser.getId();
        //System.out.println(this.currentUser.getId());
        innitCB();

    }

    private void innitCB(){
        List<Integer> ids=new ArrayList<>();
        //System.out.println("curent "+id);
        for(User us :userList){
            if(!(us.getId()==getCurrentUser().getId())){
                ids.add(us.getId());
            }
        }
        usersCB.setItems(FXCollections.observableArrayList(ids));
        usersCB.getSelectionModel().selectFirst();
        innitForm();

        //t1 es seleccionado
        usersCB.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1)->{
            innitForm();
        });
    }

    private void innitForm(){
        Optional<User> upUser = new DAOUser().findById((Integer) usersCB.getValue());
        System.out.println("chosed: "+usersCB.getValue());

        if(upUser.isPresent()){
            newUser = upUser.get();
            System.out.println("mail: "+newUser.getMail());

            boolean isadmin;
            if(newUser.getCveAdmin()==null){
                isadmin=false;
            }else {
                isadmin = true;
            }

            ArrayList<String> genre=new ArrayList<>();
            if(newUser.getGender().equals("H")){
                genre.add("M");
                genre.add("F");
            }else {
                genre.add("F");
                genre.add("M");
            }

            updateForm =Form.of(
                    Group.of(
                            Field.ofStringType(newUser.getUser()).label("User name").required("Obligatory field"),
                            Field.ofStringType(newUser.getPass()).label("Password").required("Obligatory field"),
                            Field.ofStringType(newUser.getName()).label("Name").required("Obligatory field"),
                            Field.ofIntegerType(newUser.getAge()).label("Age").required("Obligatory field"),
                            Field.ofStringType(newUser.getMail()).label("Email").required("Obligatory field"),
                            Field.ofBooleanType(isadmin).label("Administrator"),
                            Field.ofSingleSelectionType(genre,0).label("Genre").required("Obligatory field")
                    )
            );

            FormRenderer fr = new FormRenderer(updateForm);
            fr.setPrefWidth(600);

            //formUpdateCont.getChildren().add(fr);
            bp.setRight(fr);
        }
    }

    private void showMessage(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
