package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import java.io.Serializable;
import java.util.List;

public class Identifier implements Serializable {
    private String Id;
    private String Name;
    private String Type;
    private String Uri;
    private List<Operator> Operators;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public List<Operator> getOperators() {
        return Operators;
    }

    public void setOperators(List<Operator> operators) {
        Operators = operators;
    }
}
