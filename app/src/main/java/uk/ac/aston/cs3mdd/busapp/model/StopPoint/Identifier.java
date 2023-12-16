package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Identifier implements Serializable {


    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Uri")
    @Expose
    private String uri;
    @SerializedName("Operators")
    @Expose
    private Operators operators;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Operators getOperators() {
        return operators;
    }

    public void setOperators(Operators operators) {
        this.operators = operators;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( getName());
//        sb.append("Operator: " + getOperators().getOperator().toString());
        return sb.toString();
    }
}
