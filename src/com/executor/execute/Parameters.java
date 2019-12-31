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
					MethodExec(method,clazz);
					break;
				}
				if(null == ClassName|| ClassName.equals(""))
					MethodExec(method,clazz);
				
				

			}

		} else {
			System.out
					.println("Please Provide Package Name in \"methex.properties\"");
		}

	}
	
	public static void MethodExec(Method method,Class<?> clazz){
		boolean typeCheck = true;
		try {
			if (method.isAnnotationPresent(Exec.class)) {
				MethExUtil.Seperator(true, clazz.getName(),
						method.getName());
				Object o = clazz.newInstance();

				if (method.getParameterCount() == params.length) {
					Class<?>[] paramType = method
							.getParameterTypes();

					for (int i = 0; i < params.length; i++) {
						if (paramType[i].isPrimitive()) {
							typeCheck = false;
							System.out
									.println("Method Params Cant be Primitives! Must use Wrappers");
							break;
						}

						if (!paramType[i].isInstance(params[i])) {
							System.out.println("["
									+ method.getName() + "] => "
									+ paramType[i] + " !~ "
									+ params[i]);
							typeCheck = false;
							break;
						}
					}
					Class<?> methodReturnType = method.getReturnType();
					
					if (typeCheck){
						System.out.println("Return Type: "+methodReturnType+"\n");
						MethExUtil.Seperator(params);
						if(methodReturnType == void.class){
							method.invoke(o, params);
						}else{
							Object ReturnType =  method.invoke(o, params);
							MethExUtil.Seperator(ReturnType);
							if(MethExUtil.InstanceOf(ReturnType.getClass()) != 0)
								System.out.println(MethExUtil.JsonPrinter(ReturnType));
							
//							Field[] field = ReturnType.getClass().getDeclaredFields();
//							System.out.println(MethExUtil.fieldPrinter(ReturnType, field));
							
//							System.out.println(MethExUtil.JsonPrinter(MethExUtil.fieldPrinter(ReturnType, field)));
							
						}
						
						
					}
					

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MethExUtil.Seperator(false, clazz.getName(),
					method.getName());
		}
	}


}
