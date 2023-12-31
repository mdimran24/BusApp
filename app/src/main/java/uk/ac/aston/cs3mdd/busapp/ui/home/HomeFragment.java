package uk.ac.aston.cs3mdd.busapp.ui.home;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.Module;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentHomeBinding;
import uk.ac.aston.cs3mdd.busapp.model.LocationViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;

public class HomeFragment extends Fragment {

    private LocationViewModel locModel;
    private FragmentHomeBinding binding;
    private StopPointViewModel viewModel;
    private RecyclerView mRecyclerView;
    private StopListAdapter mAdapter;
    private List<StopPoint> stopsList;
    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(StopPointViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        stopsList = new ArrayList<>();
        textView = binding.textHome;
        if(stopsList.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Bus Stops Near You, Please Make Sure Location Is Enabled");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);


        final Observer<StopPointsResponseCall> stopListObserver = new Observer<StopPointsResponseCall>() {
            @Override
            public void onChanged(@Nullable final StopPointsResponseCall stopPointsResponseCall) {

//                Toast.makeText(getContext(), "We got a list of " + stopPointsResponseCall.getStopPointsResponse().getTotal() + " stops", Toast.LENGTH_LONG).show();
                if(stopPointsResponseCall.getStopPointsResponse() != null) {
                    stopsList.addAll(stopPointsResponseCall.getStopPointsResponse().getStopPoints().getStopPoint());
                    stopsList.removeIf(spr -> (spr.getLines().getIdentifier().isEmpty()));
                    mAdapter.updateData(stopsList);
                    textView.setVisibility(View.INVISIBLE);
                }


            }
        };
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.tfwm.org.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TFWM service = retrofit.create(TFWM.class);

        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new StopListAdapter(getContext(), stopsList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locModel.getCurrentLocation().observe(getViewLifecycleOwner(), loc -> {
                    if (loc != null) {
                        viewModel.requestStops(new StopRepository(service), loc);
                        Log.i("MDI Location", loc.toString());
                    }
                });
        viewModel.getAllStops().observe(getViewLifecycleOwner(), stopListObserver);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}