package uk.ac.aston.cs3mdd.busapp.ui;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentStopPointBinding;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.Prediction;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsCall;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.PredictionsViewModel;
import uk.ac.aston.cs3mdd.busapp.model.PredictionListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchMatch;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoints;
import uk.ac.aston.cs3mdd.busapp.model.location.Coordinates;
import uk.ac.aston.cs3mdd.busapp.service.PredictionRepository;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;

public class StopPointFragment extends Fragment implements OnMapReadyCallback {
    private StopPoint stopPoint;
    private SearchMatch searchMatch;
    private FragmentStopPointBinding binding;
    private GoogleMap mMap;
    private PredictionsViewModel viewModel;
    private RecyclerView mRecyclerView;
    private PredictionListAdapter mAdapter;
    private List<Prediction> predictionList;
    private ArrayList<String> stops;
    private String selectedStop;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        stopPoint = StopPointFragmentArgs.fromBundle(getArguments()).getStopPoint();
        searchMatch = StopPointFragmentArgs.fromBundle(getArguments()).getSearchMatch();

        viewModel = new ViewModelProvider(requireActivity()).get(PredictionsViewModel.class);
        binding = FragmentStopPointBinding.inflate(inflater, container, false);

        predictionList = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder;
        stops = new ArrayList<>();
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;
        try {

            if (stopPoint != null) {
                String buses = stopPoint.toStringBuses();
                addresses = geocoder.getFromLocation(stopPoint.getLat(), stopPoint.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                sb.append("Bus Lines: " + buses + "\n" + address);
            } else {
                addresses = geocoder.getFromLocation(searchMatch.getLat(), searchMatch.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                sb.append(address);
            }


        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error getting address\n" + e.getMessage());
        }

        if(stopPoint == null){
            selectedStop = searchMatch.getId() + "` " + searchMatch.getName() + "` " + searchMatch.getLat() + "` " + searchMatch.getLon();

        }else{
            selectedStop = stopPoint.getId() + "` " + stopPoint.getCommonName() + "` " + stopPoint.getLat() + "` " + stopPoint.getLon();
        }
        String filename = "savedStops";
        File file = new File(getContext().getFilesDir(), filename);


        FileInputStream fis = null;
        try {
            if(file.exists()) {
                fis = getContext().openFileInput(filename);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        stops.add(line);
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    // Error occurred when opening raw file for reading.
                } finally {
                    String contents = stringBuilder.toString();
                    Log.i("MDI Reader", stops.toString());
                    if(stops.contains(selectedStop)){
                        binding.buttonSave.setImageResource(R.drawable.baseline_bookmark_remove_24);
                    }else if(!stops.contains(selectedStop)){
                        binding.buttonSave.setImageResource(R.drawable.baseline_bookmark_add_24);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        binding.textviewSecond.setText(sb);

        binding.buttonSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!stops.contains(selectedStop)) {
                    stops.add(selectedStop);
                    Toast.makeText(getContext(), "Bookmark Saved", Toast.LENGTH_SHORT).show();
                    binding.buttonSave.setImageResource(R.drawable.baseline_bookmark_remove_24);
                } else{
                    stops.remove(selectedStop);
                    binding.buttonSave.setImageResource(R.drawable.baseline_bookmark_add_24);
                    Toast.makeText(getContext(), "Bookmark Removed", Toast.LENGTH_SHORT).show();
                }
                try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                    for (String stop : stops) {
                        fos.write((stop.toString() + "\n").getBytes());
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (searchMatch == null) {
//                    NavHostFragment.findNavController(StopPointFragment.this)
//                            .navigate(R.id.action_stopPointFragment_to_navigation_home);
//                }
//                if(stopPoint == null) {
//                    NavHostFragment.findNavController(StopPointFragment.this)
//                            .navigate(R.id.action_stopPointFragment_to_navigation_search);
//                }
//
//            }
//        });
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
        if (searchMatch == null) {
            viewModel.requestPredictions(new PredictionRepository(service), StopPointFragment.this.stopPoint.getId());
        } else {
            viewModel.requestPredictions(new PredictionRepository(service), searchMatch.getId());
        }
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
        if (searchMatch == null) {
            c.setLongitude(stopPoint.getLon());
            c.setLatitude(stopPoint.getLat());
        } else {
            c.setLatitude(searchMatch.getLat());
            c.setLongitude(searchMatch.getLon());
        }
        LatLng loc = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("Location of this stop"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}