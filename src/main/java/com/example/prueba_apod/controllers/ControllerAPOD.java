package com.example.prueba_apod.controllers;

import com.example.prueba_apod.database.dao.DAOapod;
import com.example.prueba_apod.models.APOD;
import com.example.prueba_apod.models.User;
import com.example.prueba_apod.reports.ReportAPOD;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import com.google.gson.Gson;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class ControllerAPOD implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private VBox contentVbox;
    @FXML
    private Button btnReportUser;
    @FXML
    private WebView webView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Panel mainPanel;
    @FXML
    private Text msgTitle, msgContent;
    @FXML
    private Button btnBack;
    @FXML
    private Button searchBtn;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReport;
    @FXML
    private TextFlow msgContainer;

    private APOD apod;
    private User currentUser = new User();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;
    private boolean isAdmin;
    private boolean flgLoading;
    private String key="DEMO_KEY";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        webView.setVisible(false);
        mainPanel.getStyleClass().add("panel-default");
        //ActionEvent ae= new ActionEvent();


        try {
            onSearchButtonClick(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void onSearchButtonClick(ActionEvent actionEvent) throws IOException, InterruptedException {

        //iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9
        //DEMO_KEY
        //video= 2024-04-14
        //imagen 2024-01-01
        
       // if(isOnline()){
        System.out.println(key);
            new Thread(()->{
                Platform.runLater(()->{
                    searchBtn.setDisable(true);
                    btnBack.setDisable(true);
                    btnSave.setDisable(true);
                    msgContainer.setVisible(true);
                    msgContainer.getStyleClass().add("alert-warning");
                    msgTitle.setText("Loading ");
                    msgContent.setText("Please wait");
                    System.out.println("request0: "+"https://api.nasa.gov/planetary/apod?api_key="+getKey()+"&date=" + datePicker.getValue().toString());

                });
                String aux2="https://api.nasa.gov/planetary/apod?api_key="+getKey()+"&date=" + datePicker.getValue().toString();
                try {

                    //System.out.println("request1: "+"https://api.nasa.gov/planetary/apod?api_key="+getKey()+"&date=" + datePicker.getValue().toString());
                    URL url = new URL("https://api.nasa.gov/planetary/apod?api_key="+getKey()+"&date=" + datePicker.getValue().toString());
                    //System.out.println("request: "+"https://api.nasa.gov/planetary/apod?api_key="+getKey()+"&date=" + datePicker.getValue().toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    System.out.println("    rc:    "+responseCode);
                    if(responseCode!=200&&responseCode!=429){
                        throw new RuntimeException("Error "+responseCode);
                    }else{
                        //abrir scanner para leer datos:
                        StringBuilder infoString = new StringBuilder();
                        Scanner scanner = null;
                        scanner = new Scanner(url.openStream());

                        while (scanner.hasNext()){
                            infoString.append(scanner.nextLine());
                        }

                        scanner.close();


                        //imprimir info
                        System.out.println(infoString);
                        Gson gson = new Gson();
                        String aux= String.valueOf(infoString);
                        apod = gson.fromJson(aux,APOD.class);

                        if(apod.getMedia_type().equals(new String("image"))){
                            Platform.runLater(()->{
                                webView.setVisible(true);
                                webView.getEngine().load(apod.getUrl());
                            });

                            //mainVbox.getChildren().add(image);
                            System.out.println("after image");
                        }
                        else if (apod.getMedia_type().equals(new String("video"))) {
                            Platform.runLater(()->{
                                webView.setVisible(true);
                                webView.getEngine().load(apod.getUrl());
                                System.out.println("after video");
                            });

                        }

                        Platform.runLater(()->{
                            titleLabel.setText(apod.getTitle());
                            contentLabel.setText("Copyright: "+apod.getCopyright()+"\nDate: "+apod.getDate()+"\n\nExplanation: "+apod.getExplanation());
                            searchBtn.setDisable(false);
                            btnBack.setDisable(false);
                            btnSave.setDisable(false);
                            msgContainer.setVisible(false);
                        });


                    }

                }
                catch (IOException e) {
                    System.out.println("error search: " +e.toString());}
            }).start();
    }

    public void onDatePicked(ActionEvent actionEvent) {
        if(datePicker.getValue().isAfter(LocalDate.now())){
            showMessage("Alert","Unable to display a picture from a future date", Alert.AlertType.WARNING);
            datePicker.setValue(LocalDate.now());
        }
        else if(datePicker.getValue().isBefore(LocalDate.of(1995,6,20))){
            showMessage("Alert","Unable to display a picture from a date before June 20, 1995", Alert.AlertType.WARNING);
            datePicker.setValue(LocalDate.of(1995,6,20));
        }
        System.out.println(datePicker.getValue());

    }

    private void showMessage(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }


    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {

          try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/menu-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            ControllerMenu controlador = loader.getController();
            controlador.setUser(isUser);
            controlador.setAdmin(isAdmin);
            if(currentUser!=null){
                controlador.setCurrentUser(getCurrentUser());
            }


            VBox currentRoot = (VBox) this.btnBack.getScene().getRoot();

              // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getIsUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
        if(!isUser){
            btnSave.setVisible(false);
            btnReport.setVisible(false);
            btnReportUser.setVisible(false);
        }else {
            btnSave.setVisible(true);
            btnReport.setVisible(true);
            btnReportUser.setVisible(true);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if(currentUser!=null){
            System.out.println(this.currentUser.getId());

        }

    }

    @FXML
    public void onSaveButtonCLick(ActionEvent actionEvent) {
        new Thread(()->{
            DAOapod dAPOD= new DAOapod();
            Platform.runLater(()->{
                btnSave.setDisable(true);
                msgContainer.getStyleClass().add("alert-warning");
                msgTitle.setText("Saving ");
                msgContent.setText("Please wait");
                msgContainer.setVisible(true);
            });
            System.out.println("            save: "+currentUser.getName());
            if(dAPOD.save(apod,currentUser.getId())){
                try {
                    Platform.runLater(()->{
                        msgContainer.getStyleClass().clear();
                        msgContainer.getStyleClass().add("alert-success");
                        msgTitle.setText("Success ");
                        msgContent.setText("APOD saved");
                        msgContainer.setVisible(true);
                    });
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Platform.runLater(()->{
                msgContainer.setVisible(false);
                btnSave.setDisable(false);
            });
        }).start();
    }

    public void onReportButtonCLick(ActionEvent actionEvent) {
        new Thread(()->{
            System.out.println("cick report");
            String dest = "reports/Report_APOD.pdf";
            btnReport.setDisable(true);
            btnBack.setDisable(true);
            try {
                //getWeekImages();
                new ReportAPOD().createReport(dest,getWeekImages(),"Last week APOD");
                openFile(dest);
            } catch (Exception e) {
                //throw new RuntimeException(e);
            }
            btnReport.setDisable(false);
            btnBack.setDisable(false);
        }).start();
    }

    private void openFile(String filename)
    {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    private List<APOD> getWeekImages() throws Exception{
        List<APOD> apodList = new ArrayList<>();

        for (int i = 0; apodList.size() < 7; i++) {
            System.out.println("for week: "+i);
         //   week.add(LocalDate.now().minusDays(i));

            //colocar codigo dentro de un ciclo para obtener apods de todas las fechas de week
            URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=9wR5BCEVPpBNbNg49cnajvOy1Ihch32OcAnPcaBK&date="+LocalDate.now().minusDays(i).toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode!=200&&responseCode!=429){
                throw new RuntimeException("Error "+responseCode);
            }
            else {
                //abrir scanner para leer datos:
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }

                scanner.close();

                //imprimir info
               // System.out.println(infoString);
                Gson gson = new Gson();
                String aux = String.valueOf(infoString);
                apod = gson.fromJson(aux, APOD.class);


                if(apod.getMedia_type().equals(new String("image"))){
                    System.out.println("image days: " +LocalDate.now().minusDays(i).toString());
                    apodList.add(apod);
                }
            }

            //System.out.println(LocalDate.now().minusDays(i));
            System.out.println("size: "+apodList.size());
        }
        
        return apodList;
    }

    private boolean isOnline() throws IOException, InterruptedException {
        //test connection to api.nasa.gov
        String comand="ping -c 1 google.com";
        return (Runtime.getRuntime().exec(comand).waitFor()==0);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void onReportUserButtonCLick(ActionEvent actionEvent) {
        Optional<List<APOD>> opt;
        opt=new DAOapod().findKeysAPODuser(this.currentUser.getId());
        System.out.println("busca keys");

        if(opt.isPresent()){
            List<APOD> apods = opt.get();
            for (APOD a:apods){
                System.out.println(a.toString());
            }
            System.out.println("sale for");
            System.out.println("    apods: "+apods.size());

            if(apods.size()>0){
                String dest = "reports/Report_Saved_APOD_"+currentUser.getName()+".pdf";
                try {
                    new ReportAPOD().createReport(dest,apods,"Saved APOD");
                    openFile(dest);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            System.out.println("no hay nada");
        }
    }
}