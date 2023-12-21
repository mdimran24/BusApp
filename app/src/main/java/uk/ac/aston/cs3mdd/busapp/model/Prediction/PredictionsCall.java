package uk.ac.aston.cs3mdd.busapp.model.Prediction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PredictionsCall implements Serializable {

    @SerializedName("Predictions")
    @Expose
    private Predictions predictions;

    public Predictions getPredictions() {
        return predictions;
    }

    public void setPredictions(Predictions predictions) {
        this.predictions = predictions;
    }
}

