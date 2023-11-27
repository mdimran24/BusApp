package uk.ac.aston.cs3mdd.busapp.ui.home;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.Module;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentHomeBinding;
import uk.ac.aston.cs3mdd.busapp.model.LocationViewModel;

public class HomeFragment extends Fragment {
//    private HomeViewModel model;
    private LocationViewModel model;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        binding.updatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setLocationUpdates(!model.getLocationUpdates().getValue());
                if (model.getLocationUpdates().getValue()) {
                    binding.updatesButton.setText("Stop Location Updates");
                    Log.i(MainActivity.TAG, "Location Updates Started");
                } else {
                    binding.updatesButton.setText("Start Location Updates");
                    Log.i(MainActivity.TAG, "Location Updates Stopped");
                }
            }
        });
        updateUI();

    }

    private void updateUI() {
        binding.latitude.setText("Not Set");
        binding.longitude.setText("Not Set");
        binding.timestamp.setText("No timestamp");
        model.getCurrentLocation().observe(getViewLifecycleOwner(), loc -> {
            if (loc != null) {
                // Update the UI.
                binding.latitude.setText("" + loc.getLatitude());
                binding.longitude.setText("" + loc.getLongitude());
                Date date = Calendar.getInstance().getTime();
                date.setTime(loc.getTime());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                binding.timestamp.setText(strDate);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    String addressFormatted = address + "\n" + city + "\n" + state + "\n" +
                            country + "\n" + postalCode + "\n" + knownName;
                    binding.address.setText(addressFormatted);
                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address\n"+e.getMessage());
                }
            }

        });
//        binding.textviewTitle.setText(title);
//        binding.textviewDescription.setText(m.getDescription());
//        binding.textviewLo.setText(m.getLearningOutcomes());
    }
    public void createRandomModule() {
//        Module other = new Module();
//        other.setTitle("Module " + other.getCode());
//        other.setCredits(15);
//        other.setDescription("Description of " + other.getTitle());
//        other.setLearningOutcomes("L01 ");
//        model.addModule(other);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}