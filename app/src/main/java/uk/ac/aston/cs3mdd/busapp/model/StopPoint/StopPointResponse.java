package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

public class StopPointResponse {
    private CentrePoint CentrePoint;
    private StopPoints StopPoints;
    public uk.ac.aston.cs3mdd.busapp.model.StopPoint.CentrePoint getCentrePoint() {
        return CentrePoint;
    }

    public void setCentrePoint(uk.ac.aston.cs3mdd.busapp.model.StopPoint.CentrePoint centrePoint) {
        CentrePoint = centrePoint;
    }

    public uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoints getStopPoints() {
        return StopPoints;
    }

    public void setStopPoints(uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoints stopPoints) {
        StopPoints = stopPoints;
    }
}
