package org.prospero.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class TestNGThreadPoolAnnotationTransformer implements IAnnotationTransformer {
	
	private int threadPoolSize = 1;
	private String performanceTestMethod = null;
	Logger logger = Logger.getLogger(TestNGThreadPoolAnnotationTransformer.class);
	
	public TestNGThreadPoolAnnotationTransformer() {
		threadPoolSize = Config.getInt(PropertiesConstants.PERFORMANCE_TEST_THREADS, threadPoolSize);
		performanceTestMethod = Config.getString(PropertiesConstants.PERFORMANCE_TEST_METHOD);
	}
	
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		if (performanceTestMethod != null && performanceTestMethod.equalsIgnoreCase(testMethod.getName())) {
			annotation.setThreadPoolSize(threadPoolSize);
			annotation.setInvocationCount(threadPoolSize);
		}
	}
}
