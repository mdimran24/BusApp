package uk.ac.aston.cs3mdd.busapp.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.busapp.service.UserRepository;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<List<User>> allUsers;

    public UsersViewModel(){
        super();
        allUsers = new MutableLiveData<>(new ArrayList<>());

    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void requestRandomUsers(UserRepository userRepository) {
        if (allUsers.getValue().size() == 0) {
            Call<UserList> userCall = userRepository.getListOfUsers(25, "gb");
            userCall.enqueue(new Callback<UserList>() {
                @Override
                public void onResponse(Call<UserList> call, Response<UserList> response) {
                    if (response.isSuccessful()) {
                        Log.i("MDI", response.body().toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<UserList> call, Throwable t) {
                    // show error message to user
                    Log.i("MDI", "Error: " + t.toString());
                }
            });
        } else {
            Log.i("MDI", "Already got a list of users, not getting any more");
        }
    }

    public void addAll(UserList list) {
        allUsers.getValue().addAll(list.getResults());
        allUsers.setValue(allUsers.getValue());
        Log.i("MDI", "Printing " + allUsers.getValue().size() + " Users");
        for (User user : allUsers.getValue()) {
            Log.i("MDI", user.toString());
        }
    }
}
