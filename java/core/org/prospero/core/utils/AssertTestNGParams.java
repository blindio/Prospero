package org.prospero.core.utils;

public class AssertTestNGParams {
	
	public static void assertEmptyString(String actual) {
		AssertJUnitParams.assertEmptyString(actual);
	}
	
	public static void assertEmptyString(String actual, String message) {
		AssertJUnitParams.assertEmptyString(message, actual);
	}
	
	public static void assertArrayEquals(byte[] actuals, byte[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(char[] actuals, char[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(double[] actuals, double[] expecteds, double delta) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals, delta);
	}
	
	public static void assertArrayEquals(float[] actuals, float[] expecteds, float delta) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals, delta);
	}
	
	public static void assertArrayEquals(int[] actuals, int[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(long[] actuals, long[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(Object[] actuals, Object[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(short[] actuals, short[] expecteds) {
		AssertJUnitParams.assertArrayEquals(expecteds, actuals);
	}
	
	public static void assertArrayEquals(byte[] actuals, byte[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertArrayEquals(char[] actuals, char[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertArrayEquals(double[] actuals, double[] expecteds, double delta, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals, delta);
	}
	
	public static void assertArrayEquals(float[] actuals, float[] expecteds, float delta, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals, delta);
	}
	
	public static void assertArrayEquals(int[] actuals, int[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertArrayEquals(long[] actuals, long[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertArrayEquals(Object[] actuals, Object[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertArrayEquals(short[] actuals, short[] expecteds, String message) {
		AssertJUnitParams.assertArrayEquals(message, expecteds, actuals);
	}
	
	public static void assertEquals(boolean actual, boolean expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(byte[] actual, byte[] expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(byte actual, byte expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(char actual, char expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(int actual, int expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(short actual, short expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(String actual, String expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(double actual, double expected, double delta) {
		AssertJUnitParams.assertEquals(expected, actual, delta);
	}
	
	public static void assertEquals(long actual, long expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(Object actual, Object expected) {
		AssertJUnitParams.assertEquals(expected, actual);
	}
	
	public static void assertEquals(double actual, double expected, double delta, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual, delta);
	}
	
	public static void assertEquals(boolean actual, boolean expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(byte[] actual, byte[] expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(byte actual, byte expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(char actual, char expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(int actual, int expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(short actual, short expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(String actual, String expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(long actual, long expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(Object actual, Object expected, String message) {
		AssertJUnitParams.assertEquals(message, expected, actual);
	}
	
	public static void assertFalse(boolean condition) {
		AssertJUnitParams.assertFalse(condition);
	}
	
	public static void assertFalse(boolean condition, String message) {
		AssertJUnitParams.assertFalse(message, condition);
	}
	
	public static void assertNotNull(Object object) {
		AssertJUnitParams.assertNotNull(object);
	}
	
	public static void assertNotNull(Object object, String message) {
		AssertJUnitParams.assertNotNull(message, object);
	}
	
	public static void assertNotSame(Object actual, Object expected) {
		AssertJUnitParams.assertNotSame(expected, actual);
	}
	
	public static void assertNotSame(Object actual, Object expected, String message) {
		AssertJUnitParams.assertNotSame(message, expected, actual);
	}
	
	public static void assertNull(Object object) {
		AssertJUnitParams.assertNull(object);
	}
	
	public static void assertNull(Object object, String message) {
		AssertJUnitParams.assertNull(message, object);
	}
	
	public static void assertSame(Object actual, Object expected) {
		AssertJUnitParams.assertSame(expected, actual);
	}
	
	public static void assertSame(Object actual, Object expected, String message) {
		AssertJUnitParams.assertSame(message, expected, actual);
	}
	
	public static void assertTrue(boolean condition) {
		AssertJUnitParams.assertTrue(condition);
	}
	
	public static void assertTrue(boolean condition, String message) {
		AssertJUnitParams.assertTrue(message, condition);
	}
	
	public static void fail() {
		AssertJUnitParams.fail();
	}
	
	public static void fail(String message) {
		AssertJUnitParams.fail(message);
	}
	
	public static void assertStringContainsIgnoreCase(String containerString, String subString) {
		AssertJUnitParams.assertStringContainsIgnoreCase(containerString, subString);
	}
	
	public static void assertStringContainsIgnoreCase(String containerString, String subString, String message) {
		AssertJUnitParams.assertStringContainsIgnoreCase(message, containerString, subString);
	}
	
	public static void assertStringNotContainsIgnoreCase(String containerString, String subString) {
		AssertJUnitParams.assertStringNotContainsIgnoreCase(containerString, subString);
	}
	
	public static void assertStringNotContainsIgnoreCase(String containerString, String subString,
			String message) {
		AssertJUnitParams.assertStringNotContainsIgnoreCase(message, containerString, subString);
	}
}
