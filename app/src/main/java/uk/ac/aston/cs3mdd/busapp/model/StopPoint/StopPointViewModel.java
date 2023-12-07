package uk.ac.aston.cs3mdd.busapp.model.StopPoint;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;

public class StopPointViewModel extends ViewModel {
    private MutableLiveData<List<StopPoints>> StopPoints;

    public StopPointViewModel(){
        super();
        StopPoints = new MutableLiveData<>((new ArrayList<>()));
    }



    public void requestStops(StopRepository stopRepository) {
        if (StopPoints.getValue().size() == 0) {
            Call<StopPoints> userCall = stopRepository.getListOfStops(52.465247, -1.846744, "NaptanMarkedPoint", "b9294454", "ebabd3b41a342e5d4e3a432b282bdf88", "JSON" );
            userCall.enqueue(new Callback<Stop>() {
                @Override
                public void onResponse(Call<uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse> call, Response<uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse> response) {
                    if (response.isSuccessful()) {

                        Log.i("MDI", response.body().toString());
                        Log.i("MDI", response.toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse> call, Throwable t) {
                    // show error message to user
                    Log.i("MDI", "Error: " + t.toString());
                }
            });
        } else {
            Log.i("MDI", "Already got a list of stops, not getting any more");
        }
    }
    public void addAll(uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse list) {
        StopPointResponse.getValue().addAll(list.getStopPoints());
        StopPointResponse.setValue(StopPointResponse.getValue());
        Log.i("MDI", "Printing " + StopPointResponse.getValue().size() + " Users");
        for (uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse sp : StopPointResponse.getValue()) {
            Log.i("MDI", sp.toString());
        }
    }
}
