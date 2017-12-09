package com.hp.ws.service;

import com.hp.ws.datasource.entity.AppUser;
import com.hp.ws.datasource.entity.RequestLog;
import com.hp.ws.exception.WsException;
import com.hp.ws.model.CreateUserRequest;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:04:32
 */
public interface UserService extends BaseService {

	public AppUser getAppUser(String username);

	public Boolean createUser(CreateUserRequest createUserRequest) throws WsException;

	public void requestLog(RequestLog requestLog, String appUserName) throws WsException;

}
