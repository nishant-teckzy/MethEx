package com.executor;

import java.lang.reflect.Method;

import com.executor.core.Exec;
import com.executor.util.MethExUtil;

public class MethodExecutor {
	
	public static void MethodExec(Method method,Class<?> clazz,Object[] params){
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
