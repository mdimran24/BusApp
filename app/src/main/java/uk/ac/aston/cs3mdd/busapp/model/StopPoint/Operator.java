package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import java.io.Serializable;

public class Operator implements Serializable {
    private String Name;
    private String Code;
    private String Id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
