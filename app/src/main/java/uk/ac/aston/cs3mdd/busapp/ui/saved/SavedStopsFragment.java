package uk.ac.aston.cs3mdd.busapp.ui.saved;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import uk.ac.aston.cs3mdd.busapp.model.SavedStopListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;

public class SavedStopsFragment extends Fragment {

    private LocationViewModel locModel;
    private FragmentHomeBinding binding;
    private StopPointViewModel viewModel;
    private RecyclerView mRecyclerView;
    private SavedStopListAdapter mAdapter;
    private TextView textView;
    private ArrayList<String> stops;

    private ArrayList<StopPoint> savedStopPoints;





    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(StopPointViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        stops = new ArrayList<>();
        textView = binding.textHome;
        savedStopPoints = new ArrayList<>();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        for(String s : stops){
            StopPoint sp = new StopPoint();
            String[] seperated = s.split("` ");
            sp.setId(seperated[0]);
            sp.setCommonName(seperated[1]);
            sp.setLat(Double.valueOf(seperated[2]));
            sp.setLon(Double.valueOf(seperated[3]));
//            Log.i("Saved Stop", sp.getId().toString() + sp.getCommonName().toString() + sp.getLat().toString() + sp.getLon().toString());
                savedStopPoints.add(sp);

        }
        if(savedStopPoints.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Bus Stops Saved");
        }else{
            textView.setVisibility(View.INVISIBLE);
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new SavedStopListAdapter(getContext(), savedStopPoints);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}