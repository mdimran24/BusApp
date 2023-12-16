package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import java.io.Serializable;

public class Root implements Serializable {
    private StopPointsResponse stopPointsResponse;

    public StopPointsResponse getStopPointsResponse() {
        return stopPointsResponse;
    }

    public void setStopPointsResponse(StopPointsResponse stopPointsResponse) {
        this.stopPointsResponse = stopPointsResponse;
    }
}
