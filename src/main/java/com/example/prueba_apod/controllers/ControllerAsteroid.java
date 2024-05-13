package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.AsteroidNeoWs;
import com.example.prueba_apod.models.Body;
import com.example.prueba_apod.models.NearEarthObjects;
import com.example.prueba_apod.reports.ReportAPOD;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAsteroid implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private VBox contentVbox;
    @FXML
    private WebView webView;
    @FXML
    private TableView tableAsteroids;
    @FXML
    private DatePicker datePickerstart;
    @FXML
    private DatePicker datePickerend;
    @FXML
    private Panel mainPanel;
    @FXML
    private Label loadingLabel;
    @FXML
    private Button btnBack;
    @FXML
    private Button searchBtn;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReport;

    private AsteroidNeoWs apod;
    private Body body;
    private List<Body> bodylist=new ArrayList<>();
    private NearEarthObjects NEO;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isUser;
    private boolean flgLoading;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePickerstart.setValue(LocalDate.now());
        //datePickerend.setValue(LocalDate.now());
        //webView.setVisible(false);
        mainPanel.getStyleClass().add("panel-default");



        // Scene sc = new Scene(new VBox());

        //sc.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
    }

    @FXML
    protected void onSearchButtonClick() throws IOException {

        searchBtn.setDisable(true);

        URL url = new URL("https://api.nasa.gov/neo/rest/v1/feed?start_date="+datePickerstart.getValue().toString()+"&end_date="+datePickerstart.getValue().toString()+"&api_key=DEMO_KEY");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if(responseCode!=200&&responseCode!=429){
            throw new RuntimeException("Error "+responseCode);
        }else{
            //abrir scanner para leer datos:
            StringBuilder infoString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                infoString.append(scanner.nextLine());
            }

            scanner.close();

            //imprimir info
            System.out.println(infoString);
            Gson gson = new Gson();
            String aux= String.valueOf(infoString);
            apod = gson.fromJson(aux,AsteroidNeoWs.class);

            //NEO= apod.getNearEarthObjects();
            //System.out.println(apod.getLinks().getSelf());
            //System.out.println(apod.getNearEarthObjects().getBody());
            //System.out.println(NEO.getBody().get(0).getAbsoluteMagnitudeH());
            bodylist=apod.getNearEarthObjects().getBody();
            //int i=0;
            //System.out.println(bodylist.get(0));
//            while(bodylist.get(i)!=null){
//                System.out.println(bodylist.get(i).toString());
//            }
            tableAsteroids.setItems(FXCollections.observableArrayList(bodylist));
//            FXCollections.observableArrayList();
            //body=bodylist.get(0);
            //titleLabel.setText(body.getName());
//            contentLabel.setText("Copyright: "+apod.getElementCount()+"\nDate: "+body.getCloseApproachData()+"\nExplanation: "+body.getId());
            searchBtn.setDisable(false);

            //sc.setCursor(Cursor.DEFAULT);

        }
    }

    public void onDatePicked(ActionEvent actionEvent) {
        if(datePickerstart.getValue().isAfter(LocalDate.now())){
            showMessage("Alert","Unable to display an Asteroid from a future date", Alert.AlertType.WARNING);
            datePickerstart.setValue(LocalDate.now());
        }
        else if(datePickerstart.getValue().isBefore(LocalDate.of(1995,6,20))){
            showMessage("Alert","Unable to display an Asteroid from a date before June 20, 1995", Alert.AlertType.WARNING);
            datePickerstart.setValue(LocalDate.of(1995,6,20));
        }
        System.out.println(datePickerstart.getValue());

    }

//    public void onDatePicked(ActionEvent actionEvent) {
//        if(datePickerstart.getValue().isAfter(datePickerend.getValue())){
//            showMessage("Alert","Unable to display Asteroids from a date past end_date", Alert.AlertType.WARNING);
//            datePickerstart.setValue(LocalDate.now());
//        }
//        else if(datePickerstart.getValue().isBefore(LocalDate.of(1995,6,20)) || datePickerend.getValue().isBefore(LocalDate.of(1995,6,20))){
//            showMessage("Alert","Unable to display Asteroids from a date before June 20, 1995", Alert.AlertType.WARNING);
//            datePickerstart.setValue(LocalDate.of(1995,6,20));
//            datePickerend.setValue(LocalDate.now());
//        }
//        //System.out.println(datePicker.getValue());
//
//    }

    private void showMessage(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

//    public String getdate(){
//        String aux="";
//        aux=aux+datePickerstart.getValue();
//        return aux;
//    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {

          try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prueba_apod/views/menu-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            //Controller_test controlador = loader.getController();

            VBox currentRoot = (VBox) this.btnBack.getScene().getRoot();

              // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);


        } catch (IOException ex) {
            Logger.getLogger(ControllerAsteroid.class.getName()).log(Level.SEVERE, null, ex);
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
        String dest = "reports/Report_Asteroid.pdf";
//        try {
//            new ReportAPOD().createReport(dest,bodylist);
//            openFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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

//    private List<APOD> getWeekImages() throws Exception{
//        List<AsteroidNeoWs> apodList = new ArrayList<>();
//        List<LocalDate> week = new ArrayList<>();
//        //week.add(LocalDate.now());
//        for (int i = 0; i < 7; i++) {
//            week.add(LocalDate.now().minusDays(i));
//        }
//
//        //colocar codigo dentro de un ciclo para obtener apods de todas las fechas de week
//        URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9&date="+week.get(0).toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.connect();
//
//        int responseCode = conn.getResponseCode();
//        if(responseCode!=200&&responseCode!=429){
//            throw new RuntimeException("Error "+responseCode);
//        }
//        else {
//            //abrir scanner para leer datos:
//            StringBuilder infoString = new StringBuilder();
//            Scanner scanner = new Scanner(url.openStream());
//
//            while (scanner.hasNext()) {
//                infoString.append(scanner.nextLine());
//            }
//
//            scanner.close();
//
//            //imprimir info
//            System.out.println(infoString);
//            Gson gson = new Gson();
//            String aux = String.valueOf(infoString);
//            apod = gson.fromJson(aux, AsteroidNeoWs.class);
//            apodList.add(apod);
//        }
//
//
//        return apodList;
//    }
}
/*
            * try {
                // Obtener la URL de la imagen de la API
                String imageUrl = apod.getUrl();
                System.out.println(imageUrl);

                // Crear un objeto URL a partir de la URL de la imagen
                URL imageURL = new URL(imageUrl);

                // Descargar la imagen desde la URL
                Image auxImage = new Image(imageURL.openStream());

                // Crear un ImageView con la imagen descargada
                ImageView imageView = new ImageView(auxImage);


                // Agregar el ImageView al VBox
                mainVbox.getChildren().add(imageView);

                // Actualizar el texto de bienvenida
                welcomeText.setText("Imagen cargada correctamente");
            } catch (MalformedURLException e) {
                System.err.println("Error al crear la URL de la imagen: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error al descargar la imagen: " + e.getMessage());
            }
            * */



// auxImage=ImageIO;
//image=new ImageView()

/*
        * FXMLLoader loader = new FXMLLoader(getClass().getResource("test-view.fxml"));
        try {
            Node view = loader.load();
           // content.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        * */

        /*
        * try {
            root=FXMLLoader.load(HelloApplication.class.getResource("test-view.fxml"));
            stage=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        * */