
package com.hp.ws.datasource.dao;

import com.hp.ws.datasource.entity.AppUser;


public interface UserDao extends BaseDao {

	public AppUser getUserByName(String username);

}
