package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointResponse;

public interface LocalStopPoint {
    @GET("StopPoint/")
    Call<StopPointResponse> getStops(@Query("lat") double lat,
                                     @Query("lon") double lon,
                                     @Query("stopTypes") String stopType,
                                     @Query("app_id") String appId,
                                     @Query("app_key") String appKey,
                                     @Query("formatter") String format);
}
