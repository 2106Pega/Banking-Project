/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.repo;

import com.revature.models.User;

public interface UserDAO {
	public User GetUserByUsernameAndPassword(String username, String password);
	public boolean InsertUser(User user);
}
