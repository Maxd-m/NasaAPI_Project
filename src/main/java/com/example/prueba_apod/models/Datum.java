
package com.example.prueba_apod.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class Datum {

    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photographer")
    @Expose
    private String photographer;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("nasa_id")
    @Expose
    private String nasaId;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
