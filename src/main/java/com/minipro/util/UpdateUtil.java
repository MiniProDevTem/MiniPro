package com.minipro.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateUtil {
	
	public static int setValues(Object to, Object from){
		if (to == null || from == null){
			return 0;
		}
		int count = 0;
		List<Field> fields = getFields(to.getClass(), true);
		for (Field f : fields){
			try {
				Object fromValue = getFieldValue(from, f.getName());
				if (fromValue == null){
					continue;
				}
				setFieldValue(f, to, fromValue);
				count += 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public static List<Field> getFields(Class<?> clazz, boolean includeSuperClass) {
		if (clazz == null) {
			throw new IllegalArgumentException("Argument clazz cannot be null");
		}
		List<Field> fields = new ArrayList<Field>();
		if (!clazz.equals(Object.class)) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			if (includeSuperClass) {
				clazz = clazz.getSuperclass();
				fields.addAll(getFields(clazz, includeSuperClass));
			}
		}
		return fields;
	}
	
	public static Object getFieldValue(Object object, String name) {
		if (object == null) {
			return null;
		}
		Field field = getField(object.getClass(), name, true);
		if (field == null) {
			return null;
		}
		return getFieldValue(field, object);
	}
	
	public static Field getField(Class<?> clazz, String name, boolean includeSuperClass) {
		if (clazz == null) {
			throw new IllegalArgumentException("Argument clazz cannot be null");
		}
		if (name==null) {
			throw new IllegalArgumentException("Argument name cannot be null or empty");
		}
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
		}
		if (includeSuperClass && !clazz.equals(Object.class)) {
			return getField(clazz.getSuperclass(), name, includeSuperClass);
		}
		return null;
	}
	public static Object getFieldValue(Field field, Object object) {
		if (field == null) {
			throw new IllegalArgumentException("Argument field cannot be null");
		}
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void setFieldValue(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
		} catch (Exception e) {
		}
	}

}
