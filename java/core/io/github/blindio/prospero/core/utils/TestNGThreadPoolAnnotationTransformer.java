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
package io.github.blindio.prospero.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class TestNGThreadPoolAnnotationTransformer implements
	IAnnotationTransformer {

    private int threadPoolSize = 1;
    private String performanceTestMethod = null;
    Logger logger = Logger
	    .getLogger(TestNGThreadPoolAnnotationTransformer.class);

    public TestNGThreadPoolAnnotationTransformer() {
	threadPoolSize = Config.getInt(
		PropertiesConstants.PERFORMANCE_TEST_THREADS, threadPoolSize);
	performanceTestMethod = Config
		.getString(PropertiesConstants.PERFORMANCE_TEST_METHOD);
    }

    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass,
	    Constructor testConstructor, Method testMethod) {
	if (performanceTestMethod != null
		&& performanceTestMethod.equalsIgnoreCase(testMethod.getName())) {
	    annotation.setThreadPoolSize(threadPoolSize);
	    annotation.setInvocationCount(threadPoolSize);
	}
    }
}
