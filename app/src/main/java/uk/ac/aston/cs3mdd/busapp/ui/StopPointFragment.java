package uk.ac.aston.cs3mdd.busapp.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentStopPointBinding;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.Prediction;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsCall;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsViewModel;
import uk.ac.aston.cs3mdd.busapp.model.PredictionListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoints;
import uk.ac.aston.cs3mdd.busapp.model.location.Coordinates;
import uk.ac.aston.cs3mdd.busapp.service.PredictionRepository;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;

public class StopPointFragment extends Fragment implements OnMapReadyCallback {
    private StopPoint stopPoint;
    private FragmentStopPointBinding binding;
    private GoogleMap mMap;
    private PredictionsViewModel viewModel;
    private RecyclerView mRecyclerView;
    private PredictionListAdapter mAdapter;
    private List<Prediction> predictionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        stopPoint = StopPointFragmentArgs.fromBundle(getArguments()).getStopPoint();
        viewModel = new ViewModelProvider(requireActivity()).get(PredictionsViewModel.class);
        binding = FragmentStopPointBinding.inflate(inflater, container, false);
        predictionList = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StringBuilder sb = new StringBuilder();
        sb.append("Bus Lines: " + stopPoint.toStringBuses());
        sb.append("\n");
        sb.append("Road: " + stopPoint.getCommonName());
        sb.append("\n");
        sb.append("Area: " + stopPoint.getLocality());


        binding.textviewSecond.setText(sb);
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StopPointFragment.this)
                        .navigate(R.id.action_stopPointFragment_to_navigation_home);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Observer<PredictionsCall> predictionsListObserver = new Observer<PredictionsCall>() {
            @Override
            public void onChanged(@Nullable final PredictionsCall predictionCall) {

                predictionList.addAll(predictionCall.getPredictions().getPrediction());
                mAdapter.updateData(predictionList);
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

        mAdapter = new PredictionListAdapter(getContext(), predictionList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.requestPredictions(new PredictionRepository(service), StopPointFragment.this.stopPoint.getId());
        viewModel.getAllPredictions().observe(getViewLifecycleOwner(), predictionsListObserver);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the user's location and move the camera
        Coordinates c = new Coordinates();
        c.setLongitude(stopPoint.getLon());
        c.setLatitude(stopPoint.getLat());
        LatLng loc = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("Location of this stop"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}