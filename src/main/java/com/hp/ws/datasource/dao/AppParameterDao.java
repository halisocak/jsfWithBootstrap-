
package com.hp.ws.datasource.dao;
import java.util.HashMap;
import java.util.List;

import com.hp.ws.datasource.entity.AppParameter;

public interface AppParameterDao extends BaseDao {
	List<AppParameter> getAllParameters();

	List<AppParameter> saveDefaultAppParameters();

	/**
	 * @return
	 */
	HashMap<String, String> getAllAppParameterAsHashMap();
}
