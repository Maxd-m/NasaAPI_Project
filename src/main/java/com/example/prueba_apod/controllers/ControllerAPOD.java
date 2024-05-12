package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.APOD;
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
    private ImageView image;
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

    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;
    private boolean flgLoading;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        webView.setVisible(false);
        mainPanel.getStyleClass().add("panel-default");

        try {
            if(!isOnline()){
                Platform.runLater(()->{
                    msgContainer.getStyleClass().add("alert-warning");
                    msgTitle.setText("No internet! ");
                    msgContent.setText("Check your internet connection and try again.");
                    searchBtn.setDisable(true);
                    btnReport.setDisable(true);
                    btnSave.setDisable(true);
                    datePicker.setDisable(true);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Scene sc = new Scene(new VBox());

        //sc.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
    }

    @FXML
    protected void onSearchButtonClick() throws IOException, InterruptedException {

        //iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9
        //DEMO_KEY
        //video= 2024-04-14
        //imagen 2024-01-01
        
       // if(isOnline()){
            new Thread(()->{
                searchBtn.setDisable(true);
                btnBack.setDisable(true);
                try {
                    URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9&date=" + datePicker.getValue().toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responseCode = conn.getResponseCode();
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
                            image.setVisible(true);
                            webView.setVisible(false);
                            Image auxImage=new Image(apod.getUrl());

                            //image=new ImageView(auxImage);
                            image.setImage(auxImage);
                            image.setFitWidth(300); // Ajusta el ancho según sea necesario
                            image.setPreserveRatio(true);

                            //mainVbox.getChildren().add(image);
                            System.out.println("after image");
                        }
                        else if (apod.getMedia_type().equals(new String("video"))) {
                            image.setVisible(false);
                            // WebView wb = new WebView();
                            webView.setVisible(true);
                            webView.getEngine().load(apod.getUrl());
                            //webView.setPrefSize(640,360);
                            System.out.println("after video");
                            //mainVbox.getChildren().add(wb);
                        }

                        titleLabel.setText(apod.getTitle());
                        contentLabel.setText("Copyright: "+apod.getCopyright()+"\nDate: "+apod.getDate()+"\nExplanation: "+apod.getExplanation());
                        searchBtn.setDisable(false);
                        btnBack.setDisable(false);

                    }

                }
                catch (Exception e) {
                    System.out.println("error: " +e.toString());}
            }).start();
       /* }
        else{
            new Thread(()->{
                Platform.runLater(()->{
                    msgContainer.getStyleClass().add("alert-warning");
                    msgTitle.setText("No internet ");
                    msgContent.setText("Check your internet connection and try again.");
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(()->{
                    msgContainer.setVisible(false);
                });
            }).start();

            //loadingLabel.setText("NO Internet");
        }*/

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

            VBox currentRoot = (VBox) this.btnBack.getScene().getRoot();

              // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);

              /*
              * // Creo la scene y el stage
            Scene scene = new Scene(root);
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
        * FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        try {
            Node view = loader.load();
           // content.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        * */

        /*
        * try {
            root=FXMLLoader.load(HelloApplication.class.getResource("menu-view.fxml"));
            stage=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        * */

    }

    public boolean getIsUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
        if(!isUser){
            btnSave.setVisible(false);
            btnReport.setVisible(false);
        }else {
            btnSave.setVisible(true);
            btnReport.setVisible(true);
        }
    }

    @FXML
    public void onSaveButtonCLick(ActionEvent actionEvent) {
        //Save JSON
    }

    public void onReportButtonCLick(ActionEvent actionEvent) {
        new Thread(()->{
            String dest = "reports/Report_APOD.pdf";
            btnReport.setDisable(true);
            btnBack.setDisable(true);
            try {
                getWeekImages();
                new ReportAPOD().createReport(dest,getWeekImages());
                openFile(dest);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
         //   week.add(LocalDate.now().minusDays(i));

            //colocar codigo dentro de un ciclo para obtener apods de todas las fechas de week
            URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9&date="+LocalDate.now().minusDays(i).toString());
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
        String comand="ping -c 1 api.nasa.gov";
        return (Runtime.getRuntime().exec(comand).waitFor()==0);
    }
}