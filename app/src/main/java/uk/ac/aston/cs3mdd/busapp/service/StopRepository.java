package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponse;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;

public class StopRepository {
    private TFWM tfwmService;

    public StopRepository(TFWM stopService){
        this.tfwmService = stopService;
    }

    public Call<StopPointsResponseCall> getListOfStops(double lat, double lon, String stopType, String appId, String appKey, String format){
        return tfwmService.getStops(lon, lat, stopType, appId, appKey, format);


    }

}
