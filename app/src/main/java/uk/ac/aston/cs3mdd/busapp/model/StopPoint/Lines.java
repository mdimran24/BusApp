package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lines implements Serializable {

    @SerializedName("Identifier")
    @Expose
    private List<Identifier> identifier;

    public List<Identifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public String toString() {
        for (Identifier i : identifier){
            return i.toString();
        }
        return null;
    }
}
