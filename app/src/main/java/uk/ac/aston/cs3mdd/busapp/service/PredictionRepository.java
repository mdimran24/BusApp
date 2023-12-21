package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.Predictions;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsCall;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;

public class PredictionRepository {
    private TFWM tfwmService;

    public PredictionRepository(TFWM predictionService){
        this.tfwmService = predictionService;
    }

    public Call<PredictionsCall> getListOfPredictions(String id, String appId, String appKey, String format){
        return tfwmService.getPredictions(id, appId, appKey, format);
    }
}
