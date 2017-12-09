/**
 *  @author Halis Ocak Haz 16
 */ 

package com.hp.ws.datasource.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hp.ws.datasource.dao.AppParameterDao;
import com.hp.ws.datasource.entity.AppParameter;
import com.hp.ws.utility.WsUtil;

@Repository
public class AppParameterDaoImpl extends BaseDaompl implements AppParameterDao {

	@Override	
	public List<AppParameter> getAllParameters() {
		// TODO Auto-generated method stub
		return findAll(AppParameter.class);
	}
	
	@Override
	@Cacheable(value = "appParameterList", key = "#root.method.name")
	public HashMap<String, String> getAllAppParameterAsHashMap() {
		HashMap<String, String> hashMap = new HashMap<>();
		List<AppParameter> appParameters = getAllParameters();
		if (appParameters.isEmpty()) {
			appParameters = saveDefaultAppParameters();
		}
		for (AppParameter appParameter : appParameters) {
			hashMap.put(appParameter.getProperty(), appParameter.getValue());
		}
		return hashMap;
	}

	
	
	@Transactional
	@Override
	public List<AppParameter> saveDefaultAppParameters(){
			List<AppParameter> appParameters = WsUtil.getDefaultAppParameterList();
			for (AppParameter appParameter : appParameters) {
				saveEntity(appParameter);
			}
			return appParameters;
	}
}
