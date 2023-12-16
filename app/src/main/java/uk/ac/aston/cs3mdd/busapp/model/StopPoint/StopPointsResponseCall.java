package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopPointsResponseCall {
    @SerializedName("StopPointsResponse")
    @Expose
    private StopPointsResponse stopPointsResponse;

    public StopPointsResponse getStopPointsResponse() {
        return stopPointsResponse;
    }

    public void setStopPointsResponse(StopPointsResponse stopPointsResponse) {
        this.stopPointsResponse = stopPointsResponse;
    }


}
