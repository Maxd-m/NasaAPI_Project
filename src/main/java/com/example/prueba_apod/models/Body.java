package com.example.prueba_apod.models;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Body {

    @SerializedName("links")
    @Expose
    private Links__1 links;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("neo_reference_id")
    @Expose
    private String neoReferenceId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("nasa_jpl_url")
    @Expose
    public String nasaJplUrl;
    @SerializedName("absolute_magnitude_h")
    @Expose
    private Double absoluteMagnitudeH;
    @SerializedName("estimated_diameter")
    @Expose
    private EstimatedDiameter estimatedDiameter;
    @SerializedName("is_potentially_hazardous_asteroid")
    @Expose
    public Boolean isPotentiallyHazardousAsteroid;
    @SerializedName("close_approach_data")
    @Expose
    private List<CloseApproachDatum> closeApproachData;
    @SerializedName("is_sentry_object")
    @Expose
    public Boolean isSentryObject;

    public Links__1 getLinks() {
        return links;
    }

    public void setLinks(Links__1 links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNasaJplUrl() {
        return nasaJplUrl;
    }

    public void setNasaJplUrl(String nasaJplUrl) {
        this.nasaJplUrl = nasaJplUrl;
    }

    public Double getAbsoluteMagnitudeH() {
        return absoluteMagnitudeH;
    }

    public void setAbsoluteMagnitudeH(Double absoluteMagnitudeH) {
        this.absoluteMagnitudeH = absoluteMagnitudeH;
    }

    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    public Boolean getIsPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public void setIsPotentiallyHazardousAsteroid(Boolean isPotentiallyHazardousAsteroid) {
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
    }

    public List<CloseApproachDatum> getCloseApproachData() {
        return closeApproachData;
    }

    public void setCloseApproachData(List<CloseApproachDatum> closeApproachData) {
        this.closeApproachData = closeApproachData;
    }

    public Boolean getIsSentryObject() {
        return isSentryObject;
    }

    public void setIsSentryObject(Boolean isSentryObject) {
        this.isSentryObject = isSentryObject;
    }
}
