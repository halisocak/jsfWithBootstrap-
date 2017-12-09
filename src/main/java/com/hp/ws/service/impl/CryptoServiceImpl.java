package com.hp.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.ws.cache.AppParameterCache;
import com.hp.ws.service.CryptoService;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:04:58
 */
@Service
public class CryptoServiceImpl extends BaseServiceImpl implements CryptoService {

	@Autowired
	private ISecureRandom secRandom;

	@Autowired
	private AppParameterCache appParameterCache;

	@Override
	public String generatePassword() {
		// TODO length must be parametric
		String randomValue = secRandom.getNumericRandom(appParameterCache.getPasswordLenght());
		return randomValue;
	}

	@Override
	public String generateSalt() {
		return secRandom.getNumericRandom(appParameterCache.getSaltLenght());
	}
}
