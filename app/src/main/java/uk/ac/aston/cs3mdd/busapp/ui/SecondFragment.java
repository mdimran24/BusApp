package uk.ac.aston.cs3mdd.busapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentSecondBinding;
import uk.ac.aston.cs3mdd.busapp.model.Coordinates;
import uk.ac.aston.cs3mdd.busapp.model.User;

public class SecondFragment extends Fragment  implements OnMapReadyCallback {

    private FragmentSecondBinding binding;
    private User user;
    private GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user = SecondFragmentArgs.fromBundle(getArguments()).getUser();
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textviewSecond.setText(user.toString());
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_secondFragment_to_navigation_notifications);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the user's location and move the camera
        Coordinates c = user.getLocation().getCoordinates();
        LatLng loc = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("Location of this user"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

}