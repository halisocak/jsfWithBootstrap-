package com.hp.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.ws.constant.RequestMappingConstants;
import com.hp.ws.exception.WsException;
import com.hp.ws.message.Response;
import com.hp.ws.model.BaseRequest;
import com.hp.ws.model.CreateUserRequest;
import com.hp.ws.service.UserService;

/**
 * @author Halis Ocak Haz-2016
 *
 */
@Controller
@RequestMapping(value = RequestMappingConstants.SECURE)
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = RequestMappingConstants.CREATE_USER, method = RequestMethod.POST)
	public @ResponseBody Response createUser(@RequestBody CreateUserRequest createUserRequest) throws WsException {

		userService.createUser(createUserRequest);

		System.out.println("user created");

		return getResponse();
	}

	@RequestMapping(value = "aaa", method = RequestMethod.GET)
	public @ResponseBody Response release(BaseRequest request) {

		return getResponse();
	}
}
