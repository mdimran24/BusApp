package uk.ac.aston.cs3mdd.busapp.model.SearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Matches implements Serializable {
    @SerializedName("SearchMatch")
    @Expose
    private List<SearchMatch> searchMatch;

    public List<SearchMatch> getSearchMatch() {
        return searchMatch;
    }

    public void setSearchMatch(List<SearchMatch> searchMatch) {
        this.searchMatch = searchMatch;
    }
}
