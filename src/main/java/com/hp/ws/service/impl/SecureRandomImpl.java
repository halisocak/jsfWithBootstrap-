package com.hp.ws.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:05:22
 */
@Component
public class SecureRandomImpl extends SecureRandom implements ISecureRandom {

	private static final long serialVersionUID = 1L;

	public SecureRandomImpl() {

		/*
		 * The name of the pseudo-random number generation (PRNG) algorithm
		 * supplied by the SUN provider. This algorithm uses SHA-1 as the
		 * foundation of the PRNG. It computes the SHA-1 hash over a true-random
		 * seed value concatenated with a 64-bit counter which is incremented by
		 * 1 for each operation. From the 160-bit SHA-1 output, only 64 bits are
		 * used.
		 */
		try {
			super.getInstance("SHA1PRNG");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: be sure that super.next calls the instance created in constructor
	@Override
	public String getRandomNumber(int lenght) {

		int r = next(lenght);
		return String.valueOf(r);
	}

	@Override
	public String getNumericRandom(int lenght) {

		String random = "";
		while (random.length() < lenght) {
			String tmp = String.valueOf(next(32));// [0,32]
			if (tmp.startsWith("-"))
				tmp = tmp.substring(1);
			random = random.concat(tmp);
		}
		return random.substring(0, lenght);
	}

	/**
	 * seri numarasý ve aktivasyon kodu uretme
	 *
	 * @param length
	 * @return
	 */
	@Override
	public String generateRandomNumber(int codeLength) {
		StringBuilder builder = new StringBuilder();
		int oddCount = 0;
		int evenCount = 0;
		int last = 0;
		for (int i = 0; i < codeLength; i++) {
			Random rand = new Random();
			int firstNum;

			if (i == 0) {

				firstNum = 1 + Math.abs(rand.nextInt(9));
				builder.append(firstNum);
				oddCount += firstNum;

			} else if (i > 0 && i < codeLength - 2) {

				int nums = Math.abs(rand.nextInt(9));
				builder.append(nums);

				if ((i + 1) % 2 == 1) {
					oddCount += nums;
				} else {
					evenCount += nums;
				}
			} else if (i == codeLength - 2) {
				int next = Math.abs(((oddCount * 7) - evenCount) % 10);
				last = Math.abs((oddCount + evenCount + next) % 10);
				builder.append(next);
			} else if (i == codeLength - 1) {
				builder.append(last);
			} else {
				int others = Math.abs(rand.nextInt(9));

				builder.append(others);
			}

		}

		int y = ((oddCount * 7) - evenCount) % 10;

		// System.out.println(y);

		String number = builder.toString();
		return number;
	}
}
