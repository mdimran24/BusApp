package uk.ac.aston.cs3mdd.busapp.model.Prediction;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;
import uk.ac.aston.cs3mdd.busapp.service.PredictionRepository;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;

public class PredictionsViewModel extends ViewModel {
    private MutableLiveData<PredictionsCall> allPredictions;

    public PredictionsViewModel(){
        super();
    }

    public LiveData<PredictionsCall> getAllPredictions() {
        return allPredictions;
    }

    public void requestPredictions(PredictionRepository predictionRepository, String id) {
        allPredictions = new MutableLiveData<>();
            Call<PredictionsCall> predictionCall = predictionRepository.getListOfPredictions(id,"b9294454", "ebabd3b41a342e5d4e3a432b282bdf88", "JSON");
            predictionCall.enqueue(new Callback<PredictionsCall>() {
                @Override
                public void onResponse(Call<PredictionsCall> call, Response<PredictionsCall> response) {
                    if (response.isSuccessful()) {
//                        Log.i("MDI", response.toString());
                        addAll(response.body());

                    }
                }


                @Override
                public void onFailure(Call<PredictionsCall> call, Throwable t) {
                    // show error message to user
                    Log.i("MDI", "Error: " + t.toString());
                }
            });
        }
    public void addAll(PredictionsCall list) {
        list.getPredictions().getPrediction().removeIf(p -> p.getExpectedArrival() == null);
        list.getPredictions().getPrediction().sort(new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                LocalDateTime o1Time = LocalDateTime.parse(o1.getExpectedArrival(), formatter);
                LocalDateTime o2Time = LocalDateTime.parse(o2.getExpectedArrival(), formatter);
                Duration d = Duration.between(LocalDateTime.now(), o1Time);
                Duration d2 = Duration.between(LocalDateTime.now(), o2Time);
                return ((int) d.toMinutes()) - ((int) d2.toMinutes());
            }
        });

        Prediction prediction = new Prediction();
        prediction.setDestinationName("No Further Buses At The Moment");
        list.getPredictions().getPrediction().add(prediction);
        allPredictions.setValue(list);

        Log.i("MDI", allPredictions.getValue().getPredictions().getPrediction().size() + " Predictions");
//        for (Prediction prediction : allPredictions.getValue().getPredictions().getPrediction()) {
//            Log.i("MDI", prediction.toString());
//        }
    }
}
