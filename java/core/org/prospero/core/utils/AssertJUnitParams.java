/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.prospero.core.utils;

import org.apache.log4j.Logger;

import org.prospero.core.browserdrivers.BrowserDriver;
import org.prospero.core.browserdrivers.BrowserDriverFactory;
import org.prospero.core.exceptions.ProsperoPerformanceTestUnexpectedErrorException;
import org.prospero.core.utils.Constants.AssertionFramework;

public class AssertJUnitParams {

    private static AssertionFramework assertionFramework;

    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(AssertJUnitParams.class);

    static {
	assertionFramework = BrowserDriverFactory.getAssertionFramework();
    }

    /**
     * Private Constructor For Static Only Class
     */
    private AssertJUnitParams() {
	// Do Nothing
    }

    public static void assertEmptyString(String actual) {
	assertEmptyString("String [" + actual + "] is not empty or null",
		actual);
    }

    public static void assertEmptyString(String message, String actual) {
	assertTrue(message, actual == null || actual.trim().length() == 0);
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals,
	    double delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals, delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals, delta);
	}
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals,
	    float delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals, delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals, delta);
	}
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(expecteds, actuals);
	}
    }

    public static void assertArrayEquals(String message, byte[] expecteds,
	    byte[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertArrayEquals(String message, char[] expecteds,
	    char[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertArrayEquals(String message, double[] expecteds,
	    double[] actuals, double delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals,
		    delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals, delta);
	}
    }

    public static void assertArrayEquals(String message, float[] expecteds,
	    float[] actuals, float delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals,
		    delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals, delta);
	}
    }

    public static void assertArrayEquals(String message, int[] expecteds,
	    int[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertArrayEquals(String message, long[] expecteds,
	    long[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertArrayEquals(String message, Object[] expecteds,
	    Object[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertArrayEquals(String message, short[] expecteds,
	    short[] actuals) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertArrayEquals(message, expecteds,
		    actuals);
	}
    }

    public static void assertEquals(boolean expected, boolean actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(byte expected, byte actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(byte[] expected, byte[] actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(char expected, char actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(double expected, double actual, double delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual, delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual, delta);
	}
    }

    public static void assertEquals(float expected, float actual, float delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual, delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual, delta);
	}
    }

    public static void assertEquals(int expected, int actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(long expected, long actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(Object expected, Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(short expected, short actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(String expected, String actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(expected, actual);
	}
    }

    public static void assertEquals(String message, boolean expected,
	    boolean actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, byte[] expected,
	    byte[] actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, byte expected, byte actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, char expected, char actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, int expected, int actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, short expected, short actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, String expected,
	    String actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, double expected,
	    double actual, double delta) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual, delta);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual,
		    delta);
	}
    }

    public static void assertEquals(String message, long expected, long actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertEquals(String message, Object expected,
	    Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertEquals(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertEquals(message, expected, actual);
	}
    }

    public static void assertFalse(boolean condition) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertFalse(condition);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertFalse(condition);
	}
    }

    public static void assertFalse(String message, boolean condition) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertFalse(message, condition);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertFalse(message, condition);
	}
    }

    public static void assertNotNull(Object object) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNotNull(object);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNotNull(object);
	}
    }

    public static void assertNotNull(String message, Object object) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNotNull(message, object);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNotNull(message, object);
	}
    }

    public static void assertNotSame(Object expected, Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNotSame(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNotSame(expected, actual);
	}
    }

    public static void assertNotSame(String message, Object expected,
	    Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNotSame(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNotSame(message, expected, actual);
	}
    }

    public static void assertNull(Object object) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNull(object);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNull(object);
	}
    }

    public static void assertNull(String message, Object object) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertNull(message, object);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertNull(message, object);
	}
    }

    public static void assertSame(Object expected, Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertSame(expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertSame(expected, actual);
	}
    }

    public static void assertSame(String message, Object expected, Object actual) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertSame(message, expected, actual);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertSame(message, expected, actual);
	}
    }

    public static void assertTrue(boolean condition) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertTrue(condition);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertTrue(condition);
	}
    }

    public static void assertTrue(String message, boolean condition) {
	if (assertionFramework == AssertionFramework.JUNIT) {
	    org.junit.Assert.assertTrue(message, condition);
	} else if (assertionFramework == AssertionFramework.TESTNG) {
	    org.testng.AssertJUnit.assertTrue(message, condition);
	}
    }

    public static void fail() {
	if (!BrowserDriver.isPerformanceTest()) {
	    if (assertionFramework == AssertionFramework.JUNIT) {
		org.junit.Assert.fail();
	    } else if (assertionFramework == AssertionFramework.TESTNG) {
		org.testng.AssertJUnit.fail();
	    }
	} else {
	    throw new ProsperoPerformanceTestUnexpectedErrorException();
	}
    }

    public static void fail(String message) {
	if (!BrowserDriver.isPerformanceTest()) {
	    if (assertionFramework == AssertionFramework.JUNIT) {
		org.junit.Assert.fail(message);
	    } else if (assertionFramework == AssertionFramework.TESTNG) {
		org.testng.AssertJUnit.fail(message);
	    }
	} else {
	    throw new ProsperoPerformanceTestUnexpectedErrorException(message);
	}
    }

    public static void assertStringContainsIgnoreCase(String containerString,
	    String subString) {
	assertTrue(containerString.toLowerCase().contains(
		subString.toLowerCase()));
    }

    public static void assertStringContainsIgnoreCase(String message,
	    String containerString, String subString) {
	assertTrue(message,
		containerString.toLowerCase().contains(subString.toLowerCase()));
    }

    public static void assertStringNotContainsIgnoreCase(
	    String containerString, String subString) {
	assertFalse(containerString.toLowerCase().contains(
		subString.toLowerCase()));
    }

    public static void assertStringNotContainsIgnoreCase(String message,
	    String containerString, String subString) {
	assertFalse(message,
		containerString.toLowerCase().contains(subString.toLowerCase()));
    }
}
