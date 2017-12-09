package com.hp.ws.cache.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hp.ws.cache.AppParameterCache;
import com.hp.ws.datasource.dao.AppParameterDao;
import com.hp.ws.datasource.entity.AppParameter;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:43:16
 */
@Repository
public class AppParameterCacheImpl implements AppParameterCache {
	private static final Logger logger = Logger.getLogger(AppParameterCacheImpl.class);

	@Autowired
	private AppParameterDao appParameterDao;

	@Override
	public List<AppParameter> getAllParameters() {
		return appParameterDao.getAllParameters();

	}

	@Override
	public int getParamAsInt(String paramKey, int paramDefaultValue) {
		int paramValue = paramDefaultValue;
		try {
			if (appParameterDao.getAllAppParameterAsHashMap().get(paramKey) != null) {
				paramValue = new Long(appParameterDao.getAllAppParameterAsHashMap().get(paramKey)).intValue();
			}
		} catch (Exception e) {
			logger.error("getParamAsInt paramKey:" + paramKey + "-paramDefaultValue:" + paramDefaultValue, e);
		}
		return paramValue;
	}

	private String getParamAsString(String paramKey, String paramDefaultValue) {
		String paramValue = paramDefaultValue;
		try {
			paramValue = appParameterDao.getAllAppParameterAsHashMap().get(paramKey);
		} catch (Exception e) {
			logger.error("getParamAsString paramKey:" + paramKey + "-paramDefaultValue:" + paramDefaultValue, e);
		}
		return paramValue;
	}

	@Override
	public int getPasswordLenght() {
		return getParamAsInt(AppParameterCache.passwordLenght, AppParameterCache.passwordLenghtDefault);
	}

	@Override
	public int getSaltLenght() {
		return getParamAsInt(AppParameterCache.saltLenght, AppParameterCache.saltLenghtDefault);
	}

	@Override
	public String getPasswordCryptKey() {
		return getParamAsString(AppParameterCache.passwordCryptKey, AppParameterCache.passwordCryptKeyDefault);
	}

	@Override
	public int getPasswordResetTimeOut() {
		return getParamAsInt(AppParameterCache.passwordResetTimeOut, AppParameterCache.passwordResetTimeOutDefault);
	}

	@Override
	public String getWsUserName() {
		return getParamAsString(AppParameterCache.wsUserName, "");
	}

	@Override
	public String getWsPassword() {
		return getParamAsString(AppParameterCache.wsPassword, "");
	}

	@Override
	public int getEncryotedKeyVersion() {

		return getParamAsInt(AppParameterCache.encryotedKeyVersion, AppParameterCache.encryotedKeyVersionDefault);
		
	}


	@Override
	public int getPasswordResetItemCount() {
		return getParamAsInt(AppParameterCache.passwordResetItemCount, AppParameterCache.passwordResetItemCountDefault);
	}

	@Override
	public int getReportItemCount() {
		return getParamAsInt(AppParameterCache.reportItemCount, AppParameterCache.reportItemCountDefault);
	}

}
