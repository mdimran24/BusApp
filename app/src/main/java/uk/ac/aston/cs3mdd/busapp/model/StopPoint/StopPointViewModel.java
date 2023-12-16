package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.busapp.model.LocationViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponse;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;

public class StopPointViewModel extends ViewModel {
    private MutableLiveData<StopPointsResponseCall> allStops;
    private LocationViewModel locModel;

    public StopPointViewModel() {
        super();
        allStops = new MutableLiveData<>();
    }

    public LiveData<StopPointsResponseCall> getAllStops() {
        return allStops;
    }

    public void requestStops(StopRepository stopRepository) {
        if (allStops.getValue() == null){
            Call<StopPointsResponseCall> stopCall = stopRepository.getListOfStops(-1.846744, 52.465247, "NaptanMarkedPoint", "b9294454", "ebabd3b41a342e5d4e3a432b282bdf88", "JSON");
            stopCall.enqueue(new Callback<StopPointsResponseCall>() {
                @Override
                public void onResponse(Call<StopPointsResponseCall> call, Response<StopPointsResponseCall> response) {
                    if (response.isSuccessful()) {
                        Log.i("MDI", response.toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<StopPointsResponseCall> call, Throwable t) {
                    // show error message to user
                    Log.i("MDI", "Error: " + t.toString());
                }
            });
        }else{
            Log.i("AJB", "Already got a list of users, not getting any more");
        }
    }
    public void addAll(StopPointsResponseCall list) {
            allStops.setValue(list);

//        Log.i("MDI", "Printing " + allStops.getValue().getStopPointsResponse().getTotal() + " Users");
//        for (StopPoint stop : allStops.getValue().getStopPointsResponse().getStopPoints().getStopPoint()) {
//            Log.i("MDI", stop.toString());
//        }
    }
}