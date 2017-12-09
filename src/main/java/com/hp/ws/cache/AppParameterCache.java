package com.hp.ws.cache;

import java.util.List;

import com.hp.ws.datasource.entity.AppParameter;
import com.hp.ws.service.BaseService;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:43:05
 */
public interface AppParameterCache extends BaseService {
	public final static String passwordLenght = "passwordLenght";// 8
	public final static String saltLenght = "saltLenght";// 12
	public final static String passwordResetTimeOut="passwordResetTimeOut";
	public final static String passwordResetItemCount = "passwordResetItemCount";
	public final static String reportItemCount = "reportItemCount";
	
	
	public final static int passwordLenghtDefault = 8;
	public final static int saltLenghtDefault = 12;
	public final static int passwordResetTimeOutDefault = 12;
	
	
	public final static int passwordResetItemCountDefault = 10;
	public final static int reportItemCountDefault = 12;
	
	public final static int encryotedKeyVersionDefault=1;

	public final static String passwordCryptKey = "passwordCryptKey";
	public final static String passwordCryptKeyDefault = "59AB25DF56CA29A1";

	
	public final static String  wsUserName ="wsUserName";
	public final static String  wsPassword ="wsPassword";
	public final static String  wsEndpoint ="wsEndpoint";
	public final static String encryotedKeyVersion="encryotedKeyVersion";
	
	public List<AppParameter> getAllParameters();

	int getParamAsInt(String paramKey, int paramDefaultValue);

	int getPasswordLenght();

	int getSaltLenght();

	/**
	 * @return
	 */
	String getPasswordCryptKey();
	int getPasswordResetTimeOut();
	
	int getPasswordResetItemCount();
	int getReportItemCount();
	
	String  getWsUserName();
	String  getWsPassword();
	int getEncryotedKeyVersion();

}