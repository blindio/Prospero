package org.prospero.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.log4j.Logger;

import org.prospero.core.exceptions.ProsperoConfigConversionException;

public class Config {
	
	protected static Logger logger = Logger.getLogger(Config.class);
	private static CompositeConfiguration config;
	private static Set<String> propertiesFileNames;
	
	static {
		propertiesFileNames = new CopyOnWriteArraySet<String>();
		
		// create config objects for each of the config sources and add them to the core config
		Configuration sysOverridesConfig = null;
		Configuration frameworkDefaultConfig = null;
		Configuration appObjectsConfig = null;
		Configuration testConfig = null;
		
		sysOverridesConfig = new SystemConfiguration();
		
		try {
			frameworkDefaultConfig = new PropertiesConfiguration(
					PropertiesConstants.PROPFILE_FRAMEWORK_DEFAULTS);
			propertiesFileNames.add(PropertiesConstants.PROPFILE_FRAMEWORK_DEFAULTS);
			appObjectsConfig = new PropertiesConfiguration(PropertiesConstants.PROPFILE_APP_OBJECTS);
			propertiesFileNames.add(PropertiesConstants.PROPFILE_APP_OBJECTS);
			testConfig = new PropertiesConfiguration(PropertiesConstants.PROPFILE_TEST);
			propertiesFileNames.add(PropertiesConstants.PROPFILE_TEST);
		}
		catch (ConfigurationException ce) {
			logger.fatal("Error opening properties file", ce);
			System.exit(1);
		}
		
		config = new CompositeConfiguration();
		config.setThrowExceptionOnMissing(false);
		
		// Add configs top combined config with override as first added and defaults last added
		config.addConfiguration(sysOverridesConfig);
		config.addConfiguration(testConfig);
		config.addConfiguration(appObjectsConfig);
		config.addConfiguration(frameworkDefaultConfig);
	}
	
	/**
	 * Add a property to the configuration. If it already exists then the value
	 * stated here will be added to the configuration entry. For example, if
	 * the property:
	 * 
	 * <pre>
	 * resource.loader = file
	 * </pre>
	 * 
	 * is already present in the configuration and you call
	 * 
	 * <pre>
	 * addProperty(&quot;resource.loader&quot;, &quot;classpath&quot;)
	 * </pre>
	 * 
	 * Then you will end up with a List like the following:
	 * 
	 * <pre>
	 * ["file", "classpath"]
	 * </pre>
	 * 
	 * @param key
	 *            The key to add the property to.
	 * @param value
	 *            The value to add.
	 */
	public static synchronized void addProperty(String key, Object value) {
		config.addProperty(key, value);
	}
	
	/**
	 * Check if the configuration contains the specified key.
	 * 
	 * @param key
	 *            the key whose presence in this configuration is to be tested
	 * @return <code>true</code> if the configuration contains a value for this
	 *         key, <code>false</code> otherwise
	 */
	public static boolean containsKey(String key) {
		return config.containsKey(key);
	}
	
	/**
	 * Get a {@link BigDecimal} associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated BigDecimal if key is found and has valid format
	 */
	public static BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}
	
	/**
	 * Get a {@link BigDecimal} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated BigDecimal if key is found and has valid
	 *         format, default value otherwise.
	 */
	public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return config.getBigDecimal(key, defaultValue);
	}
	
	/**
	 * Get a {@link BigInteger} associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated BigInteger if key is found and has valid format
	 */
	public static BigInteger getBigInteger(String key) {
		return config.getBigInteger(key);
	}
	
	/**
	 * Get a {@link BigInteger} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated BigInteger if key is found and has valid
	 *         format, default value otherwise.
	 */
	public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return config.getBigInteger(key, defaultValue);
	}
	
	/**
	 * Get a boolean associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated boolean.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Boolean.
	 */
	public static boolean getBoolean(String key) {
		try {
			return config.getBoolean(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Boolean} associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated boolean if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Boolean.
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		try {
			return config.getBoolean(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a boolean associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated boolean.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Boolean.
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		try {
			return config.getBoolean(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a byte associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated byte.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Byte.
	 */
	public static byte getByte(String key) {
		try {
			return config.getByte(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Byte} associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated byte if key is found and has valid format, default
	 *         value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an object that
	 *             is not a Byte.
	 */
	public static Byte getByte(String key, Byte defaultValue) {
		try {
			return config.getByte(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a byte associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated byte.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Byte.
	 */
	public static byte getByte(String key, byte defaultValue) {
		try {
			return config.getByte(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a double associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated double.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Double.
	 */
	public static double getDouble(String key) {
		try {
			return config.getDouble(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Double} associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated double if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Double.
	 */
	public static Double getDouble(String key, Double defaultValue) {
		try {
			return config.getDouble(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a double associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated double.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Double.
	 */
	public static double getDouble(String key, double defaultValue) {
		try {
			return config.getDouble(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a float associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated float.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Float.
	 */
	public static float getFloat(String key) {
		try {
			return config.getFloat(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Float} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated float if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Float.
	 */
	public static Float getFloat(String key, Float defaultValue) {
		try {
			return config.getFloat(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a float associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated float.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Float.
	 */
	public static float getFloat(String key, float defaultValue) {
		try {
			return config.getFloat(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a int associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated int.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Integer.
	 */
	public static int getInt(String key) {
		try {
			return config.getInt(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a int associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated int.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Integer.
	 */
	public static int getInt(String key, int defaultValue) {
		try {
			return config.getInt(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get an {@link Integer} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated int if key is found and has valid format, default
	 *         value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an object that
	 *             is not a Integer.
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		try {
			return config.getInteger(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get the list of the keys contained in the configuration. The returned
	 * iterator can be used to obtain all defined keys. Note that the exact
	 * behavior of the iterator's <code>remove()</code> method is specific to
	 * a concrete implementation. It <em>may</em> remove the corresponding
	 * property from the configuration, but this is not guaranteed. In any case
	 * it is no replacement for calling <code>{@link #clearProperty(String)}</code> for this property. So it
	 * is
	 * highly recommended to avoid using the iterator's <code>remove()</code> method.
	 * 
	 * @return An Iterator.
	 */
	public static Iterator<String> getKeys() {
		return config.getKeys();
	}
	
	/**
	 * Get the list of the keys contained in the configuration that match the
	 * specified prefix. For instance, if the configuration contains the
	 * following keys:<br>
	 * <code>db.user, db.pwd, db.url, window.xpos, window.ypos</code>,<br>
	 * an invocation of <code>getKeys("db");</code><br>
	 * will return the keys below:<br>
	 * <code>db.user, db.pwd, db.url</code>.<br>
	 * Note that the prefix itself is included in the result set if there is a
	 * matching key. The exact behavior - how the prefix is actually
	 * interpreted - depends on a concrete implementation.
	 * 
	 * @param prefix
	 *            The prefix to test against.
	 * @return An Iterator of keys that match the prefix.
	 * @see #getKeys()
	 */
	public static Iterator<String> getKeys(String prefix) {
		return config.getKeys(prefix);
	}
	
	/**
	 * Get a List of strings associated with the given configuration key.
	 * If the key doesn't map to an existing object an empty List is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated List.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a List.
	 */
	public static List<Object> getList(String key) {
		try {
			return config.getList(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a List of strings associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated List of strings.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a List.
	 */
	public static List<Object> getList(String key, List<Object> defaultValue) {
		try {
			return config.getList(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a long associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated long.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Long.
	 */
	public static long getLong(String key) {
		try {
			return config.getLong(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a long associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated long.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Long.
	 */
	public static long getLong(String key, long defaultValue) {
		try {
			return config.getLong(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Long} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated long if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Long.
	 */
	public static Long getLong(String key, Long defaultValue) {
		try {
			return config.getLong(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a list of properties associated with the given configuration key.
	 * This method expects the given key to have an arbitrary number of String
	 * values, each of which is of the form <code>key=value</code>. These
	 * strings are splitted at the equals sign, and the key parts will become
	 * keys of the returned <code>Properties</code> object, the value parts
	 * become values.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated properties if key is found.
	 * @throws ConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a String/List.
	 * @throws IllegalArgumentException
	 *             if one of the tokens is
	 *             malformed (does not contain an equals sign).
	 */
	public static Properties getProperties(String key) {
		return config.getProperties(key);
	}
	
	/**
	 * Gets a property from the configuration. This is the most basic get
	 * method for retrieving values of properties. In a typical implementation
	 * of the <code>Configuration</code> interface the other get methods (that
	 * return specific data types) will internally make use of this method. On
	 * this level variable substitution is not yet performed. The returned
	 * object is an internal representation of the property value for the passed
	 * in key. It is owned by the <code>Configuration</code> object. So a caller
	 * should not modify this object. It cannot be guaranteed that this object
	 * will stay constant over time (i.e. further update operations on the
	 * configuration may change its internal state).
	 * 
	 * @param key
	 *            property to retrieve
	 * @return the value to which this configuration maps the specified key, or
	 *         null if the configuration contains no mapping for this key.
	 */
	public static Object getProperty(String key) {
		return config.getProperty(key);
	}
	
	/**
	 * Get a short associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated short.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Short.
	 */
	public static short getShort(String key) {
		try {
			return config.getShort(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a short associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated short.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Short.
	 */
	public static short getShort(String key, short defaultValue) {
		try {
			return config.getShort(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a {@link Short} associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated short if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a Short.
	 */
	public static Short getShort(String key, Short defaultValue) {
		try {
			return config.getShort(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a string associated with the given configuration key.
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated string.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an object that
	 *             is not a String.
	 */
	public static String getString(String key) {
		try {
			return config.getString(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get a string associated with the given configuration key.
	 * If the key doesn't map to an existing object, the default value
	 * is returned.
	 * 
	 * @param key
	 *            The configuration key.
	 * @param defaultValue
	 *            The default value.
	 * @return The associated string if key is found and has valid
	 *         format, default value otherwise.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an object that
	 *             is not a String.
	 */
	public static String getString(String key, String defaultValue) {
		try {
			return config.getString(key, defaultValue);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Get an array of strings associated with the given configuration key.
	 * If the key doesn't map to an existing object an empty array is returned
	 * 
	 * @param key
	 *            The configuration key.
	 * @return The associated string array if key is found.
	 * @throws ProsperoConfigConversionException
	 *             is thrown if the key maps to an
	 *             object that is not a String/List of Strings.
	 */
	public static String[] getStringArray(String key) {
		try {
			return config.getStringArray(key);
		}
		catch (ConversionException ce) {
			throw new ProsperoConfigConversionException(ce);
		}
	}
	
	/**
	 * Check if the configuration is empty.
	 * 
	 * @return <code>true</code> if the configuration contains no property, <code>false</code> otherwise.
	 */
	public static boolean isEmpty() {
		return config.isEmpty();
	}
	
	/**
	 * Set a property, this will replace any previously set values. Set values
	 * is implicitly a call to clearProperty(key), addProperty(key, value).
	 * 
	 * @param key
	 *            The key of the property to change
	 * @param value
	 *            The new value
	 */
	public static synchronized void setProperty(String key, Object value) {
		config.setProperty(key, value);
	}
	
	/**
	 * Adds a properties file to the configuration if it does not already exist.
	 * NOTE: Properties in this file will NOT override existing properties
	 * 
	 * @param filename
	 */
	public static synchronized void addPropertiesFile(String filename) {
		if (!containsPropertiesFile(filename)) {
			Configuration newProps;
			try {
				newProps = new PropertiesConfiguration(filename);
			}
			catch (ConfigurationException ce) {
				logger.error("Error opening properties file", ce);
				newProps = null;
			}
			
			if (newProps != null) {
				propertiesFileNames.add(filename);
				config.addConfiguration(newProps);
			}
		}
		else {
			logger.warn("Config already contains properties file: " + filename);
		}
	}
	
	/**
	 * Determine whether a property file as been added to the configuration
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean containsPropertiesFile(String filename) {
		return propertiesFileNames.contains(filename);
	}
}
