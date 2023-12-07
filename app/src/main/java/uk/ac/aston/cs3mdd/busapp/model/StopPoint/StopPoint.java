package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import java.io.Serializable;
import java.util.List;

public class StopPoint implements Serializable {

    private Double Distance;
    private double Lat;
    private double Lon;
    private String CommonName;
    private String Locality;
    private int Id;

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCommonName());
        sb.append(" ");
        sb.append(getLocality());
        return sb.toString();
    }


}
