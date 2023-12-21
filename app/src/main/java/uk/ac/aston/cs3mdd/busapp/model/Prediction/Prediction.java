package uk.ac.aston.cs3mdd.busapp.model.Prediction;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

import uk.ac.aston.cs3mdd.busapp.model.StopPoint.Operator;

public class Prediction implements Serializable {

    @SerializedName("DestinationName")
    @Expose
    private String destinationName;
    @SerializedName("DestinationNaptanId")
    @Expose
    private String destinationNaptanId;
    @SerializedName("Direction")
    @Expose
    private String direction;
    @SerializedName("ExpectedArrival")
    @Expose
    private String expectedArrival;
    @SerializedName("ScheduledArrival")
    @Expose
    private String scheduledArrival;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("LineId")
    @Expose
    private String lineId;
    @SerializedName("LineName")
    @Expose
    private String lineName;
    @SerializedName("ModeName")
    @Expose
    private String modeName;
    @SerializedName("StopSequence")
    @Expose
    private String stopSequence;
    @SerializedName("NaptanId")
    @Expose
    private String naptanId;
    @SerializedName("PlatformName")
    @Expose
    private String platformName;
    @SerializedName("StationName")
    @Expose
    private String stationName;
    @SerializedName("TimeToLive")
    @Expose
    private String timeToLive;
    @SerializedName("TimeToStation")
    @Expose
    private String timeToStation;
    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Towards")
    @Expose
    private String towards;
    @SerializedName("VehicleId")
    @Expose
    private String vehicleId;
    @SerializedName("Operator")
    @Expose
    private Operator operator;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationNaptanId() {
        return destinationNaptanId;
    }

    public void setDestinationNaptanId(String destinationNaptanId) {
        this.destinationNaptanId = destinationNaptanId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(String expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public String getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(String scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getNaptanId() {
        return naptanId;
    }

    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getTimeToStation() {
        return timeToStation;
    }

    public void setTimeToStation(String timeToStation) {
        this.timeToStation = timeToStation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bus Line: " + getLineName());
        sb.append("Expected: " + getExpectedArrival());
        sb.append(" ");
        sb.append("Scheduled: " + getScheduledArrival());
        sb.append(" ");
        sb.append("Desination: " + getTowards());
        return sb.toString();
    }
}
