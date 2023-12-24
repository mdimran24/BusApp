package uk.ac.aston.cs3mdd.busapp.model.SearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchMatch implements Serializable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Lon")
    @Expose
    private Double lon;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Modes")
    @Expose
    private Modes modes;
    @SerializedName("TopMostParentId")
    @Expose
    private String topMostParentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Modes getModes() {
        return modes;
    }

    public void setModes(Modes modes) {
        this.modes = modes;
    }

    public String getTopMostParentId() {
        return topMostParentId;
    }

    public void setTopMostParentId(String topMostParentId) {
        this.topMostParentId = topMostParentId;
    }
}
