package com.company.Controllers.DataAccess.DAO;

import com.company.Controllers.DataAccess.DBUtil.DBDriver;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public interface UserDAO extends DBDriver {
    ArrayList<User> getAllUsers();
    User getUserByUsername(String username);
    void deleteUser(User user);
    String createUser(String username, String password, String first_name, String last_name);


}
