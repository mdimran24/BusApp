package uk.ac.aston.cs3mdd.busapp.service;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.busapp.model.UserList;

public class UserRepository {
    private RandomUser randomUserService;

    public UserRepository(RandomUser userService) {
        this.randomUserService = userService;
    }

    public Call<UserList> getListOfUsers(int numUsers, String nationality) {
        return randomUserService.getUsers(numUsers, nationality);
    }
}
