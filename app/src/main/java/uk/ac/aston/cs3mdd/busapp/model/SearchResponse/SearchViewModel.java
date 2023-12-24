package uk.ac.aston.cs3mdd.busapp.model.SearchResponse;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.busapp.service.SearchRepository;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<SearchResponseCall> allSearches;

    public SearchViewModel(){
        super();
        allSearches = new MutableLiveData<>();
    }

    public MutableLiveData<SearchResponseCall> getAllSearches() {
        return allSearches;
    }

    public void requestSearchResults(SearchRepository searchRepository, String query) {
        allSearches = new MutableLiveData<>();
            Call<SearchResponseCall> searchCall = searchRepository.getListofSearchResults(query, "b9294454", "ebabd3b41a342e5d4e3a432b282bdf88", "JSON");
            searchCall.enqueue(new Callback<SearchResponseCall>() {
                @Override
                public void onResponse(Call<SearchResponseCall> call, Response<SearchResponseCall> response) {
                    if (response.isSuccessful()) {
//                        Log.i("MDI Searches", response.toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SearchResponseCall> call, Throwable t) {
                    // show error message to user
                    Log.i("MDI Searches", "Error: " + t.toString());
                }
            });

    }

    public void addAll(SearchResponseCall list) {
        allSearches.setValue(list);
//        Log.i("MDI Searches", "Printing " + allSearches.getValue().getSearchResponse().getTotal() + " Searches");
//        for (SearchMatch search : allSearches.getValue().getSearchResponse().getMatches().getSearchMatch()) {
//            Log.i("MDI Searches", search.getName().toString());
//        }
    }
}

