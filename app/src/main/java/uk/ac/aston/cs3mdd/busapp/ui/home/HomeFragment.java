package uk.ac.aston.cs3mdd.busapp.ui.home;

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

import uk.ac.aston.cs3mdd.busapp.Module;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private HomeViewModel model;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textviewTitle;
        final TextView textViewDesc = binding.textviewDescription;
        final TextView textViewLO = binding.textviewLo;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        updateUI();

    }

    private void updateUI() {
        Module m = model.getCurrentModule().getValue();
        String title = m.getCode() + ": " + m.getTitle() + " (" + m.getCredits() + " credits)";
        binding.textviewTitle.setText(title);
        binding.textviewDescription.setText(m.getDescription());
        binding.textviewLo.setText(m.getLearningOutcomes());
    }
    public void createRandomModule() {
        Module other = new Module();
        other.setTitle("Module " + other.getCode());
        other.setCredits(15);
        other.setDescription("Description of " + other.getTitle());
        other.setLearningOutcomes("L01 ");
        model.addModule(other);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}