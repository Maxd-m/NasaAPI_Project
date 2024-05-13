package com.example.prueba_apod.models;

import java.util.List;
//import javax.annotation.Generated;
import com.example.prueba_apod.controllers.ControllerAsteroid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class NearEarthObjects {

@SerializedName("2024-05-13")
@Expose
private List<Body> Body;

public List<Body> getBody() {
return Body;
}

public void setBody(List<Body> Body) {
this.Body = Body;
}

}
