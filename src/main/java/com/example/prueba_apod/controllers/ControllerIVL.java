package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.Example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerIVL
{
    @FXML
    private VBox ap;
    @FXML
    private FontIcon fim;
    @FXML
    private FontIcon fvi;
    @FXML
    private FontIcon fau;

    private int i=0, size=5;
    private boolean isUser;
    Example example;
    @FXML
    private TextField input_search;
    @FXML
    private Button btnBack;
    @FXML
    private Button searchBtn;
    @FXML
    private Button btnb;
    @FXML
    private Button btns;
    @FXML
    private GridPane gp;
    private boolean ban1=true, ban2=true, ban3=true;

    private org.apache.http.client.HttpClient client = HttpClients.custom().build();
    Gson gson = new Gson();

    @FXML
    protected String filterurl(String url)
    {
        String aux="%20landing&media_type=";
        if (ban1)
        {
            aux=aux+"image,";
        }
        if (ban3)
        {
            aux=aux+"video,";
        }
        if (ban2)
        {
            aux=aux+"audio,";
        }
        if(ban1 && ban2 && ban3)
        {
            aux="";
        }
        else
        {
            aux=aux.substring(0, aux.length()-1);
        }
        return url+aux;
    }
    @FXML
    protected void onSearchIButtonClick() throws IOException
    {
        ban1= !ban1;
        if (!ban1 && !ban2 && !ban3)
        {
            searchBtn.setDisable(true);
        }
        else
        {
            searchBtn.setDisable(false);
        }
        if (!ban1)
        {
            fim.setIconLiteral("far-times-circle");
            fim.setIconColor(Color.RED);
        }
        else
        {
            fim.setIconLiteral("far-check-circle");
            fim.setIconColor(Color.LAWNGREEN);
        }
    }
    @FXML
    protected void onSearchAButtonClick() throws IOException
    {
        ban2= !ban2;
        if (!ban2)
        {
            fau.setIconLiteral("far-times-circle");
            fau.setIconColor(Color.RED);
        }
        else
        {
            fau.setIconLiteral("far-check-circle");
            fau.setIconColor(Color.LAWNGREEN);
        }
        if (!ban1 && !ban2 && !ban3)
        {
            searchBtn.setDisable(true);
        }
        else
        {
            searchBtn.setDisable(false);
        }
    }
    @FXML
    protected void onSearchVButtonClick() throws IOException
    {
        ban3= !ban3;
        if (!ban1 && !ban2 && !ban3)
        {
            searchBtn.setDisable(true);
        }
        else
        {
            searchBtn.setDisable(false);
        }
        if (!ban3)
        {
            fvi.setIconLiteral("far-times-circle");
            fvi.setIconColor(Color.RED);
        }
        else
        {
            fvi.setIconLiteral("far-check-circle");
            fvi.setIconColor(Color.LAWNGREEN);
        }
    }
    @FXML
    protected void onSearchButtonClick() throws IOException
    {
        i=0;
        String urls="https://images-api.nasa.gov/search?q="+getsearch();
        URL url= new URL(filterurl(urls));
        System.out.println(filterurl(urls));
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
        example =gson.fromJson(json, new TypeToken<Example>(){}.getType());
        load();
    }

    protected void load() throws MalformedURLException {
        gp.getChildren().clear();
        if (size<=5)
        {
            btnb.setDisable(true);
        }
        else if (size==example.getCollection().getItems().size())
        {
            btns.setDisable(true);
        }
        while (i<size)
        {
            WebView wb= new WebView();
                System.out.println(example.getCollection().getItems().get(i).getData().get(0).getMediaType());
            if(!example.getCollection().getItems().get(i).getData().get(0).getMediaType().equals("image"))
            {
                List<String> hrefs = example.getCollection().getItems().get(i).getHrefs();
                int x =0;
                String au;
                while (x<hrefs.size())
                {
                    au=hrefs.get(x).substring(hrefs.get(x).length()-4);
                    System.out.println(au);
                    if (au.equals(".jpg"))
                    {
                        wb.getEngine().load(modifyurl(hrefs.get(x)));
                        x=hrefs.size();
                    }
                    else
                    {
                        x=x+1;
                    }
                }
            }
            else {
                wb.getEngine().load(modifyurl(example.getCollection().getItems().get(i).getHrefs().get(0)));
            }
            changeStyle(wb);
            wb.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
            {
                WebView wbb= (WebView) event.getPickResult().getIntersectedNode();
                wbb.getEngine().reload();
                Integer col=GridPane.getColumnIndex(wbb);
                try {
                    getDetails(wbb, col);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                wbb.setDisable(true);
            });
            gp.add(wb, i, 0);
            i++;
        }
    }

    protected void getDetails(WebView wb, int index) throws MalformedURLException {
        if(example.getCollection().getItems().get(index).getData().get(0).getMediaType().equals("video"))
        {
            List<String> hrefs = example.getCollection().getItems().get(index).getHrefs();
            int x =0;
            String au;
            while (x<hrefs.size())
            {
                au=hrefs.get(x).substring(hrefs.get(x).length()-4);
                System.out.println(au);
                if (au.equals(".mp4"))
                {
                    wb.getEngine().load(modifyurl(hrefs.get(x)));
                    x=hrefs.size();
                }
                else
                {
                    x=x+1;
                }
            }
        }
        if(example.getCollection().getItems().get(index).getData().get(0).getMediaType().equals("audio"))
        {
            List<String> hrefs = example.getCollection().getItems().get(index).getHrefs();
            int x =0;
            String au;
            while (x<hrefs.size())
            {
                au=hrefs.get(x).substring(hrefs.get(x).length()-4);
                System.out.println(au);
                if (au.equals(".mp3"))
                {
                    wb.getEngine().load(modifyurl(hrefs.get(x)));
                    x=hrefs.size();
                }
                else
                {
                    x=x+1;
                }
            }
        }
        gp.getChildren().clear();
        gp.add(wb, 0,0);
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
            keywords=keywords + ", "+example.getCollection().getItems().get(index).getData().get(0).getKeywords().get(i);
        }
        String secondary=example.getCollection().getItems().get(index).getData().get(0).getPhotographer();
        Label label=new Label("Titulo: "+title
                +"\nNASA ID: "+id+"\n\nDescripcion: "+description+"\nFecha: "+data+"\nCentro: "+center+"\nKeywords: "+keywords);
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
    protected String modifyurl(String var)
    {
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
            currentRoot.setStyle("-fx-background-image: null");
            // Reemplazo el contenido del contenedor actual con el nuevo contenido
            currentRoot.getChildren().setAll(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerAPOD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void onBButtonClick(ActionEvent actionEvent) throws MalformedURLException
    {
        if (size==example.getCollection().getItems().size())
        {
            size=size-(size%5);
            i=i-(size%5)-5;
        }
        else
        {
            size=i-5;
            i=i-10;
        }

        gp.getChildren().clear();
        load();
    }

    @FXML
    public void onSButtonClick(ActionEvent actionEvent) throws MalformedURLException
    {
        if (i+5<example.getCollection().getItems().size())
        {
            size=i+5;
        }
        else
        {
            size=example.getCollection().getItems().size();
        }
        btnb.setDisable(false);
        gp.getChildren().clear();
        load();
    }
}
