package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.Example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerIVL
{
    private boolean isUser;
    Example example;
    @FXML
    private TextField input_search;
    @FXML
    private Button btnBack;
    @FXML
    private GridPane gp;
    private org.apache.http.client.HttpClient client = HttpClients.custom().build();
    Gson gson = new Gson();
    @FXML
    protected void onBackButtonClick() throws IOException {
    }
    @FXML
    protected void onDatePicked() {
    }
    @FXML
    protected void onSaveButtonClick() {

    }
    @FXML
    protected void onReportButtonClick() {

    }
    @FXML
    protected void onSearchButtonClick() throws IOException
    {
        URL url= new URL("https://images-api.nasa.gov/search?q="+getsearch());
        client = HttpClients.custom().build();
        HttpGet request = new HttpGet(String.valueOf(url));
        org.apache.http.HttpResponse response = null;
        String json=null;
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            json = rd.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load(json);
    }

    protected void load(String json) throws MalformedURLException {
        example =gson.fromJson(json, new TypeToken<Example>(){}.getType());
        System.out.println(example.getCollection().getItems().get(0).getHrefs().get(0));
        for (int i=0; i<5; i++)
        {
            WebView wb= new WebView();
            wb.getEngine().load(example.getCollection().getItems().get(i).getHrefs().get(2));
            changeStyle(wb);
            gp.add(wb, i, 0);
            wb.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
            {
                Node node=event.getPickResult().getIntersectedNode();
                Integer col=GridPane.getColumnIndex(node);
                Integer row=GridPane.getRowIndex(node);
                getDetails(node, col);
                node.setScaleZ(1);
            });
        }
    }

    protected void getDetails(Node node, int index)
    {
        gp.getChildren().clear();
        gp.add(node, 0,0);
        gp.add(createlabel(index), 1,0);
    }
    protected Label createlabel(int index)
    {
        String title=example.getCollection().getItems().get(index).getData().get(0).getTitle();
        String description=example.getCollection().getItems().get(index).getData().get(0).getDescription();
        String id=example.getCollection().getItems().get(index).getData().get(0).getNasaId();
        String data=example.getCollection().getItems().get(index).getData().get(0).getDateCreated();
        String center=example.getCollection().getItems().get(index).getData().get(0).getCenter();
        String keywords=example.getCollection().getItems().get(index).getData().get(0).getKeywords().get(0);
        for (int i=1; i<example.getCollection().getItems().get(index).getData().get(0).getKeywords().size(); i++)
        {
            keywords=keywords+", "+example.getCollection().getItems().get(index).getData().get(0).getKeywords().get(i);
        }
        String secondary=example.getCollection().getItems().get(index).getData().get(0).getPhotographer();
        Label label=new Label(title
                +"\n"+id+"\n\n"+description+"\n"+data+"\n"+center+"\n"+keywords);
        label.setStyle("-fx-text-fill: white");
        label.setWrapText(true);
        label.setMinHeight(100);
        label.setMinWidth(100);
        label.setMaxSize(500,500);
        return label;
    }
    protected String getsearch()
    {
        String var=input_search.getText();
        var=var.replaceAll(" ", "%20");
        return var;
    }

    protected void changeStyle(WebView wb)
    {
        wb.setMaxHeight(300);
        wb.setMaxWidth(300);
        wb.prefHeight(100);
        wb.prefWidth(100);
        wb.setPageFill(Color.TRANSPARENT);
    }
    protected void changeStyleNode(Node wb)
    {
        wb.maxHeight(300);
        wb.maxWidth(300);
        wb.prefHeight(300);
        wb.prefWidth(300);
        wb.autosize();
        wb.setDisable(true);
    }
    public boolean getIsUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
        if(!isUser){

        }else {

        }
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

}