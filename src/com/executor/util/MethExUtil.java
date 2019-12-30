package com.executor.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.google.gson.GsonBuilder;

public class MethExUtil {

	public MethExUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void Seperator(boolean type, String Clazz, String Method) {
		String msg = null;
		if (type) {
			msg = "*EXECUTING*****************************************\n*Class Name: " + Clazz + "\n*Method Name: "
					+ Method;
		} else {
			msg = "*****************************************EXECUTION-FINISHED OF :" + Clazz + "." + Method + "\n\n\n";
		}
		System.out.println(msg);
	}

	/**
	 * I/O Seperator kindda pretty print of Inputs and Outputs
	 * 
	 * @author Nishant Tiwari
	 * @param params
	 */
	public static void Seperator(Object params) {
		String msg = null;
		if (params.getClass().isArray()) {
			msg = "*EXECUTION-INPUT: >> \n" + JsonPrinter(params)
					+ "\n=======OK! Let's Start Execution=======\n\n\n";
		} else {
			msg = "*EXECUTION-OUTPUT: >> " + params + "\n";
		}
		System.out.println(msg);
	}

	/**
	 * Stopped Implementation in Halfway because GSON().toJson() can fulfill the
	 * purpose Implemented to fetch values from an array of Fields
	 * 
	 * @author Nishant Tiwari
	 * @param ctx
	 * @param field
	 * @return HashMap of Field Values
	 * 
	 */
	public static HashMap<String, Object> fieldPrinter(Object ctx, Field[] field) {
//		System.out.println("-->FieldPrinter Class : "+ctx.getClass());
		HashMap<String, Object> fieldValues = new HashMap<>();
		for (Field f : field) {
//			System.out.println("-->FieldPrinter Field : "+f.getName() +"---"+InstanceOf(f.getType()));
			f.setAccessible(true);
			switch (InstanceOf(f.getType())) {

			case -1:
				try {
					fieldValues.put(ctx + ">>" + f.getName(), getfields(f.get(ctx)));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 0:
				try {

					fieldValues.put(ctx + ">>" + f.getName(), String.valueOf(f.get(ctx)));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 1:
				Collection<?> CollectionVal = null;
				try {
					CollectionVal = (Collection<?>) f.get(ctx);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object[] nestedCollectionFields = CollectionVal.toArray();

				for (Object arrValue : nestedCollectionFields) {
					fieldValues.put(f.getName(), getfields(arrValue));
				}

				break;

			case 2:
				Queue<?> QueueVal = null;
				try {
					QueueVal = (Queue<?>) f.get(ctx);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object[] nestedQueueFields = QueueVal.toArray();
				for (Object arrValue : nestedQueueFields) {
//					Class<?> clazz = arrValue.getClass();
//				    Field[] nestfield = clazz.getDeclaredFields();
//				    fieldValues.put(arrValue+"#"+f.getName(),fieldPrinter(arrValue,nestfield));
					fieldValues.put(arrValue + "#" + f.getName(), getfields(arrValue));
				}
				break;

			case 3:
				Map<?, ?> MapVal = null;
				try {
					MapVal = (Map<?, ?>) f.get(ctx);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (Object k : MapVal.keySet()) {
					fieldValues.put(k + "#" + f.getName(), getfields(MapVal.get(k)));
				}
//				fieldValues.put(f.getName(),MapVal);
				break;
			}

		}
		return fieldValues;
	}

	/**
	 * Just to Identify Object Type
	 * 
	 * @author Nis-H-Ant
	 * @param check Class to check Type
	 * @return int
	 */

	public static int InstanceOf(Class<?> check) {
		int instanceOF = -1;
		if (Collection.class.isAssignableFrom(check))
			instanceOF = 1;
		else if (Queue.class.isAssignableFrom(check))
			instanceOF = 2;
		else if (Map.class.isAssignableFrom(check))
			instanceOF = 3;
		else if (check.getName().startsWith("java.lang"))
			instanceOF = 0;
		return instanceOF;
	}

	/**
	 * Just for Pretty Printing in JSON Format
	 * 
	 * @author Nis-H-Ant
	 * @param object
	 * @return String in Json Format
	 */
	public static String JsonPrinter(Object object) {
		return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(object);
	}

	/**
	 * Stopped Implementation in Halfway because GSON().toJson() can fulfill the
	 * purpose Implemented to fetch Field values for nested Objects on the basis of
	 * their type till java.lang not found if Object type found in java.lang package
	 * it returns
	 * 
	 * @author NishAnt Tiwari
	 * @param object
	 * @return HashMap of Field Values
	 */
	@Deprecated
	public static HashMap<String, Object> getfields(Object object) {
		HashMap<String, Object> fieldValues = new HashMap<>();
		switch (InstanceOf(object.getClass())) {

		case -1:
			try {
				fieldValues.put(object.toString(), fieldPrinter(object, object.getClass().getDeclaredFields()));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 0:
			try {
				fieldValues.put("java.lang", String.valueOf(object));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 1:
			Collection<?> CollectionVal = null;
			try {
				CollectionVal = (Collection<?>) object;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object[] nestedCollectionFields = CollectionVal.toArray();

			for (Object arrValue : nestedCollectionFields) {
				getfields(arrValue);
			}

			break;

		case 2:
			Queue<?> QueueVal = null;
			try {
				QueueVal = (Queue<?>) object;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object[] nestedQueueFields = QueueVal.toArray();
			for (Object arrValue : nestedQueueFields) {
				getfields(arrValue);
			}
			break;

		case 3:
			Map<?, ?> MapVal = null;
			try {
				MapVal = (Map<?, ?>) object;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Object k : MapVal.keySet()) {
				getfields(MapVal.get(k));
			}
			break;
		}

		return fieldValues;
	}

}
