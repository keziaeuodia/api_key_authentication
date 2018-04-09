package user.controllers;

import org.apache.ibatis.annotations.Delete;
import user.exception.APIKeyNotFoundException;
import user.exception.DuplicateUserException;
import user.exception.UserNotFoundException;
import user.models.user.User;
import user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/session")

public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    RestTemplate restTemplate;


    //register user
    @PostMapping("/")
    public String register(@RequestBody User user) throws DuplicateUserException {
            userService.register(user);
            return "Your API key: " + user.getApiKey();
    }

    //get user by id, whether it is active or not
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") int id,
                            @RequestParam (value = "APIKey") String apikey) throws UserNotFoundException, APIKeyNotFoundException {
        return userService.getUserById(id,apikey);
    }

    //get all active user
    @GetMapping("/")
    public ArrayList<User> getAllUser(@RequestParam (value = "APIKey") String apikey)throws APIKeyNotFoundException{
        return userService.getAllUser(apikey);
    }

    //set active user to inactive
    @DeleteMapping("/{id}")
    public User deleteUserById(@PathVariable(value = "id") int id,
                            @RequestParam (value = "APIKey") String apikey) throws UserNotFoundException, APIKeyNotFoundException {
        return userService.deleteUserById(id,apikey);
    }

    //update user, active or inactive, and generate new api key every update
    @PatchMapping("/{id}")
    public User updateUserById(@RequestBody User user,
                            @RequestParam (value = "APIKey") String apikey) throws UserNotFoundException, APIKeyNotFoundException {
        return userService.updateUserById(user,apikey);
    }


}
