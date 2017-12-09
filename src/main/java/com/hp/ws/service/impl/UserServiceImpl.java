package com.hp.ws.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.ws.cache.AppParameterCache;
import com.hp.ws.datasource.dao.UserDao;
import com.hp.ws.datasource.entity.AppUser;
import com.hp.ws.datasource.entity.RequestLog;
import com.hp.ws.enums.ErrorType;
import com.hp.ws.exception.WsException;
import com.hp.ws.model.CreateUserRequest;
import com.hp.ws.service.CryptoService;
import com.hp.ws.service.UserService;
import com.hp.ws.utility.CryptoUtil;
import com.hp.ws.utility.DateTimeUtils;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:05:37
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserDao userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	AppParameterCache appParameterCache;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CryptoService cryptoService;

	@Override
	@Transactional
	public AppUser getAppUser(String username) {
		List<AppUser> appUsers = userRepository.findByProperty(AppUser.class, AppUser.PROP_USER_NAME, username);
		if (appUsers != null && !appUsers.isEmpty()) {
			return appUsers.get(0);
		}
		return null;
	}

	@Transactional
	@Override
	public void requestLog(RequestLog requestLog, String appUserName) throws WsException {
		AppUser appUser = null;
		if (appUserName != null) {
			appUser = getAppUser(appUserName);
		}
		requestLog.setAppUser(appUser);
		userRepository.saveEntity(requestLog);
	}

	@Override
	public Boolean createUser(CreateUserRequest createUserRequest) throws WsException {
		AppUser appuser = userRepository.getUserByName(createUserRequest.getUserName());
		if (appuser != null) {
			throw new WsException(ErrorType.IdentifierAlreadyExist);
		} else {
			appuser = new AppUser();
		}

		String password = cryptoService.generatePassword();
		System.err.println("password:" + password + " for:" + createUserRequest.getUserName());

		String salt = cryptoService.generateSalt();
		appuser.setSalt(salt.getBytes());

		CryptoUtil cryptoUtil = new CryptoUtil(salt.getBytes());

		String passwordEnc = cryptoUtil.encrypt(password);

		System.err.println("passwordEnc:" + passwordEnc + " for:" + createUserRequest.getUserName() + " salt:" + salt);
		appuser.setUserName(createUserRequest.getUserName());
		appuser.setPassword(passwordEnc);
		appuser.setCreateDate(DateTimeUtils.getNowAsTimestamp());
		appuser.setRoleList(createUserRequest.getRoleList());
		appuser.setEnabled(false);
		appuser.setEmailAddress(createUserRequest.getEmailAddress());
		appuser.setDescription(createUserRequest.getDescription());
		appuser.setIv(cryptoUtil.getIv());

		appuser = userRepository.saveEntity(appuser);

		if (appuser.getId() != null) {
			sendEmail(password, createUserRequest.getEmailAddress());
			return true;
		} else {
			return false;
		}
	}


	private void sendEmail(String text, String emailAddress) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailAddress);
		msg.setText("password " + text);
		try {
			mailSender.send(msg);
		} catch (Exception ex) {
			// TODO:log exception
			System.err.println(ex.getMessage());
		}
	}

}
