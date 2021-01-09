package br.com.urlshortener.util;

import org.apache.commons.lang3.RandomStringUtils;


public class CodeGeneratorUtil {
	
	public static String generate() {
	    int length = 8;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
	    return generatedString;
	}

}
