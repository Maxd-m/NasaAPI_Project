package com.example.prueba_apod.controllers;

import com.example.prueba_apod.models.Example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.controlsfx.control.RangeSlider;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerIVL implements Initializable
{
    @FXML
    private VBox ap;
    @FXML
    private HBox paginado;
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
    private TextField input_search, maxt, mint;
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
    @FXML
    private RangeSlider sl;
    private boolean ban1=true, ban2=true, ban3=true;

    private HttpClient client = HttpClients.custom().build();
    Gson gson = new Gson();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mint.setText("1920");
        maxt.setText("2024");
        sl.setLowValue(1920);
        sl.setHighValue(2024);
        mint.textProperty().addListener((observable, oldvalue, newvalue)->
        {
            try {
                if (!mint.getText().isBlank())
                {
                    int b = Integer.parseInt(newvalue);
                    if (b >= 1920 && b <= 2024 && !maxt.getText().isBlank() && b <= Integer.parseInt(maxt.getText())) {
                        sl.setLowValue(b);
                    }
                }
            } catch (Exception e) {sl.setLowValue(1920);}
        });
        sl.highValueProperty().addListener((observable, oldvalue, newvalue)->
        {
            maxt.setText(String.valueOf((int)sl.getHighValue()));
        });
        sl.lowValueProperty().addListener((observable, oldvalue, newvalue)->
        {
            mint.setText(String.valueOf((int)sl.getLowValue()));
        });
        maxt.textProperty().addListener((observable, oldvalue, newvalue)->
        {
            try {
                if (!maxt.getText().isBlank())
                {
                    int b = Integer.parseInt(newvalue);
                    if (b >= 1920 && b <= 2024) {
                        sl.setHighValue(b);
                    }
                }
            }catch (Exception e){sl.setHighValue(2024);}
        });
    }
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
        System.out.println(sl.getHighValue());
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
        System.out.println((int) sl.getHighValue()+"");
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
    protected void onSearchButtonClick() throws IOException, InterruptedException
    {
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws MalformedURLException
            {
                i = 0;
                size = 5;
                String urls = "https://images-api.nasa.gov/search?q=" + getsearch() + "&year_start="+(int)sl.getLowValue()+"&year_end="+(int)sl.getHighValue();
                URL url = new URL(filterurl(urls));
                System.out.println(filterurl(urls));
                client = HttpClients.custom().build();
                HttpGet request = new HttpGet(String.valueOf(url));
                HttpResponse response = null;
                String json = null;
                try {
                    response = client.execute(request);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    json = rd.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                example = gson.fromJson(json, new TypeToken<Example>() {
                }.getType());
                if (example.getCollection().getItems().size() < 5) {
                    size = example.getCollection().getItems().size();
                }
                System.out.println("Ta corriendo");
                new Thread(()->{
                        Platform.runLater(()->
                        {
                            try {
                                load();
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                }).start();
                paginado.visibleProperty().setValue(true);
                return null;
            }
        };
        if (!getsearch().isBlank()) {
            new Thread(task).start();
        }
        else {Alert alert = new Alert(Alert.AlertType.WARNING, "\n" +
                "The search is null");
        alert.show();}
    }

    protected void load() throws MalformedURLException {
        gp.getChildren().clear();
        try
                    {
                        if(!example.getCollection().getItems().isEmpty())
                        {
                            if (size<=5)
                            {
                                btnb.setDisable(true);
                            }
                            if(size<5)
                            {
                                btns.setDisable(true);
                            } else if (size<example.getCollection().getItems().size())
                            {
                                btns.setDisable(false);
                            }
                            while (i<size)
                            {
                                WebView wb= new WebView();
                                if(!example.getCollection().getItems().get(i).getData().get(0).getMediaType().equals("image"))
                                {
                                    if(example.getCollection().getItems().get(i).getData().get(0).getMediaType().equals("audio"))
                                    {
                                        wb.getEngine().load("https://images.assetsdelivery.com/compings_v2/maiborodin/maiborodin2010/maiborodin201000019.jpg");
                                        Label label=new Label("Audio - "+ example.getCollection().getItems().get(i).getData().get(0).getTitle());
                                        label.setMaxWidth(300);
                                        label.setStyle("-fx-text-fill: white");
                                        label.setAlignment(Pos.CENTER);
                                        gp.add(label, i, 1);
                                    }
                                    else
                                    {
                                        Label label=new Label("Video - "+ example.getCollection().getItems().get(i).getData().get(0).getTitle());
                                        label.setMaxWidth(300);
                                        label.setStyle("-fx-text-fill: white");
                                        label.setAlignment(Pos.CENTER);
                                        gp.add(label, i, 1);
                                        List<String> hrefs = (example.getCollection().getItems().get(i).getHrefs());
                                        int x =0;
                                        String au;
                                        while (x<hrefs.size())
                                        {
                                            au=hrefs.get(x).substring(hrefs.get(x).length()-4);
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
                                }
                                else {
                                    Label label=new Label("Image - "+ example.getCollection().getItems().get(i).getData().get(0).getTitle());
                                    label.setMaxWidth(300);
                                    label.setStyle("-fx-text-fill: white");
                                    label.setAlignment(Pos.CENTER);
                                    gp.add(label, i, 1);
                                    wb.getEngine().load(modifyurl(example.getCollection().getItems().get(i).getHrefs().get(0)));
                                }
                                changeStyle(wb);
                                wb.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
                                {
                                    WebView wbb= (WebView) event.getPickResult().getIntersectedNode();
                                    wbb.getEngine().reload();
                                    Integer col=GridPane.getColumnIndex(wbb);
                                    try {
                                        getDetails(col);
                                    } catch (MalformedURLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                gp.add(wb, i, 0);
                                i++;
                            }
                        } else
                        {
                            Label l = new Label("No results found :(");
                            l.setStyle("-fx-text-fill: white; -fx-font-size: 50;");
                            paginado.setVisible(false);
                            gp.add(l,0,0);
                        }
                    } catch (Exception e)
                    {
                        i=i+1;
                        load();
                    }
    }

    protected void getDetails(int index) throws MalformedURLException
    {
        WebView wb=new WebView();
        changeStyle(wb);
        wb.setMaxSize(500,300);
        wb.setPrefSize(500,300);
        ArrayList<Node>arrayList=new ArrayList<>();
        for(int in=0; in<(gp.getChildren().size());in++)
        {
            arrayList.add(gp.getChildren().get(in));
            System.out.println(arrayList.size());
        }
        Button btncomeback = new Button();
        Button btndown = new Button();
        btncomeback.setGraphic(new FontIcon("fas-arrow-alt-circle-left"));
        btncomeback.setStyle("-fx-background-color: #ececf1; -fx-font-size: 20");
        btncomeback.setOnAction((V)->
        {
            gp.getChildren().clear();
            for(int il=0; il<arrayList.size(); il=il+2)
            {
                wb.getEngine().load(null);
                gp.add(arrayList.get(il+1),il,0);
                gp.add(arrayList.get(il),il,1);
            }
        });
        btndown.setOnAction((y)->
        {

        });
        if(example.getCollection().getItems().get(index).getData().get(0).getMediaType().equals("image")){
        wb.getEngine().load(modifyurl(example.getCollection().getItems().get(index).getHrefs().get(0)));
        }
        if(example.getCollection().getItems().get(index).getData().get(0).getMediaType().equals("video"))
        {
            List<String> hrefs = example.getCollection().getItems().get(index).getHrefs();
            int x =0;
            String au;
            while (x<hrefs.size())
            {
                au=hrefs.get(x).substring(hrefs.get(x).length()-4);
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
        gp.add(wb, 1,0);
        gp.add(btndown,1,1);
        gp.add(createlabel(index), 2,0);
        gp.add(btncomeback, 0, 0);
    }
    protected Node createlabel(int index)
    {
        ScrollPane sc=new ScrollPane();
        String title=example.getCollection().getItems().get(index).getData().get(0).getTitle();
        String description=example.getCollection().getItems().get(index).getData().get(0).getDescription();
        String id=example.getCollection().getItems().get(index).getData().get(0).getNasaId();
        String data=example.getCollection().getItems().get(index).getData().get(0).getDateCreated();
        String center=example.getCollection().getItems().get(index).getData().get(0).getCenter();

        String secondary=example.getCollection().getItems().get(index).getData().get(0).getPhotographer();
        Label label=new Label("Title: "+title
                +"\nNASA ID: "+id+"\n\nDescription: "+description+"\nDate: "+data+"\nCenter: "+center);
        label.setStyle("-fx-text-fill: white");
        try {
        String keywords = example.getCollection().getItems().get(index).getData().get(0).getKeywords().get(0);
        for (int i = 1; i < example.getCollection().getItems().get(index).getData().get(0).getKeywords().size(); i++) {
            keywords = keywords + ", " + example.getCollection().getItems().get(index).getData().get(0).getKeywords().get(i);
        }
        label.setText(label.getText()+"\nKeywords: "+keywords);
    }catch (Exception e){}
        label.setWrapText(true);
        label.setMinHeight(100);
        label.setMinWidth(100);
        label.setMaxSize(500,500);
        sc.setContent(label);
        sc.setMaxSize(500,500);
        sc.setStyle("-fx-background: black;\n -fx-background-color: black");
        sc.getContent().setStyle(";-fx-background-color: black; -fx-text-fill: white; ");
        return sc;
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
            btns.setDisable(true);
            System.out.println(size);
        }
        btnb.setDisable(false);
        gp.getChildren().clear();
        load();
    }
}
