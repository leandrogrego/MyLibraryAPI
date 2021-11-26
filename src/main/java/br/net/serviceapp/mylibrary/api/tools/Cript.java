package br.net.serviceapp.mylibrary.api.tools;

import java.security.MessageDigest;

public abstract class Cript {
    public static String hash(String text) {
		try {
			byte[] hash = MessageDigest.getInstance("SHA-256").digest(text.getBytes("UTF-8"));
			StringBuilder outText = new StringBuilder();
			for (byte b : hash) {
				outText.append(String.format("%02X", 0xFF & b));
			}
			return outText.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
