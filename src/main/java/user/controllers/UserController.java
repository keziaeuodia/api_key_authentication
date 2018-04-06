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

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") int id,
                            @RequestParam (value = "APIKey") String apikey) throws UserNotFoundException, APIKeyNotFoundException {
        return userService.getUserById(id,apikey);
    }

    @GetMapping("/")
    public ArrayList<User> getAllUser(@RequestParam (value = "APIKey") String apikey)throws APIKeyNotFoundException{
        return userService.getAllUser(apikey);
    }

    @DeleteMapping("/{id}")
    public User deleteUserById(@PathVariable(value = "id") int id,
                            @RequestParam (value = "APIKey") String apikey) throws UserNotFoundException, APIKeyNotFoundException {
        return userService.deleteUserById(id,apikey);
    }


}
