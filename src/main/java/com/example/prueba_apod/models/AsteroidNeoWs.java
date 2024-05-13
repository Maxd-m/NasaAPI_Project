package com.example.prueba_apod.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class AsteroidNeoWs {

@SerializedName("links")
@Expose
private Links links;
@SerializedName("element_count")
@Expose
private Integer elementCount;
@SerializedName("near_earth_objects")
@Expose
private NearEarthObjects nearEarthObjects;

public Links getLinks() {
return links;
}

public void setLinks(Links links) {
this.links = links;
}

public Integer getElementCount() {
return elementCount;
}

public void setElementCount(Integer elementCount) {
this.elementCount = elementCount;
}

public NearEarthObjects getNearEarthObjects() {
return nearEarthObjects;
}

public void setNearEarthObjects(NearEarthObjects nearEarthObjects) {
this.nearEarthObjects = nearEarthObjects;
}

}
