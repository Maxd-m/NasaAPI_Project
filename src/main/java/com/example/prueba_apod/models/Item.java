
package com.example.prueba_apod.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import javax.annotation.Generated;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Generated("jsonschema2pojo")
public class Item {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("links")
    @Expose
    private List<Link> links;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<String> getHrefs() throws MalformedURLException {
        org.apache.http.client.HttpClient client = HttpClients.custom().build();
        Gson gson = new Gson();
        URL url= new URL(modifyurl(getHref()));
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
        List<String>hrefs=gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        return hrefs;
    }

    protected String modifyurl(String var)
    {
        var=var.replaceAll(" ", "%20");
        return var;
    }
}
