package com.example.prueba_apod.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Kilometers {

@SerializedName("estimated_diameter_min")
@Expose
private Double estimatedDiameterMin;
@SerializedName("estimated_diameter_max")
@Expose
private Double estimatedDiameterMax;

public Double getEstimatedDiameterMin() {
return estimatedDiameterMin;
}

public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
this.estimatedDiameterMin = estimatedDiameterMin;
}

public Double getEstimatedDiameterMax() {
return estimatedDiameterMax;
}

public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
this.estimatedDiameterMax = estimatedDiameterMax;
}

}
