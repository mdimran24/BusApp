package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.Predictions;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsCall;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchResponseCall;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponse;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;

public interface TFWM {
    @GET("StopPoint?")
    Call<StopPointsResponseCall> getStops(@Query("lat") double lat,
                                          @Query("lon") double lon,
                                          @Query("stopTypes") String stopType,
                                          @Query("app_id") String appId,
                                          @Query("app_key") String appKey,
                                          @Query("formatter") String format);

    @GET("StopPoint/{id}/Arrivals?")
    Call<PredictionsCall> getPredictions(@Path("id") String id,
                                         @Query("app_id") String appId,
                                         @Query("app_key") String appKey,
                                         @Query("formatter") String format);

    @GET("StopPoint/Search/{query}?")
    Call<SearchResponseCall> getSearchResults(@Path("query") String query,
                                              @Query("app_id") String appId,
                                              @Query("app_key") String appKey,
                                              @Query("formatter") String format);


}
