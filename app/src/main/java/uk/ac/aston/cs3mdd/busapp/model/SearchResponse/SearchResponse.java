package uk.ac.aston.cs3mdd.busapp.model.SearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchResponse implements Serializable {
    @SerializedName("Matches")
    @Expose
    private Matches matches;
    @SerializedName("Query")
    @Expose
    private String query;
    @SerializedName("Total")
    @Expose
    private Integer total;

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
