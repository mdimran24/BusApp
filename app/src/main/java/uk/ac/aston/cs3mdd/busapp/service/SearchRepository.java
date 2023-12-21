package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchResponseCall;

public class SearchRepository {
    private TFWM tfwmService;

    public SearchRepository(TFWM searchService){
        this.tfwmService = searchService;
    }

    public Call<SearchResponseCall> getListofSearchResults(String query, String appId, String appKey, String format){
        return tfwmService.getSearchResults(query, appId, appKey, format);
    }
}
