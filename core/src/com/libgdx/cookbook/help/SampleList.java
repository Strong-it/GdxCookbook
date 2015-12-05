package com.libgdx.cookbook.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.libgdx.cookbook.chp02.SpriteBatchSample;
import com.libgdx.cookbook.chp02.TextureAtlasSample;

/****
 * 列举出所有的SampleList，然后在第一个页面展示
 * 点击SampleName就会进入对应的例子
 *
 */
public class SampleList {
	
	@SuppressWarnings("unchecked")
	public static final List<Class< ? extends BaseScreen>> sampleList = new ArrayList<Class< ? extends BaseScreen>>(Arrays.asList(
	 				SpriteBatchSample.class,
	 				TextureAtlasSample.class
	 				));
	
	public List<String> getNames() {
		List<String> names = new ArrayList<String>(sampleList.size());
		// eclipse中如何去除警告:Class is a raw type. References to generic type Class<T> should be parameterized 
		// 解决方法1：增加编译注解@SuppressWarnings("unchecked")
		// 解决方法2：使用泛型通配符
		for (Class<?> clazz : sampleList) {
			names.add(clazz.getSimpleName());
		}
		Collections.sort(names);
		return names;
	}
	
	/**
	 *   
	 * @param 类名字符串
	 * @return 根据类名字符串返回类
	 */
	private static Class<? extends BaseScreen> forName(String name) {
		for (Class<? extends BaseScreen> clazz : sampleList) {
				if (clazz.getSimpleName().equals(name)) {
					return clazz;
				}
		}
		return null;
	}
	
	/**
	 * 
	 * @param sampleName  
	 * @return 返回类对象初始化
	 */
	public static BaseScreen newSample(String sampleName) {
		try {
			return ClassReflection.newInstance(forName(sampleName));
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
