package uk.ac.aston.cs3mdd.busapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentNotificationsBinding;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPointViewModel;
import uk.ac.aston.cs3mdd.busapp.model.User;
import uk.ac.aston.cs3mdd.busapp.model.UserListAdapter;
import uk.ac.aston.cs3mdd.busapp.service.LocalStopPoint;
import uk.ac.aston.cs3mdd.busapp.service.StopRepository;

public class NotificationsFragment extends Fragment {

    private StopPointViewModel viewModel;
    private FragmentNotificationsBinding binding;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        viewModel = new ViewModelProvider(requireActivity()).get(StopPointViewModel.class);
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(StopPointViewModel.class);

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Observer<List<User>> userListObserver = new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> userList) {
                // Update the UI
                mAdapter.updateData(userList);
            }
        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.tfwm.org.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocalStopPoint service = retrofit.create(LocalStopPoint.class);
        viewModel.requestStops(new StopRepository(service));



    }
}