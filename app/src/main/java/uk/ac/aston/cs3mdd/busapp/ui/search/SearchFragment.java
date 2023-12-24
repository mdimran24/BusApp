package uk.ac.aston.cs3mdd.busapp.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentSearchBinding;
import uk.ac.aston.cs3mdd.busapp.model.SearchListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchMatch;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchResponseCall;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;
import uk.ac.aston.cs3mdd.busapp.service.SearchRepository;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;
import uk.ac.aston.cs3mdd.busapp.ui.StopPointFragment;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;

    private List<SearchMatch> searchList;

    private String query;

    private RecyclerView mRecyclerView;
    private SearchListAdapter mAdapter;

    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        textView = binding.textSearch;
//        textView.setVisibility(View.INVISIBLE);
        searchList = new ArrayList<>();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new SearchListAdapter(getContext(), searchList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                searchList = new ArrayList<>();
                query = binding.searchText.getText().toString();
                if(!query.isEmpty()) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Searching");
                    final Observer<SearchResponseCall> searchListObserver = new Observer<SearchResponseCall>() {
                        @Override
                        public void onChanged(@Nullable final SearchResponseCall searchResponseCall) {
                            searchList.addAll(searchResponseCall.getSearchResponse().getMatches().getSearchMatch());
                            searchList.removeIf(spr -> (spr.getId().isEmpty()));
                            mAdapter.updateData(searchList);
                            if(searchList.size() == 0) {
                                textView.setVisibility(View.VISIBLE);
                                textView.setText("No Bus Stops Found, Please Amend Search");
                            }else{
                                textView.setVisibility(View.INVISIBLE);
                            }
                        }
                    };
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://api.tfwm.org.uk/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TFWM service = retrofit.create(TFWM.class);
                    viewModel.requestSearchResults(new SearchRepository(service), query);
                    viewModel.getAllSearches().observe(getViewLifecycleOwner(), searchListObserver);

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}