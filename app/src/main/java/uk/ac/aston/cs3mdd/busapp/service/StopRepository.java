package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse;

public class StopRepository {
    private LocalStopPoint localStopPointService;

    public StopRepository(LocalStopPoint stopService){
        this.localStopPointService = stopService;
    }

    public Call<StopPointResponse> getListOfStops(double lat, double lon, String stopType, String appId, String appKey, String format){
        return localStopPointService.getStops(lon, lat, stopType, appId, appKey, format);
    }
}
