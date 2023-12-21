package uk.ac.aston.cs3mdd.busapp.model.SearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponseCall {

    @SerializedName("SearchResponse")
    @Expose
    private SearchResponse searchResponse;

    public SearchResponse getSearchResponse() {
        return searchResponse;
    }

    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }
}
