package user.services;

import user.exception.APIKeyNotFoundException;
import user.exception.DuplicateUserException;
import user.exception.UserNotFoundException;
import user.mappers.UserMapper;
import user.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //register new user and generate API key to insert to DB
    public User register(User user) throws DuplicateUserException{

        //check if there's duplicate user by email
        if (user.getEmail().equalsIgnoreCase(userMapper.getEmail(user.getEmail()))){
            throw new DuplicateUserException("You are already registered, please proceed with your API key");
        }else{
            //generate API key
            try {
                user.setApiKey(generateApiKey(128));
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.toString());
            }

            //setting user status to active
            user.setActive(true);

            //insert new user to DB
            userMapper.insertUser(user);

            //returns the latest user inserted
            return userMapper.getLatestUser();
        }

    }

    //generate API key using Java SecureRandom class
    public static String generateApiKey(int length) throws NoSuchAlgorithmException {

        SecureRandom random = new SecureRandom();
        byte [] bytes = new byte[length/8];
        random.nextBytes(bytes);

        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

    //get user by id if api key is authenticated
    public User getUserById(int id, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateApiKey(apikey)){
            return userMapper.getUserById(id);
        }else throw new UserNotFoundException("User not found");
    }

    //get user by email if api key is authenticated
    public User getUserByEmail(String email, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateApiKey(apikey)){
            return userMapper.getUserByEmail(email);
        }else throw new UserNotFoundException("User not found");
    }

    //get all active user if api key is authenticated
    public ArrayList<User> getAllUser(String apikey) throws APIKeyNotFoundException{
        if (authenticateApiKey(apikey)) {
            return userMapper.getAllUser();
        }else throw new APIKeyNotFoundException("API Key not found");
    }

    //set user (according to its id) to inactive if api key is authenticated
    public User deleteUserById(int id, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateApiKey(apikey)){
            userMapper.deleteUserById(id);
            return userMapper.getUserById(id);
        }else throw new UserNotFoundException("User not found");
    }

    //update user by id, all fields other than api key must be filled. the method will generate new api key on each update.
    public User updateUserById(User user, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateApiKey(apikey)){
            try {
                user.setApiKey(generateApiKey(128));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            userMapper.updateUserById(user);
            return userMapper.getUserById(user.getId());
        }else throw new UserNotFoundException("User not found");
    }

    //method to authenticate api key
    public boolean authenticateApiKey(String apikey){
        //creating new user object to check if there is a user with the api key registered
        User user = userMapper.getUserByKey(apikey);
        //if api key doesn't match any user returns false
        if (user == null) {
            return false;
        }else {
            return true;
        }

    }

}
