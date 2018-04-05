package crypto.controllers;

import crypto.exception.DuplicateUserException;
import crypto.models.user.User;
import crypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")

public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    RestTemplate restTemplate;


    //register user
    @PostMapping("/")
    public String register(@RequestBody User user){
        try {
            userService.register(user);
            return user.getApiKey();
        } catch (DuplicateUserException e) {
            return e.getMessage();
        }

    }

    @GetMapping("/")
    public User getUserById(@PathVariable(value = "id") int id){
        return userService.getUserById(id);
    }


}
