package user.services;

import user.exception.APIKeyNotFoundException;
import user.exception.DuplicateUserException;
import user.exception.UserNotFoundException;
import user.mappers.UserMapper;
import user.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.user.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //register new user and generate API key to insert to DB
    public User register(User user) throws DuplicateUserException{

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

            userMapper.insertUser(user);

            return userMapper.getLatestUser();
        }

    }

    //generate API key
    public static String generateApiKey(int length) throws NoSuchAlgorithmException {

        SecureRandom random = new SecureRandom();
        byte [] bytes = new byte[length/8];
        random.nextBytes(bytes);

        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }


    public User getUserById(int id, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateAPI(apikey)){
            return userMapper.getUserById(id);
        }else throw new UserNotFoundException("User not found");
    }

    public User getUserByEmail(String email, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateAPI(apikey)){
            return userMapper.getUserByEmail(email);
        }else throw new UserNotFoundException("User not found");
    }

    public ArrayList<User> getAllUser(String apikey) throws APIKeyNotFoundException{
        if (authenticateAPI(apikey)) {
            return userMapper.getAllUser();
        }else throw new APIKeyNotFoundException("API Key not found");
    }

    public User deleteUserById(int id, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateAPI(apikey)){
            userMapper.deleteUserById(id);
            return userMapper.getUserById(id);
        }else throw new UserNotFoundException("User not found");
    }

    public boolean authenticateAPI(String apikey){
        User user = userMapper.getUserByKey(apikey);
        if (user == null) {
            return false;
        }else {
            return true;
        }

    }

    public User updateUserById(User user, String apikey) throws UserNotFoundException, APIKeyNotFoundException{
        if (authenticateAPI(apikey)){
            try {
                user.setApiKey(generateApiKey(128));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            userMapper.updateUserById(user);
            return userMapper.getUserById(user.getId());
        }else throw new UserNotFoundException("User not found");
    }
}
