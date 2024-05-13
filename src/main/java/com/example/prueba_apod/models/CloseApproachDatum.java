package com.example.prueba_apod.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class CloseApproachDatum {

@SerializedName("close_approach_date")
@Expose
private String closeApproachDate;
@SerializedName("close_approach_date_full")
@Expose
private String closeApproachDateFull;
@SerializedName("epoch_date_close_approach")
@Expose
private Long epochDateCloseApproach;
@SerializedName("relative_velocity")
@Expose
private RelativeVelocity relativeVelocity;
@SerializedName("miss_distance")
@Expose
private MissDistance missDistance;
@SerializedName("orbiting_body")
@Expose
private String orbitingBody;

public String getCloseApproachDate() {
return closeApproachDate;
}

public void setCloseApproachDate(String closeApproachDate) {
this.closeApproachDate = closeApproachDate;
}

public String getCloseApproachDateFull() {
return closeApproachDateFull;
}

public void setCloseApproachDateFull(String closeApproachDateFull) {
this.closeApproachDateFull = closeApproachDateFull;
}

public Long getEpochDateCloseApproach() {
return epochDateCloseApproach;
}

public void setEpochDateCloseApproach(Long epochDateCloseApproach) {
this.epochDateCloseApproach = epochDateCloseApproach;
}

public RelativeVelocity getRelativeVelocity() {
return relativeVelocity;
}

public void setRelativeVelocity(RelativeVelocity relativeVelocity) {
this.relativeVelocity = relativeVelocity;
}

public MissDistance getMissDistance() {
return missDistance;
}

public void setMissDistance(MissDistance missDistance) {
this.missDistance = missDistance;
}

public String getOrbitingBody() {
return orbitingBody;
}

public void setOrbitingBody(String orbitingBody) {
this.orbitingBody = orbitingBody;
}

}
