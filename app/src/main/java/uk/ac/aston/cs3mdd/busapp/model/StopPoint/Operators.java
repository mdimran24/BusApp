package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Operators implements Serializable {
    @SerializedName("Operator")
    @Expose
    private List<Operator> operator;

    public List<Operator> getOperator() {
        return operator;
    }

    public void setOperator(List<Operator> operator) {
        this.operator = operator;
    }
}
