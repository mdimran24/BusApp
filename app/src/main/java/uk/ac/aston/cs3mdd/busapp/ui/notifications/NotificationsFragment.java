package uk.ac.aston.cs3mdd.busapp.ui.notifications;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.databinding.FragmentNotificationsBinding;
import uk.ac.aston.cs3mdd.busapp.model.LocationViewModel;
import uk.ac.aston.cs3mdd.busapp.model.User;
import uk.ac.aston.cs3mdd.busapp.model.UserListAdapter;
import uk.ac.aston.cs3mdd.busapp.model.UsersViewModel;
import uk.ac.aston.cs3mdd.busapp.service.RandomUser;
import uk.ac.aston.cs3mdd.busapp.service.UserRepository;

public class NotificationsFragment extends Fragment {
    private UsersViewModel viewModel;
    private FragmentNotificationsBinding binding;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        viewModel.getAllUsers().observe(getViewLifecycleOwner(), userListObserver);

        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
        mAdapter = new UserListAdapter(getContext(), viewModel.getAllUsers().getValue());
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RandomUser service = retrofit.create(RandomUser.class);
        viewModel.requestRandomUsers(new UserRepository(service));





    }
}