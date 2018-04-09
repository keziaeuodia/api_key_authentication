package user.mappers;


import org.apache.ibatis.annotations.*;
import user.models.user.User;

import java.util.ArrayList;

@Mapper
public interface UserMapper {

    String INSERT_NEW_USER = "INSERT INTO `Api_key_auth`.`register`(`id`,`firstName`,`lastName`,`email`,`password`,`apiKey`,`active`)" +
            "VALUES(#{id},#{firstName},#{lastName},#{email},#{password},#{apiKey},#{active});";
    String GET_LATEST_USER = "SELECT * FROM `Api_key_auth`.`register` ORDER BY `id` DESC LIMIT 1;";
    String GET_USER_BY_ID = "SELECT * FROM `Api_key_auth`.`register` WHERE `id` = #{id};";
    String GET_USER_BY_EMAIL = "SELECT * FROM `Api_key_auth`.`register` WHERE `email` = #{email};";
    String GET_EMAIL = "SELECT `email` FROM `Api_key_auth`.`register` WHERE `email` = #{email};";
    String GET_ALL_USER = "SELECT * FROM `Api_key_auth`.`register` WHERE `active` = 1";
    String GET_USER_BY_APIKEY = "SELECT * FROM `Api_key_auth`.`register` WHERE `apiKey` = #{apiKey};";
    String DELETE_USER_BY_EMAIL = "DELETE FROM `Api_key_auth`.`register`WHERE `email` = #{email};";
    String DELETE_USER_BY_ID = "UPDATE `Api_key_auth`.`register`SET `active` = 0 WHERE `id` = #{id};";
    String UPDATE_USER_BY_ID = "UPDATE `Api_key_auth`.`register` SET `id` = #{id},`firstName` = #{firstName}," +
            "`lastName` = #{lastName},`email` = #{email},`password` = #{password},`apiKey` = #{apiKey}," +
            "`active` = #{active} WHERE `id` = #{id};";

    @Insert(INSERT_NEW_USER)
    public void insertUser(User user);

    @Select(GET_LATEST_USER)
    public User getLatestUser();

    @Select(GET_USER_BY_ID)
    public User getUserById(int id);

    @Select(GET_USER_BY_EMAIL)
    public User getUserByEmail(String email);

    @Select(GET_EMAIL)
    public String getEmail(String email);

    @Select(GET_ALL_USER)
    public ArrayList<User> getAllUser();

    @Select(GET_USER_BY_APIKEY)
    public User getUserByKey(String apikey);

    @Delete(DELETE_USER_BY_ID)
    public void deleteUserById(int id);

    @Update(UPDATE_USER_BY_ID)
    public void updateUserById(User user);

}
