package org.prospero.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JqueryCodeFactory {
	
	private static final String WINDOW_JQUERY = "window.jQuery";
	public static final String JQUERY_FILE_PATH = "javascript/jquery-1.6.4.min.js";
	public static final String SE_SPECIFIC_JQUERY_OBJECT = WINDOW_JQUERY;
	private static String injectableJqueryLibraryAsString;
	
	public static String getVisibilityCode(String jsId) {
		// String visCode = JqueryCodeFactory.SE_SPECIFIC_JQUERY_OBJECT + "(\"" + jsId.replaceAll( "@", "" ) +
		// "\").is(':visible')";
		String visCode = JqueryCodeFactory.SE_SPECIFIC_JQUERY_OBJECT + "(\"" + jsId.replaceAll("@", "") +
				"\").is(':first')";
		return visCode;
	}
	
	public static String getJqueryLibraryString() {
		if (injectableJqueryLibraryAsString == null) {
			injectableJqueryLibraryAsString = loadJqueryFromFile();
		}
		
		return injectableJqueryLibraryAsString;
	}
	
	public static String getJqueryUndefinedString() {
		return WINDOW_JQUERY + " == undefined";
	}
	
	public static String loadJqueryFromFile() {
		String jqueryString = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(JqueryCodeFactory.JQUERY_FILE_PATH));
			jqueryString = readJqueryFile(br, jqueryString);
			new FileReader(JqueryCodeFactory.JQUERY_FILE_PATH).close();
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return jqueryString;
	}
	
	public static String readJqueryFile(BufferedReader br, String jqueryString) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			jqueryString += line += "\r\n";
		}
		return jqueryString;
	}
	
	public static String getClickCode(String locator) {
		String jQueryBlock = getJqueryExecuteWrapper(locator) +
				".bind('click.jQueryReallyClick', " +
				"function(event) {if(event.target.href){ window.location = event.target.href; }}).click().unbind('click.jQueryReallyClick');";
		
		return jQueryBlock;
	}
	
	public static String getTextCode(String locator) {
		String jQueryBlock = getJqueryExecuteWrapper(locator) + ".text()";
		
		return jQueryBlock;
	}
	
	public static String getJqueryExecuteWrapper(String locator) {
		return WINDOW_JQUERY + "(\"" + locator + "\")";
	}
	
}
