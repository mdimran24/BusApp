package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StopPoint implements Serializable {

    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Lon")
    @Expose
    private Double lon;
    @SerializedName("Indicator")
    @Expose
    private String indicator;
    @SerializedName("StopLetter")
    @Expose
    private String stopLetter;
    @SerializedName("CommonName")
    @Expose
    private String commonName;
    @SerializedName("Locality")
    @Expose
    private String locality;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Lines")
    @Expose
    private Lines lines;
    @SerializedName("Modes")
    @Expose
    private Modes modes;
    @SerializedName("NaptanId")
    @Expose
    private String naptanId;
    @SerializedName("StationNaptan")
    @Expose
    private String stationNaptan;
    @SerializedName("StopType")
    @Expose
    private String stopType;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getStopLetter() {
        return stopLetter;
    }

    public void setStopLetter(String stopLetter) {
        this.stopLetter = stopLetter;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Lines getLines() {
        return lines;
    }

    public void setLines(Lines lines) {
        this.lines = lines;
    }

    public Modes getModes() {
        return modes;
    }

    public void setModes(Modes modes) {
        this.modes = modes;
    }

    public String getNaptanId() {
        return naptanId;
    }

    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }

    public String getStationNaptan() {
        return stationNaptan;
    }

    public void setStationNaptan(String stationNaptan) {
        this.stationNaptan = stationNaptan;
    }

    public String getStopType() {
        return stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Common Name: " + getCommonName());
        sb.append(", ");
        sb.append("Location: " + getLocality());
        sb.append(", ");
//        sb.append(getLines().getIdentifier().toString());
        return sb.toString();
    }


    public String toStringBuses() {
        String buses = "";
        if(getLines() != null) {
            for (int i = 0; i < getLines().getIdentifier().size(); i++) {
                buses += getLines().getIdentifier().get(i).toString() + ", ";
            }
        }
        return buses;
    }

}
