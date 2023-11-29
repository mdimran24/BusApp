package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.ac.aston.cs3mdd.busapp.model.UserList;

public interface RandomUser {
    @GET("api/?noinfo")
    Call<UserList> getUsers(@Query("results") Integer numUsers,
                            @Query("nat") String nat);
}
