package uk.ac.aston.cs3mdd.busapp.model.Prediction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Predictions implements Serializable {
    @SerializedName("Prediction")
    @Expose
    private List<Prediction> prediction;

    public List<Prediction> getPrediction() {
        return prediction;
    }

    public void setPrediction(ArrayList<Prediction> prediction) {
        this.prediction = prediction;
    }
}
