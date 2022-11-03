package com.ssa.Klozerz.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import com.ssa.Klozerz.common.Constants;
/**
 * 
 * @author Zubair
 *
 */
public class AppUtil {
	private static final Random RANDOM = new SecureRandom();

	/**
	 *
	 * @param s   value which we want to convert in string after eliminating leading
	 *            & trailing spaces
	 * @param alt in case of null in s parameter, value in alt will be returned
	 * @return in case of null, returns alt parameter to avoid null pointer
	 *         exception, otherwise returns string after removing leading & trailing
	 *         spaces
	 */
	public static String getSafeStr(String s, String alt) {
		if (s == null || s.trim().length() == 0) {
			return alt;
		}
		return s.trim();
	}

	/**
	 * Purpose: convert a string to int
	 *
	 * @param s   value which we want to parse
	 * @param alt in case of invalid value in s parameter, value in alt will be
	 *            returned
	 * @return Converted number in case of valid string otherwise returns alt
	 *         parameter
	 */
	public static int getSafeInt(String s, int alt) {
		try {
			return Integer.parseInt(getSafeStr(s, "0"));
		} catch (Exception e) {
			return alt;
		}
	}
	
	/**
	 * Purpose: get safe int value in case of valid int value 
	 * 
	 * @param value
	 * @param alt
	 * @return
	 */
	public static int getSafeInt(Integer value, int alt) {
		try {
			if(isNullOrEmpty(value)) {
				return alt;
			}
			return value;
		} catch (Exception e) {
			return alt;
		}
	}
	/**
	 * Purpose: convert a string to Long
	 *
	 * @param s   value which we want to parse
	 * @param alt in case of invalid value in s parameter, value in alt will be
	 *            returned
	 * @return Converted number in case of valid string otherwise returns alt
	 *         parameter
	 */
	public static Long getSafeLong(String s, Long alt) {
		try {
			return Long.parseLong(getSafeStr(s, "0"));
		} catch (Exception e) {
			return alt;
		}
	}
	
	/**
	 * This method is used to check whatever value is null or not
	 * 
	 * @param value
	 * @param alt
	 * @return
	 */
	public static Long getSafeLong(Long value, Long alt) {
		try {
			if(isNullOrEmpty(value)) {
				return alt;
			}
			return value;
		} catch (Exception e) {
			return alt;
		}
	}

	/**
	 * Purpose: convert a string to Double
	 *
	 * @param s   value which we want to parse
	 * @param alt in case of invalid value in s parameter, value in alt will be
	 *            returned
	 * @return Converted number in case of valid string otherwise returns alt
	 *         parameter
	 */
	public static Double getSafeDouble(String s, Double alt) {
		try {
			return Double.parseDouble(getSafeStr(s, "0"));
		} catch (Exception e) {
			return alt;
		}
	}
	
	/**
	 * This method is used to check value is nullornot
	 * 
	 * @param v
	 * @param alt
	 * @return
	 */
	public static Double getSafeDouble(Double v, Double alt) {
		try {
			if(isNullOrEmpty(v)) {
				return alt;
			}
			return v;
		} catch (Exception e) {
			return alt;
		}
	}
	
	/**
	 * Purpose: convert a string to Float
	 *
	 * @param s   value which we want to parse
	 * @param alt in case of invalid value in s parameter, value in alt will be
	 *            returned
	 * @return Converted number in case of valid string otherwise returns alt
	 *         parameter
	 */
	public static Float getSafeFloat(String s, Float alt) {
		try {
			return Float.parseFloat(getSafeStr(s, "0"));
		} catch (Exception e) {
			return alt;
		}
	}
	
	/**
	 * This method is used to check string isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		boolean isNullOrEmpty = false;
		if (value == null || value.trim().equalsIgnoreCase("") || value.trim().equalsIgnoreCase("null")
				|| value.equalsIgnoreCase(Constants.UNDEFINED)) {
			isNullOrEmpty = true;
		}
		return isNullOrEmpty;
	}
	
	/**
	 * This method is used to check string isNotEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return value != null && value.trim().length() > 0 && !value.equalsIgnoreCase(Constants.UNDEFINED)
				&& !value.equalsIgnoreCase("null");
	}
	/**
	 * This method is used to check long value isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Long value) {
		return value == null || value == 0;
	}
	
	/**
	 * This method is used to check Integer value isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Integer value) {
		return value == null || value == 0;
	}
	
	/**
	 * This method is used to check Double value isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Double value) {
		return value == null || value == 0;
	}
	
	/**
	 * This method is used to check Float value isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Float value) {
		return value == null || value == 0;
	}
	
	/**
	 * This method is used to check byte array isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Byte[] value) {
		boolean isNullOrEmpty = false;
		if (value == null || value.length<=0) {
			isNullOrEmpty = true;
		}
		return isNullOrEmpty;
	}
	
	/**
	 * This method is used to convert string into MD5
	 * 
	 * @param info
	 * @return
	 */
	public static String MD5(String info) {

		String encryptedInfo = "";
		try {
			MessageDigest mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(info.getBytes(), 0, info.length());
			encryptedInfo = new BigInteger(1, mdEnc.digest()).toString(16);

			if (encryptedInfo.length() != 32) {
				encryptedInfo = "0" + encryptedInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encryptedInfo;
	}
	
	/**
	 * This method is used to generate Random String
	 * 
	 * @return
	 */
	public static String generateRandomStr() {

		String capsLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numericLetters = "1234567890";
		String smallLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "+!#@$&_";
		String pw = "";

		for (int i = 0; i < 2; i++) {
			int index = (int) (RANDOM.nextDouble() * capsLetters.length());
			pw += capsLetters.substring(index, index + 1);
		}

		for (int i = 0; i < 1; i++) {
			int index = (int) (RANDOM.nextDouble() * specialCharacters.length());
			pw += specialCharacters.substring(index, index + 1);
		}

		for (int i = 0; i < 2; i++) {
			int index = (int) (RANDOM.nextDouble() * numericLetters.length());
			pw += numericLetters.substring(index, index + 1);
		}

		for (int i = 0; i < 2; i++) {
			int index = (int) (RANDOM.nextDouble() * smallLetters.length());
			pw += smallLetters.substring(index, index + 1);
		}

		for (int i = 0; i < 1; i++) {
			int index = (int) (RANDOM.nextDouble() * specialCharacters.length());
			pw += specialCharacters.substring(index, index + 1);
		}

		return pw;

	}
	
	/**
	 * This method is used to generate random Token
	 * 
	 * @return
	 */
	public static String generateNewToken() {
		SecureRandom random = new SecureRandom();
	    byte[] randomBytes = new byte[24];
	    random.nextBytes(randomBytes);
	    return Base64.getUrlEncoder().encodeToString(randomBytes);
	}
	
	/**
	 * This method is used to generate 7 CharRandomStr
	 * 
	 * @return
	 */
	public static String generate7CharRandomStr() {
		String capsLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numericLetters = "1234567890";
		String smallLetters = "abcdefghijklmnopqrstuvwxyz";
		String str = "";
		for (int i = 0; i < 3; i++) {
			int index = (int) (RANDOM.nextDouble() * capsLetters.length());
			str += capsLetters.substring(index, index + 1);
		}

		for (int i = 0; i < 2; i++) {
			int index = (int) (RANDOM.nextDouble() * smallLetters.length());
			str += smallLetters.substring(index, index + 1);
		}

		for (int i = 0; i < 2; i++) {
			int index = (int) (RANDOM.nextDouble() * numericLetters.length());
			str += numericLetters.substring(index, index + 1);
		}
		return str;
	}
	
	/**
	 * This method is used to remove all spaces from the string
	 * 
	 * @param str
	 * @return
	 */
	public static String removeAllSpace(String str) {
		String nstr="";
        for (String p : str.split("\\s")) {
        	nstr=nstr.concat(p);
          }
		return nstr;
	}
	
	/**
	 * This method is used to get only digit and chars
	 * 
	 * @param s
	 * @return
	 */
	public static String getOnlyDigitAndStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z 0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
     }
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlphaChar(String str) {
		Pattern letter = Pattern.compile("[a-zA-z]");
		Matcher hasLetter = letter.matcher(str);
		return hasLetter.find();
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		Pattern digit = Pattern.compile("[0-9]");
		Matcher hasDigit = digit.matcher(str);
		return hasDigit.find();
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isSpecialChar(String str) {
		Pattern special = Pattern.compile ("[!@#$%&*:()_+=|<>?{}\\[\\]~-]");
		Matcher hasSpecial = special.matcher(str);
		return hasSpecial.find();
	}
	public static String generateRandomStringValueAsUuid() {
		 int length = 15;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
	}
}
