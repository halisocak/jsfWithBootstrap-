package com.hp.ws.service.impl;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:05:10
 */
public interface ISecureRandom {

	public String getRandomNumber(int lenght);

	public String getNumericRandom(int lenght);

	public String generateRandomNumber(int length);
}
