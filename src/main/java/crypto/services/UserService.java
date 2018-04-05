package crypto.services;

import crypto.exception.DuplicateUserException;
import crypto.mappers.UserMapper;
import crypto.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    public User getUserByEmail(String email){
        return userMapper.getUserByEmail(email);
    }

    public ArrayList<User> getAllUser(){
        return userMapper.getAllUser();
    }

}
