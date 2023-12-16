package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StopPoints implements Serializable {

    @SerializedName("StopPoint")
    @Expose
    private List<StopPoint> stopPoint;

    public List<StopPoint> getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(List<StopPoint> stopPoint) {
        this.stopPoint = stopPoint;
    }

    @Override
    public String toString() {
        return(getStopPoint().toString());
    }
}
