package uk.ac.aston.cs3mdd.busapp.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentNotificationsBinding;

import uk.ac.aston.cs3mdd.busapp.model.LocationViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointsResponseCall;
import uk.ac.aston.cs3mdd.busapp.service.TFWM;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;

public class NotificationsFragment extends Fragment {

    private StopPointViewModel viewModel;
    private FragmentNotificationsBinding binding;
    private RecyclerView mRecyclerView;
    private StopListAdapter mAdapter;

    private List<StopPoint> stopsList;
    private LocationViewModel locModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(StopPointViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        stopsList = new ArrayList<>();
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        final Observer <StopPointsResponseCall> stopListObserver = new Observer<StopPointsResponseCall>() {

            @Override
            public void onChanged(@Nullable final StopPointsResponseCall stopPointsResponseCall) {
                Toast.makeText(getContext(), "We got a list of " + stopPointsResponseCall.getStopPointsResponse().getTotal() + " stops", Toast.LENGTH_LONG).show();
                stopsList.addAll(stopPointsResponseCall.getStopPointsResponse().getStopPoints().getStopPoint());
                stopsList.removeIf(spr -> (spr.getLines().getIdentifier().isEmpty()));
                mAdapter.updateData(stopsList);


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
//        Log.i("MDI", viewModel.getAllStops().getValue().toString());
        mAdapter = new StopListAdapter(getContext(), stopsList);

// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.requestStops(new StopRepository(service));

        viewModel.getAllStops().observe(getViewLifecycleOwner(), stopListObserver);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}