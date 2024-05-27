package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.*;
import com.example.prueba_apod.reports.ReportAsteroid;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ControllerAsteroid implements Initializable {
    @FXML
    private TableView<Body> tableAsteroids;
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

    private AsteroidNeoWs asteroidNeoWs;
    private Body body;
    private List<Body> bodylist=new ArrayList<>();
    private NearEarthObjects NEO;
    Gson gson = new Gson();


    private User currentUser = new User();

    private boolean isAdmin;
    private boolean isUser;
    private String key;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePickerstart.setValue(LocalDate.now());
        //datePickerend.setValue(LocalDate.now());
        mainPanel.getStyleClass().add("panel-default");
    }

    @FXML
    protected void onSearchButtonClick() throws IOException {

        searchBtn.setDisable(true);


        new Thread(()->{

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.nasa.gov/neo/rest/v1/feed?start_date="+datePickerstart.getValue().toString()+"&end_date="+datePickerstart.getValue().toString()+"&api_key="+getKey()))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            JsonObject gsonObj = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("near_earth_objects");

            Set<Map.Entry<String, JsonElement>> entries = gsonObj.entrySet();//will return members of your object
            List<Body> nearObjectsList = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry: entries) {
                System.out.println(entry.getKey());

                JsonArray gsonArr = entry.getValue().getAsJsonArray();
                for (JsonElement obj: gsonArr)
                {
                    JsonObject gsonItem = obj.getAsJsonObject();

                    Body nearObject = gson.fromJson(gsonItem, Body.class);
                    System.out.println(nearObject.getName());
                    nearObjectsList.add(nearObject);
                    bodylist=nearObjectsList;
                }
            }


            tableAsteroids.setItems(FXCollections.observableArrayList(nearObjectsList));
        }).start();
        loadingLabel.setText("LOADING COMPLETE");

    }

    public void onDatePicked(ActionEvent actionEvent) {
        if(datePickerstart.getValue().isAfter(LocalDate.now())){
            showMessage("Alert","Unable to display an Asteroid from a future date", Alert.AlertType.WARNING);
            datePickerstart.setValue(LocalDate.now());
        }
        else {
            if(datePickerstart.getValue().isBefore(LocalDate.of(1995,6,20))){
                showMessage("Alert","Unable to display an Asteroid from a date before June 20, 1995", Alert.AlertType.WARNING);
                datePickerstart.setValue(LocalDate.of(1995,6,20));
            }
            else {
                searchBtn.setDisable(false);
            }
        }
        System.out.println(datePickerstart.getValue());

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
            //Controller_test controlador = loader.getController();
            ControllerMenu controlador = loader.getController();
            controlador.setUser(isUser);
            controlador.setAdmin(isAdmin);

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
            btnSave.setDisable(true);
            btnReport.setVisible(true);
        }
    }

    @FXML
    public void onSaveButtonCLick(ActionEvent actionEvent) {
        //Save JSON
    }

    public void onReportButtonCLick(ActionEvent actionEvent) {
        String dest = "reports/Report_Asteroid.pdf";
        try {
            new ReportAsteroid().createPdf(dest,bodylist);
            openFile(dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
        //System.out.println(this.currentUser.getId());
    }
}