package org.bautista.cybersafe.util.enctryption;

import java.util.Random;

public class KeyGenerator {
	private final static char[] charOptions = { 'a', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'@', '#', '$', '%', '&', '*', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
			'z', 'x', 'c', 'v', 'b', 'n', 'n', 'm', '?', 'Q', 'W',
			'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
			'Z', 'X', 'C', 'V', 'B', 'N', 'M', '!', };

	public static String getNewKey() {
		String key = "";
		Random random = new Random();
		for (int i = 0; i < 16; i++) {
			key += String.valueOf(charOptions[random.nextInt((charOptions.length - 1))]);
		}
		return key;
	}

	public static String getNewVector() {
		return getNewKey();
	}

}
