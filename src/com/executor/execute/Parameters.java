package com.executor.execute;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.executor.MethodExecutor;
import com.executor.core.Exec;
import com.executor.util.Constants;
import com.executor.util.MethExProperties;

/**
 * Implemented Methods For Method Execution and it's Suitable Initialization
 * Methods
 * 
 * @created 23/Dec/2019
 * @author Nishant Tiwari
 * 
 */
public class Parameters {

	// An Object Array to Store All the Parameters
	public static Object[] params = null;

	/**
	 * Implements Method's Parameter Setter
	 * @author Nishant Tiwari
	 * @param objects
	 */
	public static void set(Object... objects) {

		if (objects.length > 0) {
			params = objects;
		}
	}

	/**
	 * Implements Initialization of Method Executor Initialization
	 * 
	 * @author Nishant Tiwari
	 * @created 23/Dec/2019
	 * @see MethExProperties#getMethExProperty(String) for Fetching Configurable Properties
	 * */
	public static void Initializer() {
		
		String ClassName = null;
		if (null != MethExProperties.getInstance()
				&& null != MethExProperties.getInstance()
						.getMethExProperty(Constants.Package)) {

			Reflections reflections = new Reflections(MethExProperties
					.getInstance().getMethExProperty(Constants.Package)
					.toString(), new MethodAnnotationsScanner(),
					new FieldAnnotationsScanner());
			String ClassToScan = MethExProperties.getInstance()
					.getMethExProperty(Constants.ClassName);
			if (null != ClassToScan) {
				String[] arr = ClassToScan.split("\\.");
				System.out.println(Arrays.toString(arr));

				if (arr[arr.length - 1].trim().equals("java")) {
					ClassName = ClassToScan.substring(0,
							ClassToScan.lastIndexOf('.'));
					System.out.println(ClassName);
				} else {
					ClassName = ClassToScan;
				}

			}

			//Starting Method Scanning
			for (Method method : reflections
					.getMethodsAnnotatedWith(Exec.class)) {
				Class<?> clazz = method.getDeclaringClass();
//				System.out.println(clazz.getSimpleName()+"---"+clazz.getSimpleName().equalsIgnoreCase(ClassName.trim()));
				if (null != ClassName && clazz.getSimpleName().equalsIgnoreCase(ClassName.trim())) {
					MethodExecutor.MethodExec(method,clazz,params);
					break;
				}
				if(null == ClassName|| ClassName.equals(""))
					MethodExecutor.MethodExec(method,clazz,params);
				
				

			}

		} else {
			System.out
					.println("Please Provide Package Name in \"methex.properties\"");
		}

	}
	
	


}
