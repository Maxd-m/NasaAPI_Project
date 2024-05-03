package com.example.prueba_apod;

import com.example.prueba_apod.models.APOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;
import com.google.gson.Gson;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    //@FXML
    //private AnchorPane ap;
    @FXML
    private Button btnBack;
    @FXML
    private Button searchBtn;

    private APOD apod;

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    protected void onSearchButtonClick() throws IOException {

        //iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9
        //DEMO_KEY
        //video= 2024-04-14
        //imagen 2024-01-01
        //Scene sc = ap.getScene();
        //sc.setCursor(Cursor.WAIT);
        searchBtn.setDisable(true);

        URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=iofVxGYdLyuoYKgHtBS9DcdAXOoYitq60gm61Li9&date="+datePicker.getValue().toString());
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
            apod = gson.fromJson(aux,APOD.class);

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
                /*
                *  Media media = new Media(apod.getUrl());
               System.out.println(apod.getUrl());
               MediaPlayer mp = new MediaPlayer(media);
               mp.setAutoPlay(true);
               mediaView.setMediaPlayer(mp);
               //mediaView = new MediaView(mp);

               mediaView.setFitWidth(200);
               mediaView.setPreserveRatio(true);
                * */

               //mainVbox.getChildren().add(mediaView);

                image.setVisible(false);
               // WebView wb = new WebView();
                webView.setVisible(true);
                webView.getEngine().load(apod.getUrl());
                //webView.setPrefSize(640,360);
                System.out.println("after video");
                //mainVbox.getChildren().add(wb);


            }


            // auxImage=ImageIO;
            //image=new ImageView()

            titleLabel.setText(apod.getTitle());
            contentLabel.setText("Copyright: "+apod.getCopyright()+"\nDate: "+apod.getDate()+"\nExplanation: "+apod.getExplanation());
            searchBtn.setDisable(false);
            //sc.setCursor(Cursor.DEFAULT);

        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        webView.setVisible(false);
        mainPanel.getStyleClass().add("panel-default");

       // Scene sc = new Scene(new VBox());

       //sc.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {

          try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test-view.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            //InsertarServiciosController controlador = loader.getController();
            Controller_test controlador = loader.getController();

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

    }
}