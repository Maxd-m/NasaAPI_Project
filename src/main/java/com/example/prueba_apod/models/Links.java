package com.example.prueba_apod.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Links {

@SerializedName("next")
@Expose
private String next;
@SerializedName("previous")
@Expose
private String previous;
@SerializedName("self")
@Expose
private String self;

public String getNext() {
return next;
}

public void setNext(String next) {
this.next = next;
}

public String getPrevious() {
return previous;
}

public void setPrevious(String previous) {
this.previous = previous;
}

public String getSelf() {
return self;
}

public void setSelf(String self) {
this.self = self;
}

}
