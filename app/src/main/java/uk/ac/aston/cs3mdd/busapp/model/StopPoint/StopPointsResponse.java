package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StopPointsResponse implements Serializable {
    @SerializedName("CentrePoint")
    @Expose
    private CentrePoint centrePoint;
    @SerializedName("StopPoints")
    @Expose
    private StopPoints stopPoints;
    @SerializedName("Total")
    @Expose
    private Integer total;

    public CentrePoint getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(CentrePoint centrePoint) {
        this.centrePoint = centrePoint;
    }

    public StopPoints getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(StopPoints stopPoints) {
        this.stopPoints = stopPoints;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


//    @Override
//    public String toString() {
//        return(getStopPoints().toString());
//    }
}
