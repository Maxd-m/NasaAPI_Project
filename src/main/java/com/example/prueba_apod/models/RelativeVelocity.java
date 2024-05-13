package com.example.prueba_apod.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class RelativeVelocity {

@SerializedName("kilometers_per_second")
@Expose
private String kilometersPerSecond;
@SerializedName("kilometers_per_hour")
@Expose
private String kilometersPerHour;
@SerializedName("miles_per_hour")
@Expose
private String milesPerHour;

public String getKilometersPerSecond() {
return kilometersPerSecond;
}

public void setKilometersPerSecond(String kilometersPerSecond) {
this.kilometersPerSecond = kilometersPerSecond;
}

public String getKilometersPerHour() {
return kilometersPerHour;
}

public void setKilometersPerHour(String kilometersPerHour) {
this.kilometersPerHour = kilometersPerHour;
}

public String getMilesPerHour() {
return milesPerHour;
}

public void setMilesPerHour(String milesPerHour) {
this.milesPerHour = milesPerHour;
}

}