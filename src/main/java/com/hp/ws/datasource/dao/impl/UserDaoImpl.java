package com.hp.ws.datasource.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hp.ws.datasource.dao.UserDao;
import com.hp.ws.datasource.entity.AppUser;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:52:27
 */
@Repository
public class UserDaoImpl extends BaseDaompl implements UserDao {

	@Transactional
	@Override
	public AppUser getUserByName(String username) {
		List<AppUser> users = findByProperty(AppUser.class, AppUser.PROP_USER_NAME, username);
		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
}
